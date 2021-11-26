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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class WritingFragement extends Fragment implements View.OnClickListener{
    FirebaseStorage st;

    Uri imageUri;

    Spinner spn_writing;
    Spinner spn_kindreview;
    ImageView iv_playtime;
    Button btn_Done;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.writing, container, false);
        spn_writing = view.findViewById(R.id.spn_writing_platform);
        spn_kindreview = view.findViewById(R.id.spn_kindreview);
        platform_initspinner();
        kindreview_initspinner();

        iv_playtime = view.findViewById(R.id.iv_playtime);
        btn_Done = view.findViewById(R.id.btn_done);

        iv_playtime.setOnClickListener(this);
        btn_Done.setOnClickListener(this);

        return view;
    }

    public void onClick(View v) { //implements View.OnClickListener
        switch (v.getId()) {
            case R.id.iv_playtime:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                launcher.launch(intent);
                break;
            case R.id.btn_done:
                putImage("PC", "review2"); //샘플코드
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

    public void putImage(String platform, String reviewNum) {
        st = FirebaseStorage.getInstance();

        StorageReference storageRef = st.getReference();
        //StorageReference mountainsRef = storageRef.child("review1.jpg");
        //StorageReference mountainImagesRef = storageRef.child("PC/review1.jpg");

        StorageReference imageRef = storageRef.child(platform + "/"+ reviewNum + ".jpg");
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
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>()
            {
                @Override
                public void onActivityResult(ActivityResult result)
                {
                    if (result.getResultCode() == Activity.RESULT_OK)
                    {
                        Intent intent = result.getData();
                        imageUri = intent.getData();
                        iv_playtime.setImageURI(imageUri);
                    }
                }
            });
}
