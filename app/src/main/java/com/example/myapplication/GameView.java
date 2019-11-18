package com.example.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.View;

import java.util.ArrayList;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder surfaceHolder;
    private DrawThread drawThread;
    private ArrayList<Sprite> sprites = new ArrayList<>();

    float xTouch, yTouch;

    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context) {
        super(context);
        surfaceHolder = this.getHolder();
        surfaceHolder.addCallback(this);

        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());
        sprites.add(new Sprite());

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                xTouch=event.getX();
                yTouch=event.getY();
                return false;
            }
        });
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread();
        drawThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (null != drawThread){
            drawThread.stopTread();
            drawThread = null;
        }
    }

    private class DrawThread extends Thread{
        private Boolean beAlive = true;

        public void stopTread() {
            beAlive = false;
        }

        @Override
        public void run() {
            while(beAlive) {
                try{
                    //获得绘画的画布
                    Canvas canvas =  surfaceHolder.lockCanvas();
                    // 背景设为蓝色
                    canvas.drawColor(Color.WHITE);

                    Paint p = new Paint();
                    p.setTextSize(50);
                    p.setColor(Color.BLACK);

                    // 在 20,40位置画一个文本
                    if (xTouch > 0){
                        canvas.drawText("你点击了"+xTouch+","+yTouch, 20, 40, p);
                    }else
                        canvas.drawText("hello world!",20,40,p);

                    for (Sprite sprite:sprites){
                        sprite.move();
                    }

                    for (Sprite sprite:sprites){
                        canvas.drawCircle(sprite.getX(), sprite.getY(), 15, p);
                    }

                    //把绘画好的内容提交上去
                    surfaceHolder.unlockCanvasAndPost(canvas);//解锁
                    Thread.sleep(30);
                }catch (Exception e){

                }
            }
        }
    }

    private class Sprite {
        int x,y;
        double directionAgle;

        public Sprite() {
            x= (int) (GameView.this.getWidth()*Math.random());
            y= (int) (GameView.this.getHeight()*Math.random());
            directionAgle= Math.random()* 2 * Math.PI;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void move()
        {
            x+=15*Math.cos(directionAgle);
            y+=15*Math.sin(directionAgle);
            if(x<0) x+=GameView.this.getWidth();
            if(x>GameView.this.getWidth())x-=GameView.this.getWidth();
            if(y<0) y+=GameView.this.getHeight();
            if(y>GameView.this.getHeight())y-=GameView.this.getHeight();

            if(Math.random()<0.05)directionAgle=Math.random()*2*Math.PI;
        }
    }
}
