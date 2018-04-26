package h04n6.androidnetworking_example;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by hoang on 2/19/2018.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap>{
    private ImageView imageView;

    public DownloadImageTask(ImageView imageView){
        this.imageView = imageView;
    }

    @Override
    protected Bitmap doInBackground(String... params){
        String imageUrl = params[0];

        InputStream in = null;
        try{
            URL url = new URL(imageUrl);
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();

            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            int resCode = httpConn.getResponseCode();

            if(resCode == HttpURLConnection.HTTP_OK){
                in = httpConn.getInputStream();
            }else{
                return null;
            }

            Bitmap bitmap = BitmapFactory.decodeStream(in);
            return bitmap;
        }catch (Exception e){

        }finally {
            IOUtils.closeQuietly(in);
        }

        return null;
    }

    //khi nhiệm vụ hoàn thành, phương thức này sẽ được gọi
    //download thành công, update kết quả lên giao diện
    @Override
    protected void onPostExecute(Bitmap result){
        //if(result != null){
            try{
                this.imageView.setImageBitmap(result);
            }
            catch (Exception e){
                Log.e("My App", e.toString());
            }

        //}else{
        //    Log.e("My message", "Failed to fetch data!");
        //}
    }
}
