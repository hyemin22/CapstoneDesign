package com.capstoneandroid.capstonedesign.model;

public class User {
    private Long id;
    private String nickname;
    private String character_choice;
    private Boolean first_term;
    private Boolean second_term;
    private Boolean third_term;
    private String phone_number;
    private Long family_id;

    public User(Long id, String nickname, String character_choice,
                Boolean first_term, Boolean second_term, Boolean third_term,
                Long family_id) {
        this.id = id;
        this.nickname = nickname;
        this.character_choice = character_choice;
        this.first_term = first_term;
        this.second_term = second_term;
        this.third_term = third_term;
        this.family_id = family_id;
    }

    public User(String nickname, String character_choice, String phone_number) {
        this.nickname = nickname;
        this.character_choice = character_choice;
        this.phone_number = phone_number;
    }

    public User(Long id, String nickname, String character_choice) {
        this.id = id;
        this.nickname = nickname;
        this.character_choice = character_choice;
    }

    public Long getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCharacter_choice() {
        return character_choice;
    }

    public Boolean getFirst_term() {
        return first_term;
    }

    public Boolean getSecond_term() {
        return second_term;
    }

    public Boolean getThird_term() {
        return third_term;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Long getFamily_id() {
        return family_id;
    }
}
