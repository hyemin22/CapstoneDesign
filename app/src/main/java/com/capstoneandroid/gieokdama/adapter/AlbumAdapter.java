package com.capstoneandroid.gieokdama.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.capstoneandroid.gieokdama.activity.AlbumDiaryListActivity;
import com.capstoneandroid.gieokdama.item.AlbumItem;
import com.capstoneandroid.gieokdama.R;
import com.capstoneandroid.gieokdama.repository.DiaryRepository;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {

    //GuestbookItem 객체 리스트
    ArrayList<AlbumItem> items;
    Context context;
    private final boolean isListView; //리스트뷰에서 아이템의 크기 조정을 위해

    public AlbumAdapter(ArrayList<AlbumItem> items, Context context, boolean isListView) {
        this.items = items;
        this.context = context;
        this.isListView = isListView; // 초기값은 false로 설정
    }

    //뷰홀더 새로 생성
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(isListView) {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_album_list, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).
                    inflate(R.layout.item_album, parent, false);
        }
        return new ViewHolder(view);
    }

    //뷰홀더 재사용
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AlbumItem item = items.get(position);
        holder.setItem(item);

        // 클릭 리스너 설정
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AlbumDiaryListActivity.class);
                intent.putExtra("albumId", item.getId());
                intent.putExtra("albumname", item.getTitle());
                context.startActivity(intent);
            }
        });

        // 롱클릭 시 앨범 삭제
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                // 팝업 메뉴 생성
                PopupMenu popupMenu = new PopupMenu(context, view,
                        Gravity.END, 0, R.style.CustomPopupMenu);
                popupMenu.getMenuInflater().inflate(R.menu.menu_delete, popupMenu.getMenu());

                // 메뉴 항목 클릭 리스너 설정
                popupMenu.setOnMenuItemClickListener(menuItem -> {
                    if (menuItem.getItemId() == R.id.delete) {
                        // 앨범 삭제
                        deleteAlbum(item.getId());
                        return true;
                    }
                    return false;
                });

                // 팝업 메뉴 표시
                popupMenu.show();
                return true;
            }
        });
    }

    private void deleteAlbum(Long albumId) {
        DiaryRepository diaryRepository = new DiaryRepository();
        diaryRepository.deleteAlbum(albumId, new DiaryRepository.DiaryCallback() {
            @Override
            public void onSuccess() {
                Log.d("AlbumAdapter", "앨범이 성공적으로 삭제되었습니다");
            }

            @Override
            public void onFailure(String errorMessage) {
                Log.e("AlbumAdapter", "앨범 삭제 실패: " + errorMessage);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(AlbumItem item) {
        items.add(item);
    }

    public void setItems(ArrayList<AlbumItem> items) {
        this.items = items;
    }

    public AlbumItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, AlbumItem item) {
        items.set(position, item);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView albumTitleView;
        RelativeLayout albumBack;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            albumTitleView = itemView.findViewById(R.id.albumTitle);
            albumBack = itemView.findViewById(R.id.album_back);
        }

        //뷰 객체에 있는 데이터를 다른 것으로 보이도록 하는 역할
        public void setItem(AlbumItem item) {
            albumTitleView.setText(item.getTitle());

            // 서버에서 가져온 색 ID 값을 얻음 (예: 2131099677)
            int colorId = item.getColor();

            // 색 ID 값에 해당하는 Drawable 리소스를 매핑
            switch (colorId) {
                case 2131099677: // album_red
                    albumBack.setBackgroundResource(R.drawable.album_red);
                    break;
                case 2131099675: // album_blue
                    albumBack.setBackgroundResource(R.drawable.album_blue);
                    break;
                case 2131099679: // album_yellow
                    albumBack.setBackgroundResource(R.drawable.album_yellow);
                    break;
                case 2131099676: // album_purple
                    albumBack.setBackgroundResource(R.drawable.album_purple);
                    break;
                case 2131099678: // album_white
                    albumBack.setBackgroundResource(R.drawable.album_white);
                    break;
                default:
                    // 기본 배경 처리 (예: 흰색)
                    albumBack.setBackgroundResource(R.drawable.album_yellow);
                    break;
            }
        }
    }
}
