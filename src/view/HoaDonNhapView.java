package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
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

import DAO.HoaDonNhapDAO;
import controller.HoaDonNhapController;
import model.HoaDonNhap;

public class HoaDonNhapView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private DefaultTableModel tableModel;
	public JTextField idOrderField;
	private JTable table;
	private int selectedRow;
	public JTextField nhaCungCapField;
	public JTextField dateField;
	public JButton lastSelectedButton;
	private JPanel panelDonNhap;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	private OrderForm of = new OrderForm();
	private TonKhoView tkview = new TonKhoView();
	private baoCaoView bcView = new baoCaoView();
	private QuanTriView qtview = new QuanTriView();
	public JButton donNhapButton;
	private HoaDonNhapDAO hoaDonNhapDAO = new HoaDonNhapDAO();
	private HoaDonNhapController hoaDonNhapController;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HoaDonNhapView frame = new HoaDonNhapView();
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
	public HoaDonNhapView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
        JPanel contentPane = new JPanel();
//        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        
        // Tạo panel menu với góc bo tròn
        RoundedPanel jPanelMenu = new RoundedPanel(15, Color.WHITE);
        jPanelMenu.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Khoảng cách giữa các component

        // Thêm biểu tượng nhà vào đầu panel menu
        JLabel labelHouse = new JLabel();
        labelHouse.setHorizontalAlignment(SwingConstants.CENTER);
        Image img = new ImageIcon(getClass().getResource("/House.png")).getImage();
        labelHouse.setIcon(new ImageIcon(img.getScaledInstance(84, 84, Image.SCALE_SMOOTH)));
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER; // Chiếm toàn bộ chiều rộng
        jPanelMenu.add(labelHouse, gbc);

        // Thêm khoảng cách
        gbc.gridy++;
        jPanelMenu.add(Box.createVerticalStrut(20), gbc);

        // Thêm các mục menu vào panel
        gbc.gridy++;
        gbc.gridwidth = 1; // Trở lại chiếm một cột
        donNhapButton = createMenuButton("Đơn Nhập", "DonNhap") ;
        jPanelMenu.add(donNhapButton, gbc);
        gbc.gridy++;
        jPanelMenu.add(createMenuButton("Đơn Xuất", "DonXuat"), gbc);
        gbc.gridy++;
        jPanelMenu.add(createMenuButton("Tồn Kho", "TonKho"), gbc);
        gbc.gridy++;
        jPanelMenu.add(createMenuButton("Quản Trị", "QuanTri"), gbc);
        gbc.gridy++;
        jPanelMenu.add(createMenuButton("Thống kê", "ThongKe"), gbc);

        contentPane.add(jPanelMenu, BorderLayout.WEST);

        JPanel panelDonNhap = new JPanel(new BorderLayout());
        
        JPanel panelNhap = new JPanel(new GridBagLayout());
