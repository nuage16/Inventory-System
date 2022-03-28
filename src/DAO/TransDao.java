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
import Beans.Transactions;
import Home.DBConnection;

public class TransDao {
	
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.getDefault());
	
	//ADD DATA TO Transactions
	public static void saveProdTrans(Transactions u){  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        //TransID QtySold TotAmtSold DateSold Void	        
	        PreparedStatement ps=null;
	        ps=con.prepareStatement("insert into prodtrans(TransID,QtySold,TotAmtSold,Discount,DateSold) values(?,?,?,?,?)");  
	        
	        ps.setInt(1,u.getID());	
	        ps.setInt(2,u.getQty());  
	        ps.setDouble(3,u.getAmt()); 
	        ps.setDouble(4, u.getDiscount());
	        ps.setDate(5,u.getDate(),java.util.Calendar.getInstance());  
	        ps.executeUpdate();  
	        
	        System.out.println("Transaction Saved!");
	        
	    }catch(Exception e){System.out.println(e);}  
	}
	
	
	
	//ADD DATA TO Transactions
		public static Object[] saveSuppTrans(Transactions u){  
			int status=0;  
	        Object[] obj = new Object[5];
		    try{  
		    	DBConnection db = new DBConnection();
		        Connection con= db.getConnection(); 
		        PreparedStatement ps=null;
		        ps=con.prepareStatement("insert into supp_trans(TransID,SupplierID,ProdCode,CostPrice,"
		        		+ "TotQtyOrdered,TotAmtOrdered,DateOrdered) values(?,?,?,?,?,?,?)");  		        
		        ps.setInt(1,u.getID());	
		        ps.setInt(2, u.getSuppid());
		        ps.setString(3,u.getProdId());  
		        ps.setDouble(4,u.getCost());
		        ps.setInt(5, u.getQty());
		        ps.setDouble(6, u.getAmt());
		        ps.setDate(7,u.getDate(),java.util.Calendar.getInstance());  
		        ps.executeUpdate();  
		        
		        System.out.println("Transaction Saved!");
        
		    }catch(Exception e){System.out.println(e);}  
		    return obj;  
		}  
		
		//FILTER DATE BASED ON THE CHOSEN START DATE AND END DATE
		public static int filterDate(Date start, Date end){  
		    int status=0;  
		    try{  
		    	DBConnection db = new DBConnection();
		        Connection con= db.getConnection(); 
		        PreparedStatement ps=con.prepareStatement("select * from sales where Date between start and end");  
		       
		        status=ps.executeUpdate();  
		        System.out.println("Data Added!");
		        
		    }catch(Exception e){System.out.println(e);}  
		    return status;  
		}  
		
	
	//QUERY  DATA FROM TRANSACTIONS 
	public static List<Transactions> getTrans(int j, java.util.Date start, java.util.Date end){  
	    List<Transactions> list=new ArrayList<Transactions>();  
	try {
		DBConnection db = new DBConnection();
        Connection con= db.getConnection();  
		Statement st = con.createStatement();
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		PreparedStatement ps;
		Transactions iL = new Transactions();
		
		String fStart = String.valueOf(new Date(start.getTime()));
		String fEnd = String.valueOf(new Date(end.getTime()));
		Date sqlStart =  Date.valueOf(fStart);
		Date sqlEnd =  Date.valueOf(fEnd);
		
		if(j==0){
			rs = st.executeQuery("SELECT * from prodtrans WHERE DateSold BETWEEN '" + sqlStart + 
					"' AND '" + sqlEnd + "'" + "order by TransID DESC");  

			while(rs.next()) {
				iL = new Transactions(rs.getInt("TransID"),
						rs.getInt("QtySold"),
						rs.getDouble("TotAmtSold"),
						rs.getDate("DateSold"),
						rs.getBoolean("Void"),
						rs.getDouble("Discount")
						);
				list.add(iL);	
			}
		}
		else {
			rs = st.executeQuery("SELECT * from supp_trans WHERE DateOrdered BETWEEN '" + sqlStart + "' AND '" + sqlEnd + "'" + "order by TransID DESC");  

			while(rs.next()) {
				iL = new Transactions(rs.getInt("TransID"),
						rs.getInt("SupplierID"),
						rs.getInt("TotQtyOrdered"),
						rs.getDouble("TotAmtOrdered"),
						rs.getDate("DateOrdered"),
						rs.getBoolean("Void")
						);
				list.add(iL);
			}
		}	
		st.close();
		con.close();
		}
	catch(Exception e) {
		}
		return list;
	}
	
	public static int transTracker(int x){    
		int  nxtTransValue=0;
		try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        
	        PreparedStatement ps;
	        if(x==0)
	        ps =con.prepareStatement("select MAX(TransID) FROM prodtrans");  
	        else        
	        ps=con.prepareStatement("select MAX(TransID) FROM supp_trans");  
	        
	        ResultSet rs=ps.executeQuery();  
	        Transactions u = new Transactions();
	        if(rs.next()) {
	        nxtTransValue = rs.getInt(1);
	        }
	    }
	  catch(Exception e){System.out.println(e);}  
	    return nxtTransValue + 1;  
	}  	

	public static int transTracker(){    
		int  nxtTransValue=0;
		try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("select MAX(TransCode) FROM sales");  
	        ResultSet rs=ps.executeQuery();  
	        Transactions u = new Transactions();
	        if(rs.next()) {
	        nxtTransValue = rs.getInt(1);
	        }
	    }
	  catch(Exception e){System.out.println(e);}  
	    return nxtTransValue + 1;  
	}  
	
	
	public static int updateStatus(Transactions u,int j){  
	    int status=0;  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=null;
	        
	        if(j==0)
	        	ps=con.prepareStatement("update prodtrans set Void=? where TransID=?");  
	        else
	        	ps=con.prepareStatement("update supp_trans set Void=? where TransID=?");  
	        
	        ps.setBoolean(1,u.getFlag());  
	        ps.setInt(2,u.getID());	

	        status=ps.executeUpdate();  
	        System.out.println("\nStatus Updated!");
	        ps.close();
			con.close();
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  


}
