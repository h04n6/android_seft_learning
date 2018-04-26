package h04n6.json_example;

import android.content.Context;
import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by hoang on 2/27/2018.
 */

public class Part2 {
    private int id;
    private String question; //tên file nhạc
    private String correct;

    public Part2(){}

    public int getId(){return this.id;}

    public String getCorrect(){return this.correct;}

    public String getQuestion(){return this.question;}

    public static Part2[] readJSONFile(Context context, int jsonId) throws IOException, JSONException{
        String jsonText = Everything.readText(context, jsonId);

        JSONObject jsonRoot = new JSONObject(jsonText);
        JSONArray jsonArray = jsonRoot.getJSONArray("part2");

        //
        Part2[] part2s = new Part2[1];
        //

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            part2s[i] = new Part2();
            part2s[i].id = object.getInt("id");
            part2s[i].question = object.getString("question");
            part2s[i].correct = object.getString("correct");
        }

        return part2s;
    }


}
