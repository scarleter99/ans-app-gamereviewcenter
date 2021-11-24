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

public class SignUpActivity extends AppCompatActivity {
    Boolean isequalpass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        //상단 타이틀 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //변수 선언
        TextView tv_signup;
        EditText et_id, et_password,et_repassword,et_nickname, et_email;
        Button btn_signup;
        ImageView iv_applogo;

        iv_applogo = (ImageView) findViewById(R.id.iv_applogo);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        et_id = (EditText) findViewById(R.id.et_inputid);
        et_password = (EditText) findViewById(R.id.et_inputpassword);
        et_repassword = (EditText) findViewById(R.id.et_repassword);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_signup = (Button) findViewById(R.id.btn_signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });




    }
}
