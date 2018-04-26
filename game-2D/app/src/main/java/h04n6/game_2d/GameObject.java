package h04n6.game_2d;

/**
 * Created by hoang on 2/3/2018.
 */

import android.graphics.Bitmap;

public class GameObject {
    protected Bitmap image;

    protected final int rowCount;
    protected final int colCount;

    protected final int WIDTH; //chiều cao và rộng của ảnh to
    protected final int HEIGHT;

    protected final int width; //chiều cao và rộng của ảnh nhỏ
    protected final int height;

    protected int x; //tọa độ x của nhân vật
    protected int y; //tọa độ y của nhân vật

    public GameObject (Bitmap image, int rowCount, int colCount, int x, int y){
        this.image = image;
        this.rowCount = rowCount;
        this.colCount = colCount;

        this.x = x;
        this.y = y;

        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();

        this.width = this.WIDTH/colCount;
        this.height = this.HEIGHT/rowCount;
    }

    protected Bitmap createSubImageAt(int row, int col){
        Bitmap subImage = Bitmap.createBitmap(image, col*width, row*height, width, height);
        return subImage;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public int getHeight(){
        return this.height;
    }

    public int getWidth(){
        return this.width;
    }

}

























