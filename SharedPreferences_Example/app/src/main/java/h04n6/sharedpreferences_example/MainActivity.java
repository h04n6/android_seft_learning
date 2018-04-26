package h04n6.sharedpreferences_example;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private SeekBar seekBarSound;
    private SeekBar seekBarBrightness;

    private RadioGroup radioGroupDiffLevel;
    private RadioButton radioButtonEasy;
    private RadioButton radioButtonMedium;
    private RadioButton radioButtonHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.seekBarBrightness = (SeekBar) this.findViewById(R.id.seekBar_brightness);
        this.seekBarSound = (SeekBar) this.findViewById(R.id.seekBar_sound);

        this.radioGroupDiffLevel = (RadioGroup) this.findViewById(R.id.radioGroup_diffLevel);
        this.radioButtonEasy = (RadioButton) this.findViewById(R.id.radioButton_easy);
        this.radioButtonMedium = (RadioButton) this.findViewById(R.id.radioButton_medium);
        this.radioButtonHard = (RadioButton) this.findViewById(R.id.radioButton_hard);

        //hiển thị thiết lập đã sét đặt trong lần chơi trước
        this.loadGameSetting();
    }

    private void loadGameSetting() {
        //mode private để cho các ứng dụng khác không truy cập được vào thiết đặt của ứng dụng này
        SharedPreferences sharedPreferences = this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);
        if(sharedPreferences!= null){
            int brightness = sharedPreferences.getInt("brightness", 90);
            int sound = sharedPreferences.getInt("sound", 95);
            int checkedRadioButtonId = sharedPreferences.getInt("checkedRadioButtonId", R.id.radioButton_medium);

            this.seekBarSound.setProgress(sound);
            this.seekBarBrightness.setProgress(brightness);
            this.radioGroupDiffLevel.check(checkedRadioButtonId);

        }else{
            this.radioGroupDiffLevel.check(R.id.radioButton_medium);
            Toast.makeText(getApplicationContext(), "Use the default game setting", Toast.LENGTH_LONG).show();
        }
    }

    //được gọi khi người dùng nhấn vào nút Save
    public void doSave(View view){
        //file chia sẻ sử dụng nội bộ ứng dụng, hoặc các ứng dụng được chia sẻ cùng User
        SharedPreferences sharedPreferences = this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("brightness", this.seekBarBrightness.getProgress());
        editor.putInt("sound", this.seekBarSound.getProgress());

        //ID của radioButton đang được chọn
        int checkedRadioButtonId = radioGroupDiffLevel.getCheckedRadioButtonId();

        editor.putInt("checkedRadioButtonId", checkedRadioButtonId);

        //Save
        editor.apply();

        Toast.makeText(getApplicationContext(), "Game Setting saved", Toast.LENGTH_LONG);
    }
}
































