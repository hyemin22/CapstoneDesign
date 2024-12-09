package com.capstoneandroid.gieokdama.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.R;

import java.util.List;

public class PlaceAdapter extends RecyclerView.Adapter<PlaceAdapter.ViewHolder> {

    private List<String> placeList;
    private OnItemClickListener listener;

    public PlaceAdapter(List<String> placeList, OnItemClickListener listener) {
        this.placeList = placeList;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_place, parent, false);

        return new PlaceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String place = placeList.get(position);

        // 장소명과 주소로 나누기
        String[] placeInfo = place.split(" - ");
        String placeName = placeInfo[0];

        // HTML 태그를 제거
        placeName = placeName.replaceAll("<[^>]+>", "");

        String address = placeInfo.length > 1 ? placeInfo[1] : "";

        holder.placeTextView.setText(placeName);
        holder.addressTextView.setText(address);

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(String place);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView placeTextView;
        TextView addressTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            placeTextView = itemView.findViewById(R.id.name); // 적절한 ID로 변경
            addressTextView = itemView.findViewById(R.id.address);
        }
    }
}
