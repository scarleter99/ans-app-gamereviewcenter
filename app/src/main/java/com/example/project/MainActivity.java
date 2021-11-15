package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    TextView signupTV;
    Button loginBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signupTV = (TextView) findViewById(R.id.TV_signup);
        loginBTN = (Button) findViewById(R.id.BTN_login);
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

