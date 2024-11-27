package com.capstoneandroid.capstonedesign.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.activity.DiaryActivity;
import com.capstoneandroid.capstonedesign.item.DiaryLikeItem;
import com.capstoneandroid.capstonedesign.item.DiaryListItem;
import com.capstoneandroid.capstonedesign.repository.DiaryRepository;

import java.util.ArrayList;
import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {

    ArrayList<DiaryListItem> items = new ArrayList<DiaryListItem>();
    private static Context context;

    public DiaryAdapter(ArrayList<DiaryListItem> items, Context context) {
        this.items = items;
        this.context = context;
    }
    @NonNull
    @Override
    public DiaryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_diary, parent, false);

        return new DiaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryAdapter.ViewHolder holder, int pos) {
        DiaryListItem item = items.get(pos);
        holder.setItem(item); //아이템 데이터 바인딩

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DiaryActivity로 전달할 인텐트 생성
                Intent intent = new Intent(context, DiaryActivity.class);
                intent.putExtra("id", item.getId()); // diaryId를 Intent에 추가
                context.startActivity(intent); // DiaryActivity 시작
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

        Long userId = UserInfoManager.getInstance().getUserId();
        private Long diaryId; // 일기 아이디 저장
        TextView titleTextView, contentTextView;
        ImageView photoImageView;
        ImageView likeSelect;
        CardView likeCardView;
        private Button[] buttons;
        private List<DiaryLikeItem> diaryLikeItems = new ArrayList<>();
        LinearLayout linearLayout, backgroundOverlay;
        DiaryRepository diaryRepository = new DiaryRepository();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTextView = itemView.findViewById(R.id.title);
            contentTextView = itemView.findViewById(R.id.content);
            photoImageView = itemView.findViewById(R.id.photo);

            likeSelect = itemView.findViewById(R.id.likeSelect);
            likeCardView = itemView.findViewById(R.id.likeCardView);

            linearLayout = itemView.findViewById(R.id.linearLayout);
            backgroundOverlay = itemView.findViewById(R.id.backgroundOverlay);

            // 공감 버튼 초기화
            buttons = new Button[]{
                    itemView.findViewById(R.id.like1), itemView.findViewById(R.id.like2), itemView.findViewById(R.id.like3),
                    itemView.findViewById(R.id.like4), itemView.findViewById(R.id.like5)
            };

            // 공감 선택 버튼 클릭 이벤트 (아직 공감 안 눌렀을 때)
            likeSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // likeCardView가 현재 INVISIBLE로 숨겨져 있을 때만 애니메이션을 시작하도록 수정
                    if (likeCardView.getVisibility() == View.INVISIBLE || likeCardView.getVisibility() == View.GONE) {
                        likeCardView.setVisibility(View.VISIBLE); // 카드뷰 보이기

                        // Pivot 설정 (애니메이션 시작을 왼쪽 하단)
                        likeCardView.setPivotX(0f);
                        likeCardView.setPivotY(likeCardView.getHeight());

                        // 애니메이션 추가
                        ObjectAnimator scaleX = ObjectAnimator.ofFloat(likeCardView, "scaleX", 0f, 1f);
                        ObjectAnimator scaleY = ObjectAnimator.ofFloat(likeCardView, "scaleY", 0f, 1f);
                        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(likeCardView, "alpha", 0f, 1f);

                        // 애니메이션 속도와 인터폴레이터 설정
                        scaleX.setDuration(300);
                        scaleY.setDuration(300);
                        fadeIn.setDuration(300);
                        scaleX.setInterpolator(new OvershootInterpolator());
                        scaleY.setInterpolator(new OvershootInterpolator());
                        fadeIn.setInterpolator(new OvershootInterpolator());

                        // 애니메이션 동시에 실행
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playTogether(scaleX, scaleY, fadeIn);
                        animatorSet.start();
                    }
                }
            });

            // 화면 바깥(배경) 클릭 이벤트
            backgroundOverlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 투명도 변경 애니메이션
                    ObjectAnimator fadeOut = ObjectAnimator.ofFloat(likeCardView, "alpha", 1f, 0f);
                    fadeOut.setDuration(100);

                    fadeOut.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            likeCardView.setVisibility(View.INVISIBLE); // 카드뷰 숨기기
                        }
                    });
                    fadeOut.start();
                }
            });


            // 공감 버튼 클릭 이벤트
            for (int i = 0; i < buttons.length; i++) {
                final int index = i; // 클릭한 이미지뷰의 인덱스를 기억하도록 final 변수 사용
                buttons[i].setOnClickListener(v -> {
                    saveLikeToServer(index); // 클릭된 이미지뷰 인덱스 전달
                    likeCardView.setVisibility(View.INVISIBLE);
                    likeSelect.setVisibility(View.INVISIBLE);
                });
            }
        }

        private void saveLikeToServer(Integer type) {
            // 서버로 POST 요청 보내기
            diaryRepository.saveLike(diaryId, type, userId, new DiaryRepository.DiaryCallback() {
                @Override
                public void onSuccess() {
                    Log.d("DiaryActivity", "일기 공감이 성공적으로 추가되었습니다");
                    getLikeList(); // 공감 추가 후 최신 리스트 다시 가져오기
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("DiaryActivity", "일기 공감 추가 실패: " + errorMessage);
                }
            });
        }

        private void deleteLike(Integer type) {
            diaryRepository.deleteLike(diaryId, type, userId, new DiaryRepository.DiaryCallback() {
                @Override
                public void onSuccess() {
                    Log.d("DiaryActivity", "일기 공감이 성공적으로 삭제되었습니다");
                    getLikeList(); // 공감 삭제 후 최신 리스트 다시 가져오기
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("DiaryActivity", "일기 공감 삭제 실패: " + errorMessage);
                }
            });
        }

        private void getLikeList() {
            LayoutInflater inflater = LayoutInflater.from(context);
            String[] emojis = {"👍🏻", "👏🏻", "🔥", "🥰", "😂"};

            diaryRepository.getLikeType(diaryId, userId, new DiaryRepository.GetLikeTypeCallback() {
                @Override
                public void onSuccess(Integer userLikeType) {
                    updateLikeUI(userLikeType);
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("getLikeList", "사용자 공감 타입 조회 실패: " + errorMessage);
                    updateLikeUI(null); // `userLikeType`을 null로 처리
                }
            });
        }

        private void updateLikeUI(Integer userLikeType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            String[] emojis = {"👍🏻", "👏🏻", "🔥", "🥰", "😂"};
            diaryRepository.getLike(diaryId, new DiaryRepository.GetLikeCallback() { // 전체 공감 버튼 리스트 가져오기
                @Override
                public void onSuccess(List<DiaryLikeItem> likes) {
                    ((Activity) context).runOnUiThread(() -> {
                        // 데이터가 제대로 가져왔는지 로그 출력
                        Log.d("getLikeList", "공감 목록 사이즈: " + likes.size());
                        diaryLikeItems.clear();
                        diaryLikeItems.addAll(likes);
                        // diaryLikeItems 업데이트 확인
                        Log.d("getLikeList", "업데이트된 공감 목록: " + diaryLikeItems);

                        if (!diaryLikeItems.isEmpty()) {
                            linearLayout.removeAllViews(); // 이전에 추가된 뷰 제거

                            // 공감 리스트가 있어도 사용자가 공감하지 않았다면 likeSelect 표시
                            if (userLikeType == null) {
                                likeSelect.setVisibility(View.VISIBLE);
                            } else {
                                likeSelect.setVisibility(View.GONE);
                            }

                            for (int i = 0; i < diaryLikeItems.size(); i++) {
                                DiaryLikeItem diaryLike = diaryLikeItems.get(i);

                                // 아이템의 type과 count 가져오기
                                int type = diaryLike.getType(); // 0, 1, 2, 3, 4 중 하나
                                long count = diaryLike.getCount(); // 카운트 값

                                // like_item.xml 레이아웃을 inflate
                                View likeView = inflater.inflate(R.layout.item_diarylike, linearLayout, false);

                                // 각 TextView와 LinearLayout(background) 가져오기
                                TextView typeTextView = likeView.findViewById(R.id.type);
                                TextView countTextView = likeView.findViewById(R.id.count);
                                LinearLayout background = likeView.findViewById(R.id.background);

                                // type에 따른 이모지 설정
                                typeTextView.setText(emojis[type]);

                                // count 값을 문자열로 설정
                                countTextView.setText(String.valueOf(count));

                                // 사용자의 공감 타입과 비교하여 배경 및 텍스트 색상 설정
                                if (userLikeType != null && userLikeType == type) { // 사용자가 추가한 공감이면 파란색으로 표시
                                    background.setBackgroundResource(R.drawable.like_rounded_box_blue);
                                    countTextView.setTextColor(ContextCompat.getColor(context, R.color.white));
                                    likeView.setOnClickListener(v -> deleteLike(type)); // 사용자가 추가한 공감 삭제
                                } else {
                                    background.setBackgroundResource(R.drawable.like_rounded_box); // 사용자가 추가한 공감 외의 공감은 흰색으로 표시
                                    countTextView.setTextColor(ContextCompat.getColor(context, R.color.blue));
                                }

                                // 부모 뷰에 추가
                                linearLayout.addView(likeView);
                            }

                        } else {
                            likeSelect.setVisibility(View.VISIBLE);
                        }
                    });
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("Error", "일기 공감 조회 실패: " + errorMessage);
                }
            });
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(DiaryListItem item) {
            titleTextView.setText(item.getTitle());
            contentTextView.setText(item.getContent());
            Glide.with(itemView.getContext()).load(item.getPhoto1()).into(photoImageView);

        }
    }
}
