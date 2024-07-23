package com.example.project_app_laptop.User.Models;

import java.util.ArrayList;

public class Categories {
    private int idhang;
    private String tenhang, logo;

    public Categories() {
    }

    public Categories(int idhang, String tenhang, String logo) {
        this.idhang = idhang;
        this.tenhang = tenhang;
        this.logo = logo;
    }

    public int getIdhang() {
        return idhang;
    }

    public void setIdhang(int idhang) {
        this.idhang = idhang;
    }

    public String getTenhang() {
        return tenhang;
    }

    public void setTenhang(String tenhang) {
        this.tenhang = tenhang;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
