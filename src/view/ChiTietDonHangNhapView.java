package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import DAO.ChiTietNhapDAO;
import controller.ChiTietNhapController;
import controller.HoaDonNhapController;
import model.ChiTietNhap;
import model.SanPham;

public class ChiTietDonHangNhapView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    public String orderId;
    private JTable orderTable;
    private int selectedRow;
//	public HoaDonNhapView hdnview = new HoaDonNhapView();
	private JTextField idKhoField;
	private JTextField productIdField;
	private JTextField productNameField;
	private JTextField quantityField;
	private JTextField costField;
	private DefaultTableModel modelChiTietDN;
	public JTable tableChiTietNhap;
	private ChiTietNhapDAO chiTietNhapDAO = new ChiTietNhapDAO();
	private HoaDonNhapController hoaDonNhapController;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					ChiTietDonHangNhapView frame = new ChiTietDonHangNhapView("N1", , selectedRow);
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ChiTietDonHangNhapView(String orderId, JTable orderTable, int selectedRow, HoaDonNhapController hoaDonNhapController) {
        this.orderId = orderId;
        this.orderTable = orderTable;
        this.selectedRow = selectedRow;
        this.hoaDonNhapController = hoaDonNhapController ;
        
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(750, 450);
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(5, 5));
		
		JPanel panelTop = new JPanel(new BorderLayout());
		
        JPanel panelNhap = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các thành phần


        // Mã đơn hàng
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelNhap.add(new JLabel("Mã sản phẩm:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Để các JTextField mở rộng theo chiều ngang
        productIdField = new JTextField(15);
        panelNhap.add(productIdField, gbc);
        
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        panelNhap.add(new JLabel("Số lượng:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        quantityField = new JTextField(15);
        panelNhap.add(quantityField, gbc);
        
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelNhap.add(new JLabel("Tên sản phẩm:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        productNameField = new JTextField(15);
        panelNhap.add(productNameField, gbc);
        

        gbc.gridx = 2;
        gbc.gridy = 1;
        panelNhap.add(new JLabel("Đơn giá: "), gbc);

        gbc.gridx = 3;
        gbc.gridy = 1;
        costField = new JTextField(15);
        panelNhap.add(costField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panelNhap.add(new JLabel("Mã kho:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        idKhoField = new JTextField(15);
        panelNhap.add(idKhoField, gbc);

        
        // Tạo panel cho các button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        
        JButton addProductButton = new JButton("Thêm" );
        JButton confirmButton = new JButton("Xác nhận");
        
        buttonPanel.add(addProductButton);
        buttonPanel.add(confirmButton);
        
        panelTop.add(buttonPanel, BorderLayout.SOUTH);
        panelTop.add(panelNhap, BorderLayout.CENTER);
        
        // Tạo model và table
        modelChiTietDN = new DefaultTableModel();
        modelChiTietDN.addColumn("Mã chi tiết hóa đơn");
        modelChiTietDN.addColumn("Mã sản phẩm");
        modelChiTietDN.addColumn("Tên sản phẩm");
        modelChiTietDN.addColumn("Mã kho");
        modelChiTietDN.addColumn("Số lượng");
        modelChiTietDN.addColumn("Đơn giá");

        tableChiTietNhap = new JTable(modelChiTietDN);

        // Tạo JScrollPane và thêm bảng vào đó
        JScrollPane tableScrollPane = new JScrollPane(tableChiTietNhap);

        // Thêm buttonPanel vào phía trên và tableScrollPane vào giữa của mainPanel
  
        contentPane.add(panelTop, BorderLayout.NORTH);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
        
		setContentPane(contentPane);
		
		
//    	// Đặt mã chi tiết đơn hàng
//    	setOrderDetailCode(orderId);
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		// Add action listener for the "Thêm" button
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
            	   // Lấy mã chi tiết nhập hiện tại lớn nhất từ cơ sở dữ liệu
                String currentMaxMaChiTietNhap;
                try {
                    currentMaxMaChiTietNhap = chiTietNhapDAO.getMaxMaChiTietNhap();
                } catch (SQLException el) {
                    el.printStackTrace();
                    System.out.println("Lỗi khi lấy mã chi tiết nhập lớn nhất: " + el.getMessage());
                    return;
                }
                
                // Tập hợp các mã chi tiết nhập hiện có trong JTable
                Set<String> existingIds = new HashSet<>();
                for (int i = 0; i < modelChiTietDN.getRowCount(); i++) {
                    existingIds.add((String) modelChiTietDN.getValueAt(i, 0));
                }

                // Tạo mã chi tiết nhập mới
                String maChiTietNhap = generateUniqueMaChiTietNhap(currentMaxMaChiTietNhap, existingIds);
                
                // Get data from fields
                String idProduct = productIdField.getText();
                String productName = productNameField.getText();
                String idKho = idKhoField.getText();
                String quantity = quantityField.getText();
                String cost = costField.getText();

                // Validate data (optional, you can add more checks)
                if (maChiTietNhap.isEmpty() || idProduct.isEmpty() || productName.isEmpty() || idKho.isEmpty() || quantity.isEmpty() || cost.isEmpty()) {
                    JOptionPane.showMessageDialog(ChiTietDonHangNhapView.this, "Please fill in all fields.");
                    return;
                }
                
                try {
                    int quantityInt = Integer.parseInt(quantity);
                    double costDouble = Double.parseDouble(cost);

                    if (quantityInt < 0) {
                        JOptionPane.showMessageDialog(ChiTietDonHangNhapView.this, "Số lượng không thể âm.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    if (costDouble < 0) {
                        JOptionPane.showMessageDialog(ChiTietDonHangNhapView.this, "Giá nhập không thể âm.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                // Add data to the table model
                modelChiTietDN.addRow(new Object[]{maChiTietNhap, idProduct, productName, idKho, quantity, cost});

                // Clear fields for new input
                productIdField.setText("");
                productNameField.setText("");
                idKhoField.setText("");
                quantityField.setText("");
                costField.setText("");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(ChiTietDonHangNhapView.this, "Vui lòng nhập số hợp lệ cho số lượng và giá nhập.", "Lỗi dữ liệu", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	
                ChiTietNhapController chiTietNhapController = new ChiTietNhapController(ChiTietDonHangNhapView.this, hoaDonNhapController);
                DefaultTableModel model = (DefaultTableModel) tableChiTietNhap.getModel();

                try {
                    chiTietNhapController.processChiTietNhap(model, orderId);
                    System.out.println("Cập nhật dữ liệu chi tiết hóa đơn thành công.");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                try {
					chiTietNhapController.tinhTong();
					System.out.println("Cập nhật dữ liệu tổng tiền thành công.");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                dispose();
            }
        });
	}

    public static String generateNewMaChiTietNhap(String currentMaxMaChiTietNhap) {
        if (currentMaxMaChiTietNhap == null) {
            return "C01"; // Trường hợp không có mã chi tiết nhập nào
        }

        // Tách phần số ra khỏi mã chi tiết nhập hiện tại
        String prefix = currentMaxMaChiTietNhap.substring(0, 1); // "C"
        String numberPart = currentMaxMaChiTietNhap.substring(1); // "01"

        // Tăng phần số
        int number = Integer.parseInt(numberPart);
        number++;

        // Định dạng lại phần số với độ dài 2 chữ số, ví dụ: "02", "03"
        return prefix + String.format("%02d", number);
    }
    
    public static String generateUniqueMaChiTietNhap(String currentMaxMaChiTietNhap, Set<String> existingIds) {
        if (currentMaxMaChiTietNhap == null) {
            currentMaxMaChiTietNhap = "C00"; // Trường hợp không có mã chi tiết nhập nào, bắt đầu từ "C00"
        }

        String newMaChiTietNhap;
        int counter = 0;

        do {
            newMaChiTietNhap = generateNewMaChiTietNhap(currentMaxMaChiTietNhap);
            currentMaxMaChiTietNhap = newMaChiTietNhap; // Cập nhật mã chi tiết nhập hiện tại để tăng tiếp
            counter++;
        } while (existingIds.contains(newMaChiTietNhap) && counter <= 999); // Giới hạn kiểm tra để tránh vòng lặp vô tận

        return newMaChiTietNhap;
    }
	
    private void confirmTotal() {
        try {
            double totalAmount = 0.0;
            for (int i = 0; i < modelChiTietDN.getRowCount(); i++) {
                int quantity = Integer.parseInt(modelChiTietDN.getValueAt(i, 3).toString());
                double price = Double.parseDouble(modelChiTietDN.getValueAt(i, 4).toString());
                totalAmount += quantity * price;
            }

            // Cập nhật tổng số tiền vào hàng đã chọn
            if (selectedRow != -1) {
                orderTable.setValueAt(totalAmount, selectedRow, 3); // Cập nhật cột tổng tiền
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi tính tổng số tiền.");
        }
    }
    
    public Object[][] getProductData() {
        DefaultTableModel model = (DefaultTableModel) tableChiTietNhap.getModel();
        Object[][] data = new Object[model.getRowCount()][5]; // Số cột là 5 tương ứng với số cột trong bảng ChiTietDonHangView
        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < 5; col++) { // Lặp qua từng cột
                data[row][col] = model.getValueAt(row, col);

            }
        }

        return data;
    }

}
