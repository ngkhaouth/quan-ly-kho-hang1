package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class vidu extends JFrame {
    private JTextField productNameField;
    private JTextField quantityField;
    private JTextField priceField;
    private JButton addButton;
    private OrderTableFrame orderTableFrame;

    public vidu(OrderTableFrame orderTableFrame) {

        this.orderTableFrame = orderTableFrame;
        setTitle("Nhập Đơn Hàng");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2));

        // Tạo các trường nhập liệu
        add(new JLabel("Tên sản phẩm:"));
        productNameField = new JTextField();
        add(productNameField);

        add(new JLabel("Số lượng:"));
        quantityField = new JTextField();
        add(quantityField);

        add(new JLabel("Giá:"));
        priceField = new JTextField();
        add(priceField);

        // Nút thêm sản phẩm
        addButton = new JButton("Thêm Sản Phẩm");
        add(addButton);

        // Xử lý sự kiện khi nhấn nút
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productName = productNameField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = Double.parseDouble(priceField.getText());

                // Thêm sản phẩm vào bảng
                orderTableFrame.addProductToTable(productName, quantity, price);
            }
        });

        setVisible(true);
    }
    
    public static void main(String[] args) {
        // Tạo cửa sổ bảng sản phẩm
        OrderTableFrame orderTableFrame = new OrderTableFrame();
        
        // Tạo cửa sổ nhập đơn hàng và truyền vào cửa sổ bảng sản phẩm
        new vidu(orderTableFrame);
    }
}

class OrderTableFrame extends JFrame {
    private JTable productTable;
    private DefaultTableModel tableModel;

    public OrderTableFrame() {
        setTitle("Bảng Sản Phẩm");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tạo bảng và mô hình bảng
        tableModel = new DefaultTableModel(new Object[]{"Tên sản phẩm", "Số lượng", "Giá"}, 0);
        productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        setVisible(true);
    }

    // Phương thức để thêm sản phẩm vào bảng
    public void addProductToTable(String productName, int quantity, double price) {
        tableModel.addRow(new Object[]{productName, quantity, price});
    }


}
