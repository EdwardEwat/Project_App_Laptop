package com.example.project_app_laptop.Admin.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int masp;
    private String tensp;
    private String mota;
    private String hinhanh;
    private int giaban;
    private String cpu;
    private String ram;
    private String ocung;
    private String manhinh;
    private String vga;
    private String hedieuhanh;
    private String trongluong;
    private String pin;
    private String soluong;
    private int mahang;

    public Product() {
    }

    public Product(int masp, String tensp, String mota, String hinhanh, int giaban, String cpu, String ram, String ocung, String manhinh, String vga, String hedieuhanh, String trongluong, String pin, String soluong, int mahang) {
        this.masp = masp;
        this.tensp = tensp;
        this.mota = mota;
        this.hinhanh = hinhanh;
        this.giaban = giaban;
        this.cpu = cpu;
        this.ram = ram;
        this.ocung = ocung;
        this.manhinh = manhinh;
        this.vga = vga;
        this.hedieuhanh = hedieuhanh;
        this.trongluong = trongluong;
        this.pin = pin;
        this.soluong = soluong;
        this.mahang = mahang;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public int getGiaban() {
        return giaban;
    }

    public void setGiaban(int giaban) {
        this.giaban = giaban;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getOcung() {
        return ocung;
    }

    public void setOcung(String ocung) {
        this.ocung = ocung;
    }

    public String getManhinh() {
        return manhinh;
    }

    public void setManhinh(String manhinh) {
        this.manhinh = manhinh;
    }

    public String getVga() {
        return vga;
    }

    public void setVga(String vga) {
        this.vga = vga;
    }

    public String getHedieuhanh() {
        return hedieuhanh;
    }

    public void setHedieuhanh(String hedieuhanh) {
        this.hedieuhanh = hedieuhanh;
    }

    public String getTrongluong() {
        return trongluong;
    }

    public void setTrongluong(String trongluong) {
        this.trongluong = trongluong;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
    }
}
