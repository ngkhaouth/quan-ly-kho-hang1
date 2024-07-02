package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.ChiTietNhapDAO;
import DAO.HoaDonNhapDAO;
import DAO.SanPhamDAO;
import model.ChiTietNhap;
import model.HoaDonNhap;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class XemChiTietDonHangNhapView extends JFrame {
    private JLabel titleLabel;
    private JLabel orderIdLabel;
    private JLabel NhacungcapLabel;
    private JLabel orderDateLabel;
    private JLabel totalPriceLabel;
    private JTable productTable;
    private DefaultTableModel productTableModel;

    public XemChiTietDonHangNhapView(String orderId) {
        setTitle("Phiếu Đơn Hàng");
        setSize(600, 400);
        setLayout(new BorderLayout());
        
        HoaDonNhapDAO hoaDonNhapDAO = new HoaDonNhapDAO();
        HoaDonNhap hoaDonNhap = hoaDonNhapDAO.getOneHoaDonNhap(orderId);

        JPanel headerPanel = new JPanel(new BorderLayout());
        titleLabel = new JLabel("PHIẾU ĐƠN HÀNG");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        infoPanel.add(new JLabel("Mã đơn hàng:"));
        orderIdLabel = new JLabel(hoaDonNhap.getMaHoaDon());
        infoPanel.add(orderIdLabel);
        infoPanel.add(new JLabel("Tên nhà cung cấp:"));
        NhacungcapLabel = new JLabel(hoaDonNhap.getTenNhaCungCap());
        infoPanel.add(NhacungcapLabel);
        // Format the date
        String formattedDate = formatDate(hoaDonNhap.getNgayNhap());
        infoPanel.add(new JLabel("Ngày đặt:"));
        orderDateLabel = new JLabel(formattedDate);
        infoPanel.add(orderDateLabel);

        headerPanel.add(infoPanel, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        
        ChiTietNhapDAO chiTietNhapDAO = new ChiTietNhapDAO();
        SanPhamDAO sanPhamDAO = new SanPhamDAO();
        List<ChiTietNhap> chiTietNhapList = chiTietNhapDAO.getChiTietNhapByHoaDon(orderId);
        
        // Tạo bảng chi tiết sản phẩm
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Mã kho", "Số lượng", "Đơn giá"};
        Object[][] productData = new Object[chiTietNhapList.size()][5];
        int i = 0;
        for (ChiTietNhap chiTietNhap : chiTietNhapList) {
            productData[i][0] = chiTietNhap.getMaSanPham();
            productData[i][1] = sanPhamDAO.getTenSanPham(chiTietNhap.getMaSanPham());
            productData[i][2] = chiTietNhap.getMaKho();
            productData[i][3] = chiTietNhap.getSoLuong();
            productData[i][4] = sanPhamDAO.getGiaNhap(chiTietNhap.getMaSanPham());
            i++;
        }
        
        productTableModel = new DefaultTableModel(productData, columnNames);
        productTable = new JTable(productTableModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        add(productScrollPane, BorderLayout.CENTER);
        
        // Panel chứa thông tin tổng tiền ở phía dưới cùng
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPriceLabel = new JLabel("Tổng tiền: " + hoaDonNhap.getTongTien().toString());
        footerPanel.add(totalPriceLabel);
        add(footerPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    public String formatDate(Date date) {
        if (date == null) {
            return "N/A"; // or another placeholder
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(date);
    }
}
