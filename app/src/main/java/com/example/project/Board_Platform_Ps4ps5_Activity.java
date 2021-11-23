package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class Board_Platform_Ps4ps5_Activity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_platform_ps4ps5);
        //상단 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        String[] array = {"제목","작성자","게임명"};
        Spinner SPN_ps4ps5 = (Spinner) findViewById(R.id.SPN_ps4ps5);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SPN_ps4ps5.setAdapter(adapter);

    }
}