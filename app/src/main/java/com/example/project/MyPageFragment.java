package com.example.project;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;

public class MyPageFragment extends Fragment {

    FirebaseFirestore db;
    TextView MP_ID,MP_NickName,chkid;
    EditText MP_PW,MP_Email;
    String ID,PWChange,EmailChange,idchk;
    SharedPreferences spref;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mypage, container, false);
        MP_ID = (TextView) view.findViewById(R.id.mypage_IdShow);
        MP_NickName = (TextView) view.findViewById(R.id.mypage_IdShow2);
        MP_PW = (EditText) view.findViewById(R.id.mypage_Idshow3);
        MP_Email = (EditText) view.findViewById(R.id.mypage_Idshow4);
        db = FirebaseFirestore.getInstance();
        chkid = (TextView) view.findViewById(R.id.mp_chkid);
        spref = this.getActivity().getSharedPreferences("gref", Context.MODE_PRIVATE);
        SetMyPage(view);
        Button Change;
        Change = (Button) view.findViewById(R.id.mypage_change);
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeClicked(v);
            }
        });
        return view;
    }

    private boolean passCheck(String pass) {
        String pattern = "^.*(?=^.{8,15}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$"; //비밀번호 (숫자, 문자, 특수문자 포함 8~15자리 이내)
        if (!(Pattern.matches(pattern, pass))) {
            return false;
        }
        else{
            return  true;
        }
    }
    private boolean emailCheck(String nickname){
        String pattern = "[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]{2,8}";
        if(Pattern.matches(pattern, nickname)){ // 숫자, 영어로만 이루어져있다면 true
            return true;
        }
        else{
            return false;
        }
    }

    public void ChangeClicked(View v) {
        ID = MP_ID.getText().toString();
        PWChange = MP_PW.getText().toString();
        EmailChange = MP_Email.getText().toString();
        if(passCheck(PWChange) && emailCheck(EmailChange)){
            updateData("user",ID,"email",PWChange);
            updateData("user",ID,"pw",EmailChange);
        }
        else{
            Toast.makeText(getContext(), "올바른 형식이 아닙니다."+"\n"+ "비밀번호 : 숫자, 문자, 특수문자 포함 8~15자리 이내구성"+"\n"+ "이메일 : 영어 ,@, 숫자로만 구성", Toast.LENGTH_SHORT).show();
        }
    }

    public void SetMyPage(View v){
        String text = spref.getString("editText_id","");
        getData("user", text, 2);
        getData("user", text, 3);
        getData("user", text, 4);
        getData("user", text, 5);
    }

    // Firestore에 데이터 추가
    // 사용법:
    // Map<String, Object> data = new HashMap<>();
    // data.put(key1, value1);
    // data.put(key2, value2);
    // putData("user", "zxcv", data);
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

    // Firestore에서 데이터 가져오기
    public void getData(String collec, String doc, int what) {
        DocumentReference docRef = db.collection(collec).document(doc);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("데이터 가져오기", "DocumentSnapshot data: " + document.getData());
                        useData(document.getData(), what);
                    } else {
                        Log.d("데이터 가져오기", "No such document");
                    }
                } else {
                    Log.d("데이터 가져오기", "get failed with ", task.getException());
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
            //PW
            case 2:
                String[] splitID = data.toString().split(",");
                String[] splitID2 = splitID[2].split("=");
                MP_ID.setText(splitID2[1]);
                break;
            //NickName
            case 3:
                String[] splitNickName = data.toString().split(",");
                String[] splitNickName2 = splitNickName[1].split("=");
                MP_NickName.setText(splitNickName2[1]);
                break;
            //Name
            case 4:
                String[] splitEmail = data.toString().split(",");
                String[] splitEmail2 = splitEmail[0].split("=");
                MP_Email.setText(splitEmail2[1]);
                break;
            //Email
            case 5:
                String[] splitPW = data.toString().split(",");
                String[] splitPW2 = splitPW[3].split("=");
                String splitPW3 = splitPW2[1].replace("}","");
                MP_PW.setText(splitPW3);
                break;
        }
    }

    // Firestore에서 가져온 모든 데이터 사용
    public void useData(ArrayList<Map<String, Object>> data, int what) {
        switch (what) {
            case 1:
                //미사용
        }
    }
}
