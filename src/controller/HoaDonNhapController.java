package controller;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DAO.HoaDonNhapDAO;
import model.HoaDonNhap;
import view.HoaDonNhapView;

public class HoaDonNhapController {
    private HoaDonNhapDAO hoaDonNhapDAO;
    private JTable table;
    private SimpleDateFormat dateFormat;
	private HoaDonNhapView hoaDonNhapView;
    

    public HoaDonNhapController(HoaDonNhapDAO hoaDonNhapDAO, JTable table ,HoaDonNhapView hoaDonNhapView) {
        this.hoaDonNhapDAO = hoaDonNhapDAO;
        this.table = table;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        this.hoaDonNhapView = hoaDonNhapView;
    }

    public void addHoaDonNhapFromTable() {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();

        for (int i = 0; i < rowCount; i++) {
            String maHoaDon = (String) model.getValueAt(i, 0);
            String tenNhaCungCap = (String) model.getValueAt(i, 1);
            String ngayNhapStr = (String) model.getValueAt(i, 2);
            Object tongTienObj = model.getValueAt(i, 3);
            
            Date ngayNhap = null;
            BigDecimal tongTien = null;

            try {
                ngayNhap = dateFormat.parse(ngayNhapStr); // Chuyển đổi từ String sang Date với định dạng 'dd/MM/yyyy'
            } catch (ParseException e) {
                e.printStackTrace();
                continue; // Bỏ qua dòng lỗi
            }

            if (tongTienObj instanceof Double) {
                tongTien = BigDecimal.valueOf((Double) tongTienObj);
            } else if (tongTienObj instanceof String) {
                try {
                    tongTien = new BigDecimal((String) tongTienObj);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    continue;
                }
            }
            
            HoaDonNhap hoaDon = new HoaDonNhap(maHoaDon, ngayNhap, tenNhaCungCap, tongTien);
            hoaDonNhapDAO.insertHoaDonNhap(hoaDon);
        }
    }
    
    public void loadHoaDonNhapToTable() {
        try {
            List<HoaDonNhap> listHoaDonNhap = hoaDonNhapDAO.listAllHoaDonNhap();
            hoaDonNhapView.showHoaDonNhapList(listHoaDonNhap);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void capNhatTongTien(BigDecimal tong) throws SQLException {

    	DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = table.getSelectedRow();
        if (row != -1) {
        	String maDonHang = (String) model.getValueAt(row, 0); // Lấy mã đơn hàng từ bảng
            model.setValueAt(tong, row, 3); // Cập nhật giá trị tổng tiền vào cột thứ 4 (index 3)
            hoaDonNhapDAO.capNhatThanhTien(maDonHang, tong);
        }
    }
}

