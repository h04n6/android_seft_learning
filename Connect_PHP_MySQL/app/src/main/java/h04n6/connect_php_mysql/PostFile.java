package h04n6.connect_php_mysql;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostFile extends AppCompatActivity {

    private ImageView imageView;
    private Button buttonPostFile;
    private int MY_REQUEST_CODE = 111;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_file);

        getView();
        pickImage();

        buttonPostFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new postfile().execute("http://192.168.1.6/okhttp/postimage.php");
            }
        });

    }

    private void getView(){
        imageView = findViewById(R.id.imageViewFile);
        buttonPostFile = findViewById(R.id.buttonSendFile);
    }

    //TODO lấy image từ trong bộ nhớ máy hiện lên imageView

    private void pickImage(){
        //bấm vào imageView để chọn ảnh
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            //kết quả trả về là đường dẫn uri
            Uri uri = data.getData();
            path = getRealPathFromUri(uri);

            //convert sang dạng bitmap để có thể hiện lên trên imageView
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public String getRealPathFromUri(Uri contentUri){
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    class postfile extends AsyncTask<String, Void, String>{

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .build();

        @Override
        protected String doInBackground(String... strings) {
            File file = new File(path);
            String content_type = getType(file.getPath()); //xác định loại file
            String file_path = file.getAbsolutePath(); //lấy đường dẫn tuyệt đối

            RequestBody file_body = RequestBody.create(MediaType.parse(content_type), file);
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("uploaded_image", file_path.substring(file_path.lastIndexOf("/") + 1), file_body )
                    .setType(MultipartBody.FORM) //dòng quan trọng
                    .build();

            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(PostFile.this, s, Toast.LENGTH_LONG).show();
            super.onPostExecute(s);
        }

        private String getType(String path) {
            String extension = MimeTypeMap.getFileExtensionFromUrl(path);
            return MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
    }
}



















