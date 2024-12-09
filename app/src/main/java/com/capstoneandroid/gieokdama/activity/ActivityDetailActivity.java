package com.capstoneandroid.gieokdama.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.capstoneandroid.gieokdama.R;

public class ActivityDetailActivity extends BaseActivity {

    ImageButton backBtn;
    TextView title, type, region, address, call,
            open_time, closed_day, title1, type1, region1,
            title2, type2, region2, title3, type3, region3;
    ImageView heart, main_photo, map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_detail);

        backBtn = findViewById(R.id.backBtn);
        title = findViewById(R.id.title);
        type = findViewById(R.id.type);
        region = findViewById(R.id.region);
        main_photo = findViewById(R.id.main_photo);
        address = findViewById(R.id.address);
        call = findViewById(R.id.call);
        open_time = findViewById(R.id.open_time);
        closed_day = findViewById(R.id.closed_day);
        title1 = findViewById(R.id.title1);
        type1 = findViewById(R.id.type1);
        region1 = findViewById(R.id.region1);
        title2 = findViewById(R.id.title2);
        type2 = findViewById(R.id.type2);
        region2 = findViewById(R.id.region2);
        title3 = findViewById(R.id.title3);
        type3 = findViewById(R.id.type3);
        region3 = findViewById(R.id.region3);

        Intent intent = getIntent();
        Long id = intent.getLongExtra("id", -1L);
        String titleText = intent.getStringExtra("title");
        String typeText = intent.getStringExtra("type");
        Integer regionNum = intent.getIntExtra("region", -1);
        String main_photoName = intent.getStringExtra("main_photo");
        String addressText = intent.getStringExtra("address");
        String callText = intent.getStringExtra("call");
        String open_timeText = intent.getStringExtra("open_time");
        String closed_dayText = intent.getStringExtra("closed_day");

        title.setText(titleText);
        type.setText(typeText);
        // 지역 이름 가져와서 텍스트뷰에 넣기 (서버)
        int main_photoId = getDrawableId(main_photoName);
        main_photo.setImageResource(main_photoId);
        address.setText(addressText);
        call.setText(callText);
        open_time.setText(open_timeText);
        closed_day.setText(closed_dayText);

        // 이 곳과 비슷한 장소 - district_id, type 보내서 같은 액티비티 리스트 get 호출 (서버)
        // 리사이클러뷰

    }

    private int getDrawableId(String mainPhoto) {
        int drawableId = this.getResources().getIdentifier(mainPhoto, "drawable", this.getPackageName());

        // drawableId가 0이면 해당 drawable이 존재하지 않는 것이므로 예외 처리
        if (drawableId == 0) {
            throw new Resources.NotFoundException("Drawable not found for name: " + mainPhoto);
        }

        return drawableId;
    }
}