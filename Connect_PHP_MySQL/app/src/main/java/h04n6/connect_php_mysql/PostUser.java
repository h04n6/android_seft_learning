package h04n6.connect_php_mysql;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostUser extends AppCompatActivity {

    private EditText editTextUser, editTextPassword;
    private Button buttonPostUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_user);

        getView();

        buttonPostUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUser.getText().toString().trim();
                String passwpord = editTextPassword.getText().toString().trim();
                new PostToServer(username, passwpord).execute("http://192.168.1.6/okhttp/postuser.php");
            }
        });
    }

    private void getView(){
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextUser = findViewById(R.id.editTextUser);
        buttonPostUser = findViewById(R.id.buttonPostUser);
    }

    class PostToServer extends AsyncTask<String, Void, String>{

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();
        String user, password;

        public PostToServer(String user, String password) {
            this.user = user;
            this.password = password;
        }

        @Override
        protected String doInBackground(String... strings) {
            RequestBody requestBody = new MultipartBody.Builder()
                    .addFormDataPart("username", user)
                    .addFormDataPart("password", password)
                    .setType(MultipartBody.FORM) //chương trình tự chuyển đổi Type với dữ liệu mà mình đưa vào
                    .build();
            Request request = new Request.Builder()
                    .url(strings[0])
                    .post(requestBody)
                    .build();

            //nhận lại dữ liệu đã xử lý
            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        //TODO muốn gán dữ liệu lên view thì gán ở trong hàm onPostExecute.
        //TODO nếu gán ở hàm doInBackground thì sẽ lỗi
        @Override
        protected void onPostExecute(String s) {
            Log.d("AAA", s);
            super.onPostExecute(s);
        }
    }
}

























