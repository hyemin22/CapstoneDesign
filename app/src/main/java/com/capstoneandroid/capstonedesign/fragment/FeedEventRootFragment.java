package com.capstoneandroid.capstonedesign.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.adapter.AlbumAdapter;
import com.capstoneandroid.capstonedesign.item.AlbumItem;
import com.capstoneandroid.capstonedesign.repository.DiaryRepository;

import java.util.ArrayList;
import java.util.List;

public class FeedEventRootFragment extends Fragment {
    Long userId = UserInfoManager.getInstance().getUserId();
    private ArrayList<AlbumItem> items = new ArrayList<>();
    private AlbumAdapter adapter, adapter2;
    private static final int REQUEST_CODE_ADD_ALBUM = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_event_root, container, false);

        // 서버로 앨범 get 요청 보내기
        getAlbumList();

        adapter = new AlbumAdapter(items, getContext(), false);
        adapter2 = new AlbumAdapter(items, getContext(), true);

        //초기 뷰를 스와이프 뷰로 설정
        showSwipeView();

        return rootView;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_ADD_ALBUM && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                String albumTitle = data.getStringExtra("albumTitle");
                int selectedColor = data.getIntExtra("selectedColor", -1);
                int selectedAlbum = 0;

                if(selectedColor == R.color.album_yellow) {
                    selectedAlbum = R.drawable.album_yellow;
                } else if (selectedColor ==R.color.album_red) {
                    selectedAlbum = R.drawable.album_red;
                } else if (selectedColor ==R.color.album_blue) {
                    selectedAlbum = R.drawable.album_blue;
                } else if (selectedColor ==R.color.album_purple) {
                    //selectedAlbum = R.drawable.album_purple;
                } else {
                    selectedAlbum = R.drawable.album_white;
                }

                // 새로운 앨범 아이템을 추가
                items.add(new AlbumItem(albumTitle, selectedAlbum));
                adapter.notifyDataSetChanged();
                adapter2.notifyDataSetChanged();
            }
        }
    }

    void showSwipeView() {
        // 스와이프 뷰를 프래그먼트로 전환
        Fragment swipeFragment = new FeedEventSwipeFragment(adapter);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, swipeFragment)
                .commit();
    }

    void showListView() {
        // 리스트 뷰를 프래그먼트로 전환
        Fragment listFragment = new FeedEventListFragment(adapter2);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.container, listFragment)
                .commit();
    }

    private void getAlbumList() {
        DiaryRepository diaryRepository = new DiaryRepository();
        diaryRepository.getAlbumList(userId, new DiaryRepository.GetAlbumCallback() {
            @Override
            public void onSuccess(List<AlbumItem> albums) {
                getActivity().runOnUiThread(() -> {
                    items.clear();

                    for (int i = albums.size() - 1; i >= 0; i--) {
                        AlbumItem albumItem = albums.get(i);
                        items.add(new AlbumItem(
                                albumItem.getId(),
                                albumItem.getTitle(),
                                albumItem.getColor()
                        ));

                        Log.d("album", "앨범조회" + albumItem.getId() + "," + albumItem.getTitle() + "," + albumItem.getColor());
                    }
                    adapter.notifyDataSetChanged();
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "앨범 조회 실패: " + errorMessage);
            }
        });
    }
}

