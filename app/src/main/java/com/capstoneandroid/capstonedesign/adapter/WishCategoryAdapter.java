package com.capstoneandroid.capstonedesign.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.capstonedesign.R;
import com.capstoneandroid.capstonedesign.activity.MissionCreateActivity;
import com.capstoneandroid.capstonedesign.activity.WishCategoryActivity;
import com.capstoneandroid.capstonedesign.activity.WishCategoryCreateActivity;
import com.capstoneandroid.capstonedesign.item.WishCategoryItem;
import com.capstoneandroid.capstonedesign.repository.WishListRepository;

import java.util.ArrayList;

public class WishCategoryAdapter extends RecyclerView.Adapter<WishCategoryAdapter.ViewHolder> {
    ArrayList<WishCategoryItem> items = new ArrayList<WishCategoryItem>();
    Context context;

    public WishCategoryAdapter(ArrayList<WishCategoryItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_category, parent, false);

        return new ViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WishCategoryItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(WishCategoryItem item) {
        items.add(item);
    }
    public void setItems(ArrayList<WishCategoryItem> items) {
        this.items = items;
    }
    public WishCategoryItem getItem(int position) {
        return items.get(position);
    }
    public void setItem(int position, WishCategoryItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        ImageButton hamBtn;
        Context context;

        public ViewHolder(View itemView, Context context) {
            super(itemView);
            this.context = context;
            categoryName = itemView.findViewById(R.id.categoryName);
            hamBtn = itemView.findViewById(R.id.hamBtn);
        }
        public void setItem(WishCategoryItem item) {
            categoryName.setText(item.getName());

            hamBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // PopupMenu 생성
                    PopupMenu popupMenu = new PopupMenu(view.getContext(), hamBtn);
                    popupMenu.getMenuInflater().inflate(R.menu.menu_edit_delete, popupMenu.getMenu());

                    // 메뉴 항목 클릭 리스너 설정
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            int itemId = menuItem.getItemId();
                            if (itemId == R.id.edit) { // 수정
                                Intent intent = new Intent(context, WishCategoryCreateActivity.class);
                                intent.putExtra("id", item.getId());
                                intent.putExtra("name", item.getName());
                                intent.putExtra("source", "WishCategoryAdapter");
                                context.startActivity(intent);
                                return true;
                            } else if (itemId == R.id.delete) { // 삭제
                                deleteWishCategory(item);
                                return true;
                            }
                            return false;
                        }
                    });

                    // 팝업 메뉴 보여주기
                    popupMenu.show();
                }
            });
        }
        private void deleteWishCategory(WishCategoryItem item) {
            WishListRepository wishListRepository = new WishListRepository();

            wishListRepository.deleteWishCategoryDataToServer(item.getId(), new WishListRepository.WishListCallback() {
                @Override
                public void onSuccess() {
                    Log.d("WishListCreateActivity", "위시 카테고리가 성공적으로 삭제되었습니다");
                }

                @Override
                public void onFailure(String errorMessage) {
                    Log.e("WishListCreateActivity", "위시 카테고리 삭제 실패: " + errorMessage);

                }
            });
        }
    }
}
