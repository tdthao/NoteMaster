package com.example.notemaster.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notemaster.Adapter.DanhsachGhichuAdapter;
import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.Adapter.SwipeToDeleteCallbackDsgcOfAllNote;
import com.example.notemaster.Adapter.SwipeToDeleteCallbackDsgcOfFolder;
import com.example.notemaster.Adapter.TitleTrangchuAdapter;
import com.example.notemaster.Fragment.Fragment_Trangchu;
import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.Model.Ghichu_Thumuc;
import com.example.notemaster.Model.Thumuc;
import com.example.notemaster.R;

import java.util.ArrayList;

public class Danhsachghichu extends AppCompatActivity {
    Thumuc thumuc;
    TextView tvNameFolder;
    DatabaseHelper dbHelper;
    ImageView btnBackhome;
    ImageView imgpencil;
    ArrayList<Ghichu> arrGhichu;
    ArrayList<Ghichu_Thumuc> arrGhichuThumuc;
    DanhsachGhichuAdapter dsgcAdapter;
    TitleTrangchuAdapter titleTrangchuAdapter;
    RecyclerView recyclerView;
    int flag = 0;
    int idThumuc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danhsachghichu);

        init();

        imgpencil = findViewById(R.id.imgpencil);
        imgpencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 1) {
                    // ghi chú mới được tạo từ trong các thư mục
                    Intent intent = new Intent(getApplicationContext(), TaoGhichuMoi.class);
                    intent.putExtra("IDFolder", thumuc.getID_Thumuc());
                    intent.putExtra("NameFolder", thumuc.getTenThumuc());
                    startActivity(intent);
                } else {
                    // ghi chú mới được tạo từ tất cả các ghi chú
                    Intent intent = new Intent(getApplicationContext(), TaoGhichuMoi.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void init() {
        recyclerView = findViewById(R.id.RecyclerViewDsghichu);
        tvNameFolder = findViewById(R.id.tvTenThumuc);

        Intent intent = getIntent();
        if (intent != null) {
            // chuyển từ ContentTrangchuAdapter
            if (intent.hasExtra("ItemThumuc")) {
                thumuc = (Thumuc) intent.getSerializableExtra("ItemThumuc");
                idThumuc = thumuc.getID_Thumuc();
                String tenThumuc = thumuc.getTenThumuc();
                tvNameFolder.setText(tenThumuc);

                dbHelper = new DatabaseHelper(getApplication());
                arrGhichu = dbHelper.GetDataNoteByID(idThumuc);

                dsgcAdapter = new DanhsachGhichuAdapter(getApplication(), arrGhichu);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(dsgcAdapter);

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackDsgcOfFolder(dsgcAdapter));
                itemTouchHelper.attachToRecyclerView(recyclerView);
                EventBack();
                flag = 1;

                // Chuyển từ Fragment_Trangchu
            } else if (intent.hasExtra("AllNoteByFolder")) {
                arrGhichuThumuc = (ArrayList<Ghichu_Thumuc>) intent.getSerializableExtra("AllNoteByFolder");
                tvNameFolder.setText("Tất cả Ghi chú");
                titleTrangchuAdapter = new TitleTrangchuAdapter(getApplication(), arrGhichuThumuc);
                LinearLayoutManager llm = new LinearLayoutManager(this);
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(llm);
                recyclerView.setAdapter(titleTrangchuAdapter);

                ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackDsgcOfAllNote(titleTrangchuAdapter));
                itemTouchHelper.attachToRecyclerView(recyclerView);
                EventBack();
            }
        }
    }

    public void EventBack() {
        btnBackhome = findViewById(R.id.btnBackHomePage);
        btnBackhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment_Trangchu fragmentTrangchu = new Fragment_Trangchu();
                FragmentTransaction faFragmentTransaction = getSupportFragmentManager().beginTransaction();
                faFragmentTransaction.replace(R.id.containerActivitydsgc, fragmentTrangchu).commit();
            }
        });
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        dbHelper = new DatabaseHelper(getApplication());
//        // reload lại từ trong các thư mục con
//        if (flag == 1) {
//            arrGhichu = dbHelper.GetDataNoteByID(idThumuc);
//            dsgcAdapter = new DanhsachGhichuAdapter(getApplication(), arrGhichu);
//            LinearLayoutManager llm = new LinearLayoutManager(this);
//            llm.setOrientation(LinearLayoutManager.VERTICAL);
//            recyclerView.setLayoutManager(llm);
//            recyclerView.setAdapter(dsgcAdapter);
//            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackDsgcOfFolder(dsgcAdapter));
//            itemTouchHelper.attachToRecyclerView(recyclerView);
//            EventBack();
//            flag = 1;
//        } else {
//            // reload lại từ trong tất cả các ghi chú
//            arrGhichuThumuc = dbHelper.GetDataNoteByFolder();
//            titleTrangchuAdapter = new TitleTrangchuAdapter(getApplication(), arrGhichuThumuc);
//            LinearLayoutManager llm = new LinearLayoutManager(this);
//            llm.setOrientation(LinearLayoutManager.VERTICAL);
//            recyclerView.setLayoutManager(llm);
//            recyclerView.setAdapter(titleTrangchuAdapter);
//            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallbackDsgcOfAllNote(titleTrangchuAdapter));
//            itemTouchHelper.attachToRecyclerView(recyclerView);
//            EventBack();
//        }
//    }
}