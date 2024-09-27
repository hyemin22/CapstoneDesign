package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MypageFragment extends Fragment {

    private TextView goToFamilyControl;
    private TextView goToAlarm;
    private TextView goToFamilyScrap;
    private TextView goToMyScrap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        // 내 가족관리를 눌렀을 때 AlarmFragment로 이동
        goToFamilyControl = view.findViewById(R.id.family);
        goToFamilyControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FamilyControlFragment로 전환
                FamilyControlFragment familyFragment = new FamilyControlFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, familyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // 가족 공동 스크랩을 눌렀을 때 AlarmFragment로 이동
        goToFamilyScrap = view.findViewById(R.id.familyScrap);
        goToFamilyScrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FamilyScrapFragment 전환
                FamilyScrapFragment familyscrapFragment = new FamilyScrapFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, familyscrapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // 내 스크랩을 눌렀을 때 AlarmFragment로 이동
        goToMyScrap = view.findViewById(R.id.myScrap);
        goToMyScrap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FamilyScrapFragment 전환
                MyScrapFragment myscrapFragment = new MyScrapFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, myscrapFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        // 알림설정을 눌렀을 때 AlarmFragment로 이동
        goToAlarm = view.findViewById(R.id.alarm);
        goToAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlarmFragment로 전환
                AlarmSettingFragment alarmSettingFragment = new AlarmSettingFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, alarmSettingFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return view;
    }
}