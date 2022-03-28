package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import Beans.Inventory;
import Beans.Sales;
import Home.DBConnection;

public class SalesDao {
	
		
	//ADD DATA TO SALES FROM PRODUCTS
	public static Object[] saveSales(Sales u){  
		int status=0;  
        Object[] obj = new Object[5];
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("insert into sales(ItemSold,ProdCode,ProdName,Category,SellPrice,DateSold,TotSales,TransCode) values(?,?,?,?,?,?,?,?)");  
	        ps.setInt(1,u.getSold());	
	        ps.setString(2,u.getId());  
	        ps.setString(3,u.getName());    
	        ps.setString(4,u.getCateg());    
	        ps.setDouble(5, InventoryDao.searchRecord(u.getId()).getSell());
	        ps.setDate(6, u.getDSold(),java.util.Calendar.getInstance());
	        ps.setDouble(7, u.getTSales());
	        ps.setInt(8, u.getTrans());
	        status=ps.executeUpdate();  
	        System.out.println("Data Added!");
	        
	        ps.close();
			con.close();	        
	    }catch(Exception e){System.out.println(e);}  
	    return obj;  
	}  
	
	//ADD DATA TO SALES FROM SERVICES
	public static Object[] saveServiceSales(Sales u){  
		int status=0;  
        Object[] obj = new Object[5];
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("insert into saleservices(TransID,Code,Name,ServCh,DateRend) values(?,?,?,?,?)");  
	        ps.setInt(1,u.getTrans());
	        ps.setString(2, u.getId());
	        ps.setString(3,u.getName());  
	        ps.setDouble(4,u.getServCh());    
	        ps.setDate(5,u.getDSold(),java.util.Calendar.getInstance());    
	        status=ps.executeUpdate();  
	        System.out.println("Data Added!");
	        ps.close();
			con.close();
	        
	    }catch(Exception e){System.out.println(e);}  
	    return obj;  
	}
	
	
	//UPDATE DETAILS OF ITEM IN SALES
	public static int updateStock(Sales u){  
	    Inventory v = new Inventory();
		int status=0;  
		v = InventoryDao.searchRecord(u.getId());
	    
	    if(v!=null) {
	    	int remain = v.getStock()-u.getSold();
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("update inventory set Stock=? where Code=?");
	        ps.setInt(1,remain);  
	        ps.setString(2,v.getId());	
	        status=ps.executeUpdate();  
	        ps.close();
			con.close();
	    }
	    catch(Exception e){System.out.println(e);}  
	    }
	    else {
	    	System.out.print("\nItem does not exist\n");
	    }
	    return status;  
	} 
	
	//FILTER DATE BASED ON THE CHOSEN START DATE AND END DATE
		public static int filterDate(Date start, Date end){  
		    int status=0;  
		    try{  
		    	DBConnection db = new DBConnection();
		        Connection con= db.getConnection(); 
		        PreparedStatement ps=con.prepareStatement("select * from sales where Date between start and end");  
		       
		        status=ps.executeUpdate();  
		        System.out.println("Sales Saved!");
		        
		        ps.close();
				con.close();
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
		}  
		
	
	//QUERY SALES DATA
	public static List<Sales> getSales(int j, java.util.Date start, java.util.Date end){  
	    List<Sales> list=new ArrayList<Sales>();  
	try {
		DBConnection db = new DBConnection();
        Connection con= db.getConnection();  
		Statement st = con.createStatement();
		ResultSet rs = null;
		Sales iL = new Sales();
		
		String fStart = String.valueOf(new Date(start.getTime()));
		String fEnd = String.valueOf(new Date(end.getTime()));
		
		Date sqlStart =  Date.valueOf(fStart);
		Date sqlEnd =  Date.valueOf(fEnd);
				
		if(j==1){
				rs = st.executeQuery("select * from saleservices WHERE DateRend BETWEEN '" + sqlStart + "' AND '" + sqlEnd + "'");  

			while(rs.next()) {
				
		iL = new Sales(rs.getInt("TransID"),
						rs.getString("Code"),
						rs.getString("Name"),
						rs.getDouble("ServCh"),
						rs.getDate("DateRend"),
						rs.getBoolean("Void")
						);
		 
				list.add(iL);

			}
		}
		
		else {
			rs = st.executeQuery("SELECT * from sales WHERE DateSold BETWEEN '" + sqlStart + "' AND '" + sqlEnd + "'" + "order by TransCode DESC");  

			while(rs.next()) {
				iL = new Sales(rs.getInt("TransCode"),
						rs.getString("ProdCode"),
						rs.getString("ProdName"),
						rs.getString("Category"),
						rs.getDouble("SellPrice"),
						rs.getDate("DateSold"),
						rs.getInt("ItemSold"),
						rs.getDouble("TotSales"),
						rs.getBoolean("Void")
						);
				
				list.add(iL);
				
			}

		}
		
		System.out.println("Sales Query Successful!");
				
		st.close();
		con.close();
		}
	catch(Exception e) {
		}
		return list;
	}
		
	
	//SEARCH FOR A PRODUCT
	public static Sales searchRecord(int id){  
		Sales u=null;  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("select * from sales where ProdCode=?");  
	        ps.setInt(1,id);  
	        ResultSet rs=ps.executeQuery();  
	        while(rs.next()){  
	            u=new Sales();  
	            u.setTrans(rs.getInt("TransCode"));
	            u.setId(rs.getString("ProdCode"));  
	            u.setName(rs.getString("ProdName"));  
	            u.setCateg(rs.getString("Category"));
	            u.setSell(rs.getDouble("SellPrice"));
	            u.setDSold(rs.getDate("DateSold"));
	            u.setSold(rs.getInt("ItemSold"));
	            u.setTSales(rs.getDouble("TotSales"));
	            u.setFlag(rs.getBoolean("Void"));
	            
	            ps.close();
	            con.close();
	        }
	    }
	  catch(Exception e){System.out.println(e);}  
	    return u;  
	}  
	
	//RETRIEVES THE MAXIMUM TRANSACTION CODE/NUMBER AND RETURNS THE NEXT TRANSACTION NUMBER
	public static int transTracker(){    
		int  nxtTransValue=0;
		try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("select MAX(TransCode) FROM sales");  
	        ResultSet rs=ps.executeQuery();  
	        Sales u = new Sales();
	        if(rs.next()) {
	        nxtTransValue = rs.getInt(1);
	        }
	        ps.close();
            con.close();
	    }
	  catch(Exception e){System.out.println(e);}  
	    return nxtTransValue + 1;  
	}  
	
	//UPDATES STATUS OF CERTAIN SALES RECORD
	//IF TRUE/CHECKED = RECORD IS VOIDED
	public static int updateStatus(Sales u,int j){  
	    int status=0;  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=null;
	        
	        if(j==0) {
	        	ps=con.prepareStatement("update saleservices set Void=? where Code=?");
	        		ps.setBoolean(1,u.getFlag());  
	            	ps.setString(2,u.getId());	
	        	}  
	        else {
	        	ps=con.prepareStatement("update sales set Void=? where ProdCode=? AND TransCode=?");  
        
	    	ps.setBoolean(1,u.getFlag());  
        	ps.setString(2,u.getId());	
        	ps.setInt(3, u.getTrans());
	        }
	        status=ps.executeUpdate();  
	        
	        System.out.println("\nStatus Sales Updated!");
	        ps.close();
            con.close();
	        
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  


}
