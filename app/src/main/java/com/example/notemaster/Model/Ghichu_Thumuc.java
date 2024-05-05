package com.example.notemaster.Model;

import java.io.Serializable;

public class Ghichu_Thumuc implements Serializable {
    private int ID_Ghichu;
    private String Tieude;
    private String Noidung;
    private int ID_Thumuc;
    private String TenThumuc;
    private String Timecreate;
    private String Key;

    public Ghichu_Thumuc(int ID_Ghichu, String tieude, String noidung, int ID_Thumuc, String tenThumuc, String timecreate, String key) {
        this.ID_Ghichu = ID_Ghichu;
        Tieude = tieude;
        Noidung = noidung;
        this.ID_Thumuc = ID_Thumuc;
        TenThumuc = tenThumuc;
        Timecreate = timecreate;
        Key = key;
    }

    public int getID_Ghichu() {
        return ID_Ghichu;
    }

    public String getTieude() {
        return Tieude;
    }

    public String getNoidung() {
        return Noidung;
    }

    public int getID_Thumuc() {
        return ID_Thumuc;
    }
    public String getTenThumuc() {
        return TenThumuc;
    }

    public String getTimecreate() {
        return Timecreate;
    }

    public String getKey() {
        return Key;
    }

    public void setID_Ghichu(int ID_Ghichu) {
        this.ID_Ghichu = ID_Ghichu;
    }

    public void setTieude(String tieude) {
        Tieude = tieude;
    }

    public void setNoidung(String noidung) {
        Noidung = noidung;
    }

    public void setID_Thumuc(int ID_Thumuc) {
        this.ID_Thumuc = ID_Thumuc;
    }

    public void setTenThumuc(String tenThumuc) {
        TenThumuc = tenThumuc;
    }

    public void setTimecreate(String timecreate) {
        Timecreate = timecreate;
    }

    public void setKey(String key) {
        Key = key;
    }
}
