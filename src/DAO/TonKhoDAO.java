package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TonKhoDAO {
	private MyConnect conDB = new MyConnect();


	
    // Phương thức kiểm tra nếu mã sản phẩm và mã kho đã tồn tại
    public boolean isTonKhoExists(String maSanPham, String maKho) throws SQLException {
    	if (conDB.openConnectDB()) {
	        String query = "SELECT COUNT(*) FROM TonKho WHERE MaSanPham = ? AND MaKho = ?";
	        
	        
	        try (PreparedStatement statement = conDB.conn.prepareStatement(query)) {
	            statement.setString(1, maSanPham);
	            statement.setString(2, maKho);
	            
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getInt(1) > 0;
	                }
	            }
	        }
    	}
        return false;
    }
    
    
    // Phương thức cập nhật số lượng tồn
    public void updateTonKho(String maSanPham, String maKho, int soLuongTon) throws SQLException {
    	if (conDB.openConnectDB()) {
	        String query = "UPDATE TonKho SET SoLuongTon = SoLuongTon + ? WHERE MaSanPham = ? AND MaKho = ?";
	        
	        try (PreparedStatement statement = conDB.conn.prepareStatement(query)) {
	            statement.setInt(1, soLuongTon);
	            statement.setString(2, maSanPham);
	            statement.setString(3, maKho);
	            statement.executeUpdate();
	        }
    	}
    }

    // Phương thức thêm mới bản ghi
    public void addTonKho(String maTonKho, String maSanPham, String maKho, int soLuongTon) throws SQLException {
       	if (conDB.openConnectDB()) {
	        String query = "INSERT INTO TonKho (MaTonKho, MaSanPham, MaKho, SoLuongTon) VALUES (?, ?, ?, ?)";
	        
	        try ( PreparedStatement statement = conDB.conn.prepareStatement(query)) {
	            statement.setString(1, maTonKho);
	            statement.setString(2, maSanPham);
	            statement.setString(3, maKho);
	            statement.setInt(4, soLuongTon);
	            statement.executeUpdate();
	        }
        }
    }
    
//    // Phương thức lấy số lượng hiện tại của TonKho
//    public int getCurrentSoLuongTon(String maSanPham, String maKho) throws SQLException {
//    	if (conDB.openConnectDB()) {
//	        String query = "SELECT SoLuongTon FROM TonKho WHERE MaSanPham = ? AND MaKho = ?";
//	        
//	        try (PreparedStatement statement = conDB.conn.prepareStatement(query)) {
//	            statement.setString(1, maSanPham);
//	            statement.setString(2, maKho);
//	            
//	            try (ResultSet resultSet = statement.executeQuery()) {
//	                if (resultSet.next()) {
//	                    return resultSet.getInt("SoLuongTon");
//	                }
//	            }
//	        }
//    	}
//        return 0;
//    }

}
