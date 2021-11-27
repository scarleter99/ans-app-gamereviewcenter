package com.example.project;

import android.app.Activity;
import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class BoardPlatformPCActivity extends AppCompatActivity {
    FirebaseFirestore db;
    Button firstButton;
    ScrollView scrollView;
    LinearLayout layout_pc;
    Context context;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.board_platform_pc);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        firstButton = (Button) findViewById(R.id.btn_samplereview);
        layout_pc = (LinearLayout) findViewById(R.id.layout_pc);
        getAllData("pc");
        //상단 바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // 스피너
        String[] array = {"제목","작성자","게임명"};
        Spinner SPN_pc = (Spinner) findViewById(R.id.spn_pc);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        SPN_pc.setAdapter(adapter);

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
    public void putData(String collec, String doc, Object data) {
        DocumentReference docRef = db.collection(collec).document(doc);
        docRef
                .set(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("데이터 추가", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("데이터 추가", "Error updating document", e);
                    }
                });
    }

    // Firestore에 데이터 업데이트
    public void updateData(String collec, String doc, String key, String value) {
        DocumentReference docRef = db.collection(collec).document(doc);
        docRef
                .update(key, value)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("데이터 업데이트", "DocumentSnapshot successfully updated!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("데이터 업데이트", "Error updating document", e);
                    }
                });
    }

    // Firestore에서 데이터 읽기
    public void getData(String collec, String doc, int what) {
        DocumentReference docRef = db.collection(collec).document(doc);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("데이터 읽기", "DocumentSnapshot data: " + document.getData());
                        useData(document.getData());
                    } else {
                        Log.d("데이터 읽기", "No such document");
                    }
                } else {
                    Log.d("데이터 읽기", "get failed with ", task.getException());
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

    // Firestore에서 가져온 데이터 사용
    public void useData(Map<String, Object> data) {

    }

    // Firestore에서 가져온 모든 데이터 사용
    public void useData(ArrayList<Map<String, Object>> data) {
        Map<String, Object> target = data.get(0);
        String[] splitID = target.toString().split(",");
        String[] splitID2 = splitID[0].split("=");
        firstButton.setText(splitID2[0]);
    }
}
