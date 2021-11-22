package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Board_Platform_Xbox_Activity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_platform_xbox);
        //상단 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Button BTN_Sample = (Button) findViewById(R.id.BTN_samplereview_xbox);
        BTN_Sample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
