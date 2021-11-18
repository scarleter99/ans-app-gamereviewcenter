package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Board_Platform_Activity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_platform);
        //상단 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button BTN_Sample = (Button) findViewById(R.id.BTN_samplereview);
        BTN_Sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
