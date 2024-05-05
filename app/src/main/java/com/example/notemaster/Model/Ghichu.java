package com.example.notemaster.Model;

import java.io.Serializable;

public class Ghichu implements Serializable {
    private int ID_Ghichu;
    private String Tieude;
    private String Noidung;
    private int ID_Thumuc;
    private String Timecreate;
    private String Key;

    public Ghichu(int ID_Ghichu, String tieude, String noidung, int ID_Thumuc, String timecreate, String key) {
        this.ID_Ghichu = ID_Ghichu;
        Tieude = tieude;
        Noidung = noidung;
        this.ID_Thumuc = ID_Thumuc;
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

    public void setTimecreate(String timecreate) {
        Timecreate = timecreate;
    }

    public void setKey(String key) {
        Key = key;
    }
}
