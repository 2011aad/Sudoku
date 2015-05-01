package com.example.zhangjian.sudoku;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by zhangjian on 2015/4/24.
 */
public class Game extends Activity{
    private static final String TAG = "Sudoku";

    public static final String KEY_DIFFICULTY = "difficulty";
    public static final int EASY = 0;
    public static final int MEDIUM = 1;
    public static final int HARD = 2;

    private int[][] puzzle = new int[9][9];

    private int[][][] used = new int[9][9][];

    private PuzzleView puzzleView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        int diff = getIntent().getIntExtra(KEY_DIFFICULTY,EASY);
        puzzle = getPuzzle(diff);
        calculateUsedTiles();

        puzzleView = new PuzzleView(this);
        setContentView(puzzleView);
        puzzleView.requestFocus();
    }

    private String easyPuzzle(){
        return "360000000004230800000004200"+
                "070460003820000014500013020"+
                "001900000007048300000000045";
    }

    private String mediumPuzzle(){
        return "650000070000506000014000005"+
                "007009000002314700000700800"+
                "500000630000201000030000097";
    }

    private String hardPuzzle(){
        return "009000000080605020601078000"+
                "000000700706040102004000000"+
                "000720903090301080000000600";
    }

    private int[][] getPuzzle(int diff){
        String puz;
        switch (diff){
            case HARD:
                puz = hardPuzzle();
                break;
            case MEDIUM:
                puz = mediumPuzzle();
                break;
            case EASY:
                puz = easyPuzzle();
                break;
            default:
                puz = easyPuzzle();
                break;
        }

        return fromPuzzleString(puz);
    }

    static protected int[][] fromPuzzleString(String s){
        int [][] puz = new int[9][9];
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                puz[i][j] = s.charAt(9*i+j) - '0';

        return puz;
    }

    static private String toPuzzleString(int [][] puz){
        StringBuilder buf = new StringBuilder();
        for(int i=0;i<9;i++)
            for(int j=0;j<9;j++)
                buf.append(puz[i][j]);

        return buf.toString();
    }

    static private String toUsedString(int [] used){
        StringBuilder buf = new StringBuilder();
        for(int u:used)
            buf.append(u);

        return buf.toString();
    }

    private int getTile(int x,int y){
        return puzzle[y][x];
    }

    private void setTile(int x,int y,int value){
        puzzle[y][x] = value;
    }

    protected String getTileString(int x,int y){
        int n = getTile(x,y);
        if(n==0) return "";
        else return String.valueOf(n);
    }

    protected void showKeypadOrError(int x,int y){
        int tiles[] = getUsedTiles(x,y);
        if(tiles.length==9){
            Toast toast = Toast.makeText(this,R.string.no_moves_label,Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }
        else {
            Log.d(TAG, "showKeypad: used=" + toUsedString(tiles));
            Dialog v = new Keypad(this,tiles,puzzleView);
            v.show();
        }
    }

    protected boolean setTileIfValid(int x,int y,int value){
        int tiles[] = getUsedTiles(x,y);
        if(value!=0){
            for(int tile:tiles){
                if(tile==value) return false;
            }
        }
        setTile(x,y,value);
        calculateUsedTiles();
        return true;
    }

    protected int[] getUsedTiles(int x,int y){
        return used[y][x];
    }

    private void calculateUsedTiles(){
        for(int x=0;x<9;x++){
            for(int y=0;y<9;y++){
                used[y][x] = calculateUsedTiles(x,y);
            }
        }
    }

    private int[] calculateUsedTiles(int x,int y){
        boolean[] b = new boolean[10];
        for(int i=0;i<9;i++){
            if(i!=y)b[getTile(x,i)] = true;
            if(i!=x)b[getTile(i,y)] = true;
        }
        int s = (x/3)*3;
        int t = (y/3)*3;

        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if((s+i!=x)||(t+j!=y))
                    b[getTile(s+i,t+j)] = true;
            }
        }

        int counter = 0;
        for(int i=1;i<10;i++){
            if(b[i]) counter++;
        }

        int [] c = new int[counter];
        for(int i=1;i<10;i++){
            if(b[i]){
                c[counter-1] = i;
                counter--;
            }
        }
        return c;
    }
}
