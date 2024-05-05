package com.example.notemaster.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.notemaster.Activity.ChiTietGhiChu;
import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.R;

import java.util.ArrayList;

public class DanhsachSearchAdapter extends RecyclerView.Adapter<DanhsachSearchAdapter.ViewHolder> {
    Context context;
    ArrayList<Ghichu> listGhichu;
    Button btnDeleteItem;
    ImageView iconrightarrow;

    public DanhsachSearchAdapter(Context context, ArrayList<Ghichu> items) {
        this.listGhichu = items;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameNote;
        TextView tvTimeCreate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNameNote = itemView.findViewById(R.id.tvNamenote);
            tvTimeCreate = itemView.findViewById(R.id.tvTimecreate);
            btnDeleteItem = itemView.findViewById(R.id.btnDeleteItem);
            btnDeleteItem.setVisibility(View.GONE);
            iconrightarrow = itemView.findViewById(R.id.iconrightarrow);
            iconrightarrow.setVisibility(View.GONE);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Ghichu gc = listGhichu.get(getAdapterPosition());
                        Intent intent = new Intent(context, ChiTietGhiChu.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Ghichu", gc);
                        context.startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e("e", e.toString());
                    }
                }
            });
        }
    }

    @Override
    public DanhsachSearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongdanhsachghichu, parent, false);
        return new DanhsachSearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DanhsachSearchAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Ghichu item = listGhichu.get(position);
        holder.tvNameNote.setText(item.getTieude());
        holder.tvTimeCreate.setText(item.getTimecreate());
    }

    @Override
    public int getItemCount() {
        return listGhichu.size();
    }
}

