package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Boolean islogin = false;
    FirebaseFirestore db;
    TextView tv_signup;
    Button btn_login;
    EditText et_inputid, et_inputpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();


        // 상단 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // 회원가입 textview, 로그인 button 변수 설정
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_inputid = (EditText) findViewById(R.id.et_inputid);
        et_inputpass = (EditText) findViewById(R.id.et_inputpassword);
    }

    public void buttonClicked(View v){
        switch (v.getId()){
            case R.id.btn_login:
                login("user");
                break;
            case R.id.tv_signup:
                Intent intent2 = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent2);
                break;

        }
    }

    // 로그인함수
    private void login(String collec){
        db.collection(collec)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getId().equals(et_inputid.getText().toString())){
                                    if(document.get("pw").equals(et_inputpass.getText().toString())){
                                        islogin = true;
                                        Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                                        startActivity(intent);
                                        return;
                                    }
                                    else{
                                        islogin = false;
                                        Toast.makeText(getApplicationContext(),"비밀번호가 잘못되었습니다.", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                }
                                else{
                                    islogin = false;
                                    continue;
                                }
                            }
                            if(!islogin){
                                Toast.makeText(getApplicationContext(),"아이디가 없습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Log.d("데이터 모두 읽기", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    // 회원가입 텍스트뷰 클릭
    public void tv_SignupClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

}