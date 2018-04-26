package h04n6.json_example;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by hoang on 2/27/2018.
 */

public class Part3 {
    private int[] id;
    private String sound;
    private String[] questions;
    private String[] answers;
    private String[] corrects;

    public Part3(){}

    public String getSound(){return this.sound;}
    public String[] getQuestions(){return this.questions;}
    public String[] getAnswers(){return this.answers;}
    public String[] getCorrects(){return this.corrects;}
    public int[] getId(){return this.id;}

    public static Part3[] readJSONFile(Context context, int jsonId) throws IOException, JSONException{
        String jsonText = Everything.readText(context, jsonId);
        JSONObject jsonRoot = new JSONObject(jsonText);
        JSONArray jsonArray = jsonRoot.getJSONArray("part3");

        //
        Part3[] part3s = new Part3[1];
        //

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject object = jsonArray.getJSONObject(i);
            JSONArray jsonArrayId = object.getJSONArray("id");
            JSONArray jsonArrayQuestions = object.getJSONArray("question");
            JSONArray jsonArrayAnswers = object.getJSONArray("answer");
            JSONArray jsonArrayCorrect = object.getJSONArray("correct");

            part3s[i] = new Part3();
            part3s[i].id = new int[jsonArrayId.length()];
            part3s[i].answers = new String[jsonArrayAnswers.length()];
            part3s[i].corrects = new String[jsonArrayCorrect.length()];
            part3s[i].questions = new String[jsonArrayQuestions.length()];

            for(int k = 0; k < jsonArrayId.length(); k++){
                part3s[i].id[k] = jsonArrayId.getInt(k);
            }

            part3s[i].sound = object.getString("sound");

            //lấy ra dữ liệu từ các mảng (array) trong file JSON

            for(int k = 0; k < jsonArrayQuestions.length(); k++){
                part3s[i].questions[k] = jsonArrayQuestions.getString(k);
            }

            for(int k = 0; k < jsonArrayAnswers.length(); k++){
                part3s[i].answers[k] = jsonArrayAnswers.getString(k);
            }

            for(int k = 0; k < jsonArrayCorrect.length(); k++){
                part3s[i].corrects[k] = jsonArrayCorrect.getString(k);
            }
        }
        return part3s;
    }
}






























