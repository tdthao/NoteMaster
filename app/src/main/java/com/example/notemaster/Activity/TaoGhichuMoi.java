package com.example.notemaster.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.notemaster.Adapter.DanhsachThumucAdapter;
import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.Adapter.ImageAdapter;
import com.example.notemaster.Interface.I_ListviewNewNote;
import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.Model.HinhanhGhichu;
import com.example.notemaster.Model.Thumuc;
import com.example.notemaster.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaoGhichuMoi extends AppCompatActivity {
    ImageView btnBackHome;
    TextView btnSave;
    TextView tvTimeCreate;
    EditText edTitleNote;
    TextView tvNameFolder;
    EditText edtextContentgc;
    RelativeLayout Rlayout;
    RelativeLayout RLContainerPopup;
    ImageView btnAddImage;
    ImageView btnClosePopup;
    ListView lvImage;
    ListView lvDsthumuc;
    ArrayList<Thumuc> arrThumuc;
    ArrayList<Ghichu> arrGhichu;
    ArrayList<HinhanhGhichu> listHinhAnh;
    DatabaseHelper dbHelper;
    DanhsachThumucAdapter danhsachThumucAdapter;
    ImageAdapter adapter;
    I_ListviewNewNote iListviewNewNote;
    Integer IDThumuc = 1;
    Integer SizeArrGhichu;
    String formattedDateTime;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_ghichu_moi);

        initActivity();

        btnBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Rlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RLContainerPopup.setVisibility(View.VISIBLE);
                danhsachThumucAdapter = new DanhsachThumucAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, arrThumuc);
                danhsachThumucAdapter.iListviewNewNote = iListviewNewNote;
                lvDsthumuc.setAdapter(danhsachThumucAdapter);
            }
        });

        btnClosePopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RLContainerPopup.setVisibility(View.GONE);
            }
        });

        // event chọn tên thư mục
        iListviewNewNote = new I_ListviewNewNote() {
            @Override
            public void onDataTransfer(Integer id, String nameFolder) {
                RLContainerPopup.setVisibility(View.GONE);
                IDThumuc = id;
                tvNameFolder.setText(nameFolder);
            }
        };

        btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(TaoGhichuMoi.this)
                        .crop()
                        .compress(1024)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteHoverEdtext(edTitleNote);
                DeleteHoverEdtext(edtextContentgc);
                dbHelper.InsertTableNote(edTitleNote.getText().toString(), edtextContentgc.getText().toString(), IDThumuc, formattedDateTime);

                arrGhichu = dbHelper.GetDataGhichu();
                SizeArrGhichu = arrGhichu.size() - 1;
                dbHelper.UpdateTableImageByIDNote(SizeArrGhichu);
                dbHelper.DeleteTableNote(0);
                btnSave.setVisibility(View.GONE);
                btnAddImage.setVisibility(View.GONE);
                SendData("Save");
            }
        });
    }

    private void DeleteHoverEdtext(EditText name) {
        name.setFocusable(false);
        name.setClickable(false);
        name.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(name.getWindowToken(), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = data.getData();
        byte[] arr = uriToByteArray(selectedImageUri);
        dbHelper.InsertTableHinhanhChichu(arr, 0);
        SendData("NewNote");
    }

    private void SendData(String check) {
        listHinhAnh = dbHelper.GetDataHinhanh(0);
        if (listHinhAnh.size() == 0) {
            listHinhAnh = dbHelper.GetDataHinhanh(SizeArrGhichu);
        }
        adapter = new ImageAdapter(this, android.R.layout.simple_list_item_1, listHinhAnh, check);
        lvImage.setAdapter(adapter);
    }

    public byte[] uriToByteArray(Uri uri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, length);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void initActivity() {
        btnBackHome = findViewById(R.id.btnBackHome);
        btnSave = findViewById(R.id.btnSave);
        tvTimeCreate = findViewById(R.id.tvTimeCreate);
        edTitleNote = findViewById(R.id.tvTitleNote);
        tvNameFolder = findViewById(R.id.tvNameFolder);
        Rlayout = findViewById(R.id.RLNameFolder);
        edtextContentgc = findViewById(R.id.edtextContentgc);
        btnAddImage = findViewById(R.id.btnAddImage);
        btnClosePopup = findViewById(R.id.btnClosePopup);
        lvImage = findViewById(R.id.lvImage);
        lvDsthumuc = findViewById(R.id.lvDsthumuc);
        RLContainerPopup = findViewById(R.id.RLContainerPopup);

        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        formattedDateTime = dateFormat.format(date);
        tvTimeCreate.setText(formattedDateTime);
        dbHelper = new DatabaseHelper(getApplicationContext());
        arrThumuc = dbHelper.GetDataFolder();
        // tạo bảng ghi chú tạm thời IDGhichu = 0
        dbHelper.InsertTemporaryTableNote(0);

        // TH ghi chú mới được tạo từ các folder thì sẽ điền vào các ô tương ứng của newNote
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("IDFolder")) {
                IDThumuc = intent.getIntExtra("IDFolder", 0);
            }
            if (intent.hasExtra("NameFolder")) {
                String nameFolder = intent.getStringExtra("NameFolder");
                tvNameFolder.setText(nameFolder);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}