package com.example.project_app_laptop.User.Models;

public class ChiTiet {
    private int masp, soluong, giaban, thanhtien;
    private String tensp, hinhanh;
    private boolean checked;
    public ChiTiet() {
    }

    public ChiTiet(int masp, int soluong, int giaban, int thanhtien, String tensp, String hinhanh, boolean checked) {
        this.masp = masp;
        this.soluong = soluong;
        this.giaban = giaban;
        this.thanhtien = thanhtien;
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.checked = checked;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public int getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(int thanhtien) {
        this.thanhtien = thanhtien;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
