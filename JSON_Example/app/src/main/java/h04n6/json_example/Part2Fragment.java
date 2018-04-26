package h04n6.json_example;

import android.app.Fragment;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Created by hoang on 2/27/2018.
 */

public class Part2Fragment extends Fragment {
    private RadioGroup radioGroup;
    private Button button_again;
    private Button button_next;
    private TextView textView;

    private int count; //số thứ tự của câu hỏi

    private Part2[] part2s;

    //mediaplayer dùng để chơi nhạc
    private MediaPlayer mediaPlayer;

    private MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_part2, container, false);

        radioGroup = (RadioGroup) view.findViewById(R.id.radioGroup);
        button_again = (Button)view.findViewById(R.id.button_again);
        button_next = (Button)view.findViewById(R.id.button_next);
        textView = (TextView) view.findViewById(R.id.textView);

        count = 0; //câu hỏi thứ 1 ~ phần tử 0 của mảng part2s

        try{
            part2s = Part2.readJSONFile(mainActivity, R.raw.toeic_1);

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

        setSound(part2s[count].getQuestion());

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
        setSound(part2s[count].getQuestion());
    }

    //event của nút NEXT
    private void next(){
        String answer = "";
        if(radioGroup.getCheckedRadioButtonId() == -1){
            //no radiobutton is checked
            return;
        }else{
            //there is one radiobutton is checked
            int radioButtonId = radioGroup.getCheckedRadioButtonId();
            answer = ((RadioButton) mainActivity.findViewById(radioButtonId)).getText().toString().toLowerCase();
        }
        //sự kiện chọn đáp án đúng hoặc đáp án sai
        if(answer.equals(part2s[count].getCorrect())){
            Toast.makeText(mainActivity, "Correct answer", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(mainActivity, "Uncorrect", Toast.LENGTH_LONG).show();
        }
        count++;
        stopPlaying(); //dừng nhạc ở câu cũ
        setSound(part2s[count].getQuestion());
    }

    //thay đổi phần sound và image của câu hỏi tiếp theo
    //clear các sự lựa chọn ở radioButton
    private void setSound(String fileSound){

        //nếu đang trong chế độ "người dùng làm bài thi thật" thì các câu hỏi sẽ tự chuyển
        //sau 1 khoảng thời gian. không có nút next, và sau khi làm xong hết 1 part,
        //sẽ có 1 khoảng thời gian để người dùng lựa chọn các câu chưa chọn

        radioGroup.clearCheck();
        button_again.setEnabled(false);

        int soundId = getResources().getIdentifier(fileSound, "raw", mainActivity.getPackageName());

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
}