//        GridBagConstraints gbc = new GridBagConstraints();
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
        panelNhap.add(new JLabel("Tên nhà cung cấp:"), gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        nhaCungCapField = new JTextField(15);
        panelNhap.add(nhaCungCapField, gbc);

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
        
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, panelNhap, buttonPanel);
        
        
        // Tạo model và table
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã đơn hàng");
        tableModel.addColumn("Tên nhà cung cấp");
        tableModel.addColumn("Ngày nhập hàng");
        tableModel.addColumn("Thành tiền");
        
        table = new JTable(tableModel);
        
        HoaDonNhapController hoaDonNhapController = new HoaDonNhapController(hoaDonNhapDAO, table, this);
        
        hoaDonNhapController.loadHoaDonNhapToTable();

        // Tạo JScrollPane và thêm bảng vào đó
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Nội dung chính với CardLayout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);

        panelDonNhap.add(splitPane, BorderLayout.NORTH);
        panelDonNhap.add(tableScrollPane, BorderLayout.CENTER);
        
        // Tạo các panel khác nhau cho mỗi thẻ
        mainPanel.add(panelDonNhap, "DonNhap");
        mainPanel.add(of.getContentPane(), "DonXuat");
        mainPanel.add(tkview.contentPane, "TonKho");
        mainPanel.add(qtview.contentPane, "QuanTri");
        mainPanel.add(bcView.frame.getContentPane(), "ThongKe");
        
        contentPane.add(mainPanel, BorderLayout.CENTER);
        
        
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderCode = idOrderField.getText();
                String nhaCungCapName = nhaCungCapField.getText();
                String orderDate = dateField.getText();
                Double totalAmount = 0.0; // Tổng tiền mặc định là 0

                // Kiểm tra dữ liệu nhập liệu
                if (orderCode.isEmpty() || nhaCungCapName.isEmpty() || orderDate.isEmpty()) {
                    JOptionPane.showMessageDialog(HoaDonNhapView.this, "Vui lòng nhập đầy đủ thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // Check if MaHoaDon is unique
                if (hoaDonNhapDAO.checkMaHoaDonExists(orderCode)) {
                    JOptionPane.showMessageDialog(HoaDonNhapView.this, "Mã hóa đơn đã tồn tại trong cơ sở dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Check date format and validity
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);
                Date date = null;
                try {
                    date = dateFormat.parse(orderDate);
                } catch (ParseException el) {
                    JOptionPane.showMessageDialog(HoaDonNhapView.this, "Ngày nhập không hợp lệ hoặc không đúng định dạng (dd/MM/yyyy).", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Thêm dữ liệu vào bảng
            	tableModel.addRow(new Object[]{orderCode, nhaCungCapName, orderDate, totalAmount});
            	
                // Xóa dữ liệu trong các trường sau khi thêm
            	idOrderField.setText("");
            	nhaCungCapField.setText("");
            	dateField.setText("");
            	
            	hoaDonNhapController.addHoaDonNhapFromTable();
            }
           
        });
        
        // Thêm sự kiện lắng nghe cho nút
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String orderId = getSelectIdDH();
                if (orderId != null) {
                    ChiTietDonHangNhapView chiTietView = new ChiTietDonHangNhapView(orderId, table, selectedRow, hoaDonNhapController);
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
        

    }

    // Phương thức tạo các nút menu
    private JButton createMenuButton(String text, String cardName) {
        JButton button = new JButton(text);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button.setFocusPainted(false); // Loại bỏ viền khi được chọn
        button.setContentAreaFilled(false); // Loại bỏ nền mặc định
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Đặt icon (tùy chọn, có thể bỏ qua nếu không cần)
        String iconPath = "/icon_" + cardName + ".png";
        Image img = new ImageIcon(getClass().getResource(iconPath)).getImage();
        Image scaledImg = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        button.setIcon(new ImageIcon(scaledImg));

        // Thêm hành động khi nhấp chuột
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Khôi phục màu nền của nút trước đó nếu có
                if (lastSelectedButton != null && lastSelectedButton != button) {
                    lastSelectedButton.setBackground(null);
                    lastSelectedButton.setOpaque(false);
                }

                // Đặt màu nền cho nút hiện tại
                button.setBackground(Color.LIGHT_GRAY);
                button.setOpaque(true);

                // Cập nhật nút được chọn trước đó
                lastSelectedButton = button;

                // Hiển thị thẻ tương ứng
                cardLayout.show(mainPanel, cardName);
            }
        });

        return button;
    }

//    // Phương thức tạo các panel khác nhau
//    private JPanel createPanel(String text) {
//        JPanel panel = new JPanel();
//        panel.setBackground(Color.WHITE);
//        panel.setLayout(new BorderLayout());
//        JLabel label = new JLabel(text, SwingConstants.CENTER);
//        label.setFont(new Font("Tahoma", Font.PLAIN, 24));
//        panel.add(label, BorderLayout.CENTER);
//        return panel;
//    }
    
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
            XemChiTietDonHangNhapView chiTietView = new XemChiTietDonHangNhapView(orderId);
            chiTietView.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn một đơn hàng để xem chi tiết.");
        }
    }
    
    public void showHoaDonNhapList(List<HoaDonNhap> listHoaDonNhap) {
        tableModel.setRowCount(0); // Xóa các hàng cũ nếu có
        for (HoaDonNhap hoaDonNhap : listHoaDonNhap) {
        	String formattedDate = formatDate(hoaDonNhap.getNgayNhap());
            tableModel.addRow(new Object[]{
                hoaDonNhap.getMaHoaDon(),
                hoaDonNhap.getTenNhaCungCap(),
                formattedDate,
                hoaDonNhap.getTongTien()
            });
        }
    }

    public String formatDate(Date date) {
        if (date == null) {
            return "N/A"; // or another placeholder
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
