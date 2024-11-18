package com.capstoneandroid.capstonedesign.fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.ActivityAdapter;
import com.capstoneandroid.capstonedesign.adapter.ButtonPagerAdapter;
import com.capstoneandroid.capstonedesign.api.RegionApiService;
import com.capstoneandroid.capstonedesign.api.RetrofitClient;
import com.capstoneandroid.capstonedesign.item.ActivityItem;
import com.capstoneandroid.capstonedesign.model.District;
import com.capstoneandroid.capstonedesign.model.Province;
import com.capstoneandroid.capstonedesign.repository.ActivityRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityFragment extends Fragment {
    private ActivityAdapter adapter;
    private TextView ment, region1, region2;
    private String activityType; // Save the tab type
    private int selectedProvinceId = -1; // Variable to store selected provinceId
    private ArrayList<ActivityItem> items = new ArrayList<>();

    private RegionApiService regionApiService;

    public ActivityFragment(String activityType) {
        this.activityType = activityType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        initUI(view);
        return view;
    }

    private void initUI(View view) {
        region1 = view.findViewById(R.id.spinner1);
        region2 = view.findViewById(R.id.spinner2);
        ment = view.findViewById(R.id.ment);

        // Initialize Retrofit
        regionApiService = RetrofitClient.getClient().create(RegionApiService.class);

        // Set click listeners for region selection
        region1.setOnClickListener(v -> showProvinceDialog());
        region2.setOnClickListener(v -> showDistrictDialog());

        // Set up RecyclerView
        RecyclerView recyclerView = view.findViewById(R.id.activityView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        adapter = new ActivityAdapter(items, getContext());
        recyclerView.setAdapter(adapter);

        updateContentBasedOnType(activityType);
    }

    private void showProvinceDialog() {
        regionApiService.getProvinces().enqueue(new Callback<List<Province>>() {
            @Override
            public void onResponse(Call<List<Province>> call, Response<List<Province>> response) {
                if (response.isSuccessful()) {
                    List<Province> provinces = response.body();
                    showGridDialog("시/도 선택", provinces, true);
                } else {
                    System.out.println("지역 정보를 가져오는데 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<Province>> call, Throwable t) {
                System.out.println("API 요청에 실패했습니다: " + t.getMessage());
            }
        });
    }

    private void showDistrictDialog() {
        if (selectedProvinceId == -1) {
            Toast.makeText(getContext(), "먼저 지역을 선택하세요.", Toast.LENGTH_SHORT).show();
            return;
        }

        loadDistricts(selectedProvinceId);
    }

    private void loadDistricts(int provinceId) {
        regionApiService.getDistrictsByProvince(provinceId).enqueue(new Callback<List<District>>() {
            @Override
            public void onResponse(Call<List<District>> call, Response<List<District>> response) {
                if (response.isSuccessful()) {
                    List<District> districts = response.body();
                    showGridDialog("시/구/군 선택", districts, false);
                } else {
                    System.out.println("시/구/군 정보를 가져오는데 실패했습니다.");
                }
            }

            @Override
            public void onFailure(Call<List<District>> call, Throwable t) {
                System.out.println("API 요청에 실패했습니다: ");
            }
        });
    }

    private void showGridDialog(String title, List<?> items, boolean isProvince) {
        Dialog dialog = new Dialog(getContext());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        // 다이얼로그 레이아웃 설정
        if (isProvince) {
            dialog.setContentView(R.layout.dialog_region1);
            // 다이얼로그 위치 조정
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.y = 250; // y 값을 증가시키면 더 아래로 내려감
            dialog.getWindow().setAttributes(layoutParams);
        } else {
            dialog.setContentView(R.layout.dialog_region2);

            // 다이얼로그 위치 조정
            WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
            layoutParams.y = 430; // y 값을 증가시키면 더 아래로 내려감
            dialog.getWindow().setAttributes(layoutParams);
        }

        GridLayout gridLayout = dialog.findViewById(R.id.gridLayout);
        gridLayout.setColumnCount(isProvince ? 5 : 4);
        gridLayout.setRowCount(isProvince ? 5 : 7);

        dialog.getWindow().setLayout(970, isProvince ? 640 : 1000);

        // 아이템을 순회하며 버튼 생성 및 추가
        for (Object item : items) {
            Button button;
            if (isProvince && item instanceof Province) {
                Province province = (Province) item;
                button = createButton(province.getName(), province.getId(), dialog, true);
            } else if (!isProvince && item instanceof District) {
                District district = (District) item;
                button = createButton(district.getName(), district.getId(), dialog, false);
            } else {
                continue;
            }

            gridLayout.addView(button);
        }
        dialog.show();
    }

    private Button createButton(String name, int id, Dialog dialog, boolean isProvince) {
        Button button = new Button(getContext());
        button.setText(name);
        button.setBackgroundResource(R.drawable.button_selector);
        button.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        button.setTypeface(ResourcesCompat.getFont(getContext(), R.font.pretendardmedium));

        GridLayout.LayoutParams params2 = new GridLayout.LayoutParams();
        params2.width = isProvince ? 165 : 210; // 원하는 너비로 설정
        params2.height = 120; // 높이 설정
        params2.setMargins(10, 0, 10, 15);
        button.setLayoutParams(params2); // 설정 후 LayoutParams 적용

        button.setOnClickListener(v -> {
            if (isProvince) {
                selectedProvinceId = id;
                region1.setText(name);
            } else {
                region2.setText(name);
            }
            dialog.dismiss();
        });

        return button;
    }

    private void updateContentBasedOnType(String type) {
        switch (type) {
            case "맛집":
                ment.setText("가족끼리 먹기 좋은\n근처 맛집");
                getActivityFromServer(1);
                break;
            case "여행":
                ment.setText("가족과 함께 추억을\n쌓을 수 있는 여행지");
                getActivityFromServer(2);
                break;
            case "이색":
                ment.setText("색다른 경험을 제공하는\n이색 활동");
                getActivityFromServer(3);
                break;
            case "야외":
                ment.setText("가족과 함께하는\n다양한 야외 활동");
                getActivityFromServer(4);
                break;
            case "실내":
                ment.setText("편안하게 함께 할 수\n있는 실내 활동");
                getActivityFromServer(5);
            default:
                break;
        }

        adapter.notifyDataSetChanged(); // 데이터 변경 알림
    }

    private void getActivityFromServer(Integer category) {
        ActivityRepository activityRepository = new ActivityRepository();
        activityRepository.getActivityDataFromServer(category, new ActivityRepository.GetActivityCallBack() {
            @Override
            public void onSuccess(List<ActivityItem> activityItems) {
                getActivity().runOnUiThread(() -> {
                    items.clear();

                    // 서버에서 가져온 활동 응답을 추가
                    for (ActivityItem activityItem : activityItems) {
                        items.add(new ActivityItem(
                                getContext(),
                                activityItem.getId(),
                                activityItem.getCategory(),
                                activityItem.getTitle(),
                                activityItem.getProfile(),
                                activityItem.getMain_photo(),
                                activityItem.getType(),
                                activityItem.getReview_count(),
                                activityItem.getAddress(),
                                activityItem.getDistrict_id(),
                                activityItem.getPhone_number(),
                                activityItem.getOpen_time(),
                                activityItem.getClosed_day()
                        ));

                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("Error", "활동 조회 실패: " + errorMessage);
            }
        });
    }
}
