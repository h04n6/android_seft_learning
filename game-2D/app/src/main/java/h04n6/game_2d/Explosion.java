package h04n6.game_2d;

/**
 * Created by hoang on 2/4/2018.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Explosion extends GameObject{
    private int rowIndex = 0; //chỉ số hàng trong ảnh to (5 hàng)
    private int colIndex = -1; //chỉ số cột trong ảnh to(5 cột)

    private boolean finish = false;
    private GameSurface gameSurface;

    public Explosion(GameSurface gameSurface, Bitmap image, int x, int y){
        super(image, 5, 5, x, y);
        this.gameSurface = gameSurface;
    }

    //lấy ra lần lượt các ảnh nhỏ từ ảnh lớn
    public void update(){
        this.colIndex ++;

        //chơi file nhạc explosion.wav
        //điều kiện: khi bắt đầu chạy hình nhỏ đầu tiên (hàng 0 cột 0) thì phát nhạc
        if(this.colIndex==0 && this.rowIndex==0){
            this.gameSurface.playSoundExplosion();
        }

        if(this.colIndex >= this.colCount){
            this.colIndex = 0;
            this.rowIndex++;
            if(this.rowIndex >= this.rowCount){
                this.finish = true;
            }
        }
    }

    public void draw(Canvas canvas){
        if(!finish){
            Bitmap bitmap = this.createSubImageAt(rowIndex, colIndex);
            canvas.drawBitmap(bitmap, this.x, this.y, null);
        }
    }

    public boolean isFinish(){
        return finish;
    }

}
