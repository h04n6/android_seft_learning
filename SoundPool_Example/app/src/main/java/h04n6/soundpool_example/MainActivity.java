package h04n6.soundpool_example;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private SoundPool soundPool;

    private AudioManager audioManager;

    //số luồng âm thanh phát ra tối đa
    private static final int MAX_STREAMS = 5;

    //chọn loại luồng âm thanh để phát nhạc
    private static final int streamType = AudioManager.STREAM_MUSIC;

    private boolean loaded;

    private int soundIdDestroy;
    private int soundIdGun;
    private float volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //đối tượng audioManager sử dụng để điều chỉnh âm lượng
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //chỉ số âm lượng hiện tại của loại luồng nhạc cụ thể (streamType)
        float currentVolumeIndex = (float) audioManager.getStreamVolume(streamType);

        //chỉ số âm lượng max của loại luồng nhạc cụ thể (streamType)
        float maxVolumeIndex = (float) audioManager.getStreamMaxVolume(streamType);

        //âm lượng (0 --> 1)
        this.volume = currentVolumeIndex / maxVolumeIndex;

        //cho phép thay đổi âm lượng các luồng kiểu "streamType" bằng các nút điều khiển của phần cứng
        this.setVolumeControlStream(streamType);

        //với phiên bản Android SDK >= 21
        if(Build.VERSION.SDK_INT >= 21){
            AudioAttributes audioAttrib = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttrib).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        //với phiên bản Android SDK < 21
        else{
            this.soundPool = new SoundPool(MAX_STREAMS, streamType, 0);
        }

        //sự kiện soundPool đã được tải lên bộ nhớ thành công
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                loaded = true;
            }
        });

        //tải file nhạc tiếng vật thể bị phá hủy (destroy.wav) vào SoundPool
        this.soundIdDestroy = this.soundPool.load(this, R.raw.destroy, 1);
        this.soundIdGun = this.soundPool.load(this, R.raw.gun, 1);
    }

    //Khi người dùng nhấn vào button "Gun"
    public void playSoundGun(View view){
        if(loaded){
            float leftVolumn = volume;
            float rightVolumn = volume;
            //phát âm thanh tiếng súng, trả về id của luồng mới phát ra
            int streamId = this.soundPool.play(this.soundIdGun, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

    //khi người dùng nhấn vòa button "Destroy"
    public void playSoundDestroy(View view){
        if(loaded){
            float leftVolumn = volume;
            float rightVolumn = volume;
            //phát âm thanh tiếng súng, trả về id của luồng mới phát ra
            int streamId = this.soundPool.play(this.soundIdDestroy, leftVolumn, rightVolumn, 1, 0, 1f);
        }
    }

}


























