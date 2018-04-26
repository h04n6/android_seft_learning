package h04n6.game_2d;

/**
 * Created by hoang on 2/3/2018.
 */

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.provider.Settings;

public class ChibiCharactor extends GameObject{

    //đánh số các hàng tương ứng với hành động nào trong ảnh to
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    private int rowUsing = ROW_LEFT_TO_RIGHT; //dòng ảnh đang được sử dụng
    private int colUsing; //là chỉ số của hình ảnh nhỏ đang dùng (0, 1 hoặc 2)

    //mỗi mảng chứa 3 hình ảnh nhỏ với hành động tương ứng (sẽ được nạp dữ liệu ở hàm dưới)
    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;

    public static final float VELOCITY = 0.1f; //vận tóc của nhân vật

    private int movingVectorX = 10;
    private int movingVectorY = 5;

    private long lastDrawNanoTime = -1;

    private GameSurface gameSurface;

    public ChibiCharactor(GameSurface gameSurface, Bitmap image, int x, int y){
        super(image, 4, 3, x, y); //4 là số hàng (rowCount), 3 là số cột (colCount)

        this.gameSurface = gameSurface;

        this.topToBottoms = new Bitmap[colCount]; //vì đã khởi tạo từ hàm super nên colCount = 3
        this.rightToLefts = new Bitmap[colCount];
        this.leftToRights = new Bitmap[colCount];
        this.bottomToTops = new Bitmap[colCount];

        for(int col = 0; col < this.colCount; col++){//tạo ra 12 ảnh nhân vật từ ảnh to
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col] = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col] = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
    }

    public Bitmap[] getMoveBitmap(){ //trả về hàng (row) hành động tương ứng
        switch(rowUsing){
            case ROW_BOTTOM_TO_TOP:
                return this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
        }
    }

    //trả về 1 hành động đang xảy ra trong 3 hành động đang trong hàng hành động vừa lấy ra
    //hành động ~ ảnh nhỏ
    public Bitmap getCurrentMoveBitmap(){
        Bitmap[] bitmaps = this.getMoveBitmap();
        return bitmaps[this.colUsing];
    }

    public void update(){
        this.colUsing++;
        if(colUsing >= this.colCount){
            this.colUsing = 0;
        }

        long now = System.nanoTime(); //thời điểm hiện tại theo nano giây

        if(lastDrawNanoTime == -1){ //nếu chưa vẽ lần nào
            lastDrawNanoTime = now;
        }

        //đổi nano giây ra mili giây (1 nanosecond = 1000000 milisecond)
        int deltaTime = (int)((now - lastDrawNanoTime)/1000000);

        //quãng đường mà nhân vật đi được
        float distance = VELOCITY*deltaTime;

        //độ dài vector chuyển động
        double movingVectorLength = Math.sqrt(movingVectorX*movingVectorX + movingVectorY*movingVectorY);

        //vị trí mới của nhân vật
        //ứng dụng tam giác đồng dạng (vẽ hình ra sẽ thấy)
        this.x = x + (int)(distance*movingVectorX/movingVectorLength);
        this.y = y + (int)(distance*movingVectorY/movingVectorLength);

        //khi nhân vật chạm vào cạnh màn hình thì đổi hướng
        if(this.x < 0){
            this.x = 0;
            this.movingVectorX =- this.movingVectorX;
        }else if(this.x > this.gameSurface.getWidth() - width){
            this.x = this.gameSurface.getHeight() - height;
            this.movingVectorX =- this.movingVectorX;
        }

        if(this.y < 0){
            this.y = 0;
            this.movingVectorY =- this.movingVectorY;
        }else if(this.x > this.gameSurface.getWidth() - width){
            this.y = this.gameSurface.getHeight() - height;
            this.movingVectorY =- this.movingVectorY;
        }

        //tính toán rowUsing (chọn hướng di chuyển của nhân vật)
        if(movingVectorX > 0){
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)){
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)){
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else{
                this.rowUsing = ROW_LEFT_TO_RIGHT;
            }
        }else{
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)){
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)){
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else{
                this.rowUsing = ROW_RIGHT_TO_LEFT;
            }
        }
    }

    public void draw (Canvas canvas){
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap, x, y, null);

        //thời điểm vẽ cuối cùng (nano giây)
        this.lastDrawNanoTime = System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY){
        this.movingVectorX = movingVectorX;
        this.movingVectorY = movingVectorY;
    }
}