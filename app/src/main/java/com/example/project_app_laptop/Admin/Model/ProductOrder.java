package com.example.project_app_laptop.Admin.Model;

import java.io.Serializable;

public class ProductOrder implements Serializable {
    private int maSanPham;
    private String tenSanPham, hinhAnh;
    private int giaBan;
    private String soLuong;
    private int soLuongChiTiet;
    private int maHang;

    public ProductOrder() {
    }

    public ProductOrder(int maSanPham, String tenSanPham, String hinhAnh, int giaBan, String soLuong, int SLCT, int maHang) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.hinhAnh = hinhAnh;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.soLuongChiTiet = SLCT;
        this.maHang = maHang;
    }

    public int getSoLuongChiTiet() {
        return soLuongChiTiet;
    }

    public void setSoLuongChiTiet(int soLuongChiTiet) {
        this.soLuongChiTiet = soLuongChiTiet;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public int getMaHang() {
        return maHang;
    }

    public void setMaHang(int maHang) {
        this.maHang = maHang;
    }
}
