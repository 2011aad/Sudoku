package com.example.zhangjian.sudoku;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;


public class Sudoku extends ActionBarActivity implements OnClickListener{
    private static final String TAG = "Sudoku";

    private void openNewGameDialog(){
        new AlertDialog.Builder(this).setTitle(R.string.new_game_title)
                .setItems(R.array.difficulty,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGame(which);
                    }
                }).show();
    }

    private void startGame(int i){
        Log.d(TAG,"clicked on " + i);
        //start game here...
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sudoku);

        View aboutButton = this.findViewById(R.id.about_button);
        aboutButton.setOnClickListener(this);
        View continueButton = this.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(this);
        View newButton = this.findViewById(R.id.new_button);
        newButton.setOnClickListener(this);
        View exitButton = this.findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_sudoku, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()){
            case R.id.settings:
                startActivity(new Intent(this, Settings.class));
                return true;
        }
        return false;
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.about_button:
                Intent i = new Intent(this,About.class);
                startActivity(i);
                break;
            case R.id.new_button:
                openNewGameDialog();
                break;
            case R.id.continue_button:
                break;
            case R.id.exit_button:
                this.finish();
                break;
        }
    }
}
