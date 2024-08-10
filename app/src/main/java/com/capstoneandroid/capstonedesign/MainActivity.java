package com.capstoneandroid.capstonedesign;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.jetbrains.annotations.NotNull;

public class MainActivity extends AppCompatActivity {

    private Fragment1 fragment1;
    private Fragment2 fragment2;
    private Fragment3 fragment3;
    private Fragment4 fragment4;
    private Fragment5 fragment5;

    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    BottomNavigationView bottomNavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragment5 = new Fragment5();

        setFragment(R.id.tab1);

        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                setFragment(item.getItemId()); //item의 id를 인자로 한다.
                return true; //true의 의미 = bottomMenu의 애니메이션을 적용시킨다.
            }
        });
    }

    private void setFragment(int n){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        if (n == R.id.tab1) {
            fragmentTransaction.replace(R.id.container, fragment1);
        } else if (n == R.id.tab2) {
            fragmentTransaction.replace(R.id.container, fragment2);
        } else if (n == R.id.tab3) {
            fragmentTransaction.replace(R.id.container, fragment3);
        } else if (n == R.id.tab4) {
            fragmentTransaction.replace(R.id.container, fragment4);
        } else if (n == R.id.tab5) {
            fragmentTransaction.replace(R.id.container, fragment5);
        }
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//    // 쪽지 토스트 메시지
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(item.getItemId() == R.id.icon_post) {
//
//            LayoutInflater inflater = getLayoutInflater();
//
//            View layout = inflater.inflate(
//                    R.layout.toast_post, (ViewGroup) findViewById(R.id.toast_layout_root));
//
//            TextView text = layout.findViewById(R.id.text);
//
//            Toast toast = new Toast(this);
//            text.setText("쪽지가 도착했어요.");
//            toast.setGravity(Gravity.TOP, 400, 110);
//            toast.setDuration(Toast.LENGTH_SHORT);
//            toast.setView(layout);
//            toast.show();
//        }
//        return super.onOptionsItemSelected(item);
//    }

}