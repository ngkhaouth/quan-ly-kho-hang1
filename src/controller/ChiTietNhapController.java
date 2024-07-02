package controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAO.ChiTietNhapDAO;
import DAO.SanPhamDAO;
import DAO.TonKhoDAO;
import model.ChiTietNhap;
import model.SanPham;
import view.ChiTietDonHangNhapView;
import view.TonKhoView;

public class ChiTietNhapController {


	private ChiTietNhapDAO chiTietNhapDAO = new ChiTietNhapDAO();
	private ChiTietDonHangNhapView chiTietDonHangNhapView;
	private HoaDonNhapController hoaDonNhapController;
	private BigDecimal tongTienHienTai;
	private SanPhamDAO sanPhamDAO = new SanPhamDAO();
	private TonKhoDAO tonKhoDAO = new TonKhoDAO();
	
	
    public ChiTietNhapController(ChiTietDonHangNhapView chiTietDonHangNhapView, HoaDonNhapController hoaDonNhapController) {
		this.chiTietDonHangNhapView = chiTietDonHangNhapView;
		this.hoaDonNhapController = hoaDonNhapController;
		this.tongTienHienTai = BigDecimal.ZERO;
	}

    
 // Giả sử đây là phương thức để tạo mã tồn kho duy nhất
    public String generateUniqueMaTonKho() {
        // Tạo mã tồn kho duy nhất, ví dụ, bằng UUID ngắn hoặc logic khác
        return UUID.randomUUID().toString().substring(0, 3).toUpperCase();
    }

	
    public void processChiTietNhap(DefaultTableModel model, String orderId) {
        int rowCount = model.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String maChiTietNhap = (String) model.getValueAt(i, 0);
            String maSanPham = (String) model.getValueAt(i, 1);
            String tenSanPham = (String) model.getValueAt(i, 2);
            String maKho = (String) model.getValueAt(i, 3);
            int soLuong = Integer.parseInt(model.getValueAt(i, 4).toString());
            double giaNhap = Double.parseDouble(model.getValueAt(i, 5).toString());
            double giaBan = 0.0; // Placeholder or input if necessary
            
            SanPham sanPham = new SanPham(maSanPham, tenSanPham, giaNhap, giaBan);
            try {
                if (sanPhamDAO.isSanPhamExists(maSanPham)) {
                	sanPhamDAO.updateSanPham(sanPham);
                } else {
                	sanPhamDAO.addSanPham(sanPham);
                }

                if (tonKhoDAO.isTonKhoExists(maSanPham, maKho)) {
                	tonKhoDAO.updateTonKho(maSanPham, maKho, soLuong);
                } else {
                    String maTonKho = generateUniqueMaTonKho();
                    tonKhoDAO.addTonKho(maTonKho, maSanPham, maKho, soLuong);
                }

                ChiTietNhap chiTietNhap = new ChiTietNhap(maChiTietNhap, orderId, maSanPham, maKho, soLuong);
                chiTietNhapDAO.addChiTietNhap(chiTietNhap);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void tinhTong() throws SQLException {
        DefaultTableModel model = (DefaultTableModel)chiTietDonHangNhapView.tableChiTietNhap.getModel();
        int rowCount = model.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            int soLuong = Integer.parseInt(model.getValueAt(i, 4).toString());
            BigDecimal donGia = new BigDecimal(model.getValueAt(i, 5).toString());
            tongTienHienTai  = tongTienHienTai .add(donGia.multiply(BigDecimal.valueOf(soLuong)));
        }

        // Cập nhật tổng tiền vào HoaDonNhapView
        hoaDonNhapController.capNhatTongTien(tongTienHienTai );
    }
}
