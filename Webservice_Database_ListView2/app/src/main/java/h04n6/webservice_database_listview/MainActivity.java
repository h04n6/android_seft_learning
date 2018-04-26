package h04n6.webservice_database_listview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String urlGetData = "http://192.168.1.5/nckh/getdata.php";

    ListView listViewSinhVien;
    ArrayList<SinhVien> arraySinhVien;
    SinhVienAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewSinhVien = (ListView) findViewById(R.id.listViewSinhVien);
        arraySinhVien = new ArrayList<>();
        adapter = new SinhVienAdapter(this, R.layout.dong_sinh_vien, arraySinhVien);
        listViewSinhVien.setAdapter(adapter);

        GetData(urlGetData);
        if(arraySinhVien.isEmpty()){
            Toast.makeText(this, "rỗng", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "không rỗng", Toast.LENGTH_SHORT).show();
        }

    }

    private void GetData(String url){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>(){
                    @Override
                    public void onResponse(JSONArray response) {
                        //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                        for(int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                arraySinhVien.add(new SinhVien(
                                        object.getString("image"),
                                        object.getString("sound"),
                                        object.getString("correct")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }
}
