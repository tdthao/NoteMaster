package com.example.notemaster.Fragment;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.notemaster.Activity.ThemNhacnho;
import com.example.notemaster.Adapter.DanhsachAlarmAdapter;
import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.Model.Nhacnho;
import com.example.notemaster.R;

import java.util.ArrayList;

public class Fragment_Alarm extends Fragment {
    TextView tvFix;
    ImageView imvAdd;
    TextView tvComplete;
    Switch switchButton;
    ListView lvDsAlarm;
    ArrayList<Nhacnho> arrAlarm;
    RelativeLayout RLmain;
    DanhsachAlarmAdapter adapter;
    DatabaseHelper dbHelper;

    public Fragment_Alarm() {
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        arrAlarm = dbHelper.GetDataAlarm();
        adapter = new DanhsachAlarmAdapter(getContext(), android.R.layout.simple_list_item_1, arrAlarm, "first");
        lvDsAlarm.setAdapter(adapter);
    }

    public void reloadData() {
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        RLmain = view.findViewById(R.id.RLmain);
        imvAdd = view.findViewById(R.id.imvAdd);
        lvDsAlarm = view.findViewById(R.id.lvDsAlarm);
        tvFix = view.findViewById(R.id.tvFix);
        tvComplete = view.findViewById(R.id.tvComplete);
        dbHelper = new DatabaseHelper(getContext());

        imvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ThemNhacnho.class);
                startActivity(intent);
            }
        });

        tvFix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new DanhsachAlarmAdapter(getContext(), android.R.layout.simple_list_item_1, arrAlarm, "fix");
                lvDsAlarm.setAdapter(adapter);
                tvComplete.setVisibility(View.VISIBLE);
                tvFix.setVisibility(View.GONE);
            }
        });

        tvComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new DanhsachAlarmAdapter(getContext(), android.R.layout.simple_list_item_1, arrAlarm, "complete");
                lvDsAlarm.setAdapter(adapter);
                tvFix.setVisibility(View.VISIBLE);
                tvComplete.setVisibility(View.GONE);
            }
        });
        return view;
    }
}
