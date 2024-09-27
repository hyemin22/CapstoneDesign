package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FamilyControlFragment extends Fragment {

    private ImageButton backButton;
    private TextView familySetting;
    private ImageView familySettingImage1, familySettingImage2, familySettingImage3; // 가족 설정 이미지를 담는 ImageView들
    private boolean isEditing = false; // 편집 상태를 확인하는 플래그

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_family_control, container, false);

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

        // TextView를 초기화하고 클릭 이벤트 설정
        familySetting = view.findViewById(R.id.familysetting);

        // ImageView 초기화
        familySettingImage1 = view.findViewById(R.id.familySettingImage1);
        familySettingImage2 = view.findViewById(R.id.familySettingImage2);
        familySettingImage3 = view.findViewById(R.id.familySettingImage3);

        familySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEditing) {
                    // "완료" 상태에서 다시 "편집"으로 변경
                    familySetting.setText("편집");

                    // 가족 설정 이미지를 다시 family_setting으로 변경
                    familySettingImage1.setImageResource(R.drawable.family_setting);
                    familySettingImage2.setImageResource(R.drawable.family_setting);
                    familySettingImage3.setImageResource(R.drawable.family_setting);

                    isEditing = false;
                } else {
                    // "편집" 상태에서 "완료"로 변경
                    familySetting.setText("완료");

                    // 가족 설정 이미지를 delete.png로 변경
                    familySettingImage1.setImageResource(R.drawable.delete);
                    familySettingImage2.setImageResource(R.drawable.delete);
                    familySettingImage3.setImageResource(R.drawable.delete);

                    isEditing = true;
                }
            }
        });

        return view;
    }
}