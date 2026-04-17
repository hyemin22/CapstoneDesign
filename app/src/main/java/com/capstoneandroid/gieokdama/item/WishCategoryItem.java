package com.capstoneandroid.gieokdama.item;

import android.content.Context;

import java.io.Serializable;

public class WishCategoryItem implements Serializable {
    Integer id;
    String name;

    public WishCategoryItem(Context context, Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
