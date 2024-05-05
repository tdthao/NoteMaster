package com.example.notemaster.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notemaster.Adapter.DatabaseHelper;
 import com.example.notemaster.Adapter.TitleTrangchuAdapter;
import com.example.notemaster.Model.Ghichu_Thumuc;
import com.example.notemaster.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Fragment_Calendar extends Fragment {
    CalendarView calendarView;
    ArrayList<Ghichu_Thumuc> arrNote;
    DatabaseHelper dbHelper;
    RecyclerView recyclerView;
    RelativeLayout RLTitle;
    TitleTrangchuAdapter titleTrangchuAdapter;

    public Fragment_Calendar() {
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    // khi cần load lại dlieu của activity này nó sẽ vào đây
    @Override
    public void onResume() {
        super.onResume();
    }

    // hàm load lại dlieu khi cần và đc gọi đến
    public void reloadData() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView = view.findViewById(R.id.Calendar);
        recyclerView = view.findViewById(R.id.rcDsghichu);
        RLTitle = view.findViewById(R.id.RLTitle);
        dbHelper = new DatabaseHelper(getContext());

        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = dateFormat.format(currentDate);
        renderRecycleview(formattedDate);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                renderRecycleview(selectedDate);
            }
        });

        RLTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long currentTimeMillis = System.currentTimeMillis();
                calendarView.setDate(currentTimeMillis);
            }
        });

        return view;
    }

    private void renderRecycleview(String date) {
        arrNote = dbHelper.GetNoteByDate(date);
        titleTrangchuAdapter = new TitleTrangchuAdapter(getContext(), arrNote);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(titleTrangchuAdapter);
    }

}
