package h04n6.game_2d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by hoang on 2/3/2018.
 */

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback{

    private GameThread gameThread;

    private final List<ChibiCharactor> chibiList = new ArrayList<ChibiCharactor>();
    private final List<Explosion> explosionList = new ArrayList<Explosion>();

    private static final int MAX_STREAMS = 100;
    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;

    public GameSurface(Context context){
        super(context);

        //đảm bảo Game Surface có thể focus để điều khiển các sự kiện
        this.setFocusable(true);

        //set các sự kiện liên quan đến game
        this.getHolder().addCallback(this);

        this.initSoundPool();
    }

    public void update(){
        for(ChibiCharactor chibi: chibiList){
            chibi.update();
        }
        for(Explosion explosion: explosionList){
            explosion.update();
        }

        Iterator<Explosion> iterator = this.explosionList.iterator();
        while(iterator.hasNext()){
            Explosion explosion = iterator.next();

            if(explosion.isFinish()){
                //nếu explosion đã hoàn thành, loại nó ra khỏi iterator và list
                //loại bỏ phần tử hiện thời ra khỏi bộ lặp
                iterator.remove();
                continue;
            }
        }
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        for(ChibiCharactor chibi: chibiList){
            chibi.draw(canvas);
        }

        for(Explosion explosion: explosionList){
            explosion.draw(canvas);
        }
    }

    //thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder){
        //R.drawable.chibi2 là lấy ảnh tên chibi1 từ folder "drawable"
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi1);
        ChibiCharactor chibi1 = new ChibiCharactor(this, chibiBitmap1, 100, 50);

        Bitmap chibiBitmap2 = BitmapFactory.decodeResource(this.getResources(), R.drawable.chibi2);
        ChibiCharactor chibi2 = new ChibiCharactor(this, chibiBitmap2, 300, 150);

        this.chibiList.add(chibi1);
        this.chibiList.add(chibi2);

        this.gameThread = new GameThread(this, holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    //thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

    }

    //thi hành phương thức của interface SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                this.gameThread.setRunning(false);

                //luồng cha, cần phải tạm dừng chờ GameThread kết thúc
                this.gameThread.join();

            }catch (InterruptedException e){
                e.printStackTrace();
            }
            retry = true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        if(e.getAction() == MotionEvent.ACTION_DOWN){
            int x = (int)e.getX();
            int y = (int)e.getY();

            Iterator<ChibiCharactor> iterator = this.chibiList.iterator();

            //kiểm tra xem có click vào nhân vật không
            //người dùng chạm ngón tay vào màn hình tạo nên 1 điểm A(xa, ya)
            //nếu điểm A đó nằm trong khoảng kích thước của ảnh (ảnh là 1 hình chữ nhật) thì
            //tức là người dùng chạm vào ảnh (nhân vật đang di chuyển)
            while(iterator.hasNext()){
                ChibiCharactor chibi = iterator.next();
                if(chibi.getX() < x && y < chibi.getX() + chibi.getWidth()
                        && chibi.getY() < y && y < chibi.getY() + chibi.getHeight()){

                    //loại bỏ nhân vật hiện tại ra khỏi iterator và list
                    iterator.remove();

                    //tạo mới một đối tượng Explosion
                    Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.explosion);
                    Explosion explosion = new Explosion(this, bitmap, chibi.getX(), chibi.getY());

                    this.explosionList.add(explosion);

                }
            }

            for(ChibiCharactor chibi: chibiList){
                int movingVectorX = x - chibi.getX();
                int movingVectorY = y - chibi.getY();
                chibi.setMovingVector(movingVectorX, movingVectorY);
            }
            return true;
        }
        return false;
    }

    //đặt tên hàm là init vì init ~ initial: ban đầu, bắt đầu
    //hàm này là hàm ban đầu/khởi tạo SoundPool
    private void initSoundPool(){
        //với phiên bản android API < 21
        if(Build.VERSION.SDK_INT >= 21){
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();

            SoundPool.Builder builder = new SoundPool.Builder();
            builder.setAudioAttributes(audioAttributes).setMaxStreams(MAX_STREAMS);

            this.soundPool = builder.build();
        }
        //với phiên bản android API > 21
        else{
            this.soundPool = new SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0);
        }

        //sự kiện sau khi SoundPool đã tải lên bộ nhớ thành công
        this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener(){
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
                soundPoolLoaded = true;

                //phát nhạc nền
                playSoundBackground();
            }
        });

        //tải file nhạc tiếng nhạc nền (background.mp3) vào SoundPool
        this.soundIdBackground = this.soundPool.load(this.getContext(), R.raw.background, 1);

        //tải file nhạc tiếng nổ (explosion.wav) vào SoundPool
        this.soundIdExplosion = this.soundPool.load(this.getContext(), R.raw.explosion, 1);
    }

    public void playSoundExplosion(){
        if(this.soundPoolLoaded){
            float leftVolumn = 0.08f;
            float rightVolumn = 0.08f;

            //phát âm thanh explosion.wav
            int streamId = this.soundPool.play(this.soundIdExplosion, leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }

    public void playSoundBackground(){
        if(this.soundPoolLoaded){
            float leftVolumn = 0.08f;
            float rightVolumn = 0.08f;

            //phát ra âm thanh background.mp3
            int streamId = this.soundPool.play(this.soundIdBackground, leftVolumn, rightVolumn, 1, -1, 1f);
        }
    }
}


























