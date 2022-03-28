package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import Beans.Inventory;
import Beans.Sales;
import Home.DBConnection;

public class InventoryDao {
		
	//ADDS DATA INTO INVENTORY
	public static void saveEliquid(Inventory u){  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=null;
	        ps=con.prepareStatement("insert into inventory(Code,Name,Brand,Category,Description,Stock,CostPrice,Markup,SellPrice,Flag) values(?,?,?,?,?,?,?,?,?,?)");  
	        
	        ps.setString(1,u.getId());	
	        ps.setString(2,u.getName());  
	        ps.setString(3,u.getBrand());  
	        ps.setString(4,u.getCateg());
	        ps.setString(5,u.getDesc());  
	        ps.setInt(6,u.getStock());
	        ps.setDouble(7, u.getCost());
	        ps.setDouble(8,u.getMarkup()); 
	        ps.setDouble(9,u.getSell());  
	        ps.setBoolean(10, u.getFlag());
	        ps.executeUpdate();  
	        
	        System.out.println("Inventory Data Added!");
	        ps.close();
			con.close();
	        
	    }catch(Exception e){System.out.println(e);}  
	}  
	
	
	//UPDATE DETAILS OF DATA IN INVENTORY
	public static int update(Inventory u){  
	    int status=0;  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=null;
	        
	        ps=con.prepareStatement("update inventory set Name=?,Brand=?,Category=?,Description=?,Stock=?,CostPrice=?,Markup=?, SellPrice=? where Code=?");  
 	
	        ps.setString(1,u.getName());  
	        ps.setString(2,u.getBrand()); 
	        ps.setString(3,u.getCateg());  
	        ps.setString(4,u.getDesc());  
	        ps.setInt(5, u.getQty());
	        ps.setDouble(6,u.getCost());  
	        ps.setDouble(7,u.getMarkup());  
	        ps.setDouble(8,u.getSell());
	        ps.setString(9,u.getId());	

	        status=ps.executeUpdate();  
	        
	        System.out.println("\nInventory Updated!");
	        ps.close();
			con.close();
	        
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	//UPDATE DETAILS OF STOCKS AND COSTPRICE IN INVENTORY
		public static int updateStock(Inventory u){  
		    int status=0;  
		    
		    try{  
		    	DBConnection db = new DBConnection();
		        Connection con= db.getConnection(); 
		        PreparedStatement ps=null;
		 
		        ps=con.prepareStatement("update inventory set Stock=Stock+?,CostPrice=?, SellPrice=((Markup/100)*?)+? where Code=?");  
		        ps.setInt(1, u.getQty());
		        ps.setDouble(2,u.getCost());    
		        ps.setDouble(3,u.getCost());
		        ps.setDouble(4,u.getCost());
		        ps.setString(5,u.getId());	

		        status=ps.executeUpdate();  
		        
		        System.out.println("\nStock Updated!");
		        ps.close();
				con.close();
		        
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
		}  

	//UPDATES STATUS OF CERTAIN SALES RECORD
	//IF TRUE/CHECKED = RECORD IS VOIDED
	public static int updateStatus(Inventory u,int j){  
	    int status=0;  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=null;
	        
	        if(j==0)
	        	ps=con.prepareStatement("update services set Void=? where Code=?");  
	        else
	        	ps=con.prepareStatement("update inventory set Flag=? where Code=?");  
	        
	        ps.setBoolean(1,u.getFlag());  
	        ps.setString(2,u.getId());	
	        status=ps.executeUpdate();  
	      
	        System.out.println("\nInventory Status Updated!");
	        ps.close();
			con.close();
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  

	//SEARCH FOR A PRODUCT
	public static Inventory searchRecord(String id){  
		Inventory u=null;  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("select * from inventory where Code=?");  
	        ps.setString(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){
	            u=new Inventory();  
	            u.setId(rs.getString("Code"));  
	            u.setName(rs.getString("Name"));  
	            u.setBrand(rs.getString("Brand"));
	            u.setCateg(rs.getString("Category"));
	            u.setDesc(rs.getString("Description"));
	            u.setStock(rs.getInt("Stock"));
	            u.setCost(rs.getDouble("CostPrice"));
	            u.setMarkup(rs.getDouble("Markup"));
	            u.setSell(rs.getDouble("SellPrice"));
	            u.setFlag(rs.getBoolean("Flag"));
	        	}
	        System.out.println("\nInventory Search Successful!"); 
	        ps.close();
			con.close();
	    }
	  catch(Exception e){
		  System.out.println(e);
		  }  
	   return u;  
	}  
		
	//DATA QUERY
	//PARAMETER IS USED TO IDENTIFY TABLE FROM DATABASE
	public static List<Inventory> getInventory(int j){  
	    List<Inventory> list=new ArrayList<Inventory>();  
	try {
		DBConnection db = new DBConnection();
        Connection con= db.getConnection();  
		Statement st = con.createStatement();
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		Inventory iL;	
		
		if(j==1) {
		//QUERY FROM SERVICES
			rs = st.executeQuery("SELECT * FROM services");
			while(rs.next()) {
				iL = new Inventory(
						rs.getString("Code"),
						rs.getString("Name"),
						rs.getDouble("ServiceCharge"),
						rs.getBoolean("Void")
						);
				list.add(iL);
			}
		}
		
		else{
		//QUERY FROM PRODUCT INVENTORY	
			rs = st.executeQuery("SELECT * FROM inventory");
			while(rs.next()) {
				iL = new Inventory(
						rs.getString("Code"),
						rs.getString("Name"),
						rs.getString("Brand"),
						rs.getString("Category"),
						rs.getString("Description"),
						rs.getInt("Stock"),
						rs.getDouble("CostPrice"),
						rs.getDouble("Markup"),	
						rs.getDouble("SellPrice"),
						rs.getBoolean("Flag")
						);
				list.add(iL);
			}
		}
		 System.out.println("\nInventory Query Successful!");
		st.close();
		con.close();
		}
	catch(Exception e) {System.out.println(e);}
		return list;
	}
}
