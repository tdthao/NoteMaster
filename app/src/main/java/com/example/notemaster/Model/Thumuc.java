package com.example.notemaster.Model;

import java.io.Serializable;

public class Thumuc implements Serializable {
    private int ID_Thumuc;
    private String TenThumuc;
    private int ID_Nguoidung;

    public Thumuc(int ID_Thumuc, String tenThumuc, int ID_Nguoidung) {
        this.ID_Thumuc = ID_Thumuc;
        TenThumuc = tenThumuc;
        this.ID_Nguoidung = ID_Nguoidung;
    }

    public int getID_Thumuc() {
        return ID_Thumuc;
    }

    public String getTenThumuc() {
        return TenThumuc;
    }

    public int getID_Nguoidung() {
        return ID_Nguoidung;
    }

    public void setID_Thumuc(int ID_Thumuc) {
        this.ID_Thumuc = ID_Thumuc;
    }

    public void setTenThumuc(String tenThumuc) {
        TenThumuc = tenThumuc;
    }

    public void setID_Nguoidung(int ID_Nguoidung) {
        this.ID_Nguoidung = ID_Nguoidung;
    }
}
