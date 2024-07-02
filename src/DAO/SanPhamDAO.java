package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.SanPham;

public class SanPhamDAO {
	private MyConnect conDB = new MyConnect();
	
	// Kiểm tra nếu sản phẩm đã tồn tại
	public boolean isSanPhamExists(String maSanPham) throws SQLException {
		if(conDB.openConnectDB()) {
	        String query = "SELECT COUNT(*) FROM SanPham WHERE MaSanPham = ?";
	        try (PreparedStatement statement = conDB.conn.prepareStatement(query)) {
	            statement.setString(1, maSanPham);
	            try (ResultSet resultSet = statement.executeQuery()) {
	                if (resultSet.next()) {
	                    return resultSet.getInt(1) > 0;
	                }
	            }
	        }
		}
        return false;
    }
	
    // Cập nhật sản phẩm
    public void updateSanPham(SanPham sanPham) throws SQLException {
    	if(conDB.openConnectDB()) {
	        String query = "UPDATE SanPham SET TenSanPham = ?, GiaNhap = ?, GiaBan = ? WHERE MaSanPham = ?";
	        try (PreparedStatement statement = conDB.conn.prepareStatement(query)) {
	            statement.setString(1, sanPham.getTenSanPham());
	            statement.setDouble(2, sanPham.getGiaNhap());
	            statement.setDouble(3, sanPham.getGiaBan());
	            statement.setString(4, sanPham.getMaSanPham());
	            statement.executeUpdate();
	        }
    	}
    }
	
	// Phương thức thêm sản phẩm vào bảng SanPham
	public void addSanPham(SanPham sanPham) throws SQLException {
		if (conDB.openConnectDB()) {
			String query = "INSERT INTO SanPham (MaSanPham, TenSanPham, GiaNhap, GiaBan) VALUES (?, ?, ?, ?)";

			try (PreparedStatement statement = conDB.conn.prepareStatement(query)) {
				statement.setString(1, sanPham.getMaSanPham());
				statement.setString(2, sanPham.getTenSanPham());
				statement.setDouble(3, sanPham.getGiaNhap());
				statement.setDouble(4, sanPham.getGiaBan());
				statement.executeUpdate();
			}
		}
	}
	
    public String getTenSanPham(String maSanPham) {
		if (conDB.openConnectDB()) {
	        String tenSanPham = "";
	        String query = "SELECT TenSanPham FROM sanpham WHERE MaSanPham = ?";
	        try ( PreparedStatement stmt = conDB.conn.prepareStatement(query)) {
	
	            stmt.setString(1, maSanPham);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                tenSanPham = rs.getString("TenSanPham");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return tenSanPham;
		}
		return null;
    }
    
    public String getGiaNhap(String maSanPham) {
        String giaNhap = "";
		if (conDB.openConnectDB()) {
	        String query = "SELECT GiaNhap FROM sanpham WHERE MaSanPham = ?";
	        try (  PreparedStatement stmt = conDB.conn.prepareStatement(query)) {
	
	            stmt.setString(1, maSanPham);
	            ResultSet rs = stmt.executeQuery();
	            if (rs.next()) {
	                giaNhap = rs.getBigDecimal("GiaNhap").toString();
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return giaNhap;
		}
		return null;
    }
}
