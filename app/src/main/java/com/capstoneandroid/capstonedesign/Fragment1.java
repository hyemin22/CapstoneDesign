package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class Fragment1 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        initUI(rootView);

        return rootView;
    }

    //xml 레이아웃 안에 들어 있는 위젯이나 레이아웃을 찾아 변수에 할당하는 코드들을 넣기 위해 만듦
    private void initUI(ViewGroup rootView) {

    }
}
