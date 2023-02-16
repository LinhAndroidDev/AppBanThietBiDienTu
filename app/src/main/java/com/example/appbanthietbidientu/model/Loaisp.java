package com.example.appbanthietbidientu.model;

public class Loaisp {
    private int id;
    private String tenloaisp;
    int hinhanhloaisp;

    public Loaisp(int id, String tenloaisp, int hinhanhloaisp) {
        this.id = id;
        this.tenloaisp = tenloaisp;
        this.hinhanhloaisp = hinhanhloaisp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenloaisp() {
        return tenloaisp;
    }

    public void setTenloaisp(String tenloaisp) {
        this.tenloaisp = tenloaisp;
    }

    public int getHinhanhloaisp() {
        return hinhanhloaisp;
    }

    public void setHinhanhloaisp(int hinhanhloaisp) {
        this.hinhanhloaisp = hinhanhloaisp;
    }
}
