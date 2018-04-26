package h04n6.connect_php_mysql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetURL extends AppCompatActivity {

    private Button buttonURL;
    private EditText editTextURL;
    private TextView textViewURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_url);

        buttonURL = (Button) findViewById(R.id.buttonCheck);
        editTextURL = (EditText) findViewById(R.id.editTextURL);
        textViewURL = (TextView) findViewById(R.id.textViewURL);

        buttonURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Geturll().execute("http://" + editTextURL.getText().toString().trim());
            }
        });

    }

    class Geturll extends AsyncTask<String, String, String>{
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS) //mặc định là 10s nếu k cài đặt
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true) //khi bị kết nối yếu sẽ cố gắng kết nối lại
                .build();

        @Override
        protected String doInBackground(String... strings) {
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);

            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                //return response.body().string();
                String jsonData = response.body().string();
                try{
                    JSONObject jsonObject = new JSONObject(jsonData);
                    JSONArray jsonArray = jsonObject.getJSONArray("websites");
                    StringBuilder res = new StringBuilder();
                    for(int i = 0; i < jsonArray.length(); i++){
                        //JSONObject object = jsonArray.getJSONObject(i);
                        res.append(jsonArray.getString(i));
                        res.append("\n");
                    }
                    return res.toString();
                }catch (JSONException jsonE){
                    jsonE.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if(!s.equals("")){
                textViewURL.append(s);
            }else{
                Toast.makeText(GetURL.this, "Your url is errored", Toast.LENGTH_LONG).show();
            }

            super.onPostExecute(s);
        }
    }
}
