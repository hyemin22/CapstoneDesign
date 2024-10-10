package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.capstonedesign.R;

public class Fragment5 extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment5, container, false);

        initUI(rootView);

        return rootView;
    }

    private void initUI(ViewGroup rootView) {

    }
}