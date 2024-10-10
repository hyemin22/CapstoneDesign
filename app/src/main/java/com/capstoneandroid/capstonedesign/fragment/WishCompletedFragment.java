package com.capstoneandroid.capstonedesign.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.item.WishCompletedItem;
import com.capstoneandroid.capstonedesign.adapter.WishCompletedAdapter;

public class WishCompletedFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public WishCompletedFragment() {
        // Required empty public constructor
    }

    public static FeedCalMonthFragment newInstance(String param1, String param2) {
        FeedCalMonthFragment fragment = new FeedCalMonthFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_wish_completed, container, false);

        initUI(rootView);
        return rootView;
    }

    private void initUI(ViewGroup rootView) {

        //위시리스트
        RecyclerView recyclerView1 = rootView.findViewById(R.id.wishCompletedView);
        LinearLayoutManager linearManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView1.setLayoutManager(linearManager);
        WishCompletedAdapter adapter = new WishCompletedAdapter(getContext());

        adapter.addItem(new WishCompletedItem(getContext(),"❤️","다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"🔥","대전 랑골로에서 파스타 먹기", "2024년 5월 11일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"✈️","다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"🖌️","대전 랑골로에서 파스타 먹기", "2024년 5월 11일 예정"));
        adapter.addItem(new WishCompletedItem(getContext(),"🎧","다같이 한강 가서 치킨 먹기", "2024년 5월 5일 예정"));

        recyclerView1.setAdapter(adapter);
    }
}