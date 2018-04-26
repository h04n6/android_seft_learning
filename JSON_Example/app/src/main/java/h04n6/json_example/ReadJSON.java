package h04n6.json_example;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hoang on 2/17/2018.
 */

public class ReadJSON {

    private int jsonId; //số của file json cần đọc, ví dụ: R.raw.toeic_1.json

    public ReadJSON(int jsonId){
        this.jsonId = jsonId;
    }

    //đọc file company.json và chuyển thành đối tượng java
    public Company[] readCompanyJSONFile(Context context) throws IOException, JSONException{
        //đọc nội dung text của file company.json
        String jsonText = readText(context, jsonId);

        //đối tượng JSONObject gốc mô tả toàn bộ tài liệu JSON
        JSONObject jsonRoot = new JSONObject(jsonText);

        int id = jsonRoot.getInt("id");
        String name = jsonRoot.getString("name");

        JSONArray jsonArray = jsonRoot.getJSONArray("websites");
        String[] websites = new String[jsonArray.length()];
        for(int i = 0; i < jsonArray.length(); i++){
            websites[i] = jsonArray.getString(i);
        }

        Address[] addresses = new Address[2];
        JSONArray jsonAddress = (JSONArray) jsonRoot.get("address");
        for(int i = 0; i < jsonAddress.length(); i++){
            JSONObject object = jsonAddress.getJSONObject(i);
            String street = object.getString("street");
            String city = object.getString("city");
            addresses[i] = new Address(street, city);
        }

        Company[] company = new Company[2];
        for(int i = 0; i < 2; i++){
            company[i] = new Company();
            company[i].setId(id);
            company[i].setName(name);
            company[i].setAdress(addresses[i]);
            company[i].setWebsites(websites);
        }

        return company;
    }

    //đọc nội dung text của một file nguồn .json
    private String readText(Context context, int resId) throws IOException{
        InputStream is = context.getResources().openRawResource(resId);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String s = null;
        while((s = br.readLine())!=null){
            sb.append(s);
            sb.append("\n");
        }
        return sb.toString();
    }
}
