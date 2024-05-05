package com.example.notemaster.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.notemaster.Fragment.Fragment_Alarm;
import com.example.notemaster.Fragment.Fragment_Calendar;
import com.example.notemaster.Fragment.Fragment_Setting;
import com.example.notemaster.Fragment.Fragment_Trangchu;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment_Trangchu();
            case 1:
                return new Fragment_Calendar();
            case 2:
                return new Fragment_Alarm();
            case 3:
                return new Fragment_Setting();
            default:
                return new Fragment_Trangchu();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
