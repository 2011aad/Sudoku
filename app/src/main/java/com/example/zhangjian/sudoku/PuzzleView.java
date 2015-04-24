package com.example.zhangjian.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;

/**
 * Created by zhangjian on 2015/4/24.
 */
public class PuzzleView extends View{
    private static final String TAG = "Sudoku";
    private final Game g;
    private float width,height;
    private int selX,selY;
    private final Rect selRect = new Rect();

    public PuzzleView(Context con){
        super(con);
        this.g = (Game) con;
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        width = w / 9f;
        height = h / 9f;
        getRect(selX,selY,selRect);
        Log.d(TAG,"onSizeChanged: width" + width + ", height" + height);
        super.onSizeChanged(w,h,oldw,oldw);
    }

    protected void onDraw(Canvas canvas){
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));
        canvas.drawRect(0,0,getWidth(),getHeight(),background);

        Paint dark = new Paint();
        dark.setColor(getResources().getColor(R.color.puzzle_dark));

        Paint high_light = new Paint();
        high_light.setColor(getResources().getColor(R.color.high_light));

        Paint light = new Paint();
        light.setColor(getResources().getColor(R.color.high_light));

        for(int i=0;i<9;i++){
            canvas.drawLine(0,i*height,getWidth(),i*height,light);
            canvas.drawLine(0,i*height+1,getWidth(),i*height+1,high_light);
            canvas.drawLine(i*width,0,i*width,getHeight(),light);
            canvas.drawLine(i*width+1,0,i*width+1,getHeight(),high_light);
        }

        for(int i=0;i<3;i++){
            canvas.drawLine(0,i*3*height,getWidth(),i*3*width,dark);
            canvas.drawLine(0,i*3*height+1,getWidth(),i*3*width+1,high_light);
            canvas.drawLine(i*3*width,0,i*3*width,getHeight(),dark);
            canvas.drawLine(i*3*width+1,0,i*3*width+1,getHeight(),high_light);
        }
    }

    private void getRect(int x, int y, Rect rect){
        rect.set((int)(x*width),(int)(y*height),(int)((x+1)*width),(int)((y+1)*height));
    }
}
