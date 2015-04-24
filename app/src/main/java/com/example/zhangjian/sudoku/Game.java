package com.example.zhangjian.sudoku;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

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

    private PuzzleView puzzleView;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        int diff = getIntent().getIntExtra(KEY_DIFFICULTY,EASY);
        puzzle = getPuzzle(diff);
//        calculateUsedTiles();

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
            for(int j=0;j<0;j++)
                buf.append(puz[i][j]);

        return puz.toString();
    }
}
