package com.example.notemaster.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notemaster.Interface.I_ListviewNewNote;
import com.example.notemaster.Model.Thumuc;
import com.example.notemaster.R;

import java.util.ArrayList;

public class DanhsachThumucAdapter extends ArrayAdapter<Thumuc> {
    ArrayList<Thumuc> thumucList;
    public I_ListviewNewNote iListviewNewNote;

    public DanhsachThumucAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Thumuc> objects) {
        super(context, resource, objects);
        this.thumucList = objects;
    }

    class ViewHolder {
        TextView tenFolder;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DanhsachThumucAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dongdanhsachthumuc, null);
            viewHolder = new DanhsachThumucAdapter.ViewHolder();
            viewHolder.tenFolder = convertView.findViewById(R.id.tvNamefolder);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DanhsachThumucAdapter.ViewHolder) convertView.getTag();
        }

        Thumuc thumuc = getItem(position);
        viewHolder.tenFolder.setText(thumuc.getTenThumuc());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iListviewNewNote.onDataTransfer(thumuc.getID_Thumuc(), thumuc.getTenThumuc());
            }
        });

        return convertView;
    }

}