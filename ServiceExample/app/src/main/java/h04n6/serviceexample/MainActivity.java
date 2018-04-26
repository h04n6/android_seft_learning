package h04n6.serviceexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //method này được gọi khi người dùng Click vào nút Start
    public void playSong(View view){
        //tạo ra một đối tượng Intent cho một dịch vụ (PlaySongService)
        Intent myIntent = new Intent(MainActivity.this, PlaySongService.class);
        //gọi phương thức startService (Truyền vào đối tượng Intent)
        this.startService(myIntent);
    }

    //method này được gọi khi người dùng Click vào nút Stop
    public void stopSong(View view){
        //tạo ra một đối tượng intent
        Intent myIntent = new Intent(this, PlaySongService.class);
        this.stopService(myIntent);
    }

}
