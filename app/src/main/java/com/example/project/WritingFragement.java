package com.example.project;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WritingFragement extends Fragment implements View.OnClickListener{
    FirebaseStorage st;
    FirebaseFirestore db;

    Uri imageUri;

    RatingBar rb;
    Spinner spn_writing;
    ImageView iv_putplaytime;
    Button btn_Done;
    EditText WR_title,WR_gametitle,WR_attr1,WR_attr2,WR_attr3,WR_attr4,WR_attr5,WR_attr6,WR_attr7,WR_attr8;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.writing, container, false);
        spn_writing = (Spinner) view.findViewById(R.id.spn_writing_platform);
        platform_initspinner();

        WR_title = (EditText) view.findViewById(R.id.et_writing_title);
        WR_gametitle = (EditText) view.findViewById(R.id.et_writing_gamename);
        WR_attr1 = (EditText) view.findViewById(R.id.et_content1);
        WR_attr2 = (EditText) view.findViewById(R.id.et_content2);
        WR_attr3 = (EditText) view.findViewById(R.id.et_content3);
        WR_attr4 = (EditText) view.findViewById(R.id.et_content4);
        WR_attr5 = (EditText) view.findViewById(R.id.et_content5);
        WR_attr6 = (EditText) view.findViewById(R.id.et_content6);
        WR_attr7 = (EditText) view.findViewById(R.id.et_content7);
        WR_attr8 = (EditText) view.findViewById(R.id.et_content8);
        rb = (RatingBar) view.findViewById(R.id.rb_rating);

        iv_putplaytime = view.findViewById(R.id.iv_putplaytime);
        btn_Done = view.findViewById(R.id.btn_done);

        iv_putplaytime.setOnClickListener(this);
        btn_Done.setOnClickListener(this);

        return view;
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
                putImage("review1"); //샘플코드
                Map<String, Object> data = new HashMap<>();
                String spn = spn_writing.getSelectedItem().toString();
                float f_rating = rb.getRating();
                Toast.makeText(getContext(),spn,Toast.LENGTH_SHORT).show();
                break;
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
                //TV_sample.setText(data.toString()); // 샘플코드
        }
    }

    // Firestore에서 가져온 모든 데이터 사용
    public void useData(ArrayList<Map<String, Object>> data, int what) {
        switch (what) {
            case 1:
                //TV_sample.setText(data.toString()); // 샘플코드
        }
    }
}
