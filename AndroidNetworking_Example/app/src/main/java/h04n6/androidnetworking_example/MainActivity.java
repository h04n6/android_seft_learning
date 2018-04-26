package h04n6.androidnetworking_example;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.imageView = (ImageView)this.findViewById(R.id.imageView);
        this.textView = (TextView)this.findViewById(R.id.textView);
    }

    private boolean checkIntenetConnection(){
        //lấy ra bộ quản lý kết nối
        ConnectivityManager connManager =
                (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        //thông tin mạng đang kích hoạt
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();

        if(networkInfo == null){
            Toast.makeText(this, "No default network is currently active",  Toast.LENGTH_LONG).show();
            return false;
        }

        if(!networkInfo.isConnected()){
            Toast.makeText(this, "Network is not connected", Toast.LENGTH_LONG).show();
            return false;
        }

        if(!networkInfo.isAvailable()){
            Toast.makeText(this, "Network is not available", Toast.LENGTH_LONG).show();
            return false;
        }

        Toast.makeText(this, "Network OK", Toast.LENGTH_LONG).show();
        return true;
    }

    //khi người dùng click vào nút "Download Image"
    public void downloadAndShowImage(View view){
        boolean networkOK = this.checkIntenetConnection();
        if(!networkOK){
           return;
        }
        String imageUrl ="D:\\logo.png";

        //Tạo một đối tượng làm nhiệm vụ download Image
        DownloadImageTask downloadImageTask = new DownloadImageTask(this.imageView);

        //Thực thi nhiệm vụ (truyền vào Url)
        downloadImageTask.execute(imageUrl);
    }

    //khi người dùng click vào nút "Download JSON"
    public void downloadAnhShowJSON(View view){
        boolean networkOK = this.checkIntenetConnection();
        //if(!networkOK){
        //    return;
        //}
        String JSONUrl = "http://o7planning.org/download/static/default/demo-data/company.json";

        //Tạo một đối tượng làm nhiệm vụ download JSON
        DownloadJSONTask downloadJSONTask = new DownloadJSONTask(this.textView);

        //Thực thi nhiệm vụ (truyền vào url)
        downloadJSONTask.execute(JSONUrl);
    }
}
