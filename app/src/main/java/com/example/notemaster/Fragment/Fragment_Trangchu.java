package com.example.notemaster.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.notemaster.Activity.Danhsachghichu;
import com.example.notemaster.Activity.TaoGhichuMoi;
import com.example.notemaster.Activity.TimkiemGhichu;
import com.example.notemaster.Adapter.ContentTrangchuAdapter;
import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.Model.Ghichu_Thumuc;
import com.example.notemaster.Model.Thumuc;
import com.example.notemaster.R;

import java.util.ArrayList;

public class Fragment_Trangchu extends Fragment {
    Button btSua;
    Button btnComplete;
    ImageView imvSearch;
    ImageView imvAddfolder;
    ImageView imvNewnote;
    TextView tv;
    ListView listView;
    DatabaseHelper dbHelper;
    ContentTrangchuAdapter contentTrangchuAdapter;
    ArrayList<Thumuc> arrThumuc;
    ArrayList<Ghichu> arrGhichu;
    ArrayList<Ghichu_Thumuc> arrAllGhichu;
    RelativeLayout RelativeLayoutTitle;

    public Fragment_Trangchu() {

    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void reloadData() {
        Toast.makeText(getActivity(), "Reload fragment TC", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trangchu, container, false);
        btSua = view.findViewById(R.id.btnSua);
        btnComplete = view.findViewById(R.id.btnComplete);
        imvAddfolder = view.findViewById(R.id.iconAddfolder);
        imvSearch = view.findViewById(R.id.iconSearch);
        imvNewnote = view.findViewById(R.id.imgpencil);
        tv = view.findViewById(R.id.tvsumnote);
        listView = view.findViewById(R.id.lvContent);
        RelativeLayoutTitle = view.findViewById(R.id.RelativeLayoutTitle);
        dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
        arrGhichu = dbHelper.GetDataGhichu();
        String size = "(" + String.valueOf(arrGhichu.size()) + ")";
        tv.setText(size.toString());

        GetData("first");

        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btSua.setVisibility(View.GONE);
                btnComplete.setVisibility(View.VISIBLE);
                GetData("fix");
            }
        });

        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnComplete.setVisibility(View.GONE);
                btSua.setVisibility(View.VISIBLE);
                GetData("complete");
            }
        });

        imvAddfolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openDialogAddFolder(Gravity.CENTER);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("loi", e.toString());
                }
            }
        });

        imvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TimkiemGhichu.class);
                startActivity(intent);
            }
        });

        RelativeLayoutTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrAllGhichu = dbHelper.GetDataNoteByFolder();
                Intent intent = new Intent(getContext(), Danhsachghichu.class);
                intent.putExtra("AllNoteByFolder", arrAllGhichu);
                getContext().startActivity(intent);
            }
        });

        imvNewnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TaoGhichuMoi.class);
                intent.putExtra("NewNote", "newNote");
                getContext().startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dbHelper = new DatabaseHelper(getActivity().getApplicationContext());
            arrThumuc = dbHelper.GetDataFolder();
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Lỗi", e.toString());
        }
    }

    private void GetData(String input) {
        contentTrangchuAdapter = new ContentTrangchuAdapter(getActivity(), android.R.layout.simple_list_item_1, arrThumuc, input);
        listView.setAdapter(contentTrangchuAdapter);
    }

    private void openDialogAddFolder(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_addfolder);

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
        EditText edittTenfolder = dialog.findViewById(R.id.editNamefolder);

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btTao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String input = edittTenfolder.getText().toString();
                    if (!input.isEmpty()) {
                        dbHelper.InsertTableFolder(input, null);
                        int size = arrThumuc.size() + 1;
                        try {
                            Thumuc newData = new Thumuc(size, input, 1);
                            arrThumuc.add(newData);
                            contentTrangchuAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            Log.e("loi", "loi adapter");
                        }
                        dialog.dismiss();
                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "Vui lòng nhập tên thư mục.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Err", "Lỗi tạo mới thư mục");
                }
            }
        });

        dialog.show();
    }

}
