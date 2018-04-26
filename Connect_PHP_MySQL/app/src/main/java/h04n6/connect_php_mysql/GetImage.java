package h04n6.connect_php_mysql;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetImage extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonGetImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_image);

        imageView = findViewById(R.id.imageView);
        buttonGetImage = findViewById(R.id.buttonGetImage);

        buttonGetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new getimage().execute("https://o7planning.org/download/static/default/demo-data/logo.png");
            }
        });
    }

    class getimage extends AsyncTask<String, Void, byte[]>{

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build(); //không set gì nên các time out read, write, retry là mặc định.

        @Override
        protected byte[] doInBackground(String... strings) {
            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);

            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().bytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(byte[] bytes) {
            if(bytes.length > 0){
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imageView.setImageBitmap(bitmap);
            }
            super.onPostExecute(bytes);
        }
    }
}
