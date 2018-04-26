package h04n6.network;

/**
 * Created by hoang on 3/3/2018.
 */

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// Một nhiệm vụ với tham số đầu vào String, và kết quả trả về là String.
public class DownloadJsonTask
        // AsyncTask<Params, Progress, Result>
        extends AsyncTask<String, Void, String> {

    private String resStr;

    public String getResStr(){return resStr;}

    public DownloadJsonTask(){};

    @Override
    protected String doInBackground(String... params) {
        String textUrl = params[0];

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

                StringBuilder sb= new StringBuilder();
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
        return null;
    }


    // Khi nhiệm vụ hoàn thành, phương thức này sẽ được gọi.
    // Download thành công, update kết quả lên giao diện.
    @Override
    protected void onPostExecute(String result) {
        if(result  != null){
            resStr = result;
        } else{
            Log.e("MyMessage", "Failed to fetch data!");
        }
    }
}