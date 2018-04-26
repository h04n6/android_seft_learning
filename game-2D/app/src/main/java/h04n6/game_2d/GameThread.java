package h04n6.game_2d;

/**
 * Created by hoang on 2/3/2018.
 */

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread{

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder){
        this.gameSurface = gameSurface;
        this.surfaceHolder = surfaceHolder;
    }

    @Override
    public void run(){
        long startTime = System.nanoTime();

        while(running){
            Canvas canvas = null;
            try{
                //lấy ra đối tượng canvas và khóa nó lại
                canvas = this.surfaceHolder.lockCanvas();

                //đồng bộ hóa
                synchronized (canvas){
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }catch (Exception e){
                //khong lam gi
            }finally {
                if(canvas != null){
                    //mở khóa cho canvas
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime();

            //thời gian cập nhật lại giao diện Game
            //đổi từ nanosecond sang millisecond
            long waitTime = (now - startTime)/1000000;
            if(waitTime < 10){
                waitTime = 10; //millisecond
            }
            System.out.print("Wait Time = " + waitTime);

            try{
                //ngừng chương trình một chút
                this.sleep(waitTime);
            }catch (InterruptedException e){
                //không làm gì
            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }

    public void setRunning(boolean running){
        this.running = running;
    }
}


























