package h04n6.json_example;

import android.app.Fragment;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * Created by hoang on 2/25/2018.
 */

public class Part1Fragment extends Fragment {
    private ImageView imageView;
    private RadioGroup radioGroup;
    private Button button_again;
    private Button button_next;
    private TextView editTextId;

    private int count; //số thứ tự của câu hỏi

    private Part1[] part1s;

    //mediaplayer dùng để chơi nhạc
    private MediaPlayer mediaPlayer;

    private MainActivity mainActivity;

    //mảng Boolean lưu lại sự đúng sai của từng câu để tổng kết lúc làm xong bài.
    private Boolean[] result;
    private int countRes;

    //mảng String lưu lại câu trả lời của người dùng
    //TODO lưu vào file JSON người dùng
    private String[] userAnswer;
    private int countUserAnser;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_part1, container, false);

        imageView = (ImageView) view.findViewById(R.id.imageView);
        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        button_again = (Button)view.findViewById(R.id.button_again);
        button_next = (Button)view.findViewById(R.id.button_next);

        editTextId = (TextView)view.findViewById(R.id.textView);

        countRes = 0;
        result = new Boolean[10];
        countUserAnser = 0;
        userAnswer = new String[10];

        count = 0; //câu hỏi thứ 1 ~ phần tử 0 của mảng part1s

        try{
            part1s = Part1.readJSONFile(mainActivity, R.raw.toeic_1);

        }catch (Exception e){
            e.printStackTrace();
        }
        button_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                again();
            }
        });
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

        setImageAndSound(part1s[count].getQuestion(), part1s[count].getSound());

        Part1[] p = new Part1[2];
        p[0] = new Part1(1, "xin chao", "helo", "a");
        p[1] = new Part1(2, "xin", "hola", "b");

        String s = CreatJSONFile.createPart1(p);
        editTextId.setText(s);

        return view;

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof MainActivity){
            this.mainActivity = (MainActivity) context;
        }
    }

    //event của nút AGAIN
    //cho người dùng nghe lại file âm thanh
    private void again(){
        setImageAndSound(part1s[count].getQuestion(), part1s[count].getSound());
    }

    //event của nút NEXT
    private void next(){
        checkResultAndgetUserAnswer();
        count++;
        stopPlaying(); //dừng nhạc ở câu cũ
        setImageAndSound(part1s[count].getQuestion(), part1s[count].getSound());
    }

    //thay đổi phần sound và image của câu hỏi tiếp theo
    //clear các sự lựa chọn ở radioButton
    private void setImageAndSound(String fileImage, String fileSound){

        //nếu đang trong chế độ "người dùng làm bài thi thật" thì các câu hỏi sẽ tự chuyển
        //sau 1 khoảng thời gian. không có nút next, và sau khi làm xong hết 1 part,
        //sẽ có 1 khoảng thời gian để người dùng lựa chọn các câu chưa chọn

        radioGroup.clearCheck();
        button_again.setEnabled(false);

        int imageId = getResources().getIdentifier(fileImage, "mipmap", mainActivity.getPackageName());
        int soundId = getResources().getIdentifier(fileSound, "raw", mainActivity.getPackageName());

        //bật image
        imageView.setImageResource(imageId);

        //bật media player
        mediaPlayer = MediaPlayer.create(mainActivity, soundId);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                button_again.setEnabled(true);
            }
        });

        //delay 2s rồi phát nhạc
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.start();
            }
        }, 2000);
    }

    //dừng sound nếu người dùng bấm next
    private void stopPlaying(){
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    //TODO kiểm tra sự đúng sai của câu rồi lưu vào mảng result
    //TODO lưu lại câu trả lời của người dùng vào mảng userAnswer
    private void checkResultAndgetUserAnswer(){
        int res = radioGroup.getCheckedRadioButtonId();
        if(res == -1){
            result[count] = false;
            userAnswer[count] = "null";
        }else{
            userAnswer[count] = String.valueOf(((RadioButton)radioGroup.getChildAt(res)).getId());
            String s = ((RadioButton)radioGroup.getChildAt(res)).getText().toString().toLowerCase();
            if(s.equals(part1s[count].getCorrect())){
                result[count] = true;
            }else {
                result[count] = false;
            }
        }
    }
}
