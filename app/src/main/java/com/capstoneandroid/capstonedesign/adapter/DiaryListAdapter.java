package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.capstoneandroid.capstonedesign.activity.DiaryActivity;
import com.capstoneandroid.capstonedesign.item.DiaryListItem;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.repository.DiaryRepository;

import java.util.ArrayList;
import java.util.List;

public class DiaryListAdapter extends RecyclerView.Adapter<DiaryListAdapter.ViewHolder> {

    //객체 리스트
    ArrayList<DiaryListItem> items = new ArrayList<DiaryListItem>();
    Context context;
    boolean isEditMode = false; //편집 모드인가?

    public DiaryListAdapter(ArrayList<DiaryListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    public void setEditMode(boolean isEditMode) {
        this.isEditMode = isEditMode;
        notifyDataSetChanged(); //모든 아이템 새로고침
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public DiaryListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_diarylist, parent, false);

        return new DiaryListAdapter.ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull DiaryListAdapter.ViewHolder holder, int pos) {
        DiaryListItem item = items.get(pos);
        holder.setItem(item); //아이템 데이터 바인딩

        if(isEditMode) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            // position을 final로 저장
            final int position = pos;
            holder.deleteButton.setOnClickListener(new View.OnClickListener() { // 편집모드일 때: 삭제버튼 표시, 버튼 클릭 시 아이템 삭제
                @Override
                public void onClick(View view) {
                    // DB에서 일기 삭제
                    deleteDiaryData(item.getId());

                    items.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, items.size());
                }
            });
            // 편집모드에서는 itemView 클릭 리스너 비활성화
            holder.itemView.setOnClickListener(null);
        } else {
            holder.deleteButton.setVisibility(View.INVISIBLE);
            holder.itemView.setOnClickListener(new View.OnClickListener() { // 편집모드 아닐 때: 클릭 시 특정 일기 화면으로 넘어감
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DiaryActivity.class);
                    intent.putExtra("id", item.getId());
                    intent.putExtra("title", item.getTitle());
                    intent.putExtra("diary_date", item.getDiary_date());
                    ArrayList<String> imagePaths = new ArrayList<>(item.getImagePaths());  // List<String>을 ArrayList<String>으로 변환하여 전달
                    intent.putStringArrayListExtra("imagePaths", imagePaths);  // imagePaths 전달
                    intent.putExtra("content", item.getContent());
                    intent.putExtra("address", item.getAddress());
                    intent.putExtra("album_title", item.getAlbum_title());
                    intent.putExtra("user_character", item.getUser_character());
                    intent.putExtra("user_nickname", item.getUser_nickname());

                    context.startActivity(intent);
                }
            });
        }
    }

    private void deleteDiaryData(Long diaryId) {
        // 서버로 DELETE 요청 보내기
        DiaryRepository diaryRepository = new DiaryRepository();
        diaryRepository.deleteDiary(diaryId, new DiaryRepository.DiaryCallback() {
            @Override
            public void onSuccess() {
                // 일기 삭제 성공
                Log.d("DiaryListAdapter", "일기가 성공적으로 삭제되었습니다");
            }

            @Override
            public void onFailure(String errorMessage) {
                // 일기 삭제 실패
                Log.e("DiaryListAdapter", "일기 삭제 실패: " + errorMessage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(DiaryListItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<DiaryListItem> items) {
        this.items = items;
    }

    public DiaryListItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, DiaryListItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        GridLayout gridLayout;
        TextView titleTextView, dateTextView;
        ImageView deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            gridLayout = itemView.findViewById(R.id.gridLayout);
            titleTextView = itemView.findViewById(R.id.title);
            dateTextView = itemView.findViewById(R.id.date);
            deleteButton = itemView.findViewById(R.id.deleteBtn);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(DiaryListItem item) {
            List<String> imagePaths = item.getImagePaths();
            int imageNum = imagePaths.size();

            //기존 이미지뷰들 초기화
            gridLayout.removeAllViews();

            //이미지 개수에 따른 열 개수 설정
            if (imageNum == 1) {
                gridLayout.setColumnCount(1); // 1개일 때는 1열
                gridLayout.setRowCount(1);
            } else if (imageNum == 2) {
                gridLayout.setColumnCount(1); // 2개일 때는 1열
                gridLayout.setRowCount(2);    // 2개의 행
            } else if (imageNum == 3) {
                gridLayout.setColumnCount(2); // 3개일 때는 2열
                gridLayout.setRowCount(2);
            } else if (imageNum >= 4) {
                gridLayout.setColumnCount(2); // 4개 이상일 때는 2열
                gridLayout.setRowCount(2);    // 2행
            }

            //이미지뷰 동적 생성 및 배치
            for (int i = 0; i < imageNum; i++) {
                // 5개 이상의 이미지는 첫 4개만 표시하도록 처리
                if (i >= 4) {
                    break;  // 5번째 이미지부터는 처리하지 않음
                }

                ImageView imageView = new ImageView(itemView.getContext());

                // 이미지 로드
                // Glide.with(itemView.getContext()).load(imagePaths.get(i)).into(imageView);
                Glide.with(itemView.getContext())
                        .load(imagePaths.get(i))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                Log.e("Glide Error", e.getMessage()); //
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                Log.d("Glide Success", "Image loaded: " + imagePaths);
                                return false;
                            }
                        })
                        .into(imageView);
                System.out.println("imagePath:" + imagePaths.get(i));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = 0;

                // 이미지뷰의 크기와 배치 설정
                if (imageNum == 1) {
                    // 1개의 이미지는 그리드 전체를 차지하게 설정
                    params.rowSpec = GridLayout.spec(0, 1f);
                    params.columnSpec = GridLayout.spec(0, 1f);
                } else if (imageNum == 2) {
                    // 2개의 이미지는 각각 그리드의 위쪽과 아래쪽을 차지하도록 설정
                    params.rowSpec = GridLayout.spec(i, 1f);  // i가 0이면 첫 번째 행, 1이면 두 번째 행
                    params.columnSpec = GridLayout.spec(0, 1f);  // 열은 1개이므로 0열 사용
                } else if (imageNum == 3) {
                    // 3개의 이미지 배치: 첫 두 개는 위, 마지막 이미지는 전체 너비 차지
                    if (i < 2) {
                        params.rowSpec = GridLayout.spec(0, 1f);
                        params.columnSpec = GridLayout.spec(i, 1f);
                    } else {
                        params.rowSpec = GridLayout.spec(1, 1f);
                        params.columnSpec = GridLayout.spec(0, 2,1f); // 마지막 이미지가 2열을 차지하게 설정
                    }
                } else {
                    // 4개 이상의 이미지는 2x2로 배치
                    params.rowSpec = GridLayout.spec(i / 2, 1f);
                    params.columnSpec = GridLayout.spec(i % 2, 1f);
                }

                //레이아웃 파라미터를 이미지뷰에 설정
                imageView.setLayoutParams(params);

                //이미지뷰를 그리드 레이아웃에 추가
                gridLayout.addView(imageView);
            }
            titleTextView.setText(item.getTitle());
            dateTextView.setText(item.getDiary_date());
        }
    }


}
