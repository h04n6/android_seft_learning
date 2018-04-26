package h04n6.json_example;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by hoang on 2/28/2018.
 */

public class Part3Fragment extends Fragment {

    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;
    private RadioGroup radioGroup3;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private Button button_again;
    private Button button_next;

    private MediaPlayer mediaPlayer;

    private MainActivity mainActivity;

    private Boolean[] result;
    private int countRes;//số đếm phần tử của mảng result

    private int count;//số đếm phần tử của mảng JSONArray "part3"
                        //mỗi 1 count là 1 file sound ~ 3 câu hỏi ~ 4 câu trả lời ~ 4 đáp án
    private int questionId;
    private Part3[] part3s;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_part3, container, false);

        radioGroup1 = (RadioGroup) view.findViewById(R.id.radioGroup1);
        radioGroup2 = (RadioGroup) view.findViewById(R.id.radioGroup2);
        radioGroup3 = (RadioGroup) view.findViewById(R.id.radioGroup3);
        textView1 = (TextView) view.findViewById(R.id.textView1);
        textView2 = (TextView) view.findViewById(R.id.textView2);
        textView3 = (TextView) view.findViewById(R.id.textView3);
        button_again = (Button) view.findViewById(R.id.button_again);
        button_next = (Button) view.findViewById(R.id.button_next);
        result = new Boolean[30]; //vì có 30 câu nên có 30 kết quả

        try{
            part3s = Part3.readJSONFile(mainActivity, R.raw.toeic_1);
        }catch (Exception e){
            e.printStackTrace();
        }

        count = 0;
        countRes = 0;
        questionId = 41;

        button_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        setSoundAndQA(part3s[count].getSound(),part3s[count].getId(), part3s[count].getQuestions(),
                part3s[count].getAnswers());

        return view;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity) context;
        }
    }

    //event của button_again: nghe lại file sound của câu hỏi
    private void again(){
        setSoundAndQA(part3s[count].getSound(),part3s[count].getId(), part3s[count].getQuestions(),
                part3s[count].getAnswers());
    }

    //event của button_next : chuyển câu hỏi
    private void next(){
        //dừng nhạc
        stopSound();

        int res1 = radioGroup1.getCheckedRadioButtonId();
        int res2 = radioGroup2.getCheckedRadioButtonId();
        int res3 = radioGroup3.getCheckedRadioButtonId();
        String[] correct = part3s[count].getCorrects();

        //lưu đáp án của người dùng vào file Json khác.
        //Khi người dùng muốn xem lại câu hỏi sai, ta lấy dữ liệu từ file Json, sau đó kết hợp với id
        //câu hỏi và mảng result để hiển thị.
        //id - 40 = số thứ tự của phần tử tương ứng trong mảng result

        //Ta đang làm việc theo TUẦN TỰ
        //check kết quả cho vào mảng result
        //sai hoặc chưa chọn đáp án nào trả về false
        //đúng thì trả về true
        if(res1 == -1){
            result[countRes] = false;
        }else {
            if(((RadioButton) mainActivity.findViewById(res1)).getText().toString().toLowerCase().equals(correct[0])){
                result[countRes] = true;
            }
            else{
                result[countRes] = false;
            }
        }
        countRes++;

        if(res2 == -1){
            result[countRes] = false;
        }else {
            if(((RadioButton) mainActivity.findViewById(res2)).getText().toString().toLowerCase().equals(correct[1])){
                result[countRes] = true;
            }
            else{
                result[countRes] = false;
            }
        }
        countRes++;

        if(res3 == -1){
            result[countRes] = false;
        }else {
            if(((RadioButton) mainActivity.findViewById(res3)).getText().toString().toLowerCase().equals(correct[2])){
                result[countRes] = true;
            }
            else{
                result[countRes] = false;
            }
        }
        countRes++;

        //tăng chỉ số ~ chuyển sang file sound ~ bộ câu hỏi tiếp theo
        count++;
        setSoundAndQA(part3s[count].getSound(),part3s[count].getId(), part3s[count].getQuestions(),
                part3s[count].getAnswers());

    }

    //thiết lập
    private void setSoundAndQA(String fileSound, int[] id, String[] questions, String[] answers){
        radioGroup1.clearCheck();
        radioGroup2.clearCheck();
        radioGroup3.clearCheck();
        button_again.setEnabled(false);

        int soundId = getResources().getIdentifier(fileSound, "raw", mainActivity.getPackageName());
        mediaPlayer = MediaPlayer.create(mainActivity, soundId);
        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                button_again.setEnabled(true);
            }
        });

        textView1.setText(String.valueOf(id[0]) + ". " + questions[0]);
        textView2.setText(String.valueOf(id[1]) + ". " + questions[1]);
        textView3.setText(String.valueOf(id[2]) + ". " + questions[2]);

        for(int i = 0; i < radioGroup1.getChildCount(); i++){
            ((RadioButton) radioGroup1.getChildAt(i)).setText(answers[i]);
        }

        for(int i = 0; i < radioGroup2.getChildCount(); i++){
            ((RadioButton) radioGroup2.getChildAt(i)).setText(answers[i+4]);
        }

        for(int i = 0; i < radioGroup3.getChildCount(); i++){
            ((RadioButton) radioGroup3.getChildAt(i)).setText(answers[i+8]);
        }
    }

    //dừng nhạc khi người dùng bấm nút next
    private void stopSound(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
































