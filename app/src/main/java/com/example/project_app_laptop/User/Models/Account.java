package com.example.project_app_laptop.User.Models;

import java.io.Serializable;

public class Account implements Serializable {
    private  int mataikhoan;
    private String tenkh, tentk, matkhau;

    public Account() {
    }

    public Account(int mataikhoan, String tenkh, String tentk, String matkhau) {
        this.mataikhoan = mataikhoan;
        this.tenkh = tenkh;
        this.tentk = tentk;
        this.matkhau = matkhau;
    }

    public int getMataikhoan() {
        return mataikhoan;
    }

    public void setMataikhoan(int mataikhoan) {
        this.mataikhoan = mataikhoan;
    }

    public String getTenkh() {
        return tenkh;
    }

    public void setTenkh(String tenkh) {
        this.tenkh = tenkh;
    }

    public String getTentk() {
        return tentk;
    }

    public void setTentk(String tentk) {
        this.tentk = tentk;
    }

    public String getMatkhau() {
        return matkhau;
    }

    public void setMatkhau(String matkhau) {
        this.matkhau = matkhau;
    }
}
