package com.example.notemaster.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.Fragment.Fragment_Alarm;
import com.example.notemaster.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.example.notemaster.Adapter.ViewPagerAdapter;
import com.example.notemaster.Fragment.Fragment_Calendar;
 import com.example.notemaster.Fragment.Fragment_Setting;
import com.example.notemaster.Fragment.Fragment_Trangchu;

public class MainActivity extends AppCompatActivity {
    private androidx.viewpager.widget.ViewPager viewPager;
    private BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.initDatabase();

        initFragment();
    }

    public void initFragment() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            // Sự kiện vuốt màn hình chuyển fragment
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_TrangChu).setChecked(true);
                        break;
                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_Calendar).setChecked(true);
                        break;
                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_Image).setChecked(true);
                        break;
                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_tab_Setting).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        // sự kiện nhấn vào icon để chuyển fragment
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_tab_TrangChu) {
                    viewPager.setCurrentItem(0);
                    Fragment_Trangchu fragment = (Fragment_Trangchu) viewPager.getAdapter().instantiateItem(viewPager, 0);
                    // khi nhấn vào lại các fragment dlieu sẽ đc load lại
                    fragment.reloadData();
                } else if (item.getItemId() == R.id.menu_tab_Calendar) {
                    viewPager.setCurrentItem(1);
                    Fragment_Calendar fragment = (Fragment_Calendar) viewPager.getAdapter().instantiateItem(viewPager, 1);
                    fragment.reloadData();
                } else if (item.getItemId() == R.id.menu_tab_Image) {
                    viewPager.setCurrentItem(2);
                    Fragment_Alarm fragment = (Fragment_Alarm) viewPager.getAdapter().instantiateItem(viewPager, 2);
                    fragment.reloadData();
                } else if (item.getItemId() == R.id.menu_tab_Setting) {
                    viewPager.setCurrentItem(3);
                    Fragment_Setting fragment = (Fragment_Setting) viewPager.getAdapter().instantiateItem(viewPager, 3);
                    fragment.reloadData();
                }
                return true;
            }
        });

    }
}