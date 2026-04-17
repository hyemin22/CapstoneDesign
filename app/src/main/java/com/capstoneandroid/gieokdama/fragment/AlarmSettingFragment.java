package com.capstoneandroid.gieokdama.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.gieokdama.R;

public class AlarmSettingFragment extends Fragment {

    private ImageButton backButton;
    private Switch switch1, switch2, switch3, switch4, switch5, switch6, switch7, switch8;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_setting, container, false);

        // ImageButton을 찾아서 뒤로 가기 이벤트 처리
        backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 백 스택에서 이전 프래그먼트로 돌아가기
                if (getFragmentManager() != null) {
                    getFragmentManager().popBackStack();
                }
            }
        });

        // 스위치들을 초기화
        switch1 = view.findViewById(R.id.switch1);
        switch2 = view.findViewById(R.id.switch2);
        switch3 = view.findViewById(R.id.switch3);
        switch4 = view.findViewById(R.id.switch4);
        switch5 = view.findViewById(R.id.switch5);
        switch6 = view.findViewById(R.id.switch6);
//        switch7 = view.findViewById(R.id.switch7);
        switch8 = view.findViewById(R.id.switch8);

        // 첫 번째 스위치의 상태가 변경되었을 때 나머지 스위치들도 변경되도록 설정
        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // 첫 번째 스위치가 체크되면 나머지 스위치들도 체크/언체크
                setAllSwitches(isChecked);
            }
        });

        return view;
    }

    // 나머지 스위치들의 상태를 첫 번째 스위치에 맞춰 설정하는 메서드
    private void setAllSwitches(boolean isChecked) {
        switch2.setChecked(isChecked);
        switch3.setChecked(isChecked);
        switch4.setChecked(isChecked);
        switch5.setChecked(isChecked);
        switch6.setChecked(isChecked);
        switch7.setChecked(isChecked);
        switch8.setChecked(isChecked);
    }
}