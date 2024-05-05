package com.example.notemaster.Model;

public class HinhanhGhichu {
    private int ID_Hinhanh;
    private byte[] Hinhanh;
    private int ID_Ghichu;

    public HinhanhGhichu(int ID_Hinhanh, byte[] hinhanh, int ID_Ghichu) {
        this.ID_Hinhanh = ID_Hinhanh;
        Hinhanh = hinhanh;
        this.ID_Ghichu = ID_Ghichu;
    }

    public int getID_Hinhanh() {
        return ID_Hinhanh;
    }

    public byte[] getHinhanh() {
        return Hinhanh;
    }

    public int getID_Ghichu() {
        return ID_Ghichu;
    }

    public void setID_Hinhanh(int ID_Hinhanh) {
        this.ID_Hinhanh = ID_Hinhanh;
    }

    public void setHinhanh(byte[] hinhanh) {
        Hinhanh = hinhanh;
    }

    public void setID_Ghichu(int ID_Ghichu) {
        this.ID_Ghichu = ID_Ghichu;
    }
}
