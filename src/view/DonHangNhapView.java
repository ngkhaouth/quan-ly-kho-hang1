package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class DonHangNhapView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel tableModel;
	public JTextField idOrderField;
	private JTable table;
	private int selectedRow;
	public JTextField customerField;
	public JTextField dateField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DonHangNhapView frame = new DonHangNhapView();
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
	public DonHangNhapView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Create the menu panel with rounded corners
        RoundedPanel jPanelMenu = new RoundedPanel(50, Color.WHITE);
        jPanelMenu.setLayout(new BorderLayout(0, 0));


        // Add the house icon to the top of the menu panel
        JLabel labelHouse = new JLabel();
        labelHouse.setHorizontalAlignment(SwingConstants.CENTER);
        Image img = new ImageIcon(this.getClass().getResource("/House.png")).getImage();
        labelHouse.setIcon(new ImageIcon(img));
        jPanelMenu.add(labelHouse, BorderLayout.NORTH);

        // Create a panel for the menu items
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        jPanelMenu.add(panel, BorderLayout.CENTER);

        // Add vertical glue to center the items
        panel.add(Box.createVerticalGlue());

        // Add menu items to the panel
        panel.add(createMenuItem("Đơn Hàng", "/icon_DonHang.png"));
        panel.add(createMenuItem("Tồn Kho", "/icon_TonKho.png"));
        panel.add(createMenuItem("Nhập/Xuất", "/icon_NhapXuat.png"));
        panel.add(createMenuItem("Vị Trí Kho", "/icon_ViTri.png", 40, 36)); // Custom width
        panel.add(createMenuItem("Quản Trị", "/icon_QuanTri.png"));
        panel.add(createMenuItem("Thống kê", "/icon_ThongKe.png"));

        // Add vertical glue to push items to the center
        panel.add(Box.createVerticalGlue());
        
        contentPane.add(jPanelMenu, BorderLayout.WEST);
        
        
        
        // Tạo panel chính với BorderLayout
        JPanel panelDonNhap = new JPanel(new BorderLayout());
        
        
//        JPanel panelTop = new JPanel(new BorderLayout());
//        
//        JPanel panelNhap = new JPanel(new GridLayout(1,6));
//        
//        // Tạo các trường nhập liệu
//        panelNhap.add(new JLabel("Mã đơn hàng:"));
//        JTextField idOrderField = new JTextField();
//        panelNhap.add(idOrderField);
//
//        panelNhap.add(new JLabel("Tên khách hàng:"));
//        JTextField customerField = new JTextField();
//        panelNhap.add(customerField);
//
//        panelNhap.add(new JLabel("Ngày nhập hàng :"));
//        JTextField dateField = new JTextField();
//        panelNhap.add(dateField);
        
        JPanel panelNhap = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần

        // Mã đơn hàng
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelNhap.add(new JLabel("Mã đơn hàng:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Để các JTextField mở rộng theo chiều ngang
        idOrderField = new JTextField(15);
        panelNhap.add(idOrderField, gbc);

        // Tên khách hàng
        gbc.gridx = 2;
        gbc.gridy = 0;
        panelNhap.add(new JLabel("Tên khách hàng:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        customerField = new JTextField(15);
        panelNhap.add(customerField, gbc);

        // Ngày nhập hàng
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelNhap.add(new JLabel("Ngày nhập hàng:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        dateField = new JTextField(15);
        panelNhap.add(dateField, gbc);

        // Tạo panel cho các button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton createButton = new JButton("Tạo đơn" );
        JButton addProduct = new JButton("Thêm sản phẩm");
        JButton viewButton = new JButton("Xem chi tiết");
        JButton deleteButton = new JButton("Xóa đơn");

        
        // Thêm các button vào buttonPanel
        buttonPanel.add(createButton);
        buttonPanel.add(addProduct);
        buttonPanel.add(viewButton);
        buttonPanel.add(deleteButton);
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderCode = idOrderField.getText();
                String customerName = customerField.getText();
                String orderDate = dateField.getText();
                double totalAmount = 0.0; // Tổng tiền mặc định là 0

                // Kiểm tra dữ liệu nhập liệu
                if (orderCode.isEmpty() || customerName.isEmpty() || orderDate.isEmpty()) {
                    JOptionPane.showMessageDialog(DonHangNhapView.this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Thêm dữ liệu vào bảng
                	tableModel.addRow(new Object[]{orderCode, customerName, orderDate, totalAmount});

                    // Xóa dữ liệu trong các trường sau khi thêm
                	idOrderField.setText("");
                	customerField.setText("");
                	dateField.setText("");
                }
            }
        });
        
        // Thêm sự kiện lắng nghe cho nút
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderId = getSelectIdDH();
                if (orderId != null) {
                    ChiTietDonHangNhapView chiTietView = new ChiTietDonHangNhapView(orderId, table, selectedRow);
                    chiTietView.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Vui lòng chọn một đơn hàng trước.");
                }
                
            }
        });
        
        
        // Sự kiện cho nút "Xem chi tiết"
        viewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewOrderDetails();
            }
        });
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelNhap, buttonPanel);
        
        
        // Tạo model và table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã đơn hàng");
        tableModel.addColumn("Tên nhà cung cấp");
        tableModel.addColumn("Ngày nhập hàng");
        tableModel.addColumn("Thành tiền");

        table = new JTable(tableModel);

        // Tạo JScrollPane và thêm bảng vào đó
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Thêm buttonPanel vào phía trên và tableScrollPane vào giữa của mainPanel
        JLabel title = new JLabel("Đơn Nhập");
        panelDonNhap.add(splitPane, BorderLayout.NORTH);
        panelDonNhap.add(tableScrollPane, BorderLayout.CENTER);
        
        contentPane.add(panelDonNhap, BorderLayout.CENTER);
        
        
        
    }

    // Helper method to create menu items
    private JLabel createMenuItem(String text, String iconPath) {
        return createMenuItem(text, iconPath, 36, 36); // Default size
    }

    // Overloaded method to allow custom icon size
    private JLabel createMenuItem(String text, String iconPath, int iconWidth, int iconHeight) {
        JLabel label = new JLabel(text);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(new Font("Tahoma", Font.PLAIN, 16));
        Image img = new ImageIcon(this.getClass().getResource(iconPath)).getImage();
        Image scaledImg = img.getScaledInstance(iconWidth, iconHeight, Image.SCALE_SMOOTH);
        label.setIcon(new ImageIcon(scaledImg));
        label.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return label;
    }
    
    public String getSelectIdDH() {
        selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            try {
                String orderId = (String) table.getValueAt(selectedRow, 0);
                return orderId;
            } catch (NumberFormatException e) {
                System.err.println("Error: Invalid order ID format.");
            }
        }
        return null;
    }

    
    private void viewOrderDetails() {
        selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String orderId = (String) table.getValueAt(selectedRow, 0);
            String customerName = (String) table.getValueAt(selectedRow, 1);
            String orderDate = (String) table.getValueAt(selectedRow, 2);
            String totalPrice = String.valueOf(table.getValueAt(selectedRow, 3));
            
            // Lấy dữ liệu sản phẩm từ ChiTietDonHangNhapView
            ChiTietDonHangNhapView chiTietNhapView = new ChiTietDonHangNhapView(orderId, table, selectedRow);
            Object[][] productData = chiTietNhapView.getProductData();
            
            XemChiTietDonHangNhapView chiTietView = new XemChiTietDonHangNhapView(orderId, customerName, orderDate, totalPrice, productData);
            chiTietView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một đơn hàng để xem chi tiết.");
        }
    }

}
