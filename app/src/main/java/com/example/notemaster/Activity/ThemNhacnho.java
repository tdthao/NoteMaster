package com.example.notemaster.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notemaster.Adapter.DatabaseHelper;
import com.example.notemaster.R;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;

public class ThemNhacnho extends AppCompatActivity {
    RelativeLayout RLChooseTime;
    TextView tvChoosetime;
    TextView tvSaveAlarm;
    TextView btnHuy;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    MaterialTimePicker timePicker;
    Calendar calendar;
    DatabaseHelper dbHelper;
    EditText edTitleAlarm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_nhacnho);
        RLChooseTime = findViewById(R.id.RLChooseTime);
        tvChoosetime = findViewById(R.id.tvChoosetime);
        tvSaveAlarm = findViewById(R.id.tvSaveAlarm);
        edTitleAlarm = findViewById(R.id.edTitleAlarm);
        btnHuy = findViewById(R.id.btnHuy);
        dbHelper = new DatabaseHelper(getApplicationContext());

        createNotificationChannel();

        RLChooseTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                timePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).
                        setHour(calendar.get(Calendar.HOUR_OF_DAY))
                        .setMinute(calendar.get((Calendar.MINUTE))).build();
                timePicker.show(getSupportFragmentManager(), "androidknowledge");

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int hour = timePicker.getHour();
                        int minute = timePicker.getMinute();
                        tvChoosetime.setText(hour + ":" + minute);
                    }
                });
            }
        });

        tvSaveAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(ThemNhacnho.this, AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(ThemNhacnho.this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);
                Toast.makeText(ThemNhacnho.this, "Alarm Set", Toast.LENGTH_SHORT).show();

                dbHelper.InsertTableAlarm(tvChoosetime.getText().toString(), edTitleAlarm.getText().toString());
                onBackPressed();
            }
        });

        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "akchannel";
            String desc = "Channel for Alarm Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("androidknowledge", name, imp);
            channel.setDescription(desc);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}