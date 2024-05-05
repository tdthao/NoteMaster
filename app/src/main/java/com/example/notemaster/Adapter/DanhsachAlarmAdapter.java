package com.example.notemaster.Adapter;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.notemaster.Model.Nhacnho;
import com.example.notemaster.R;

import java.util.ArrayList;

public class DanhsachAlarmAdapter extends ArrayAdapter<Nhacnho> {
    ArrayList<Nhacnho> arr;
    DatabaseHelper dbHelper;
    String check;
    ImageView imvDelete;
    ImageView imvArrowRight;
    Switch switchButton;
    RelativeLayout RLcontainer;

    public DanhsachAlarmAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Nhacnho> objects, String check) {
        super(context, resource, objects);
        this.arr = objects;
        this.check = check;
    }

    class ViewHolder {
        TextView tvtimeAlarm;
        TextView tvTitle;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DanhsachAlarmAdapter.ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.dong_danhsach_alarm, null);
            viewHolder = new DanhsachAlarmAdapter.ViewHolder();
            viewHolder.tvtimeAlarm = convertView.findViewById(R.id.tvtimeAlarm);
            viewHolder.tvTitle = convertView.findViewById(R.id.tvTitle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (DanhsachAlarmAdapter.ViewHolder) convertView.getTag();
        }
        imvDelete = convertView.findViewById(R.id.imvDelete);
        switchButton = convertView.findViewById(R.id.switchButton);
        imvArrowRight = convertView.findViewById(R.id.imvArrowRight);
        RLcontainer = convertView.findViewById(R.id.RLcontainer);
        dbHelper = new DatabaseHelper(getContext());

        Nhacnho nhacnho = getItem(position);
        viewHolder.tvtimeAlarm.setText(nhacnho.getTimeAlarm());
        viewHolder.tvTitle.setText(nhacnho.getTitleAlarm());

        if (check.equals("fix")) {
            switchButton.setVisibility(View.GONE);
            imvArrowRight.setVisibility(View.VISIBLE);
            AnimationItem(-100, 0, convertView);
        } else if (check.equals("complete")) {
            switchButton.setVisibility(View.VISIBLE);
            imvArrowRight.setVisibility(View.GONE);
            AnimationItem(0, -100, convertView);
        } else if (check.equals("first")) {
            switchButton.setVisibility(View.VISIBLE);
            imvArrowRight.setVisibility(View.GONE);
            AnimationItem(-100, -100, convertView);
        }

        imvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.DeleteTableAlarm(nhacnho.getID_Alarm());
                arr.remove(nhacnho);
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    private void AnimationItem(float x, float y, View convertView) {
        ValueAnimator animator = ValueAnimator.ofFloat(x, y); // Điểm đầu và điểm kết thúc của hiệu ứng trượt (thay đổi giá trị tùy ý).
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        View finalConvertView = convertView;
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                finalConvertView.setTranslationX(value); // Cập nhật giá trị translationX để thực hiện hiệu ứng trượt.
            }
        });
        animator.start();
    }
}
