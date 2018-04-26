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
 * Created by hoang on 2/25/2018.
 */

public class Part1 {
    private int id;
    private String sound;
    private String question;
    private String correct;

    public Part1 (){}

    public Part1(int id, String sound, String question, String correct){
        this.id = id;
        this.sound = sound;
        this.question = question;
        this.correct = correct;
    }

    public int getId(){
        return this.id;
    }

    public String getCorrect(){
        return this.correct;
    }

    public String getSound(){
        return this.sound;
    }

    public String getQuestion(){
        return this.question;
    }

    //đọc file company.json và chuyển thành đối tượng java
    //jsonId là id của file json. ví dụ: R.raw.toeic_1.json)
    public static Part1[] readJSONFile(Context context, int jsonId) throws IOException, JSONException {
        //đọc nội dung text của file company.json
        String jsonText = Everything.readText(context, jsonId);

        //đối tượng JSONObject gốc mô tả toàn bộ tài liệu JSON
        JSONObject jsonRoot = new JSONObject(jsonText);
        JSONArray jsonArray = jsonRoot.getJSONArray("part1");

        //
        Part1[] p = new Part1[3]; //part 1 có 10 câu hỏi ~ đẻ ý dòng này
        //

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            p[i] = new Part1();
            p[i].id = object.getInt("id");
            p[i].question = object.getString("question");
            p[i].sound = object.getString("sound");
            p[i].correct = object.getString("correct");
        }

        return p;
    }
}
