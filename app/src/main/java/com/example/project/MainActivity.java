package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView TV_signup;
    Button BTN_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //상단 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //회원가입 textview, 로그인 button 변수 설정
        TV_signup = (TextView) findViewById(R.id.TV_signup);
        BTN_login = (Button) findViewById(R.id.BTN_login);
    }

    public void signupTextviewClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), SignUp.class);
        startActivity(intent);
    }
    public void loginButtonClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
        startActivity(intent);
    }
}

