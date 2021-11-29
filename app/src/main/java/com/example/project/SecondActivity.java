package com.example.project;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_second);

        //상단 타이틀바 제거
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //프레그먼트 변수설정
        Fragment fragment_board;
        Fragment fragment_mypage;
        Fragment fragment_writing;
        fragment_board = new BoardFragment();
        fragment_mypage = new MyPageFragment();
        fragment_writing = new WritingFragement();

        // 바텀네비게이션 변수 설정
        BottomNavigationView bnv_menu;
        bnv_menu = (BottomNavigationView) findViewById(R.id.bnv_menu);

        // 처음화면 게시판으로 설정
        getSupportFragmentManager().beginTransaction().add(R.id.layout_activity_main_second, new BoardFragment()).commit();

        // 바텀네비게이션 화면 전환
        bnv_menu.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                switch (item.getItemId()) {
                    case R.id.item_board:
                        ft.replace(R.id.layout_activity_main_second, fragment_board);
                        ft.commit();
                        return true;
                    case R.id.item_mypage:
                        ft.replace(R.id.layout_activity_main_second, fragment_mypage);
                        ft.commit();
                        return true;
                    case R.id.item_writing:
                        ft.replace(R.id.layout_activity_main_second, fragment_writing);
                        ft.commit();
                        return true;
                }
                return true;
            }
        });
    }

}

