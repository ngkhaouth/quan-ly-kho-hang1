package DAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.bcct;

public class baoCaoChiTietDAO {
	    private static final Logger LOGGER = Logger.getLogger(baoCaoChiTietDAO.class.getName());
	    private MyConnect myConnect = new MyConnect();

	    public ArrayList<bcct> getListBCCT(int year) {
	        ArrayList<bcct> list = new ArrayList<>();
	        String query = "SELECT * FROM kho.baocaochitiet WHERE Nam = ?";
	        if (myConnect.openConnectDB()) {
	            try (PreparedStatement statement = myConnect.conn.prepareStatement(query)) {
	                statement.setInt(1, year);
	                try (ResultSet resultSet = statement.executeQuery()) {
	                    while (resultSet.next()) {
	                        int Nam = resultSet.getInt("Nam");                    
	                        int Thang = resultSet.getInt("Thang");
	                        double DoanhThu = (resultSet.getDouble("DoanhThu"));
	                        double LoiNhuan = (resultSet.getDouble("LoiNhuan"));
	                        double ChiPhi = (resultSet.getDouble("ChiPhi"));

	                        bcct sp = new bcct();
	                        sp.setNam(Nam);
	                        sp.setDoanhThu(DoanhThu);
	                        sp.setThang(Thang);
	                        sp.setLoiNhuan(LoiNhuan);
	                        sp.setChiPhi(ChiPhi);
	                        list.add(sp);
	                    }
	                    LOGGER.log(Level.INFO, "Data retrieval successfulll");
	                }
	            } catch (SQLException e) {
	                LOGGER.log(Level.SEVERE, "Error while retrieving data", e);
	            } finally {
	                myConnect.closeConnectDB();
	            }
	        } else {
	            LOGGER.log(Level.SEVERE, "Could not establish connection to database");
	        }

	        return list;
	    }
	    
	   


	

}
