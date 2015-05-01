package com.example.zhangjian.sudoku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;

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
        //draw background
        Paint background = new Paint();
        background.setColor(getResources().getColor(R.color.puzzle_background));
        canvas.drawRect(0,0,getWidth(),getHeight(),background);

        //draw lines
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
            canvas.drawLine(0,i*3*height,getWidth(),i*3*height,dark);
            canvas.drawLine(0,i*3*height+1,getWidth(),i*3*height+1,high_light);
            canvas.drawLine(i*3*width,0,i*3*width,getHeight(),dark);
            canvas.drawLine(i*3*width+1,0,i*3*width+1,getHeight(),high_light);
        }

        //draw numbers
        Paint foreground = new Paint();
        foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
        foreground.setStyle(Paint.Style.FILL);
        foreground.setTextSize(height*0.75f);
        foreground.setTextScaleX(width/height);
        foreground.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm = foreground.getFontMetrics();
        float x = width/2;
        float y = height/2 - (fm.ascent+fm.descent)/2;
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                canvas.drawText(this.g.getTileString(i,j),i*width+x,j*height+y,foreground);
            }
        }

        //draw selected tile
        Log.d(TAG, "selRect= "+selRect);
        Paint selected = new Paint();
        selected.setColor(getResources().getColor(R.color.puzzle_selected));
        canvas.drawRect(selRect,selected);

        //draw hints
        Paint hint = new Paint();
        int c[] = {getResources().getColor(R.color.puzzle_hint_0),
                    getResources().getColor(R.color.puzzle_hint_1),
                    getResources().getColor(R.color.puzzle_hint_2)};
        Rect r = new Rect();
        for(int i=0;i<9;i++){
            for (int j=0;j<9;j++){
                int moveleft = 9 - g.getUsedTiles(i,j).length;
                if(moveleft<c.length){
                    getRect(i,j,r);
                    hint.setColor(c[moveleft]);
                    canvas.drawRect(r,hint);
                }
            }
        }
    }

    private void getRect(int x, int y, Rect rect){
        rect.set((int)(x*width),(int)(y*height),(int)((x+1)*width),(int)((y+1)*height));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event){
        Log.d(TAG,"OnKeyDown: keycode=" + keyCode + ", event=" + event);

        switch (keyCode){
            case KeyEvent.KEYCODE_DPAD_UP:
                select(selX,selY-1);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                select(selX,selY+1);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                select(selX-1,selY);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                select(selX+1,selY);
                break;
            case KeyEvent.KEYCODE_0:
            case KeyEvent.KEYCODE_SPACE:
                setSelectedTile(0);
                break;
            case KeyEvent.KEYCODE_1:
                setSelectedTile(1);
                break;
            case KeyEvent.KEYCODE_2:
                setSelectedTile(2);
                break;
            case KeyEvent.KEYCODE_3:
                setSelectedTile(3);
                break;
            case KeyEvent.KEYCODE_4:
                setSelectedTile(4);
                break;
            case KeyEvent.KEYCODE_5:
                setSelectedTile(5);
                break;
            case KeyEvent.KEYCODE_6:
                setSelectedTile(6);
                break;
            case KeyEvent.KEYCODE_7:
                setSelectedTile(7);
                break;
            case KeyEvent.KEYCODE_8:
                setSelectedTile(8);
                break;
            case KeyEvent.KEYCODE_9:
                setSelectedTile(9);
                break;
            case KeyEvent.KEYCODE_DPAD_CENTER:
                g.showKeypadOrError(selX,selY);
                break;
            default:
                return super.onKeyDown(keyCode,event);
        }
        return true;
    }

    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction()!=MotionEvent.ACTION_DOWN)
            return super.onTouchEvent(event);

        select((int)(event.getX()/width),(int)(event.getY()/height));
        g.showKeypadOrError(selX,selY);
        Log.d(TAG,"onTouchEvent: x " + selX + ", y " + selY);
        return true;
    }

    public void select(int x,int y){
        invalidate(selRect);
        if(x<0) x = 8;
        if(x>8) x = 0;
        if(y<0) y = 8;
        if(y>8) y = 0;
        selX = x;
        selY = y;
        getRect(selX,selY,selRect);
        invalidate(selRect);
    }

    public void setSelectedTile(int tile){
        if(g.setTileIfValid(selX,selY,tile)) invalidate();
        else{
            Log.d(TAG, "setSelectedTile: invalid: " + tile);
            startAnimation(AnimationUtils.loadAnimation(g,R.anim.shake));
        }
    }
}
