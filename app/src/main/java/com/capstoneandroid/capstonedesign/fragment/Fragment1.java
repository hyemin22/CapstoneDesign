package com.capstoneandroid.capstonedesign.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;
import androidx.viewpager2.widget.ViewPager2.PageTransformer;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.activity.AlarmActivity;
import com.capstoneandroid.capstonedesign.activity.GuestBookCreateActivity;
import com.capstoneandroid.capstonedesign.activity.MissionActivity;
import com.capstoneandroid.capstonedesign.adapter.DayMissionAdapter;
import com.capstoneandroid.capstonedesign.adapter.GuestbookAdapter;
import com.capstoneandroid.capstonedesign.adapter.HomeWishAdapter;
import com.capstoneandroid.capstonedesign.item.DayMissionItem;
import com.capstoneandroid.capstonedesign.item.GuestbookItem;
import com.capstoneandroid.capstonedesign.item.HomeWishItem;
import com.capstoneandroid.capstonedesign.item.MyMissionItem;
import com.capstoneandroid.capstonedesign.item.WishListItem;
import com.capstoneandroid.capstonedesign.repository.GuestBookRepository;
import com.capstoneandroid.capstonedesign.repository.MissionRepository;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;

import java.util.ArrayList;
import java.util.List;

//홈화면
public class Fragment1 extends Fragment {
    Long userId = UserInfoManager.getInstance().getUserId();
    private ImageButton post, guestWrite, imgBtnArrow, imgBtnArrow2;
    private GuestbookAdapter adapter;  // 어댑터 선언
    private HomeWishAdapter adapter2;
    private DayMissionAdapter adapter3;
    private ArrayList<GuestbookItem> items = new ArrayList<>(); //방명록 아이템 추가
    private ArrayList<WishListItem> items2 = new ArrayList<>();
    private ArrayList<MyMissionItem> items3 = new ArrayList<>();
    private ViewPager2 viewPager;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment1, container, false);

        initUI(rootView);

        return rootView;
    }

    //xml 레이아웃 안에 들어 있는 위젯이나 레이아웃을 찾아 변수에 할당하는 코드들을 넣기 위해 만듦
    private void initUI(ViewGroup rootView) {

        //---------------알림 및 쪽지------------------
        //알림 및 쪽지 버튼 클릭 시 동작
        post = rootView.findViewById(R.id.post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        //    // 쪽지 토스트 메시지
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.icon_post) {
//
//            LayoutInflater inflater = getLayoutInflater();
//
//            View layout = inflater.inflate(
//                    R.layout.toast_post, (ViewGroup) findViewById(R.id.toast_layout_root));
//
//            TextView text = layout.findViewById(R.id.text);
//
//            Toast toast = new Toast(this);
//            text.setText("쪽지가 도착했어요.");
//            toast.setGravity(Gravity.TOP, 400, 110);
//            toast.setDuration(Toast.LENGTH_SHORT);
//            toast.setView(layout);
//            toast.show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

        //---------------방명록------------------
        //방명록 추가 버튼 클릭 시 동작
        guestWrite = rootView.findViewById(R.id.guestWrite);
        guestWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = items.size();
                Intent intent = new Intent(getActivity(), GuestBookCreateActivity.class);
                intent.putExtra("source_activity", "Fragment1"); //액티비티 구분 위한 식별자
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

        // 서버로 방명록 get 요청 보내기
        sendGetGuestBookData();

        adapter = new GuestbookAdapter(items, getContext());
        viewPager = rootView.findViewById(R.id.guestView);

        viewPager.setOffscreenPageLimit(3);
        viewPager.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager.setAdapter(adapter);

        CompositePageTransformer transform = new CompositePageTransformer();
        transform.addTransformer(new MarginPageTransformer(8));
        transform.addTransformer(new PageTransformer() {
            @Override
            public void transformPage(View view, float position) {
                float v = 1 - Math.abs(position);

                // 중앙에 있는 페이지의 크기를 줄입니다.
                float scaleFactor = 0.8f + v * 0.2f;

                // 아이템을 겹치게 하기 위해 페이지 간격을 설정합니다.
                float pageOffset = 200; // 페이지 간격을 150픽셀로 설정합니다.
                float pageMargin = 20; // 페이지 마진을 20픽셀로 설정합니다.
                float myOffset = position * -(2 * pageOffset + pageMargin);

                // 페이지의 크기를 설정합니다.
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // 페이지의 위치를 조절하여 겹치도록 합니다.
                view.setTranslationX(myOffset);

                // 페이지 투명도를 조절합니다.
                float alphafront = 1.0f;
                float alphaback = 0.5f; // 뒤에 보이는 페이지 투명도
                float alphaelse = 0.0f; // 그 외의 페이지 투명도

                // 현재 보이는 페이지는 투명도를 1로 설정하고, 바로 앞, 뒤 페이지들은 투명도를 조절합니다. 그 외의 페이지는 보이지 않도록 설정합니다.
                if (position == 0) {
                    view.setAlpha(alphafront);
                    view.setTranslationZ(0); // 현재 페이지는 최상위에 위치
                } else if((position > 0 && position <= 1) || (position < 0 && position >= -1)){
                    view.setAlpha(alphaback);
                    view.setTranslationZ(-1); // 다른 페이지들을 뒤에 위치시킵니다.
                } else {
                    view.setAlpha(alphaelse);
                }
            }
        });

        viewPager.setPageTransformer(transform);

        //---------------위시리스트------------------
        //위시리스트 화면으로 넘어가는 > 버튼 클릭 시 동작
        imgBtnArrow = rootView.findViewById(R.id.imgBtnArrow);
        imgBtnArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent 대신 FragmentTransaction 사용
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                Fragment2 fragment2 = new Fragment2();

                // Fragment2로 전환
                transaction.replace(R.id.container, fragment2);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // 서버로 위시 get 요청 보내기
        sendGetRecentWishData();

        RecyclerView recyclerView1 = rootView.findViewById(R.id.wishlistView);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(linearManager);
        adapter2 = new HomeWishAdapter(items2, getContext());

        recyclerView1.setAdapter(adapter2);

        //---------------하루 미션------------------
        //미션 메인 화면으로 넘어가는 > 버튼 클릭 시 동작
        imgBtnArrow2 = rootView.findViewById(R.id.imgBtnArrow2);
        imgBtnArrow2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MissionActivity.class);
                startActivity(intent);
            }
        });

        // 서버로 하루 미션 get 요청 보내기
        sendGetTodayMissionData();

        //하루 미션 리사이클러뷰
        RecyclerView recyclerView2 = rootView.findViewById(R.id.daymissionView);
        GridLayoutManager gridManager = new GridLayoutManager(getContext(), 2);
        recyclerView2.setLayoutManager(gridManager);
        adapter3 = new DayMissionAdapter(items3, getContext());

        //하루 미션
        recyclerView2.setAdapter(adapter3);

    }

    private void sendGetGuestBookData() {
        GuestBookRepository guestBookRepository = new GuestBookRepository();
        // 방명록 데이터 가져오기
        guestBookRepository.getUsersGuestBook(userId, new GuestBookRepository.GetGuestBookCallback() {
            @Override
            public void onIDGetSuccess(List<GuestbookItem> guestbookItems) {
                getActivity().runOnUiThread(() -> {
                    // items 리스트에 서버에서 받아온 응답 데이터 추가
                    items.clear(); // 기존 데이터 초기화 (필요 시)

                    // guestbookItems 리스트를 역순으로 순회하면서 아이템을 추가 - 최신순으로
                    for (int i = guestbookItems.size() - 1; i >= 0; i--) {
                        GuestbookItem guestbookItem = guestbookItems.get(i);
                        items.add(new GuestbookItem(
                                guestbookItem.getId(),
                                guestbookItem.getCharacter_choice(), // 기본 프로필 이미지
                                guestbookItem.getContent(), // 방명록 내용
                                guestbookItem.getNickname() // 작성자 이름
                        ));
                    }

                    // 어댑터에 변경 사항을 알림
                    adapter.notifyDataSetChanged();

                    if (!guestbookItems.isEmpty()) {
                        viewPager.setCurrentItem(0, false); // 마지막 페이지
                    }
                });
            }

            @Override
            public void onIDGetFailure(String errorMessage) {
                Log.e("Error", "방명록 조회 실패: " + errorMessage);
            }
        });
    }

    private void sendGetTodayMissionData() {
        MissionRepository missionRepository = new MissionRepository();
        missionRepository.getTodayMissionFromServer(userId, new MissionRepository.GetMyMissionListCallback() {
            @Override
            public void onSuccess(List<MyMissionItem> myMissionItems) {
                if (getActivity() != null) {
                    getActivity().runOnUiThread(() -> {
                        items3.clear();
                        for (MyMissionItem myMissionItem : myMissionItems) {
                            items3.add(new MyMissionItem(
                                    getContext(),
                                    myMissionItem.getId(),
                                    myMissionItem.getTitle(),
                                    myMissionItem.getEmoji(),
                                    myMissionItem.getCycle(),
                                    myMissionItem.getRepeat_day(),
                                    myMissionItem.getRepeat_time(),
                                    myMissionItem.getNow_time(),
                                    myMissionItem.getGoal_time(),
                                    myMissionItem.getPercent(),
                                    myMissionItem.getAlarm(),
                                    myMissionItem.getAlarm_time()
                            ));
                        }

                        // 어댑터에 변경 사항을 알림
                        adapter3.notifyDataSetChanged();
                    });
                }
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "하루 미션 조회 실패: " + errorMessage);
            }
        });
    }

    private void sendGetRecentWishData() {
        WishListRepository wishListRepository = new WishListRepository();
        wishListRepository.getFamilyRecentWishList(userId, new WishListRepository.GetListCallback() {
            @Override
            public void onListGetSuccess(List<WishListItem> wishLists) {
                getActivity().runOnUiThread(() -> {
                    items2.clear();
                    for (WishListItem wishListItem : wishLists) {
                        items2.add(new WishListItem(
                                getContext(),
                                wishListItem.getId(), // 아이디
                                wishListItem.getTitle(), // 제목
                                wishListItem.getStartDate(), // 시작날짜
                                wishListItem.getEndDate(), // 끝 날짜
                                wishListItem.getCategory(), // 카테고리
                                wishListItem.getEmoji(), // 이모지
                                wishListItem.getAlarm(), // 알람 여부
                                wishListItem.getMemo(), // 메모
                                wishListItem.getCompletedDate(), // 완료일
                                wishListItem.getDday() // 디데이
                        ));
                    }
                    // 어댑터에 변경 사항을 알림
                    adapter2.notifyDataSetChanged();
                });
            }

            @Override
            public void onListGetFailure(String errorMessage) {
                Log.e("Error", "2주 내의 위시 조회 실패: " + errorMessage);
            }
        });
    }
    private boolean isReceiverRegistered = false;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int deletePosition = intent.getIntExtra("delete_position", -1);
            Log.d("Fragment1", "Delete position received: " + deletePosition);
            if (deletePosition != -1) {
                adapter.removeItem(deletePosition);
            } else {
                Log.d("Fragment1", "Invalid delete position received");
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        if (!isReceiverRegistered) {
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter("DELETE_GUESTBOOK_ITEM"));
            isReceiverRegistered = true;
            Log.d("Fragment1", "Receiver registered");
        }
        //LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, new IntentFilter("DELETE_GUESTBOOK_ITEM"));
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isReceiverRegistered) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
            isReceiverRegistered = false;
            Log.d("Fragment1", "Receiver unregistered");
        }
        //LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

}
