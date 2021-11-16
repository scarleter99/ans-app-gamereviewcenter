package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    Boolean isequalpass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //상단 타이틀 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //변수 선언
        TextView TV_signup;
        EditText ET_id, ET_password,ET_repassword,ET_nickname, ET_email;
        Button BTN_signup;
        ImageView IV_applogo;

        IV_applogo = (ImageView) findViewById(R.id.IV_applogo);
        TV_signup = (TextView) findViewById(R.id.TV_signup);
        ET_id = (EditText) findViewById(R.id.ET_inputid);
        ET_password = (EditText) findViewById(R.id.ET_inputpassword);
        ET_repassword = (EditText) findViewById(R.id.ET_repassword);
        ET_nickname = (EditText) findViewById(R.id.ET_nickname);
        ET_email = (EditText) findViewById(R.id.ET_email);
        BTN_signup = (Button) findViewById(R.id.BTN_signup);

        BTN_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });




    }
}
