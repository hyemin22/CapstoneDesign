package com.capstoneandroid.capstonedesign;

public class UserInfoManager {
    private static UserInfoManager instance;
    private Long userId; // 카카오 사용자 고유 ID

    // private 생성자로 외부에서 객체 생성 불가
    private UserInfoManager() {}

    // Singleton 인스턴스 제공 메서드
    public static synchronized UserInfoManager getInstance() {
        if (instance == null) {
            instance = new UserInfoManager();
        }
        return instance;
    }

    // 사용자 ID 가져오기
    public Long getUserId() {
        return userId;
    }

    // 사용자 ID 설정
    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
