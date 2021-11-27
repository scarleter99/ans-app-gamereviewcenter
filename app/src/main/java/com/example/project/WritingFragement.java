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
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.InputStream;

public class WritingFragement extends Fragment implements View.OnClickListener{
    FirebaseStorage st;

    Uri imageUri;

    Spinner spn_writing;
    Spinner spn_kindreview;
    ImageView iv_putplaytime;
    Button btn_Done;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.writing, container, false);
        spn_writing = view.findViewById(R.id.spn_writing_platform);
        spn_kindreview = view.findViewById(R.id.spn_kindreview);
        platform_initspinner();
        kindreview_initspinner();

        iv_putplaytime = view.findViewById(R.id.iv_putplaytime);
        btn_Done = view.findViewById(R.id.btn_done);

        iv_putplaytime.setOnClickListener(this);
        btn_Done.setOnClickListener(this);

        // 샘플코드
        try {
            st = FirebaseStorage.getInstance();
            StorageReference stRef = st.getReference("PC/review1.PNG");
            stRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // Glide 이용하여 이미지뷰에 로딩
                        Glide.with(getActivity()).load(task.getResult()).override(600, 600).into(iv_putplaytime);
                        Log.d("사진 읽기", "Success");
                    } else {
                        Log.d("사진 읽기", "Failure");
                    }
                }
            });
        } catch (Exception e) {
            Log.d("사진 읽기", "Failure");
        }

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
                break;
        }
    }

    private void platform_initspinner(){
        String[] array = {"PC","mobile","Nintendo", "PS4/PS5", "XBOX", "ETC"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_dropdown_item_1line, array);
        spn_writing.setAdapter(adapter);
    }

    private void kindreview_initspinner(){
        String[] array2 = {"스토리","그래픽","난이도", "최적화", "가격", "소통", "진입장벽", "ost", "기타"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, array2);
        spn_kindreview.setAdapter(adapter2);
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
}