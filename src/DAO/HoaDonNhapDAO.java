package DAO;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.ChiTietNhap;
import model.HoaDonNhap;

public class HoaDonNhapDAO {
	
	private MyConnect conDB = new MyConnect();
    public void insertHoaDonNhap(HoaDonNhap hoaDon) {
    	if(conDB.openConnectDB()) {
	        String sql = "INSERT INTO HoaDonNhap (MaHoaDonNhap, NgayNhap, TenNhaCungCap, TongTien) VALUES (?, ?, ?, ?)";
	        try (PreparedStatement statement = conDB.conn.prepareStatement(sql)) {
	            statement.setString(1, hoaDon.getMaHoaDon());
	            statement.setDate(2, new java.sql.Date(hoaDon.getNgayNhap().getTime()));
	            statement.setString(3, hoaDon.getTenNhaCungCap());
	            statement.setBigDecimal(4, hoaDon.getTongTien());
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	}
    }
    
    public boolean checkMaHoaDonExists(String maHoaDon) {
    	if(conDB.openConnectDB()) {
	        String query = "SELECT COUNT(*) FROM HoaDonNhap WHERE MaHoaDonNhap = ?";
	        try (PreparedStatement stmt = conDB.conn.prepareStatement(query)) {
	            stmt.setString(1, maHoaDon);
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt(1) > 0;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
    	}
        return false;
    }
    
    public HoaDonNhap getOneHoaDonNhap(String maHoaDon) {
        HoaDonNhap hoaDonNhap = null;
        String query = "SELECT MaHoaDonNhap, NgayNhap, TenNhaCungCap, TongTien FROM hoadonnhap WHERE MaHoaDonNhap = ?";
       if(conDB.openConnectDB())
             try(PreparedStatement stmt = conDB.conn.prepareStatement(query)) {

            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String maHoaDonNhap = rs.getString("MaHoaDonNhap");
                Date ngayNhap = new Date(rs.getDate("NgayNhap").getTime()); // Convert to java.util.Date
                String tenNhaCungCap = rs.getString("TenNhaCungCap");
                BigDecimal tongTien = rs.getBigDecimal("TongTien");

                hoaDonNhap = new HoaDonNhap(maHoaDonNhap, ngayNhap, tenNhaCungCap, tongTien);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return hoaDonNhap;
    }
    
    public List<HoaDonNhap> listAllHoaDonNhap() throws SQLException {
        List<HoaDonNhap> listHoaDonNhap = new ArrayList<>();
	        if(conDB.openConnectDB()) {
		        String sql = "SELECT * FROM hoadonnhap";
		
		        Statement statement = conDB.conn.createStatement();
		        ResultSet resultSet = statement.executeQuery(sql);
		
		        while (resultSet.next()) {
		            String maHoaDonNhap = resultSet.getString("MaHoaDonNhap");
		            Date ngayNhap = resultSet.getDate("NgayNhap");
		            String tenNhaCungCap = resultSet.getString("TenNhaCungCap");
		            BigDecimal tongTien = resultSet.getBigDecimal("TongTien");
		
		            HoaDonNhap hoaDonNhap = new HoaDonNhap(maHoaDonNhap, ngayNhap, tenNhaCungCap, tongTien);
		            listHoaDonNhap.add(hoaDonNhap);
		        }
		        return listHoaDonNhap;
	        }

        return null;
    }
    
    // Phương thức cập nhật thành tiền dựa trên mã đơn hàng
    public void capNhatThanhTien(String maDonHang, BigDecimal thanhTien) throws SQLException {
    	if(conDB.openConnectDB()) {
	        String sql = "UPDATE hoadonnhap SET TongTien = ? WHERE MaHoaDonNhap = ?";
	        try (PreparedStatement statement = conDB.conn.prepareStatement(sql)) {
	            statement.setBigDecimal(1, thanhTien);
	            statement.setString(2, maDonHang);
	            statement.executeUpdate();
	        }
    	}
    }
    
}
