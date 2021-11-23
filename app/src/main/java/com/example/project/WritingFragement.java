package com.example.project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class WritingFragement extends Fragment {
    Spinner SPN_writing;
    Spinner SPN_kindreview;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.writing, container, false);
        SPN_writing = view.findViewById(R.id.SPN_writing);
        SPN_kindreview = view.findViewById(R.id.SPN_kindreview);
        platform_initspinner();
        kindreview_initspinner();
        return view;
    }

    private void platform_initspinner(){

        String[] array = {"PC","mobile","Nintendo", "PS4/PS5", "XBOX", "ETC"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, array);
        SPN_writing.setAdapter(adapter);
    }

    private void kindreview_initspinner(){
        String[] array2 = {"스토리","그래픽","난이도", "최적화", "가격", "소통", "진입장벽", "ost", "기타"};
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, array2);
        SPN_kindreview.setAdapter(adapter2);
    }
}
