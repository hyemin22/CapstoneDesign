package com.capstoneandroid.capstonedesign.item;

public class GuestbookItem {
    String character_choice;
    String content;
    String nickname;

    public GuestbookItem(String character_choice, String content, String nickname) {
        this.character_choice = character_choice;
        this.content = content;
        this.nickname = nickname;
    }

    public String getCharacter_choice() {
        return character_choice;
    }

    public String getContent() {
        return content;
    }

    public String getNickname() {
        return nickname;
    }




    public void setCharacter_choice(String character_choice) {
        this.character_choice = character_choice;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

}