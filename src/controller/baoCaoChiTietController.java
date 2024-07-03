package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;

import org.jfree.data.category.DefaultCategoryDataset;

import model.bcct;
import view.baoCaoView;
import DAO.baoCaoChiTietDAO;

public class baoCaoChiTietController {
    private baoCaoView view;
    private baoCaoChiTietDAO bcctDAO = new baoCaoChiTietDAO();
    private DecimalFormat decimalFormat = new DecimalFormat("#,###.##");
    

    public baoCaoChiTietController(baoCaoView view) {
        this.view = view;
        this.view.addComboBoxListenerBCCT(new ComboBoxListener());
        updateTableDataBCCT("2022"); 
        updateTableDataBCCT("2023"); 
        updateTableDataBCCT("2024"); 
       
    }
    public void updateTableDataBCCT(String selectedYear) {
        Object[][] newData = getDataBCCT(selectedYear);
        view.updateTableBCCT(newData);
    }

    public void updateChart(String selectedYear) {
        DefaultCategoryDataset dataset = getChartDataset(selectedYear);
        view.updateChartBCCT(dataset);
    }
    
    private DefaultCategoryDataset getChartDataset(String selectedYear) {
        int year = Integer.parseInt(selectedYear);
        ArrayList<bcct> listBCCT = bcctDAO.getListBCCT(year);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (bcct record : listBCCT) {
            String thang = String.valueOf(record.getThang());  
            double doanhThu = record.getDoanhThu();
            double chiPhi = record.getChiPhi();
            double loiNhuan = record.getLoiNhuan();

            dataset.addValue(chiPhi, "Chi Phí", thang);
            dataset.addValue(doanhThu, "Doanh Thu", thang);
            dataset.addValue(loiNhuan, "Lợi Nhuận", thang);
        }

        return dataset;
    }
 
     public class ComboBoxListener implements ActionListener {
         @Override
         public void actionPerformed(ActionEvent e) {
             JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
             String selectedYear = (String) comboBox.getSelectedItem();
             updateTableDataBCCT(selectedYear);
             updateChart(selectedYear);
         }
	}
	private Object[][] getDataBCCT(String selectedYear) {
        int year = Integer.parseInt(selectedYear);
        ArrayList<bcct> listBCCT = bcctDAO.getListBCCT(year);

        Object[][] data = new Object[12][4]; 
    
        for (bcct record : listBCCT) {
        	
        	 int thang = record.getThang();
        	 double doanhThu = record.getDoanhThu();
        	 double chiPhi=record.getChiPhi();
        	 double loiNhuan=record.getLoiNhuan();
        	data[thang-1][0] =thang;
            data[thang-1][1] = decimalFormat.format(chiPhi); 
            data[thang-1][2] = decimalFormat.format(doanhThu);
            data[thang-1][3] = decimalFormat.format(loiNhuan);
            
        }

        return data;
    }

    public class ComboBoxListener1 implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox<String> comboBox = (JComboBox<String>) e.getSource();
            String selectedYear = (String) comboBox.getSelectedItem();
            updateTableDataBCCT(selectedYear);
        }
    }
}



