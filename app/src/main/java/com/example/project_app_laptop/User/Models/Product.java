package com.example.project_app_laptop.User.Models;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private int masp, mahang, soluong;
    private String tensp, mota, hinhanh, cpu, ram, ocung, manhinh, vga, hedieuhanh, trongluong, pin;
    private long giaban;

    public Product() {
    }

    public Product(int masp, int mahang, int soluong, String tensp, String mota, String hinhanh, String cpu, String ram, String ocung, String manhinh, String vga, String hedieuhanh, String trongluong, String pin, long giaban) {
        this.masp = masp;
        this.mahang = mahang;
        this.soluong = soluong;
        this.tensp = tensp;
        this.mota = mota;
        this.hinhanh = hinhanh;
        this.cpu = cpu;
        this.ram = ram;
        this.ocung = ocung;
        this.manhinh = manhinh;
        this.vga = vga;
        this.hedieuhanh = hedieuhanh;
        this.trongluong = trongluong;
        this.pin = pin;
        this.giaban = giaban;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public int getMasp() {
        return masp;
    }

    public void setMasp(int masp) {
        this.masp = masp;
    }

    public int getMahang() {
        return mahang;
    }

    public void setMahang(int mahang) {
        this.mahang = mahang;
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

    public long getGiaban() {
        return giaban;
    }

    public void setGiaban(long giaban) {
        this.giaban = giaban;
    }
}
