package h04n6.externalstorage_example;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.pm.PathPermission;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.os.EnvironmentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private Button saveButton;
    private Button readButton;
    private Button listButton;

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private final String fileName = "note.json";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) this.findViewById(R.id.editText);
        textView = (TextView) this.findViewById(R.id.textView);

        saveButton = (Button) this.findViewById(R.id.button_save);
        readButton = (Button) this.findViewById(R.id.button_read);
        listButton = (Button) this.findViewById(R.id.button_list);

        saveButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndWriteFile();
            }
        });

        readButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                askPermissionAndReadFile();
            }
        });

        listButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                listExternalStorages();
            }
        });
    }

    private void askPermissionAndReadFile() {
        /*boolean canRead = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE);
        if(canRead){
            this.readFile();
        }*/
        this.readFile();
    }

    private void askPermissionAndWriteFile() {
        /*boolean canWrite = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //if(canWrite){
            this.writeFile();
       }*/

        this.writeFile();
    }

    //với android level >= 23 bạn phải hỏi người dùng cho phép các quyền với thiết bị
    //chẳng hạn đọc/ghi dữ liệu vào thiết bị
    private boolean askPermission(int requestId, String permissionName){
        if(Build.VERSION.SDK_INT >=23){
            //kiểm tra quyền
            int permission = ActivityCompat.checkSelfPermission(this, permissionName);
            if(permission != PackageManager.PERMISSION_GRANTED){
                //nếu không có quyền cần nhắc người dùng cho phép
                this.requestPermissions(new String[]{permissionName}, requestId);
            }
            return false;
        }
        return true;
    }

    //khi yêu cầu hỏi người dùng được trả về (chấp nhận hoặc không chấp nhận)
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //nếu yêu cầu bị hủy, mảng kết quả trả về là rỗng
        if(grantResults.length > 0){
            switch (requestCode){
                case REQUEST_ID_READ_PERMISSION:{
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        readFile();
                    }
                }

                case REQUEST_ID_WRITE_PERMISSION:{
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                        writeFile();
                    }
                }
            }
        }else{
            Toast.makeText(getApplicationContext(), "Permission Canceled!", Toast.LENGTH_LONG).show();
        }
    }

    private void writeFile(){
        //thư mục gốc của SD card
        // ==> /storage/emulated/0
        File extStore = Environment.getExternalStorageDirectory();
        // ==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i("ExternalStorageDemo", "Save to: " + path);

        String data = editText.getText().toString();

        try{
            File myFile = new File(path);
            myFile.createNewFile();
            FileOutputStream fOut = new FileOutputStream(myFile);
            OutputStreamWriter myOutWritter = new OutputStreamWriter(fOut);
            myOutWritter.append(data);
            myOutWritter.close();
            fOut.close();

            Toast.makeText(getApplicationContext(), fileName + " saved", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void readFile(){
        //thư mục gốc của SD card
        // ==> /storage/emulated/0
        File extStore = Environment.getExternalStorageDirectory();
        //==> /storage/emulated/0/note.txt
        String path = extStore.getAbsolutePath() + "/" + fileName;
        Log.i("External Storage Demo", "Read file: " + path);

        String s = "";
        String fileContent = "";
        try{
            File myFile = new File(path);
            FileInputStream fIn = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader(new InputStreamReader(fIn));

            while((s = myReader.readLine()) != null){
                fileContent += s + "\n";
            }

            myReader.close();
            this.textView.setText(fileContent);
        }catch (Exception e){
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(), fileContent, Toast.LENGTH_LONG).show();
    }

    private void listExternalStorages(){
        StringBuilder sb = new StringBuilder();

        sb.append("Data Directory: ").append("\n - ")
                .append(Environment.getDataDirectory().toString()).append("\n");

        sb.append("Download Cache Directory").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("External Storage State: ").append("\n - ")
                .append(Environment.getExternalStorageState().toString()).append("\n");

        sb.append("External Storage Directory: ").append("\n - ")
                .append(Environment.getExternalStorageDirectory().toString()).append("\n");

        sb.append("Is External Storage Emulated?: ").append("\n - ")
                .append(Environment.isExternalStorageEmulated()).append("\n");

        sb.append("Is External Storage Removable?: ").append("\n - ")
                .append(Environment.isExternalStorageRemovable()).append("\n");

        sb.append("External Storage Public Directory (Music): ").append("\n - ")
                .append(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).toString()).append("\n");

        sb.append("Download Cache Directory: ").append("\n - ")
                .append(Environment.getDownloadCacheDirectory().toString()).append("\n");

        sb.append("Root Directory: ").append("\n - ")
                .append(Environment.getRootDirectory().toString()).append("\n");

        Log.i("External Storage Demo", sb.toString());
        this.textView.setText(sb.toString());
    }
}
















