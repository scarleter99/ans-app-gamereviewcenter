package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    FirebaseFirestore db;
    TextView tv_signup;
    EditText et_id, et_password,et_repassword,et_nickname, et_email;
    Button btn_signup, btn_idcheck, btn_nicknamecheck;
    ImageView iv_applogo;

    Boolean isidcheck = false; // 아이디 중복 검사 boolean 변수
    String tmpid ="";
    Boolean isnicknamecheck = false;
    String tmpnick = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        // 상단 타이틀 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // 변수 선언
        db = FirebaseFirestore.getInstance();
        iv_applogo = (ImageView) findViewById(R.id.iv_applogo);
        tv_signup = (TextView) findViewById(R.id.tv_signup);
        et_id = (EditText) findViewById(R.id.et_inputid);
        et_password = (EditText) findViewById(R.id.et_inputpassword);
        et_repassword = (EditText) findViewById(R.id.et_repassword);
        et_nickname = (EditText) findViewById(R.id.et_nickname);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_signup = (Button) findViewById(R.id.btn_signup);
        btn_nicknamecheck = (Button) findViewById(R.id.btn_nicknamecheck);
        btn_idcheck = (Button) findViewById(R.id.btn_idcheck);

        // 버튼 클릭 이벤트
    }
    // 버튼 클릭 이벤트
    public void buttonClicked(View v) { //implements View.OnClickListener
        switch (v.getId()) {
            case R.id.btn_signup:
                // 아이디 중복확인
                if(!isidcheck || !tmpid.equals(et_id.getText().toString())){
                    isidcheck = false;
                    Toast.makeText(getApplicationContext(),"아이디 중복 체크를 해야합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 비밀번호와 비밀번호 재입력이 같은지 확인
                else if(!isequalPassword(et_password.getText().toString(), et_repassword.getText().toString())){ // false 면 회원가입 불가능
                    return;
                }// 비밀번호 형식 확인
                else if(!passCheck(et_password.getText().toString())){
                    return;
                }
                // 닉네임 중복 확인
                else if(!isnicknamecheck || !tmpnick.equals(et_nickname.getText().toString())){
                    isnicknamecheck = false;
                    Toast.makeText(getApplicationContext(),"닉네임 중복 체크를 해야합니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 이메일 형식 확인
                else if(!emailCheck(et_email.getText().toString())){
                    return;
                }
                // 회원가입 완료
                else {
                    Map<String, Object> data = new HashMap<>();
                    data.put("id",et_id.getText().toString());
                    data.put("pw",et_password.getText().toString());
                    data.put("nickname",et_nickname.getText().toString());
                    data.put("email",et_email.getText().toString());
                    putData("user", et_id.getText().toString(), data);

                    Toast.makeText(getApplicationContext(), "회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    return;
                }
            case R.id.btn_idcheck:
                doubleCheck("user", 0);
                break;
            case R.id.btn_nicknamecheck:
                doubleCheck("user", 1);
                break;
        }
    }

    // 비밀번호와 비밀번호 재입력이 같은지 확인하는 함수
    private boolean isequalPassword(String pass, String repass){
        if(pass.equals(repass)){
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    // 비밀번호 형식 체크 함수
    private boolean passCheck(String pass) {
        String pattern = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"; //비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내)
        if (!(Pattern.matches(pattern, pass))) {
            Toast.makeText(getApplicationContext(), "올바른 비밀번호 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return  true;
        }
    }
    // 아이디 형식 체크 함수
    private boolean idCheck(String id){
        String pattern = "^[a-z0-9]{5,20}$";
        Log.d("id = ", String.valueOf(id.length()));
        if(!(Pattern.matches(pattern, id))){ // 5~20자, 소문자와 숫자조합이 아니라면 return false
            Toast.makeText(getApplicationContext(), "올바른 아이디 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }
    }
    // 닉네임 형식 체크 함수
    private boolean nicknameCheck(String nickname){
        String pattern = "[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]{2,8}";
        if(Pattern.matches(pattern, nickname)){ // 숫자, 영어, 한글로만 이루어져있다면 true
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "올바른 닉네임 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    // 이메일 형식 체크 함수
    private boolean emailCheck(String email){
        String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        if(Pattern.matches(pattern, email)) {
            return true;
        } else {
            Toast.makeText(getApplicationContext(), "올바른 이메일 형식이 아닙니다.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
    // 데이터 추가 함수
    public void putData(String collec, String doc, Object data) {
        CollectionReference collecRef = db.collection(collec);
        collecRef.document(doc).set(data);
    }

    // 중복 체크 함수
    private void doubleCheck(String collec, int num) {
        ArrayList<String> arr = new ArrayList<>();
        et_id = (EditText) findViewById(R.id.et_inputid);
        tmpid = et_id.getText().toString();
        tmpnick = et_nickname.getText().toString();
        db.collection(collec)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) { //데이터를 arr에 삽입
                        if (task.isSuccessful()) {
                            switch (num) {
                                case 0: // 아이디 중복 체크
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("데이터 모두 읽기", document.getId());
                                        arr.add(document.getId()); // arr에 모든 id 값 저장
                                    }
                                    if(arr.contains(et_id.getText().toString())){ // true면 db 안에 아이디가 존재하므로 중복 메시지 출력
                                        Toast.makeText(getApplicationContext(), "중복된 아이디입니다.", Toast.LENGTH_SHORT).show();
                                        isidcheck = false;
                                    }
                                    // 아이디 형식 확인
                                    else if(!idCheck(et_id.getText().toString())){
                                        isidcheck = false;
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "사용할 수 있는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                        isidcheck = true;
                                    }
                                    break;
                                case 1: // 닉네임 중복 체크
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("닉네임 모두 읽기", document.getData().get("nickname").toString());
                                        arr.add(document.getData().get("nickname").toString()); // 닉네임 키 값의 모든 데이터를 arr에 저장
                                    }
                                    if(arr.contains(et_nickname.getText().toString())){ // true면 db 안에 아이디가 존재하므로 중복 메시지 출력
                                        Toast.makeText(getApplicationContext(), "중복된 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                        isnicknamecheck = false;
                                    }
                                    else if(!nicknameCheck(et_nickname.getText().toString())){
                                        isnicknamecheck = false;
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "사용할 수 있는 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                        isnicknamecheck = true;
                                    }
                                    break;
                            }

                    }
                }
                });
        }

    }





