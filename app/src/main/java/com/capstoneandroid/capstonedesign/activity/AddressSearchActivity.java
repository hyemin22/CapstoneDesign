package com.capstoneandroid.capstonedesign.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.adapter.MyMissionAdapter;
import com.capstoneandroid.capstonedesign.adapter.PlaceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressSearchActivity extends BaseActivity {
    private EditText searching;
    private Button search;
    private RecyclerView view;
    private PlaceAdapter adapter;
    private String placeName, placeAddress;
    private Double latitude, longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        searching = findViewById(R.id.searching);
        search = findViewById(R.id.search);
        view = findViewById(R.id.list);
        view.setLayoutManager(new LinearLayoutManager(this)); // LayoutManager 설정

        searching.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // 텍스트 변경 전
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                // 텍스트가 변경되면 searchPlace 호출
                String query = charSequence.toString().trim();
                if (!query.isEmpty()) {
                    searchPlace(query); // 장소 검색
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 텍스트 변경 후
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressSearchActivity.this, DiaryCreateActivity.class);
                intent.putExtra("placeName", placeName);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                Log.d("위도 경도", "위도경도" + latitude + "," + longitude);
                setResult(RESULT_OK, intent); // 결과 설정
                finish();
            }
        });

    }

    private void searchPlace(String query) {
        String clientId = "9mGTneCz3Xu7ThPXst_Z"; // 네이버 API Client ID
        String clientSecret = "_IM4WQFd6I"; // 네이버 API Client Secret
        String apiUrl = "https://openapi.naver.com/v1/search/local.json?query=" + query + "&display=10&start=1";

        // HTTP 요청을 위한 비동기 작업 (예: OkHttp 또는 Volley 사용)
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, apiUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    // JSON 응답을 파싱하여 결과 리스트를 표시
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray items = jsonObject.getJSONArray("items");

                    Log.d("API_RESPONSE", response);

                    List<String> placeList = new ArrayList<>();
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject place = items.getJSONObject(i);
                        placeList.add(place.getString("title") + " - " + place.getString("address"));
                    }

                    // 검색된 결과를 리스트에 표시
                    updatePlaceList(placeList, items);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Naver-Client-Id", clientId);
                headers.put("X-Naver-Client-Secret", clientSecret);
                return headers;
            }
        };

        // 요청을 큐에 추가
        queue.add(stringRequest);
    }

    private void updatePlaceList(List<String> placeList, JSONArray items) {
        adapter = new PlaceAdapter(placeList, new PlaceAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String place) {
                try {
                    // 선택된 장소의 정보를 EditText에 설정
                    String[] placeInfo = place.split(" - ");
                    placeName = placeInfo[0];

                    // HTML 태그를 제거
                    placeName = placeName.replaceAll("<[^>]+>", "");

                    placeAddress = placeInfo[1];
                    searching.setText(placeName);

                    // 선택된 장소의 인덱스를 찾기
                    for (int i = 0; i < items.length(); i++) {
                        JSONObject selectedPlace = items.getJSONObject(i);
                        String placeTitle = selectedPlace.getString("title");

                        // 선택된 장소가 일치하는지 확인
                        if (placeTitle.replaceAll("<[^>]+>", "").equals(placeName)) {
                            // 선택된 장소의 위도, 경도 추출
                            latitude = selectedPlace.getDouble("mapy") / 10000000.0;  // 위도
                            longitude = selectedPlace.getDouble("mapx") / 10000000.0; // 경도
                            break;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        view.setAdapter(adapter);
    }
}
