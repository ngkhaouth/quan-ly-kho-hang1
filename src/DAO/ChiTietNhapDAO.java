package DAO;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ChiTietNhap;
import model.SanPham;

public class ChiTietNhapDAO {
	private MyConnect conDB = new MyConnect();


    // Phương thức để lấy mã chi tiết nhập lớn nhất hiện tại
    public String getMaxMaChiTietNhap() throws SQLException {
    	if (conDB.openConnectDB()) {
	        String query = "SELECT MAX(MaChiTietNhap) FROM ChiTietNhap";
	        String maxMaChiTietNhap = null;
	
	        try (PreparedStatement statement = conDB.conn.prepareStatement(query);
	             ResultSet resultSet = statement.executeQuery()) {
	
	            if (resultSet.next()) {
	                maxMaChiTietNhap = resultSet.getString(1);
	            }
	        }
	        return maxMaChiTietNhap;
    	}
    	return null;
    }
    
	public void addChiTietNhap(ChiTietNhap chiTietNhap) throws SQLException {
		if (conDB.openConnectDB()) {
			String sql = "INSERT INTO chitietnhap (MaChiTietNhap, MaHoaDonNhap, MaSanPham, MaKho, SoLuong) VALUES (?, ?, ?, ?, ?)";
			try (PreparedStatement stmt = conDB.conn.prepareStatement(sql)) {
				stmt.setString(1, chiTietNhap.getMaChiTietNhap());
				stmt.setString(2, chiTietNhap.getMaHoaDonNhap());
				stmt.setString(3, chiTietNhap.getMaSanPham());
				stmt.setString(4, chiTietNhap.getMaKho());
				stmt.setInt(5, chiTietNhap.getSoLuong());
				stmt.executeUpdate();
			}
		}
	}
	
    public List<ChiTietNhap> getChiTietNhapByHoaDon(String maHoaDonNhap) {
        List<ChiTietNhap> chiTietNhapList = new ArrayList<>();
        if (conDB.openConnectDB()) {
	        String query = "SELECT MaChiTietNhap, MaHoaDonNhap, MaSanPham, MaKho, SoLuong FROM chitietnhap WHERE MaHoaDonNhap = ?";
	             try(PreparedStatement stmt = conDB.conn.prepareStatement(query)) {
	
	            stmt.setString(1, maHoaDonNhap);
	            ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                ChiTietNhap chiTietNhap = new ChiTietNhap(
	                    rs.getString("MaChiTietNhap"),
	                    rs.getString("MaHoaDonNhap"),
	                    rs.getString("MaSanPham"),
	                    rs.getString("MaKho"),
	                    rs.getInt("SoLuong")
	                );
	                chiTietNhapList.add(chiTietNhap);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return chiTietNhapList;
        }
        return null;
    }
}
