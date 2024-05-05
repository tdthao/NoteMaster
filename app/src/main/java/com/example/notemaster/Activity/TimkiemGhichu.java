package com.example.notemaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notemaster.Adapter.DanhsachSearchAdapter;
import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.Adapter.ImageAdapter;
import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.R;

import java.util.ArrayList;

public class TimkiemGhichu extends AppCompatActivity {
    ImageView btnBackHome;
    ImageView btnDeleteQuery;
    ImageView btnSearch;
    EditText edSearch;
    TextView tvSumResult;
    RecyclerView rvNoteResult;
    DatabaseHelper dbHelper;
    ArrayList<Ghichu> arrGhichu;
    DanhsachSearchAdapter danhsachSearchAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timkiem_ghichu);

        btnBackHome = findViewById(R.id.iconarrowLeft);
        btnDeleteQuery = findViewById(R.id.iconDelete);
        edSearch = findViewById(R.id.edSearch);
        btnSearch = findViewById(R.id.iconSearch);
        rvNoteResult = findViewById(R.id.rvNoteResult);
        tvSumResult = findViewById(R.id.tvSumResult);
        tvSumResult.setVisibility(View.GONE);
        dbHelper = new DatabaseHelper(getApplicationContext());

        edSearch.setFocusable(true);
        edSearch.setClickable(true);


        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        btnDeleteQuery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edSearch.getText().clear();
                rvNoteResult.setVisibility(View.GONE);
                tvSumResult.setText("");
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = edSearch.getText().toString();
                arrGhichu = dbHelper.SearchNote(query);
                tvSumResult.setVisibility(View.VISIBLE);
                if (arrGhichu.size() > 0) {
                    rvNoteResult.setVisibility(View.VISIBLE);
                    tvSumResult.setText("Đã tìm thấy " + arrGhichu.size() + " kết quả.");
                } else {
                    tvSumResult.setText("Không tìm thấy kết quả.");
                }

                danhsachSearchAdapter = new DanhsachSearchAdapter(getApplicationContext(), arrGhichu);
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rvNoteResult.setLayoutManager(llm);
                rvNoteResult.setAdapter(danhsachSearchAdapter);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}