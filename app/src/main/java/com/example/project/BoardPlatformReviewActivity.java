package com.example.project;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class BoardPlatformReviewActivity extends AppCompatActivity {

    FirebaseStorage st;
    Uri imageUri;

    ImageView iv_getplaytime;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_getplaytime = findViewById(R.id.iv_getplaytime);

        getImage("PC", "review3");

    }
    public void getImage(String platform, String review) {
        st = FirebaseStorage.getInstance();
        StorageReference stRef = st.getReference(platform + "/" + review + ".PNG");
        //Glide.with(this).load(stRef).into(iv_putplaytime);

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
    }
}
