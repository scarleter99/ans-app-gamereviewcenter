package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BoardFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.board, container, false);
        ImageButton btn_pc, btn_mobile, btn_nintendo, btn_ps4ps5, btn_xbox, btn_etc;
        btn_pc = (ImageButton) view.findViewById(R.id.btn_pc);
        btn_mobile = (ImageButton) view.findViewById(R.id.btn_mobile);
        btn_nintendo = (ImageButton) view.findViewById(R.id.btn_nintendo);
        btn_ps4ps5 = (ImageButton) view.findViewById(R.id.btn_ps4ps5);
        btn_xbox = (ImageButton) view.findViewById(R.id.btn_xbox);
        btn_etc = (ImageButton) view.findViewById(R.id.btn_etc);

        //pc버튼 클릭
        btn_pc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board_Platform_Activity.class);
                startActivity(intent);
            }
        });

        //mobile버튼 클릭
        btn_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board_Platform_Mobile_Activity.class);
                startActivity(intent);
            }
        });

        //닌텐도버튼 클릭
        btn_nintendo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board_Platform_Nintendo_Activity.class);
                startActivity(intent);
            }
        });

        //플레이스테이션버튼 클릭
        btn_ps4ps5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board_Platform_Ps4ps5_Activity.class);
                startActivity(intent);
            }
        });

        //xbox버튼 클릭
        btn_xbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board_Platform_Xbox_Activity.class);
                startActivity(intent);
            }
        });

        //etc버튼 클릭
        btn_etc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Board_Platform_Etc_Activity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
