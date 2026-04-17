package com.capstoneandroid.gieokdama.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.UserInfoManager;
import com.capstoneandroid.gieokdama.item.AlbumItem;
import com.capstoneandroid.gieokdama.repository.DiaryRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class DiaryCreateActivity extends BaseActivity {
    private Long userId = UserInfoManager.getInstance().getUserId();
    private String title, diary_date, diary_content, address;
    private Long albumId, wishId;
    private Double latitude, longitude;
    DiaryRepository diaryRepository = new DiaryRepository();
    private EditText titleEdit, editTextDate, editPlace, content;
    private TextView backBtn, search;
    private ImageView editDate;
    private Button okBtn;
    private Spinner albumPicker;
    ArrayAdapter<String> adapter;
    private static final int PICK_IMAGE = 100; //갤러리에서 이미지 선택할 때 사용하는 요청 코드
    private int currentImageIndex = 0; // 현재 이미지가 추가될 이미지뷰의 인덱스
    private static final int MAX_IMAGES = 10; // 이미지뷰 최대 개수
    private ImageView[] imageViews;
    private CardView[] cardViews;
    private List<Uri> selectedImageUris = new ArrayList<>(); // 선택한 이미지 URI를 저장할 리스트
    private static final int REQUEST_CODE_ADDRESS_SEARCH = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diary_create);

        // XML에서 참조
        backBtn = findViewById(R.id.backBtn);
        okBtn = findViewById(R.id.okBtn);
        titleEdit = findViewById(R.id.titleEdit);
        editTextDate = findViewById(R.id.editTextDate);
        editDate = findViewById(R.id.editDate);
        editPlace = findViewById(R.id.editPlace);
        albumPicker = findViewById(R.id.albumPicker);
        content = findViewById(R.id.content);
        search = findViewById(R.id.search);

        // 위시에서 넘어온 경우, 제목과 날짜를 자동으로 입력
        Intent intent = getIntent();
        wishId = intent.getLongExtra("id", -1);
        titleEdit.setText(intent.getStringExtra("title"));
        editTextDate.setText(intent.getStringExtra("date"));

        // 이미지뷰 배열 초기화
        imageViews = new ImageView[]{
                findViewById(R.id.img1), findViewById(R.id.img2), findViewById(R.id.img3),
                findViewById(R.id.img4), findViewById(R.id.img5), findViewById(R.id.img6),
                findViewById(R.id.img7), findViewById(R.id.img8), findViewById(R.id.img9),
                findViewById(R.id.img10)
        };

        // 카드뷰 배열 초기화
        cardViews = new CardView[] {
                findViewById(R.id.card1), findViewById(R.id.card2), findViewById(R.id.card3),
                findViewById(R.id.card4), findViewById(R.id.card5), findViewById(R.id.card6),
                findViewById(R.id.card7), findViewById(R.id.card8), findViewById(R.id.card9),
                findViewById(R.id.card10)
        };

        // 초기 이미지뷰 설정 (첫 번째 이미지만 활성화)
        for (int i = 1; i < imageViews.length; i++) {
            cardViews[i].setVisibility(View.GONE);
        }

        // 모든 이미지뷰에 클릭 리스너 설정
        for (int i = 0; i < imageViews.length; i++) {
            final int index = i; // 클릭한 이미지뷰의 인덱스를 기억하도록 final 변수 사용
            imageViews[i].setOnClickListener(v -> {
                openGalleryForImageView(index); // 클릭된 이미지뷰 인덱스 전달
            });
        }

        // 스피너와 어댑터 초기화
        adapter = new ArrayAdapter<>(this, R.layout.spinner_item, new ArrayList<>());
        adapter.setDropDownViewResource(R.layout.spinner_dropdown);
        albumPicker.setAdapter(adapter);

        // DB에서 앨범 리스트 가져오는 요청 보내기
        fetchAlbumListFromDB();

        // 취소(뒤로가기) 버튼
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed(); // 이전 화면으로 이동
            }
        });

        // 저장하기 버튼
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // DB에 새로운 일기 저장
                title = titleEdit.getText().toString(); // 제목
                diary_date = editTextDate.getText().toString(); // 날짜
                diary_content = content.getText().toString(); // 글
                address = editPlace.getText().toString(); // 장소

                // 서버로 POST 요청 보내기
                saveDiaryData();

                finish();
            }
        });

        // EditText 클릭 시 DatePickerDialog 표시
        editDate.setOnClickListener(v -> showDatePickerDialog());

        // 검색 버튼 클릭시 검색창으로 이동
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DiaryCreateActivity.this, AddressSearchActivity.class);
                startActivityForResult(intent, REQUEST_CODE_ADDRESS_SEARCH);
            }
        });
    }

    private void fetchAlbumListFromDB() {
        //앨범 리스트 GET 요청
        diaryRepository.getAlbumList(userId, new DiaryRepository.GetAlbumCallback() {
            @Override
            public void onSuccess(List<AlbumItem> albums) {
                runOnUiThread(() -> {
                    // 서버에서 받은 앨범 리스트 응답 기반으로 스피너에 리스트 넣기
                    List<String> albumList = new ArrayList<>();

                    albumList.add("일기를 보관하고 싶은 앨범을 선택하세요.");

                    // 앨범명 리스트에 추가
                    for (AlbumItem album : albums) {
                        albumList.add(album.getTitle());
                    }

                    adapter.clear();
                    adapter.addAll(albumList);
                    adapter.notifyDataSetChanged();

                    albumPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // 선택된 앨범 아이디 저장
                            if (position != 0) {
                                albumId = albums.get(position - 1).getId();
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // 선택되지 않은 상태에 대한 처리
                        }
                    });
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "앨범 리스트 조회 실패: " + errorMessage);
            }
        });
    }

    private void showDatePickerDialog() {
        // 현재 날짜로 설정
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // 스피너 스타일의 DatePickerDialog 생성
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                android.R.style.Theme_Holo_Light_Dialog_NoActionBar, // 스피너 형태로 설정
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // 선택된 날짜로 캘린더 객체 설정
                        Calendar selectedDate = Calendar.getInstance();
                        selectedDate.set(year, month, dayOfMonth);

                        // 요일을 얻기 위한 배열
                        String[] daysOfWeek = {"일", "월", "화", "수", "목", "금", "토"};
                        int dayOfWeekIndex = selectedDate.get(Calendar.DAY_OF_WEEK) - 1; // 요일 인덱스 얻기

                        // 월과 일을 두 자리 숫자로 형식화
                        String formattedDate = String.format("%04d.%02d.%02d(%s)", year, month + 1, dayOfMonth, daysOfWeek[dayOfWeekIndex]);

                        editTextDate.setText(formattedDate);
                    }
                },
                year, month, day);

        // 다이얼로그를 스피너 모드로 설정
        datePickerDialog.getDatePicker().setCalendarViewShown(false);
        datePickerDialog.getDatePicker().setSpinnersShown(true);
        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }

    private void openGalleryForImageView(int imageViewIndex) {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        //gallery.setType("image/*");
        gallery.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        try {
            startActivityForResult(gallery, PICK_IMAGE);
        } catch (SecurityException e) {
            // 보안 예외가 발생한 경우 사용자에게 메시지를 표시하여 앱이 외부 저장소에 접근할 수 없음을 알립니다.
            Toast.makeText(this, "갤러리에 접근할 수 없습니다.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    //다른 페이지에서 받은 값 처리
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        // 사진 처리
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == PICK_IMAGE) {
                // 선택한 이미지를 담을 리스트
                int remainingSlots = MAX_IMAGES - currentImageIndex; // 남은 추가 가능한 이미지 슬롯

                if (data.getClipData() != null) { // 다중 선택된 경우
                    int count = data.getClipData().getItemCount();

                    if (count > remainingSlots) { // 선택한 이미지 개수가 제한을 초과할 경우
                        Toast.makeText(this, "이미지는 최대 " + MAX_IMAGES + "개까지 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        count = remainingSlots; // 남은 슬롯까지만 처리
                    }

                    for (int i = 0; i < count && i < imageViews.length; i++) {
                        if (currentImageIndex >= MAX_IMAGES) break; // 최대 이미지 개수 초과 시 중단
                        Uri imageUri = data.getClipData().getItemAt(i).getUri();
                        selectedImageUris.add(imageUri); // 서버로 보낼 이미지 URI 리스트에 저장
                        imageViews[currentImageIndex].setImageURI(imageUri);
                        cardViews[currentImageIndex].setVisibility(View.VISIBLE);
                        currentImageIndex++; // 다음 이미지뷰로 이동

                        System.out.println("imageUri1:" + imageUri);
                    }
                } else if (data.getData() != null) { // 단일 선택된 경우
                    if (currentImageIndex < MAX_IMAGES) {
                        Uri imageUri = data.getData();
                        selectedImageUris.add(imageUri); // 서버로 보낼 이미지 URI 리스트에 저장
                        imageViews[currentImageIndex].setImageURI(imageUri);
                        cardViews[currentImageIndex].setVisibility(View.VISIBLE);
                        currentImageIndex++; // 다음 이미지뷰로 이동

                        System.out.println("imageUri2:" + imageUri);
                    } else {
                        Toast.makeText(this, "이미지는 최대 " + MAX_IMAGES + "개까지 추가할 수 있습니다.", Toast.LENGTH_SHORT).show();
                    }
                }
                // 다음 빈 이미지뷰를 보이도록 설정
                if (currentImageIndex < MAX_IMAGES) {
                    cardViews[currentImageIndex].setVisibility(View.VISIBLE);
                }
            } else if (requestCode == REQUEST_CODE_ADDRESS_SEARCH) {
                address = data.getStringExtra("placeName");
                latitude = data.getDoubleExtra("latitude", 0.0);
                longitude = data.getDoubleExtra("longitude", 0.0);

                editPlace.setText(address);
            }
        }
    }

    private void saveDiaryData() {
        // 서버로 POST 요청 보내기
        Map<String, RequestBody> dataMap = new HashMap<>();
        dataMap.put("id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId)));
        dataMap.put("title", RequestBody.create(MediaType.parse("text/plain"), title));
        dataMap.put("diary_date", RequestBody.create(MediaType.parse("text/plain"), diary_date));
        dataMap.put("content", RequestBody.create(MediaType.parse("text/plain"), diary_content));
        dataMap.put("address", RequestBody.create(MediaType.parse("text/plain"), address));
        dataMap.put("album_id", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(albumId)));
        dataMap.put("latitude", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(latitude)));
        dataMap.put("longitude", RequestBody.create(MediaType.parse("text/plain"), String.valueOf(longitude)));

        // 이미지 파일 리스트
        List<MultipartBody.Part> files = new ArrayList<>();
        for (Uri uri : selectedImageUris) { // files는 File 객체의 리스트
            File file = new File(uriToFilePath(uri));
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("files", file.getName(), requestBody);
            files.add(part);
        }

        diaryRepository.saveDiary(dataMap, files, wishId, new DiaryRepository.DiaryCallback() {
            @Override
            public void onSuccess() {
                // 일기 추가 성공
                Log.d("DiaryCreateActivity", "일기가 성공적으로 추가되었습니다");
            }

            @Override
            public void onFailure(String errorMessage) {
                // 일기 추가 실패
                Log.e("DiaryCreateActivity", "일기 추가 실패: " + errorMessage);
            }
        });
    }

    // URI를 FILE path로 변환
    private String uriToFilePath(Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            if (cursor.moveToFirst()) {
                filePath = cursor.getString(columnIndex);
            }
            cursor.close();
        }

        return filePath;
    }
}