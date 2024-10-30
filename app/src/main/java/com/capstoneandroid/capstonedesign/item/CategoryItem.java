package com.capstoneandroid.capstonedesign.item;

public class CategoryItem {
    String categoryEmoji;
    String categoryTitle;
    String categoryName;

    public CategoryItem(String emoji, String categoryTitle, String name) {
        this.categoryEmoji = emoji;
        this.categoryTitle = categoryTitle;
        this.categoryName = name;
    }

    public String getEmoji() { return categoryEmoji;}
    public String getCategoryTitle() {
        return categoryTitle;
    }
    public String getCategoryName() {
        return categoryName;
    }

    public void setEmoji(String emoji) {this.categoryEmoji = emoji;}
    public void setCategoryTitle(String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
