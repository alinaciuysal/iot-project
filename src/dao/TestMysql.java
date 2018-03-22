package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.MachineStatus;
import model.Product;

public class TestMysql {
	public Connection con = null;
	public Statement st = null;
	public ResultSet rs = null;
	public PreparedStatement pst = null;
	
    
    public static Connection prepareCon() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
    	Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/nodered", "root", "123456");
		return con;
    }

	public static void main(String[] args) {
        try {
            System.out.println(TestMysql.getEntityListByMachineId(1));
        } catch (SQLException ex) {
        
            Logger lgr = Logger.getLogger(TestMysql.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
	
	public static List<Product> getEntityListByMachineId(int machineId) throws SQLException {
		Connection c = TestMysql.prepareCon();
		PreparedStatement pst = c.prepareStatement("select * from product where machineId = ?;");
		pst.setInt(1, machineId);
		ResultSet rs = pst.executeQuery();
		
		List<Product> products = new ArrayList<Product>();
		
        while (rs.next()) {
        	Product p = new Product();
        	p.setId(rs.getInt("id"));
        	p.setProductName(rs.getString("productName"));
        	p.setAmount(rs.getInt("productAmount"));
        	p.setMachineId(rs.getInt("machineId"));
        	products.add(p);
        }
        if (pst != null) {
            pst.close();
        }
        if (c != null) {
            c.close();
        }		
		return products;
	}
	
	public static Product getEntityById(long id) throws Exception {
		Connection c = TestMysql.prepareCon();
		PreparedStatement pst = c.prepareStatement("select * from product where id = ?;");
		pst.setFloat(1, id);
		ResultSet rs = pst.executeQuery();
		
		Product p = new Product();
		
		while (rs.next()) {
        	p.setId(rs.getInt("id"));
        	p.setProductName(rs.getString("productName"));
        	p.setAmount(rs.getInt("productAmount"));
        	p.setMachineId(rs.getInt("machineId"));
        }
        if (pst != null) {
            pst.close();
        }
        if (c != null) {
            c.close();
        }		
		return p;
	}
	
	public static boolean updateEntity(long id, int update_amount, int machineId) throws Exception {
		Connection c = TestMysql.prepareCon();
		PreparedStatement pst = c.prepareStatement("update product set productAmount = ? where machineId = ? and id = ?;");
		System.out.println("updateEntity method's parameter(update_amount) " + update_amount);
		pst.setInt(1, update_amount);
		pst.setInt(2, machineId);
		pst.setLong(3, id);
		int rs = pst.executeUpdate();
		if (rs == 0) {
			System.out.println("some error in updateEntity function");
			return false;
		}
		if (pst != null) {
            pst.close();
        }
        if (c != null) {
            c.close();
        }
        return true;
		
	}

	public static List<Product> getEntityList() throws SQLException {
		
		Connection c = TestMysql.prepareCon();
		PreparedStatement pst = c.prepareStatement("select * from product;");
		ResultSet rs = pst.executeQuery();
		
		List<Product> products = new ArrayList<Product>();
		
        while (rs.next()) {
        	Product p = new Product();
        	p.setId(rs.getInt("id"));
        	p.setProductName(rs.getString("productName"));
        	p.setAmount(rs.getInt("productAmount"));
        	p.setMachineId(rs.getInt("machineId"));
        	products.add(p);
        }
        if (pst != null) {
            pst.close();
        }
        if (c != null) {
            c.close();
        }		
		return products;
	}

	public static List<MachineStatus> getTemperatureById(int machineId) {
		try {
			SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy' 'HH:mm:ss");
			Connection c = TestMysql.prepareCon();
			PreparedStatement pst = c.prepareStatement("select * from machinestatus where machineId = ? order by date desc limit 5;");
			pst.setInt(1, machineId);
			ResultSet rs = pst.executeQuery();
			
			List<MachineStatus> status = new ArrayList<MachineStatus>();
			
	        while (rs.next()) {
	        	MachineStatus ms = new MachineStatus();
	        	ms.setId(rs.getInt("id"));
	        	ms.setTemperature(rs.getDouble("temperature"));
	        	ms.setMachineId(rs.getInt("machineId"));
	        	Timestamp t = rs.getTimestamp("date");
	        	ms.setProperTime(df.format(t));
	        	boolean b = rs.getBoolean("isOperating");
	        	ms.setProperOperational(b);
	        	status.add(ms);
	        }
	        if (pst != null) {
				pst.close();
			}
			
	        if (c != null) {
	            c.close();
	        }		
			return status;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}

}
