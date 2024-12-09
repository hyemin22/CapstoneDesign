package com.capstoneandroid.gieokdama.fragment;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.activity.MissionActivity;
import com.capstoneandroid.gieokdama.model.User;
import com.capstoneandroid.gieokdama.repository.UserRepository;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment5 extends Fragment {
    Long userId = UserInfoManager.getInstance().getUserId();
    private TextView nickname1, nickname2, phone_number,
            goToFamilyControl, goToAlarm, goToFamilyScrap, goToMyScrap, goToMission;
    private CircleImageView profile;
;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment5, container, false);

        nickname1 = view.findViewById(R.id.nickname1);
        nickname2 = view.findViewById(R.id.nickname2);
        phone_number = view.findViewById(R.id.phone_number);
        profile = view.findViewById(R.id.profile);
        goToFamilyControl = view.findViewById(R.id.family);
        goToAlarm = view.findViewById(R.id.alarm);
//        goToFamilyScrap = view.findViewById(R.id.familyScrap);
//        goToMyScrap = view.findViewById(R.id.myScrap);
        goToMission = view.findViewById(R.id.mission);

        // 유저 이름, 전화번호, 캐릭터 띄우기
        // 서버로 GET 요청 보내기
        getUserInfoData();

        // 내 가족관리를 눌렀을 때 AlarmFragment로 이동
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

        // 미션 관리 페이지를 눌렀을 때 MissionAcitivity로 이동
        goToMission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), MissionActivity.class);
                startActivity(intent);
            }
        });

//        // 가족 공동 스크랩을 눌렀을 때 AlarmFragment로 이동
//        goToFamilyScrap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // FamilyScrapFragment 전환
//                FamilyScrapFragment familyscrapFragment = new FamilyScrapFragment();
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, familyscrapFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });
//
//        // 내 스크랩을 눌렀을 때 AlarmFragment로 이동
//        goToMyScrap.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // FamilyScrapFragment 전환
//                MyScrapFragment myscrapFragment = new MyScrapFragment();
//                FragmentManager fragmentManager = getParentFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.container, myscrapFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();
//            }
//        });

        // 알림설정을 눌렀을 때 AlarmFragment로 이동
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

    private void getUserInfoData() {
        // 서버로 Get 요청 보내기
        UserRepository userRepository = new UserRepository();
        userRepository.getUserInfo(userId, new UserRepository.GetInfoCallback() {
            @Override
            public void onInfoGetSuccess(User user) {
                nickname1.setText(user.getNickname());
                nickname2.setText(user.getNickname());
                phone_number.setText(user.getPhone_number());

                //선택한 캐릭터 띄워주기
                String ch_name = user.getCharacter_choice();
                int drawableId = getResources().getIdentifier(ch_name, "drawable", getContext().getPackageName());
                System.out.println("drawable" + drawableId);

                if (drawableId != 0) {
                    profile.setImageResource(drawableId);
                } else {
                    profile.setImageResource(R.drawable.ic_my);
                }
            }

            @Override
            public void onInfoGetFailure(String errorMessage) {
                Log.e(TAG, "유저 정보 가져오기 실패: " + errorMessage);
            }
        });
    }
}