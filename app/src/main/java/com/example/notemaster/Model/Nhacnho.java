package com.example.notemaster.Model;

public class Nhacnho {
    private int ID_Alarm;
    private String TimeAlarm;
    private String TitleAlarm;
    private int ID_Nguoidung;

    public Nhacnho(int ID_Alarm, String timeAlarm, String titleAlarm, int ID_Nguoidung) {
        this.ID_Alarm = ID_Alarm;
        TimeAlarm = timeAlarm;
        TitleAlarm = titleAlarm;
        this.ID_Nguoidung = ID_Nguoidung;
    }

    public int getID_Alarm() {
        return ID_Alarm;
    }

    public String getTimeAlarm() {
        return TimeAlarm;
    }

    public String getTitleAlarm() {
        return TitleAlarm;
    }

    public int getID_Nguoidung() {
        return ID_Nguoidung;
    }

    public void setID_Alarm(int ID_Alarm) {
        this.ID_Alarm = ID_Alarm;
    }

    public void setTimeAlarm(String timeAlarm) {
        TimeAlarm = timeAlarm;
    }

    public void setTitleAlarm(String titleAlarm) {
        TitleAlarm = titleAlarm;
    }

    public void setID_Nguoidung(int ID_Nguoidung) {
        this.ID_Nguoidung = ID_Nguoidung;
    }
}
