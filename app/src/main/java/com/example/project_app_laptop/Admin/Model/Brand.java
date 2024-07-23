package com.example.project_app_laptop.Admin.Model;

public class Brand {
    private int mahang;
    private String tenhang;
    private String logo;

    public Brand() {
    }

    public Brand(int mahang, String tenhang, String logo) {
        this.mahang = mahang;
        this.tenhang = tenhang;
        this.logo = logo;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
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
