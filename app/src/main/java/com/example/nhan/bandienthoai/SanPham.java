package com.example.nhan.bandienthoai;

/**
 * Created by Nhan on 4/3/2017.
 */

public class SanPham {
    String anhSP,tenSP;
    String sotienSP;
    String mieutaSP;
    public SanPham() {
    }

    public SanPham(String anhSP, String tenSP, String sotienSP, String mieutaSP) {
        this.anhSP = anhSP;
        this.tenSP = tenSP;
        this.sotienSP = sotienSP;
        this.mieutaSP = mieutaSP;
    }

    public String getAnhSP() {
        return anhSP;
    }

    public void setAnhSP(String anhSP) {
        this.anhSP = anhSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getSotienSP() {
        return sotienSP;
    }

    public void setSotienSP(String sotienSP) {
        this.sotienSP = sotienSP;
    }

    public String getMieutaSP() {
        return mieutaSP;
    }

    public void setMieutaSP(String mieutaSP) {
        this.mieutaSP = mieutaSP;
    }
}
