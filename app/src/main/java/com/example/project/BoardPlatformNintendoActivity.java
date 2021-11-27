package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class BoardPlatformNintendoActivity extends AppCompatActivity {
    FirebaseFirestore db;
    Button firstButton;
    ScrollView scrollView;
    LinearLayout layout_nintendo;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.board_platform_nintendo);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        firstButton = (Button) findViewById(R.id.btn_samplereview_nintendo);
        layout_nintendo = (LinearLayout) findViewById(R.id.layout_nintendo);
        getAllData("nintendo");
        //상단 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // 스피너
        String[] array = {"제목","작성자","게임명"};
        Spinner SPN_nintendo = (Spinner) findViewById(R.id.spn_nintendo);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SPN_nintendo.setAdapter(adapter);

        firstButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String comment = firstButton.getText().toString();
                if (comment.equals("업로드된 글이 없습니다.")){
                    Toast.makeText(getApplicationContext(), "글을 등록해주세요!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), BoardPlatformPCActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), BoardPlatformReviewActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    // Firestore에서 데이터 모두가져오기
    public void getAllData(String collec) {
        ArrayList<Map<String, Object>> arr = new ArrayList<>();
        db.collection(collec).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.d("데이터 모두 읽기", document.getId() + " => " + document.getData());
                        arr.add(document.getData());
                    }
                    useData(arr);
                } else {
                    Log.d("데이터 모두 읽기", "Error getting documents: ", task.getException());
                }
            }
        });
    }
    // Firestore에서 가져온 모든 데이터 사용
    public void useData(ArrayList<Map<String, Object>> data) {
        Button btn;
        Map<String, Object> target = data.get(0);
        String buttonText = ""; // 버튼에 기록될 문자열
        String[] splitID = target.toString().split("");
        firstButton.setText(splitID[0]);//제목출력
        for (int i = 1; i < data.size();i++){
            btn = new Button(this);
            target = data.get(i);
            buttonText = ""; // 버튼에 기록될 문자열
            splitID = target.toString().split("");
            btn.setText(splitID[0]);//제목출력
            layout_nintendo.addView(btn);
        }
    }
}
