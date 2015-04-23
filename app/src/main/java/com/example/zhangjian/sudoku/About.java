package com.example.zhangjian.sudoku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Created by zhangjian on 2015/4/22.
 */
public class About extends ActionBarActivity implements OnClickListener{
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);

        View backButton = this.findViewById(R.id.back_from_about);
        backButton.setOnClickListener(this);
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.back_from_about:
                this.finish();
                break;
        }
    }
}
