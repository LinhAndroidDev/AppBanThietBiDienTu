package com.example.appbanthietbidientu.model;

public class User {
    String tenkhachhang;
    int sodienthoai;
    String email;

    public User(String tenkhachhang, int sodienthoai, String email) {
        this.tenkhachhang = tenkhachhang;
        this.sodienthoai = sodienthoai;
        this.email = email;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public int getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(int sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
