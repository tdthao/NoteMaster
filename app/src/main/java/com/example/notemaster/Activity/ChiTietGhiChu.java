package com.example.notemaster.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.Adapter.ImageAdapter;
import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.Model.Ghichu_Thumuc;
import com.example.notemaster.Model.HinhanhGhichu;
import com.example.notemaster.Model.Thumuc;
import com.example.notemaster.R;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ChiTietGhiChu extends AppCompatActivity {
    ImageView imvBack;
    ImageView imvoption;
    TextView tvSave;
    ImageView imvpencil;
    ImageView imvAddImage;
    TextView txtTenThumuc;
    TextView txtTimeCreate;
    TextView txtTieudegc;
    EditText edTextNd;
    DatabaseHelper dbHelper;
    String nameFolder;
    ListView lvImage;
    ArrayList<HinhanhGhichu> listHinhAnh;
    ImageAdapter adapter;
    RelativeLayout RLChitietghichu;
    Ghichu ghichu;
    Ghichu_Thumuc gcthumuc;
    int flag = 0;
    boolean changeMenu = false;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_ghi_chu);
        RLChitietghichu = findViewById(R.id.ActivityChitietghichu);

        GetTag();

        initActivity();

        imvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        imvpencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvSave.setVisibility(View.VISIBLE);
                imvpencil.setVisibility(View.GONE);
                imvoption.setVisibility(View.GONE);
                imvAddImage.setVisibility(View.VISIBLE);
                edTextNd.setFocusable(true);
                edTextNd.setFocusableInTouchMode(true);
                edTextNd.requestFocus();
                // cho con trỏ chuột ở cuối cx đoạn text
                edTextNd.setSelection(edTextNd.getText().length());

                if (flag == 0) {
                    SendData("Fix", "Ghichu");
                } else if (flag == 1) {
                    SendData("Fix", "GhichuThumuc");
                }
            }
        });

        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edTextNd.setFocusable(false);
                edTextNd.setClickable(false);
                edTextNd.clearFocus();
                imvAddImage.setVisibility(View.GONE);
                // ẩn bàn phím khi k focus nữa
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edTextNd.getWindowToken(), 0);
                tvSave.setVisibility(View.GONE);
                imvpencil.setVisibility(View.VISIBLE);
                imvoption.setVisibility(View.VISIBLE);

                String content = edTextNd.getText().toString();
                if (flag == 0) {
                    SendData("Save", "Ghichu");
                    dbHelper.UpdateContentNote(ghichu.getID_Ghichu(), content);
                } else if (flag == 1) {
                    SendData("Save", "GhichuThu,uc");
                    dbHelper.UpdateContentNote(gcthumuc.getID_Ghichu(), content);
                }
            }
        });

        imvAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ChiTietGhiChu.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        imvoption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // changeMenu = true sẽ mở khóa còn false là tạo khóa
                if (changeMenu) {
                    ShowMenuUnLock();
                } else {
                    ShowMenuSetKey();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri selectedImageUri = data.getData();
        byte[] arr = uriToByteArray(selectedImageUri);
        if (flag == 0) {
            dbHelper.InsertTableHinhanhChichu(arr, ghichu.getID_Ghichu());
            SendData("Fix", "Ghichu");
        } else if (flag == 1) {
            dbHelper.InsertTableHinhanhChichu(arr, gcthumuc.getID_Ghichu());
            SendData("Fix", "GhichuThumuc");
        }
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
        try {
            Intent intent = getIntent();
            if (intent != null) {
                if (intent.hasExtra("Ghichu")) {
                    // Từ DanhsachGhichuAdapter chuyển sang
                    ghichu = (Ghichu) intent.getSerializableExtra("Ghichu");
                    String NewKey = ghichu.getKey().replace(" ", "");
                    // Ktra xem ghi chú có khóa không
                    if (!NewKey.isEmpty()) {
                        RLChitietghichu.setVisibility(View.GONE);
                        openDialogOpenKey(Gravity.CENTER, ghichu.getKey());
                        changeMenu = true;
                    } else {
                        changeMenu = false;
                    }

                    dbHelper = new DatabaseHelper(getApplicationContext());
                    nameFolder = dbHelper.GetNameFolderByID(ghichu.getID_Thumuc());
                    txtTimeCreate.setText(ghichu.getTimecreate());
                    txtTieudegc.setText(ghichu.getTieude());
                    edTextNd.setText(ghichu.getNoidung());
                    txtTenThumuc.setText(nameFolder);
                    SendData("LoadData", "Ghichu");
                    flag = 0;
                } else if (intent.hasExtra("GhichuThumuc")) {
                    // Từ TitleTrangchuAdapter chuyển sang
                    gcthumuc = (Ghichu_Thumuc) intent.getSerializableExtra("GhichuThumuc");
                    String NewKey = gcthumuc.getKey().replace(" ", "");
                    // Ktra xem ghi chú có khóa không
                    if (!NewKey.isEmpty()) {
                        RLChitietghichu.setVisibility(View.GONE);
                        openDialogOpenKey(Gravity.CENTER, gcthumuc.getKey());
                        changeMenu = true;
                    } else {
                        changeMenu = false;
                    }

                    dbHelper = new DatabaseHelper(getApplicationContext());
                    nameFolder = dbHelper.GetNameFolderByID(gcthumuc.getID_Thumuc());

                    txtTimeCreate.setText(gcthumuc.getTimecreate());
                    txtTieudegc.setText(gcthumuc.getTieude());
                    edTextNd.setText(gcthumuc.getNoidung());
                    txtTenThumuc.setText(nameFolder);
                    SendData("LoadData", "GhichuThumuc");
                    flag = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Err", "Lỗi nhận intent từ DsghichuAdapter, TitleTrangchuAdapter");
        }
    }

    private void GetTag() {
        imvBack = findViewById(R.id.btnBackdsgc);
        imvoption = findViewById(R.id.btnoption);
        imvpencil = findViewById(R.id.btnFixnote);
        imvAddImage = findViewById(R.id.btnAddImage);
        tvSave = findViewById(R.id.btnSave);
        txtTenThumuc = findViewById(R.id.tvNameFolder);
        txtTimeCreate = findViewById(R.id.tvTimeCreate);
        txtTieudegc = findViewById(R.id.tvTitleNote);
        lvImage = findViewById(R.id.lvImage);
        edTextNd = findViewById(R.id.edtextContentgc);
        edTextNd.setFocusable(false);
        edTextNd.setClickable(false);
        imvAddImage.setVisibility(View.GONE);
    }

    private void SendData(String check, String name) {
        if (name.equals("GhichuThumuc")) {
            listHinhAnh = dbHelper.GetDataHinhanh(gcthumuc.getID_Ghichu());
        } else if (name.equals("Ghichu")) {
            listHinhAnh = dbHelper.GetDataHinhanh(ghichu.getID_Ghichu());
        }
        adapter = new ImageAdapter(this, android.R.layout.simple_list_item_1, listHinhAnh, check);
        lvImage.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void ShowMenuSetKey() {
        PopupMenu popupMenu = new PopupMenu(this, imvoption);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_setkey, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.setKey) {
                    openDialogCreateKey(Gravity.CENTER);
                } else if (item.getItemId() == R.id.deleteNote) {
                    DeleteNote();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void ShowMenuUnLock() {
        PopupMenu popupMenu = new PopupMenu(this, imvoption);
        popupMenu.getMenuInflater().inflate(R.menu.menu_popup_unlock, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.unLock) {
                    if (flag == 0) {
                        changeMenu = false;
                        dbHelper.UpdateKeyNote(ghichu.getID_Ghichu(), " ");
                    } else {
                        changeMenu = false;
                        dbHelper.UpdateKeyNote(gcthumuc.getID_Ghichu(), " ");
                    }
                } else if (item.getItemId() == R.id.deleteNote) {
                    DeleteNote();
                }
                return true;
            }
        });
        popupMenu.show();
    }

    private void DeleteNote() {
        if (flag == 0) {
            dbHelper.DeleteTableNote(ghichu.getID_Ghichu());
            onBackPressed();
        } else if (flag == 1) {
            dbHelper.DeleteTableNote(gcthumuc.getID_Ghichu());
            onBackPressed();
        }
    }

    private void openDialogOpenKey(int gravity, String key) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_diaolg_openkey);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false);
        }

        Button btHuy = dialog.findViewById(R.id.btncancel);
        Button btOK = dialog.findViewById(R.id.btnok);
        EditText edTextKey = dialog.findViewById(R.id.edTextKey);
        TextView tvLogOpen = dialog.findViewById(R.id.tvLogOpen);
        ImageView imvEyepass = dialog.findViewById(R.id.imvEyepass);

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        btOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String input = edTextKey.getText().toString();
                    if (input.equals(key)) {
                        dialog.dismiss();
                        RLChitietghichu.setVisibility(View.VISIBLE);
                    } else {
                        tvLogOpen.setText("Đó không phải là mật khẩu đúng. Vui lòng nhập lại");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Err", "Lỗi mở ghi chú");
                }
            }
        });

        imvEyepass.setOnTouchListener(new View.OnTouchListener() {
            boolean isTouching = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Khi người dùng ấn giữ biểu tượng
                        isTouching = true;
                        edTextKey.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edTextKey.setSelection(edTextKey.getText().length());
                        return true;
                    case MotionEvent.ACTION_UP:
                        // Khi người dùng nhả tay biểu tượng
                        isTouching = false;
                        edTextKey.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edTextKey.setSelection(edTextKey.getText().length());
                        return true;
                }
                return false;
            }
        });

        dialog.show();
    }

    private void openDialogCreateKey(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_createkey);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = gravity;
        window.setAttributes(windownAttributes);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(false);
        }

        Button btHuy = dialog.findViewById(R.id.btncancel);
        Button btTao = dialog.findViewById(R.id.btncreate);
        EditText edTextKey = dialog.findViewById(R.id.edTextKey);
        TextView tvlog = dialog.findViewById(R.id.tvlog);

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                onBackPressed();
            }
        });

        btTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String Key = edTextKey.getText().toString().replace(" ", "");
                    if (!Key.isEmpty()) {
                        if (flag == 0) {
                            dbHelper.UpdateKeyNote(ghichu.getID_Ghichu(), Key);
                            changeMenu = true;
                            dialog.dismiss();
                        } else {
                            dbHelper.UpdateKeyNote(gcthumuc.getID_Ghichu(), Key);
                            changeMenu = true;
                            dialog.dismiss();
                        }
                    } else {
                        tvlog.setVisibility(View.VISIBLE);
                        tvlog.setText("Mật khẩu tạo không hợp lệ.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Err", e.toString());
                }
            }
        });

        dialog.show();
    }
}