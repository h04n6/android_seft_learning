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

        import java.util.zip.Inflater;

/**
 * Created by h04n6 on 02/03/2018.
 */

public class Part5Fragment extends Fragment {
    private Button button_save;
    private Button button_again;
    private RadioGroup radioGroup;
    private TextView textView;
    private MainActivity mainActivity;
    private int countRes;

    private Boolean[] result;

    private Part5[] part5s;
    private int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstaceState){
        View view = inflater.inflate(R.layout.fragment_part5, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        textView = (TextView) view.findViewById(R.id.textView);
        button_again = (Button) view.findViewById(R.id.button_again);
        button_save = (Button) view.findViewById(R.id.button_next);
        result = new Boolean[30];

        try{
            part5s = Part5.readJSONFile(mainActivity, R.raw.toeic_1);
        }catch (Exception e){
            e.printStackTrace();
        }

        count = 0;
        countRes = 0;

        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        button_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        setQA(part5s[count].getId(), part5s[count].getQuestion(), part5s[count].getAnswers());

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity) context;
        }
    }

    private void next(){
        //lưu lại câu trả lời của người dùng
        int res = radioGroup.getCheckedRadioButtonId();
        String correct = part5s[count].getCorrect();

        if(res == -1){
            result[countRes] = false;
        }else{
            if(((RadioButton) mainActivity.findViewById(res)).getText().toString().toLowerCase().equals(part5s[count].getCorrect())){
                result[countRes] = true;
            }else{
                result[countRes] = false;
            }
        }
        countRes++;

        //chuyển sang câu tiếp theo
        count++;
        setQA(part5s[count].getId(), part5s[count].getQuestion(), part5s[count].getAnswers());
    }

    private void again(){

    }

    private void setQA(int id, String question, String[] answer){
        radioGroup.clearCheck();
        button_again.setEnabled(false);
        String s = String.valueOf(id) + ". " + question;
        textView.setText(s);
        for(int i = 0; i < radioGroup.getChildCount(); i++){
            ((RadioButton)radioGroup.getChildAt(i)).setText(answer[i]);
        }
    }
}
