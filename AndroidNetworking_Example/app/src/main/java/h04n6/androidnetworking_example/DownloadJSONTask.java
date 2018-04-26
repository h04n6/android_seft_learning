package h04n6.androidnetworking_example;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by hoang on 2/19/2018.
 */

// Một nhiệm vụ với tham số đầu vào String, và kết quả trả về là String.
public class DownloadJSONTask
        // AsyncTask<Params, Progress, Result>
        extends AsyncTask<String, Void, String> {

    private TextView textView;

    public DownloadJSONTask(TextView textView)  {
        this.textView= textView;
    }

    @Override
    protected String doInBackground(String... params) {
        String textUrl = params[0];

        StringBuilder sb = new StringBuilder();

        InputStream in = null;
        BufferedReader br= null;
        try {
            URL url = new URL(textUrl);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();

            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if (resCode == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
                br= new BufferedReader(new InputStreamReader(in));
                String s= null;
                while((s= br.readLine())!= null) {
                    sb.append(s);
                    sb.append("\n");
                }
                return sb.toString();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(br);
        }
        return sb.toString();
    }


    // Khi nhiệm vụ hoàn thành, phương thức này sẽ được gọi.
    // Download thành công, update kết quả lên giao diện.
    @Override
    protected void onPostExecute(String result) {
        if(result  != null){
            this.textView.setText(result);
        } else{
            Log.e("MyMessage", "Failed to fetch data!");
        }
    }
}































