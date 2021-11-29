package com.example.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    Boolean islogin = false;
    FirebaseFirestore db;
    TextView tv_signup;
    Button btn_login;
    EditText et_inputid, et_inputpass;
    SharedPreferences spref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        spref = getSharedPreferences("gref", MODE_PRIVATE);
        editor = spref.edit();

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
                getAllData("user");
                break;
            case R.id.tv_signup:
                Intent intent2 = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent2);
                break;

        }
    }
    // 데이터 모두 불러오기
    public void getAllData(String collec) {
        ArrayList<Map<String, Object>> arr = new ArrayList<>();
        db.collection(collec)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("데이터 모두 읽기", document.getId() + " => " + document.getData());
                                arr.add(document.getData());
                            }
                            useData(arr, et_inputid.getText().toString(), et_inputpass.getText().toString());
                        } else {
                            Log.d("데이터 모두 읽기", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }


    // Firestore에서 가져온 모든 데이터 사용
    public void useData(ArrayList<Map<String, Object>> data, String id, String pw) {
        for(int i = 0; i < data.size() ; i++){
            if(data.get(i).get("id").equals(id)){
                if(data.get(i).get("pw").equals(pw)){
                    islogin = true;
                    Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT).show();
                    editor.putString("editText_id", et_inputid.getText().toString());
                    editor.putString("writer", data.get(i).get("nickname").toString());
                    editor.commit();
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
            Toast.makeText(getApplicationContext(),"없는 아이디 입니다.", Toast.LENGTH_SHORT).show();
        }
    }

    // 회원가입 텍스트뷰 클릭
    public void tv_SignupClicked(View v) {
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);
    }

    // 뒤로가기 버튼 클릭시


}