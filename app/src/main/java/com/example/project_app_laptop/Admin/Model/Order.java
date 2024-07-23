package com.example.project_app_laptop.Admin.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Order implements Serializable {
    private int maHoaDon;
    private String ngayMua, diaChi, SDT, hinhThucThanhToan, tenKhachHang;
    private int tongTien;
    private String trangThai;
    private ArrayList<ProductOrder> lstProduct;

    public Order() {
    }

    public Order(int maHoaDon, String tenKhachHang, String ngayMua, String diaChi, String SDT, String hinhThucThanhToan, int tongTien, String trangThai, ArrayList<ProductOrder> lstProduct) {
        this.maHoaDon = maHoaDon;
        this.tenKhachHang = tenKhachHang;
        this.ngayMua = ngayMua;
        this.diaChi = diaChi;
        this.SDT = SDT;
        this.hinhThucThanhToan = hinhThucThanhToan;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.lstProduct = lstProduct;
    }

    public ArrayList<ProductOrder> getLstProduct() {
        return lstProduct;
    }

    public void setLstProduct(ArrayList<ProductOrder> lstProduct) {
        this.lstProduct = lstProduct;
    }

    public int getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(int maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getNgayMua() {
        return ngayMua;
    }

    public void setNgayMua(String ngayMua) {
        this.ngayMua = ngayMua;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getHinhThucThanhToan() {
        return hinhThucThanhToan;
    }

    public void setHinhThucThanhToan(String hinhThucThanhToan) {
        this.hinhThucThanhToan = hinhThucThanhToan;
    }

    public int getTongTien() {
        return tongTien;
    }

    public void setTongTien(int tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }
}
