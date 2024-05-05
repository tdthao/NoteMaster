package com.example.notemaster.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notemaster.Model.HinhanhGhichu;
import com.example.notemaster.R;

import java.util.List;

public class ImageAdapter extends ArrayAdapter<HinhanhGhichu> {
    List<HinhanhGhichu> imageList;
    DatabaseHelper dbHelper;
    String check;
    ImageView imvtrashbin;

    public ImageAdapter(@NonNull Context context, int resource, @NonNull List<HinhanhGhichu> objects, String check) {
        super(context, resource, objects);
        this.imageList = objects;
        this.check = check;
    }

    class ViewHolder {
        ImageView imvHinhanh;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ImageAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item, null);
            viewHolder = new ImageAdapter.ViewHolder();
            viewHolder.imvHinhanh = convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ImageAdapter.ViewHolder) convertView.getTag();
        }
        imvtrashbin = convertView.findViewById(R.id.icondeleteImg);

        HinhanhGhichu hinhanh = getItem(position);
        Bitmap imgBH = BitmapFactory.decodeByteArray(hinhanh.getHinhanh(), 0, hinhanh.getHinhanh().length);
        viewHolder.imvHinhanh.setImageBitmap(imgBH);

        if (check.equals("Fix") || check.equals("NewNote")) {
            imvtrashbin.setVisibility(View.VISIBLE);
            imvtrashbin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItemListview(position);
                    dbHelper = new DatabaseHelper(getContext());
                    dbHelper.DeleteHinhanhByID(hinhanh.getID_Hinhanh());
                }
            });
        } else if (check.equals("Save")) {
            imvtrashbin.setVisibility(View.GONE);
        }

        return convertView;
    }

    private void deleteItemListview(int position) {
        if (position >= 0 && position < imageList.size()) {
            imageList.remove(position);
            notifyDataSetChanged();
        }
    }

}