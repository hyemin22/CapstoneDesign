package com.capstoneandroid.capstonedesign.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.item.CategoryItem;

import java.util.ArrayList;

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    ArrayList<CategoryItem> items = new ArrayList<CategoryItem>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_category, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(CategoryItem item) {
        items.add(item);
    }
    public void setItems(ArrayList<CategoryItem> items) {
        this.items = items;
    }
    public CategoryItem getItem(int position) {
        return items.get(position);
    }
    public void setItem(int position, CategoryItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryEmoji;
        TextView categoryTitle;
        TextView categoryName;

        public ViewHolder(View itemView) {
            super(itemView);

            categoryEmoji = itemView.findViewById(R.id.categoryEmoji);
            categoryTitle = itemView.findViewById(R.id.categoryTitle);
            categoryName = itemView.findViewById(R.id.categoryName);
        }
        public void setItem(CategoryItem item) {
            categoryEmoji.setText(item.getEmoji());
            categoryTitle.setText(item.getCategoryTitle());
            categoryName.setText(item.getCategoryName());
        }
    }
}
