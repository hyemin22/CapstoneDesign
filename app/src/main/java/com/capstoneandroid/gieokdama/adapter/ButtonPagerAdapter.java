package com.capstoneandroid.gieokdama.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.R;

public class ButtonPagerAdapter extends RecyclerView.Adapter<ButtonPagerAdapter.ViewHolder> {
    private Context context;

    public ButtonPagerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_region2_gridlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // 동적으로 버튼 추가
        for (int i = 0; i < 4; i++) { // 버튼 수 조정 가능
            Button button = new Button(context);
            button.setText("Button " + (i + 1)); // 버튼 텍스트 설정
            button.setLayoutParams(new GridLayout.LayoutParams(
                    GridLayout.spec(i / 2),  // 행
                    GridLayout.spec(i % 2)   // 열
            ));
            button.setOnClickListener(v -> {
                // 버튼 클릭 시 동작
            });

            // GridLayout에 버튼 추가
            holder.gridLayout.addView(button);
        }
    }

    @Override
    public int getItemCount() {
        return 3; // 페이지 수
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        GridLayout gridLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridLayout = itemView.findViewById(R.id.gridLayout);
        }
    }
}
