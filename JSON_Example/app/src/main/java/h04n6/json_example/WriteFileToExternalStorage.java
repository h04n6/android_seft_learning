package h04n6.json_example;

/**
 * Created by hoang on 3/3/2018.
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class WriteFileToExternalStorage extends AppCompatActivity {

    private EditText editText;
    private TextView textView;
    private Button saveButton;
    private Button readButton;
    private Button listButton;

    private static final int REQUEST_ID_READ_PERMISSION = 100;
    private static final int REQUEST_ID_WRITE_PERMISSION = 200;

    private String fileName;

    //khởi tạo đối tượng với filename tương ứng để lưu vào bộ nhớ
    public WriteFileToExternalStorage(String fileName){
        this.fileName = fileName;
    }

    //phương thức tạo file ~ gọi từ các lớp khác
    public void askPermissionAndWriteFile() {
        boolean canWrite = this.askPermission(REQUEST_ID_READ_PERMISSION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(canWrite){
            this.writeFile();
        }
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
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                writeFile();
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
}
















