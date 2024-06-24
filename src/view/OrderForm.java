package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class OrderForm extends JFrame {

	 private JTextField Field_MaHoaDon;
	 private JTextField Field_TenKhachHang;
	 private JTextField Field_NgayXuat;
	 private JTextField Field_TongTien;
	 private JButton Button_TaoHoaDon;
	 private JButton Button_ThemSanPham;
	 private JButton Button_ChiTietHD;
	 private JButton Button_HuyHD;
	 private JTable orderTable;
	 private DefaultTableModel tableModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderForm frame = new OrderForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OrderForm() {
		setTitle("Quản lý đơn xuất hàng");
        getContentPane().setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 2));

        Field_MaHoaDon = new JTextField();
        Field_TenKhachHang = new JTextField();
        Field_NgayXuat = new JTextField();
        Field_TongTien = new JTextField();
        Field_TongTien.setEditable(false);

        formPanel.add(new JLabel("Mã hóa đơn:"));
        formPanel.add(Field_MaHoaDon);
        formPanel.add(new JLabel("Tên khách hàng:"));
        formPanel.add(Field_TenKhachHang);
        formPanel.add(new JLabel("Ngày xuất hàng:"));
        formPanel.add(Field_NgayXuat);
        formPanel.add(new JLabel("Tổng tiền:"));
        formPanel.add(Field_TongTien);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        Button_TaoHoaDon = new JButton("Tạo đơn hàng");
        Button_ThemSanPham = new JButton("Thêm sản phẩm vào đơn hàng");
        Button_ChiTietHD = new JButton("Xem chi tiết hóa đơn");
        Button_HuyHD = new JButton("Hủy hóa đơn");

        buttonPanel.add(Button_TaoHoaDon);
        buttonPanel.add(Button_ThemSanPham);
        buttonPanel.add(Button_ChiTietHD);
        buttonPanel.add(Button_HuyHD);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã đơn hàng");
        tableModel.addColumn("Tên khách hàng");
        tableModel.addColumn("Ngày xuất hàng");
        tableModel.addColumn("Tổng tiền");

        orderTable = new JTable(tableModel);

        getContentPane().add(formPanel, BorderLayout.NORTH);
        getContentPane().add(buttonPanel, BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(orderTable), BorderLayout.SOUTH);

        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public JTextField getMaHoaDon() {
        return Field_MaHoaDon;
    }

    public JTextField getTenKhachHang() {
        return Field_TenKhachHang;
    }

    public JTextField getNgayXuat() {
        return Field_NgayXuat;
    }

    public JTextField getTongTien() {
        return Field_TongTien;
    }

    public JButton getButton_TaoHoaDon() {
        return Button_TaoHoaDon;
    }

    public JButton get_Button_ThemSanPham() {
        return Button_ThemSanPham;
    }

    public JButton get_Button_XemChiTiet() {
        return Button_ChiTietHD;
    }

    public JButton get_Button_HuyDon() {
        return Button_HuyHD;
    }

    public DefaultTableModel getTableModel() {
        return (DefaultTableModel) orderTable.getModel();
    }

    public JTable getOrderTable() {
        return orderTable;
    }
    
    // Phương thức để lấy content pane
    public Container getContentPanel() {
        return getContentPane();
    }
}
