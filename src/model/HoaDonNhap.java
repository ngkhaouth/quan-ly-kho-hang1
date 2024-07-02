package model;

import java.math.BigDecimal;
import java.util.Date;

public class HoaDonNhap {
    private String maHoaDon;
    private Date ngayNhap;
    private String tenNhaCungCap;
    private BigDecimal tongTien;

    // Constructors, getters, and setters
    public HoaDonNhap() {}

    public HoaDonNhap(String maHoaDon, Date ngayNhap, String tenNhaCungCap, BigDecimal tongTien) {
        this.maHoaDon = maHoaDon;
        this.ngayNhap = ngayNhap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.tongTien = tongTien;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public Date getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(Date ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public BigDecimal getTongTien() {
        return tongTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.tongTien = tongTien;
    }
}
