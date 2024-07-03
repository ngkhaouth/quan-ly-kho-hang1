package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;

import model.TonKho;
import model.tktq;
import view.baoCaoView;
import DAO.thongKeTongQuatDAO;

public class thongKeTongQuatController {
    private baoCaoView view;
    private thongKeTongQuatDAO tktqDAO = new thongKeTongQuatDAO();
    private DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

    public thongKeTongQuatController(baoCaoView view) {
        this.view = view;
        this.view.addComboBoxListenerTKTQ(new ComboBoxListener());
        updateTableDataTKTQ("2022"); 
        updateTableDataTKTQ("2023"); 
        updateTableDataTKTQ("2024"); 
        updateTotalRevenue();
        updateTonKho();
    }
    public void updateTableDataTKTQ(String selectedYear) {
        Object[][] newData = getData(selectedYear);
        view.updateTableTKTQ(newData);
    }

    private Object[][] getData(String selectedYear) {
        int year = Integer.parseInt(selectedYear);
        ArrayList<tktq> listTKTQ = tktqDAO.getListTKTQ(year);

        Object[][] data = new Object[2][5]; 
        data[0][0] = "Doanh thu";
        data[1][0] = "Tổng cộng";

    
        for (tktq record : listTKTQ) {
        	 int quy = record.getQuy();
        	 double doanhThu = record.getDoanhThu();
        	 double TongDoanhThuQuy=record.getTongDoanhThuQuy();
            data[0][quy] = decimalFormat.format(doanhThu); 
            data[1][1] = decimalFormat.format(TongDoanhThuQuy);
        }

        return data;
    }
    
//cap nhap nut doanh thu
    public void updateTotalRevenue() {
        double totalRevenue = 0;
        for (String year : new String[] {"2022", "2023", "2024"}) {
            ArrayList<tktq> listTKTQ = tktqDAO.getListTKTQ(Integer.parseInt(year));
            for (tktq record : listTKTQ) {
                totalRevenue += record.getDoanhThu();
               
                
            }
        }

        
        String formattedTotalRevenue = decimalFormat.format(totalRevenue); 
        view.updateTotalRevenue(formattedTotalRevenue);
    
    }

  //cap nhap nut ton kho
  	public void updateTonKho() {
  		
  		int tongTonKho=0;
  		
              ArrayList<TonKho> list = tktqDAO.getList();
              for (TonKho record : list) {
                  tongTonKho += record.getSoLuongTon();
                 
                  
              
          }

          
         
          view.updateTonKho(tongTonKho);
      
  	}
  


    public class ComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            String selectedYear = (String) comboBox.getSelectedItem();
            updateTableDataTKTQ(selectedYear);
        }
    }
}




