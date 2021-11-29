package com.example.project;

import android.content.SharedPreferences;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

public class BoardPlatformReviewActivity extends AppCompatActivity {

    FirebaseStorage st;
    FirebaseFirestore db;
    Uri imageUri;

    ImageView iv_getplaytime;
    TextView tv_pl_re_platform, tv_platform_review_title, tv_writer, tv_platform_review_gamename;
    TextView tv_rating, tv_content1, tv_content2, tv_content3, tv_content4, et_content5,tv_content6, tv_content7, tv_content8;
    SharedPreferences pref;
    RatingBar rb_rating;
    ArrayList <String> keydata = new ArrayList<String>(); // 플랫폼이 무엇인지 구별하기 위한 리스트;
    String prefData;
    String [] splitID;
    Button btn_reply;
    int recommendpush; //추천수
    int pushtry;// 추천횟수

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board_platform_review);

        iv_getplaytime = findViewById(R.id.iv_getplaytime);
        tv_pl_re_platform = findViewById(R.id.tv_pl_re_platform);
        tv_platform_review_title = findViewById(R.id.tv_platform_review_title);
        tv_writer = findViewById(R.id.tv_writer);
        tv_platform_review_gamename = findViewById(R.id.tv_platform_review_gamename);
        tv_rating = findViewById(R.id.tv_rating);
        tv_content1 = findViewById(R.id.tv_content1);
        tv_content2 = findViewById(R.id.tv_content2);
        tv_content3 = findViewById(R.id.tv_content3);
        tv_content4 = findViewById(R.id.tv_content4);
        et_content5 = findViewById(R.id.et_content5);
        tv_content6 = findViewById(R.id.tv_content6);
        tv_content7 = findViewById(R.id.tv_content7);
        tv_content8 = findViewById(R.id.tv_content8);
        rb_rating = findViewById(R.id.rb_rating);
        btn_reply = findViewById(R.id.btn_reply);
        db = FirebaseFirestore.getInstance();
        pushtry = 0; //추천버튼 누른 횟수

        setPage();
        String platform = tv_pl_re_platform.getText().toString().trim();//플랫폼
        String title = tv_platform_review_title.getText().toString().trim();//제목
        getData(platform, title);


        // 상단 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btn_reply.setOnClickListener(new View.OnClickListener(){ // 추천 버튼을 눌렀을 때
            public void onClick(View v){
                pushtry += 1;
                if (pushtry < 1) {
                    updateData(platform, title, "recommend", recommendpush+1);//추천수 증가된 데이터 전달
                    getData(platform, title);
                }
                else {
                    Toast.makeText(getApplicationContext(), "이미 추천한 글입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void getImage(String review) {
        try {
            st = FirebaseStorage.getInstance();
            StorageReference stRef = st.getReference("PC/" + review + ".PNG");

            stRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // Glide 이용하여 이미지뷰에 로딩
                        Glide.with(getApplicationContext()).load(task.getResult()).override(600, 600).into(iv_getplaytime);
                        Log.d("사진 읽기", "Success");
                    } else {
                        Log.d("사진 읽기", "Failure");
                    }
                }
            });
        } catch (Exception e) {
            Log.d("사진 읽기", "Failure");
        }
    }

    public void updateData(String collec, String doc, String key, int value) {
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

    public void getData(String collec, String doc) {
        DocumentReference docRef = db.collection(collec).document(doc);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("데이터 가져오기", "DocumentSnapshot data: " + document.getData());
                        useData(document.getData());
                    } else {
                        Log.d("데이터 가져오기", "No such document");
                    }
                } else {
                    Log.d("데이터 가져오기", "get failed with ", task.getException());
                }
            }
        });
    }

    public void useData(Map<String, Object> data) {
        String[] splitdata = data.toString().split(",");
        for (int k = 0; k < splitdata.length; k++) {// 괄호제거
            if (k == 0) {
                splitdata[k] = splitdata[k].replace("{", "");
            } else if (k == splitdata.length - 1) {
                splitdata[k] = splitdata[k].replace("}", "");
            }
        }
        for (int j = 0; j < splitdata.length; j++) {//제목출력
            String str1 = splitdata[j].trim();
            if (str1.contains("recommend=")) {//추천수 표시
                str1 = str1.replace("recommend=","");
                tv_rating.setText("추천수 : "+str1);
                recommendpush = Integer.parseInt(str1);
            }
        }
    }

    public void setPage(){
        keydata.add("PC");
        keydata.add("mobile");
        keydata.add("nintendo");
        keydata.add("playstation");
        keydata.add("xbox");
        keydata.add("etc");
        pref = getSharedPreferences("Info_Pref", MODE_PRIVATE);
        for (int i = 0; i < keydata.size(); i++){
            prefData = pref.getString(keydata.get(i), "");
            if (!prefData.equals("")){
                tv_pl_re_platform.setText(keydata.get(i));
                splitID = prefData.split(",");
                for (int k = 0; k < splitID.length; k++){// 괄호제거
                    if (k == 0){
                        splitID[k] = splitID[k].replace("{","");
                    } else if (k == splitID.length-1){
                        splitID[k] = splitID[k].replace("}","");
                    }
                }
                for (int j = 0; j< splitID.length; j++){//내용출력
                    splitID[j] = splitID[j].trim();
                    if (splitID[j].contains("rating")){
                        splitID[j] = splitID[j].replace("rating=", "");
                        rb_rating.setRating(Float.parseFloat(splitID[j]));
                    }else if (splitID[j].contains("gametitle")){
                        splitID[j] = splitID[j].replace("gametitle=", "");
                        tv_platform_review_gamename.setText(splitID[j]);
                    }else if (splitID[j].contains("writer")){
                        splitID[j] = splitID[j].replace("writer=", "");
                        tv_writer.setText(splitID[j]);
                    }else if (splitID[j].contains("attribute")){
                        splitID[j]=splitID[j].replace("attribute=[","");
                        splitID[j+7] = splitID[j+7].replace("]","");
                        for (int k = 0; k<8 ; k++){
                            if (splitID[j+k].contains("emptycontent")){
                                splitID[j+k] = "";
                            }
                        }
                        tv_content1.setText(splitID[j]);
                        tv_content2.setText(splitID[j+1]);
                        tv_content3.setText(splitID[j+2]);
                        tv_content4.setText(splitID[j+3]);
                        et_content5.setText(splitID[j+4]);
                        tv_content6.setText(splitID[j+5]);
                        tv_content7.setText(splitID[j+6]);
                        tv_content8.setText(splitID[j+7]);
                        j += 7;
                    }else {
                        splitID[j] = splitID[j].replace("title=","");
                        tv_platform_review_title.setText(splitID[j]);
                        getImage(splitID[j]);
                    }
                }
            }
            else{

            }
        }

    }
}
