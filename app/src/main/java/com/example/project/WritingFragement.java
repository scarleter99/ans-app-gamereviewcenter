package com.example.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class WritingFragement extends Fragment implements View.OnClickListener{
    FirebaseStorage st;
    FirebaseFirestore db;

    Uri imageUri;

    RatingBar rb;
    Spinner spn_writing;
    ImageView iv_putplaytime;
    Button btn_Done;
    EditText WR_title,WR_gametitle,WR_attr1,WR_attr2,WR_attr3,WR_attr4,WR_attr5,WR_attr6,WR_attr7,WR_attr8;
    SharedPreferences spref;
    Boolean istitlecheck = false;
    String tmptitle = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.writing, container, false);
        spn_writing = (Spinner) view.findViewById(R.id.spn_writing_platform);
        platform_initspinner();

        WR_title = (EditText) view.findViewById(R.id.et_writing_title);
        WR_gametitle = (EditText) view.findViewById(R.id.et_writing_gamename);
        WR_attr1 = (EditText) view.findViewById(R.id.tv_content1);
        WR_attr2 = (EditText) view.findViewById(R.id.tv_content2);
        WR_attr3 = (EditText) view.findViewById(R.id.tv_content3);
        WR_attr4 = (EditText) view.findViewById(R.id.tv_content4);
        WR_attr5 = (EditText) view.findViewById(R.id.et_content5);
        WR_attr6 = (EditText) view.findViewById(R.id.tv_content6);
        WR_attr7 = (EditText) view.findViewById(R.id.tv_content7);
        WR_attr8 = (EditText) view.findViewById(R.id.tv_content8);
        rb = (RatingBar) view.findViewById(R.id.rb_rating);
        db = FirebaseFirestore.getInstance();
        spref = this.getActivity().getSharedPreferences("gref", Context.MODE_PRIVATE);

        iv_putplaytime = view.findViewById(R.id.iv_putplaytime);
        btn_Done = view.findViewById(R.id.btn_done);

        iv_putplaytime.setOnClickListener(this);
        btn_Done.setOnClickListener(this);

        return view;
    }

    // 중복 체크 함수
    private void doubleCheck(String collec, int num, String temp) {
        ArrayList<String> arr = new ArrayList<>();
        tmptitle = temp;
        db.collection(collec)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) { //데이터를 arr에 삽입
                        if (task.isSuccessful()) {
                            switch (num) {
                                case 0: // 글제목 중복 체크
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("제목 모두 읽기", document.getData().get("title").toString());
                                        arr.add(document.getData().get("title").toString()); // arr에 모든 title 값 저장
                                    }
                                    if(arr.contains(tmptitle.toString())){ // true면 db 안에 글 제목이 존재하므로 중복 메시지 출력
                                        Toast.makeText(getContext(), "사용불가능한 제목입니다.", Toast.LENGTH_SHORT).show();
                                        Log.d("데이터 모두 읽기", "제목사용 불가능");
                                        istitlecheck = false;
                                    }
                                    else {
                                        Toast.makeText(getContext(), "사용가능한 제목입니다.", Toast.LENGTH_SHORT).show();
                                        Log.d("데이터 모두 읽기", "제목사용 가능");
                                        useData();
                                    }
                                    break;
                            }

                        }
                    }
                });
    }

    public void getTitleData(String collec, String title) {
        ArrayList<String> arr = new ArrayList<>();
        db.collection(collec)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("데이터 모두 읽기", document.getId() + " => " + document.getData());
                                arr.add(document.getData().get("title").toString());
                            }
                            useData(arr,title);
                        } else {
                            Log.d("데이터 모두 읽기", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    public void onClick(View v) { //implements View.OnClickListener
        switch (v.getId()) {
            case R.id.iv_putplaytime:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                imgLauncher.launch(intent);
                break;
            case R.id.btn_done:
                String spn = spn_writing.getSelectedItem().toString();
                String title = WR_title.getText().toString();
                getTitleData(spn,title);

        }
    }

    private void platform_initspinner(){
        String[] array = {"PC","mobile","Nintendo", "PS4/PS5", "XBOX", "ETC"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, array);
        spn_writing.setAdapter(adapter);
    }

    public void putImage(String reviewNum) {
        try {
            st = FirebaseStorage.getInstance();
            StorageReference storageRef = st.getReference();

            StorageReference imageRef = storageRef.child("PC/"+ reviewNum + ".PNG");
            UploadTask uploadTask = imageRef.putFile(imageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.d("사진 추가", "Failure");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Log.d("사진 추가", "Success");
                }
            });
        } catch (Exception e){
            Log.d("사진 추가", "Failure");

        }
    }

    ActivityResultLauncher<Intent> imgLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent intent = result.getData();
                        imageUri = intent.getData();
                        iv_putplaytime.setImageURI(imageUri);
                    }
                }
            });
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
                        useData(document.getData(), what);
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
    public void getAllData(String collec, int what) {
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
                            useData(arr, what);
                        } else {
                            Log.d("데이터 모두 읽기", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    // Firestore에서 가져온 데이터 사용
    public void useData(Map<String, Object> data, int what) {
        switch (what) {
            case 1:
                // 사용하지않는코드
        }
    }

    public void useData() {

    }

    public void useData(ArrayList<String> arr, String temp) {
        tmptitle = temp;
        if(arr.contains(tmptitle.toString())){ // true면 db 안에 아이디가 존재하므로 중복 메시지 출력
            Toast.makeText(getContext(), "사용 불가능한 제목입니다.", Toast.LENGTH_SHORT).show();
            istitlecheck = false;
        }
        else{
            Toast.makeText(getContext(), "사용 가능한 제목입니다.", Toast.LENGTH_SHORT).show();
            istitlecheck = true;
        }
        Map<String, Object> data = new HashMap<>();
        String spn = spn_writing.getSelectedItem().toString();
        Boolean isfill = false;
        float rating = rb.getRating();
        String gmtitle = WR_gametitle.getText().toString();
        String title = WR_title.getText().toString();
        String writer = spref.getString("writer","");
        data.put("writer",writer);
        data.put("rating",rating);
        data.put("recommend","0");
        putImage(title);
        ArrayList<String> attr = new ArrayList<>();
        if(WR_attr1.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr1.getText().toString());
            isfill = true;}
        if(WR_attr2.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr2.getText().toString());
            isfill = true;}
        if(WR_attr3.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr3.getText().toString());
            isfill = true;}
        if(WR_attr4.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr4.getText().toString());
            isfill = true;}
        if(WR_attr5.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr5.getText().toString());
            isfill = true;}
        if(WR_attr6.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr6.getText().toString());
            isfill = true;}
        if(WR_attr7.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr7.getText().toString());
            isfill = true;}
        if(WR_attr8.getText().toString().equals("")){
            attr.add("emptycontent"); }
        else{
            attr.add(WR_attr8.getText().toString());
            isfill = true;}
        if(WR_title.getText().toString().equals("")){
            Toast.makeText(getContext(),"제목을 입력해 주세요",Toast.LENGTH_SHORT).show();}
        else{
            data.put("title",title);
            isfill = true;}
        if(WR_gametitle.getText().toString().equals("")){
            Toast.makeText(getContext(),"게임이름을 입력해 주세요",Toast.LENGTH_SHORT).show();}
        else{
            data.put("gametitle",gmtitle);
            isfill = true;}
        data.put("attribute",attr);
        Boolean finchk = istitlecheck;
        if(isfill&&finchk){
            putData(spn,title,data);
        }
        else if(!isfill){
            Toast.makeText(getContext(),"내용을 한칸이라도 입력해주세요!",Toast.LENGTH_SHORT).show();
        }
    }


    // Firestore에서 가져온 모든 데이터 사용
    public void useData(ArrayList<Map<String, Object>> data, int what) {
        switch (what) {
            case 1:
                // 사용하지않는코드
        }
    }
}
