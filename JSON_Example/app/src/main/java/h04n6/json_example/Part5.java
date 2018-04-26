package h04n6.json_example;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by h04n6 on 02/03/2018.
 */

public class Part5 {
    private int id;
    private String question;
    private String[] answers;
    private String correct;

    public int getId(){return id;}
    public String getQuestion(){return question;}
    public String getCorrect(){return correct;}
    public String[] getAnswers(){return answers;}


    public Part5(){
    }

    public static Part5[] readJSONFile(Context context, int jsonId) throws IOException, JSONException{
        String jsonText = Everything.readText(context, jsonId);

        JSONObject jsonRoot = new JSONObject(jsonText);
        JSONArray jsonArray = jsonRoot.getJSONArray("part5");

        //
        Part5[] part5s = new Part5[2];
        //

        for(int i = 0; i < jsonArray.length(); i++){
            JSONObject object= jsonArray.getJSONObject(i);
            part5s[i] = new Part5();
            part5s[i].id = object.getInt("id");
            part5s[i].question = object.getString("question");
            part5s[i].correct = object.getString("correct");
            JSONArray jsonArrayAnswer = object.getJSONArray("answer");
            for(int k = 0; k < jsonArrayAnswer.length(); k++){
                part5s[i].answers[k] = jsonArrayAnswer.getString(i);
            }
        }

        return part5s;
    }
}
