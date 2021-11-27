package com.example.project;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BoardPlatformPCActivity extends AppCompatActivity {
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_platform_pc);
        //상단 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // 스피너
        String[] array = {"제목","작성자","게임명"};
        Spinner SPN_pc = (Spinner) findViewById(R.id.spn_pc);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SPN_pc.setAdapter(adapter);

    }
}
