package com.example.notemaster.Adapter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.notemaster.Activity.Danhsachghichu;
import com.example.notemaster.Fragment.Fragment_Trangchu;
import com.example.notemaster.Model.Thumuc;
import com.example.notemaster.R;

import java.util.ArrayList;
import java.util.List;

public class ContentTrangchuAdapter extends ArrayAdapter<Thumuc> {
    private List<Thumuc> thumucList;
    String check;
    ImageView imvDelete;
    ImageView iconfolder;
    ImageView btsua;

    public ContentTrangchuAdapter(@NonNull Context context, int resource, @NonNull List<Thumuc> objects, String check) {
        super(context, resource, objects);
        this.thumucList = objects;
        this.check = check;
    }

    class ViewHolder {
        TextView tvNameFoler;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dongcontenttrangchu, null);
            viewHolder = new ViewHolder();
            viewHolder.tvNameFoler = convertView.findViewById(R.id.tvNamefolder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Thumuc thumuc = getItem(position);
        viewHolder.tvNameFoler.setText(thumuc.getTenThumuc());

        btsua = convertView.findViewById(R.id.btnSuaTenFolder);
        imvDelete = convertView.findViewById(R.id.imvDelete);
        iconfolder = convertView.findViewById(R.id.iconfolder);

        btsua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RenameFolder(position, thumuc.getID_Thumuc(), thumuc.getTenThumuc());
            }
        });

        ViewHolder finalViewHolder = viewHolder;
        imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteFolder(position, thumuc.getID_Thumuc());
            }
        });

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Danhsachghichu.class);
                intent.putExtra("ItemThumuc", thumuc);
                getContext().startActivity(intent);
            }
        });

        if (check.equals("fix")) {
            btsua.setVisibility(View.GONE);
            AnimationItem(-120, 0, viewHolder.tvNameFoler, imvDelete, iconfolder);
        } else if (check.equals("complete")) {
            btsua.setVisibility(View.VISIBLE);
            AnimationItem(0, -120, viewHolder.tvNameFoler, imvDelete, iconfolder);
        } else if (check.equals("first")) {
            AnimationItem(-120, -120, viewHolder.tvNameFoler, imvDelete, iconfolder);
        }

        return convertView;
    }

    private void AnimationItem(float x, float y, View convertView1, View imvDelete, View iconfolder) {
        ValueAnimator animator = ValueAnimator.ofFloat(x, y); // Điểm đầu và điểm kết thúc của hiệu ứng trượt (thay đổi giá trị tùy ý).
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                convertView1.setTranslationX(value); // Cập nhật giá trị translationX để thực hiện hiệu ứng trượt.
                imvDelete.setTranslationX(value); // Cập nhật giá trị translationX để thực hiện hiệu ứng trượt.
                iconfolder.setTranslationX(value); // Cập nhật giá trị translationX để thực hiện hiệu ứng trượt.
            }
        });
        animator.start();
    }

    private void deleteItemListview(int position) {
        if (position >= 0 && position < thumucList.size()) {
            thumucList.remove(position);
            notifyDataSetChanged();
        }
    }

    private void DeleteFolder(int position, int idFolder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_deletefolder);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.BOTTOM;
        window.setAttributes(windownAttributes);

        Button btHuy = dialog.findViewById(R.id.btncancel);
        Button btXoa = dialog.findViewById(R.id.btndelete);

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItemListview(position);
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                dbHelper.DeleteTableFolder(idFolder);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void RenameFolder(int position, int idFolder, String NameFolder) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_renamefolder);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windownAttributes = window.getAttributes();
        windownAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAttributes);

        Button btHuy = dialog.findViewById(R.id.btncancel);
        Button btSua = dialog.findViewById(R.id.btnrename);
        EditText editnamefolder = dialog.findViewById(R.id.editNamefolder);
        editnamefolder.setText(NameFolder);

        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rename = editnamefolder.getText().toString();
                renameItemListview(position, rename);
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());
                dbHelper.RenameTableFolder(idFolder, rename);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void renameItemListview(int position, String nameNew) {
        if (position >= 0 && position < thumucList.size()) {
            Thumuc thumucToEdit = thumucList.get(position);
            thumucToEdit.setTenThumuc(nameNew);
            notifyDataSetChanged();
        }
    }

}
