package com.example.notemaster.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.notemaster.Activity.ChiTietGhiChu;
import com.example.notemaster.Model.Ghichu;
import com.example.notemaster.R;
import java.util.ArrayList;

public class DanhsachGhichuAdapter extends RecyclerView.Adapter<DanhsachGhichuAdapter.ViewHolder> {
    Context context;
    ArrayList<Ghichu> listGhichu;
    DatabaseHelper dbHelper;
    boolean isSwiping = false;

    public DanhsachGhichuAdapter(Context context, ArrayList<Ghichu> items) {
        this.listGhichu = items;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNameNote;
        TextView tvTimeCreate;
        Button deleteButton;
        ImageView imgarrow;
        ImageView imgarrow2;
        int idGhichu;

        public ViewHolder(View itemView) {
            super(itemView);
            tvNameNote = itemView.findViewById(R.id.tvNamenote);
            tvTimeCreate = itemView.findViewById(R.id.tvTimecreate);
            deleteButton = itemView.findViewById(R.id.btnDeleteItem);
            imgarrow = itemView.findViewById(R.id.iconrightarrow);
            imgarrow2 = itemView.findViewById(R.id.iconrightarrow2);

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dongdanhsachghichu, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Ghichu item = listGhichu.get(position);
        holder.tvNameNote.setText(item.getTieude());
        holder.tvTimeCreate.setText(item.getTimecreate());
        holder.idGhichu = item.getID_Ghichu();

        if (isSwiping) {
            holder.deleteButton.setVisibility(View.VISIBLE);
            holder.imgarrow2.setVisibility(View.GONE);
            holder.imgarrow.setVisibility(View.VISIBLE);
        } else {
            holder.deleteButton.setVisibility(View.GONE);
            holder.imgarrow2.setVisibility(View.VISIBLE);
            holder.imgarrow.setVisibility(View.GONE);
        }

        holder.deleteButton.setOnClickListener(view -> {
            final Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.layout_dialog_deletenote);

            Window window = dialog.getWindow();
            if (window == null) {
                return;
            }

            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windownAttributes = window.getAttributes();
            windownAttributes.gravity = Gravity.BOTTOM;
            window.setAttributes(windownAttributes);

            Button btHuy = dialog.findViewById(R.id.btncancelNote);
            Button btXoa = dialog.findViewById(R.id.btndeleteNote);

            btHuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btXoa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listGhichu.remove(position);
                    notifyDataSetChanged();
                    dbHelper = new DatabaseHelper(view.getContext());
                    dbHelper.DeleteTableNote(item.getID_Ghichu());
                    dialog.dismiss();
                }
            });
            dialog.show();
        });
    }

    @Override
    public int getItemCount() {
        return listGhichu.size();
    }

    public void setSwiping(boolean isSwiping) {
        this.isSwiping = isSwiping;
    }

}
