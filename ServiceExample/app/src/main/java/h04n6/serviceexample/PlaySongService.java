package h04n6.serviceexample;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class PlaySongService extends Service {

    private MediaPlayer mediaPlayer;

    public PlaySongService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        //throw new UnsupportedOperationException("Not yet implemented");
        // Service này là loại không giàng buộc (Un bounded)
        // vì vậy method này không bao giờ được gọi
        return null;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        //tạo đối tượng MediaPlayer, chơi file nhạc của bạn
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.huongdembayxa);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //chơi nhạc
        mediaPlayer.start();
        return START_NOT_STICKY; //khi tắt đa nhiệm, dừng hẳn nhạc
        //nếu là return START_STICKY thì sẽ chơi nhạc lại từ đầu
    }

    //hủy bỏ dịch vụ
    @Override
    public void onDestroy(){
        //giải phóng nguồn giữ nguồn phát nhạc
        mediaPlayer.release();
        super.onDestroy();
    }
}
