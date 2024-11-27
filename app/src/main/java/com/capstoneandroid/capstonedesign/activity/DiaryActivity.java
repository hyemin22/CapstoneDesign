package com.capstoneandroid.capstonedesign.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.UserInfoManager;
import com.capstoneandroid.capstonedesign.item.DiaryLikeItem;
import com.capstoneandroid.capstonedesign.repository.DiaryRepository;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DiaryActivity extends BaseActivity {
    Long userId = UserInfoManager.getInstance().getUserId();
    TextView albumname, nickname, date, place, title, content;
    CircleImageView profile;
    ImageView backBtn, likeSelect;
    CardView likeCardView;
    private Button[] buttons;
    private ImageView[] imageViews;
    private CardView[] cardViews;
    private List<DiaryLikeItem> diaryLikeItems = new ArrayList<>();
    private Long diaryId; // 일기 아이디 저장
    LinearLayout linearLayout, backgroundOverlay;
    DiaryRepository diaryRepository = new DiaryRepository();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);

        // 위젯 초기화
        backBtn = findViewById(R.id.backBtn);
        likeSelect = findViewById(R.id.likeSelect);
        likeCardView = findViewById(R.id.likeCardView);
        albumname = findViewById(R.id.albumname);
        nickname = findViewById(R.id.nickname);
        date = findViewById(R.id.date);
        place = findViewById(R.id.place);
        title = findViewById(R.id.title);
        content = findViewById(R.id.content);
        profile = findViewById(R.id.profile);
        linearLayout = findViewById(R.id.linearLayout);
        backgroundOverlay = findViewById(R.id.backgroundOverlay);

        // 이미지뷰 배열 초기화
        imageViews = new ImageView[]{
                findViewById(R.id.img1), findViewById(R.id.img2), findViewById(R.id.img3),
                findViewById(R.id.img4), findViewById(R.id.img5), findViewById(R.id.img6),
                findViewById(R.id.img7), findViewById(R.id.img8), findViewById(R.id.img9),
                findViewById(R.id.img10)
        };

        // 카드뷰 배열 초기화
        cardViews = new CardView[]{
                findViewById(R.id.card1), findViewById(R.id.card2), findViewById(R.id.card3),
                findViewById(R.id.card4), findViewById(R.id.card5), findViewById(R.id.card6),
                findViewById(R.id.card7), findViewById(R.id.card8), findViewById(R.id.card9),
                findViewById(R.id.card10)
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

        // 초기 이미지뷰 설정 (첫 번째 이미지만 활성화)
        for (int i = 1; i < imageViews.length; i++) {
            cardViews[i].setVisibility(View.GONE);
        }

        // 공감 버튼 초기화
        buttons = new Button[]{
                findViewById(R.id.like1), findViewById(R.id.like2), findViewById(R.id.like3),
                findViewById(R.id.like4), findViewById(R.id.like5)
        };

        // 공감 버튼 클릭 이벤트
        for (int i = 0; i < buttons.length; i++) {
            final int index = i; // 클릭한 이미지뷰의 인덱스를 기억하도록 final 변수 사용
            buttons[i].setOnClickListener(v -> {
                saveLikeToServer(index); // 클릭된 이미지뷰 인덱스 전달
                likeCardView.setVisibility(View.INVISIBLE);
                likeSelect.setVisibility(View.INVISIBLE);
            });
        }

        // backBtn 클릭 이벤트
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // 현재 액티비티 종료
            }
        });

        // 일기 내용 채우기
        Intent intent = getIntent();
        diaryId = intent.getLongExtra("id", -1L);
        String diary_title = intent.getStringExtra("title");
        String diary_date = intent.getStringExtra("diary_date");
        ArrayList<String> imagePaths = intent.getStringArrayListExtra("imagePaths");
        String diary_content = intent.getStringExtra("content");
        String address = intent.getStringExtra("address");
        String album_title = intent.getStringExtra("album_title");
        String user_character = intent.getStringExtra("user_character");
        String user_nickname = intent.getStringExtra("user_nickname");

        albumname.setText(album_title);
        nickname.setText(user_nickname);

        // 이미지 크기에 맞게 카드뷰 보기 설정
        for (int i = 1; i < imagePaths.size(); i++) {
            cardViews[i].setVisibility(View.VISIBLE);
        }

        // 작성자 캐릭터
        int drawableId = getResources().getIdentifier(user_character, "drawable", this.getPackageName());
        System.out.println("drawable??!!" + drawableId);

        if (drawableId != 0) {
            profile.setImageResource(drawableId);
        } else {
            profile.setImageResource(R.drawable.ic_my);
        }

        date.setText(diary_date);
        place.setText(address);
        title.setText(diary_title);
        content.setText(diary_content);

        // Glide를 사용하여 이미지 로드
        if (imagePaths != null && !imagePaths.isEmpty()) {
            for (int i = 0; i < imagePaths.size(); i++) {
                if (i < imageViews.length) {
                    Glide.with(this)
                            .load(imagePaths.get(i)) // 이미지 경로 또는 URL을 사용
                            .into(imageViews[i]); // 해당 ImageView에 로드
                }
            }
        }

        // DB에서 공감 버튼 리스트 가져오기
        getLikeList();
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
        LayoutInflater inflater = LayoutInflater.from(this);
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
        LayoutInflater inflater = LayoutInflater.from(this);
        String[] emojis = {"👍🏻", "👏🏻", "🔥", "🥰", "😂"};
        diaryRepository.getLike(diaryId, new DiaryRepository.GetLikeCallback() { // 전체 공감 버튼 리스트 가져오기
            @Override
            public void onSuccess(List<DiaryLikeItem> likes) {
                runOnUiThread(() -> {
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
                                countTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
                                likeView.setOnClickListener(v -> deleteLike(type)); // 사용자가 추가한 공감 삭제
                            } else {
                                background.setBackgroundResource(R.drawable.like_rounded_box); // 사용자가 추가한 공감 외의 공감은 흰색으로 표시
                                countTextView.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.blue));
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
}
