package h04n6.json_example;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * Created by hoang on 3/2/2018.
 */

public class Part6Fragment extends Fragment{
    private TextView textViewBanner;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private Button button_again;
    private Button butotn_next;

    private MainActivity mainActivity;
    private Boolean[] result;
    private int countRes;
    private int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_part6, container, false);

        textViewBanner = (TextView) view.findViewById(R.id.textViewBanner);
        textView1 = (TextView)view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        radioGroup1 = (RadioGroup) view.findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) view.findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) view.findViewById(R.id.radioGroup3);

        //
        Part3[] part6s = new Part3[1];
        //

        countRes = 0;
        count = 0;
        setQA(part6s[count].getId(), part6s[count].getQuestions(), part6s[count].getAnswers()
                , part6s[count].getCorrects());

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity) context;
        }
    }

    public void setQA(int[] id, String[] question, String[] answer, String[] correct){
        textViewBanner.setText("Question " + String.valueOf(id[0]) + " - " +
                String.valueOf(id[2]) + " reference to ");
        textView1.setText(question[0]);
        textView1.setText(question[1]);
        textView1.setText(question[2]);

        for(int i = 0; i < radioGroup1.getChildCount(); i++){
            ((RadioButton)radioGroup1.getChildAt(i)).setText(answer[i]);
        }

        for(int i = 0; i < radioGroup2.getChildCount(); i++){
            ((RadioButton)radioGroup2.getChildAt(i)).setText(answer[i+4]);
        }

        for(int i = 0; i < radioGroup2.getChildCount(); i++){
            ((RadioButton)radioGroup2.getChildAt(i)).setText(answer[i+8]);
        }
    }
}


































