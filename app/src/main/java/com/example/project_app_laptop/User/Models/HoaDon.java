package com.example.project_app_laptop.User.Models;

import java.io.Serializable;

public class HoaDon implements Serializable {
    private int mahd, matk, tongtien;
    private String ngaymua, diachi, sdt, hinhthucthanhtoan, trangthai;

    public HoaDon() {
    }

    public HoaDon(int mahd, int matk, int tongtien, String ngaymua, String diachi, String sdt, String hinhthucthanhtoan, String trangthai) {
        this.mahd = mahd;
        this.matk = matk;
        this.tongtien = tongtien;
        this.ngaymua = ngaymua;
        this.diachi = diachi;
        this.sdt = sdt;
        this.hinhthucthanhtoan = hinhthucthanhtoan;
        this.trangthai = trangthai;
    }

    public int getMahd() {
        return mahd;
    }

    public void setMahd(int mahd) {
        this.mahd = mahd;
    }

    public int getMatk() {
        return matk;
    }

    public void setMatk(int matk) {
        this.matk = matk;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public String getNgaymua() {
        return ngaymua;
    }

    public void setNgaymua(String ngaymua) {
        this.ngaymua = ngaymua;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getHinhthucthanhtoan() {
        return hinhthucthanhtoan;
    }

    public void setHinhthucthanhtoan(String hinhthucthanhtoan) {
        this.hinhthucthanhtoan = hinhthucthanhtoan;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }
}
