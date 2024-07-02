package model;

import java.math.BigDecimal;

public class ChiTietNhap {
    private String maChiTietNhap;
    private String maHoaDonNhap;
    private String maSanPham;
    private String maKho;
    private int soLuong;

    // Constructor không đối số
    public ChiTietNhap() {}

    public ChiTietNhap(String maChiTietNhap, String maHoaDonNhap,  String maSanPham, String maKho, int soLuong) {
		this.maChiTietNhap = maChiTietNhap;
		this.maHoaDonNhap = maHoaDonNhap;
		this.maSanPham = maSanPham;
		this.maKho = maKho;
		this.soLuong = soLuong;
	}

	public String getMaChiTietNhap() {
		return maChiTietNhap;
	}

	public void setMaChiTietNhap(String maChiTietNhap) {
		this.maChiTietNhap = maChiTietNhap;
	}

	public String getMaHoaDonNhap() {
		return maHoaDonNhap;
	}

	public void setMaHoaDonNhap(String maHoaDonNhap) {
		this.maHoaDonNhap = maHoaDonNhap;
	}

	public String getMaKho() {
		return maKho;
	}

	public void setMaKho(String maKho) {
		this.maKho = maKho;
	}

	public String getMaSanPham() {
		return maSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		this.maSanPham = maSanPham;
	}

	public int getSoLuong() {
		return soLuong;
	}

	public void setSoLuong(int soLuong) {
		this.soLuong = soLuong;
	}



}
