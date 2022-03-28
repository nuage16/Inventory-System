package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Beans.Supplier;
import Beans.Supplier;
import Beans.Supplier;
import Home.DBConnection;

	public class SupplierDao {
	
	
	//ADD DATA TO SUPPLIER
	public static int save(Supplier u){  
	  int status=0;  
	   try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("insert into supplier(SupplierID,Name,Address,ContactNo,OtherInfo) values(?,?,?,?,?)");  
	        ps.setInt(1,u.getCode());	
	        ps.setString(2,u.getName());  
	        ps.setString(3,u.getAddress());  
	        ps.setString(4,u.getContact());
	        ps.setString(5,u.getInfo());
	        status=ps.executeUpdate();  
	        System.out.println("Data Added!");
	        
	        ps.close();
			con.close();
	   }catch(Exception e){System.out.println(e);}  
	   return status;  
	}  
	
	
	//UPDATE DETAILS  OF SUPPLIER
	public static int update(Supplier u){  
	    int status=0;  
	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("update supplier set Name=?,Address=?,ContactNo=?,OtherInfo=? where SupplierID=?");  
	        ps.setString(1,u.getName());  
	        ps.setString(2,u.getAddress());  
	        ps.setString(3,u.getContact());
	        ps.setString(4,u.getInfo());
	        ps.setInt(5, u.getCode());
	        
	        status=ps.executeUpdate();  
	        ps.close();
			con.close();
	    }catch(Exception e){System.out.println(e);}  
	    return status;  
	}  
	
	
	//SEARCH FOR A SUPPLIER
		public static Supplier searchRecord(int code){  
			Supplier u=null;  
		    try{  
		    	DBConnection db = new DBConnection();
		        Connection con= db.getConnection(); 
		        PreparedStatement ps=con.prepareStatement("select * from supplier where SupplierID=?");  
		        ps.setInt(1,code);  
		        ResultSet rs=ps.executeQuery();  
		        while(rs.next()){  
		            u=new Supplier();  
		            u.setCode(rs.getInt("SupplierID"));  
		            u.setName(rs.getString("Name"));  
		            u.setAddress(rs.getString("Address"));
		            u.setContact(rs.getString("ContactNo"));
		            u.setInfo(rs.getString("OtherInfo"));
		        	}
		        ps.close();
				con.close();
		    }
		  catch(Exception e){System.out.println(e);}  
		    return u;  
		}  
	
	////////////////////////////////////////////////////////////////////////////////////
	
	//QUERY SUPPLIER DATA
	public static List<Supplier> getRecords(){  
	    List<Supplier> list=new ArrayList<Supplier>();  
	try {
		DBConnection db = new DBConnection();
        Connection con= db.getConnection();  
		Statement st = con.createStatement();
		ResultSet rs = null;
		ResultSetMetaData rsmd;
		Supplier iL = new Supplier();
	
			//	QUERY FROM SERVICES
			rs = st.executeQuery("SELECT * FROM supplier");
			while(rs.next()) {
				
				iL = new Supplier(rs.getInt("SupplierID"),
						rs.getString("Name"),
						rs.getString("Address"),
						rs.getString("ContactNo"),
						rs.getString("OtherInfo")
						);
				list.add(iL);
			}
		
		st.close();
		con.close();
		}
	catch(Exception e) {
		}
		return list;
	}
	
	////////////////////////////////////////////////////////////////////////////////
	
	public static List<Supplier> getSupplier() {
		List<Supplier> list=new ArrayList<Supplier>();  
		try {
			DBConnection db = new DBConnection();
	        Connection con= db.getConnection();  
			Statement st = con.createStatement();
			ResultSet rs = null;
			ResultSetMetaData rsmd;
			Supplier iL,u;	
	
			rs = st.executeQuery("SELECT * FROM supplier");
				while(rs.next()) {
					iL = new Supplier(
							rs.getInt("SupplierID"),
							rs.getString("Name"),
							rs.getString("Address"),
							rs.getString("ContactNo"),
							rs.getString("OtherInfo")
							
							);
				list.add(iL);
				}
				
			st.close();
			con.close();
			}
		catch(Exception e) {
			}
			return list;
	}
	
	public static String setSupplier(int id){  
		Supplier u=null;  
        String value;

	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("SELECT * FROM supplier WHERE SupplierID=?");  
	        ps.setInt(1,id);  
	        ResultSet rs=ps.executeQuery();
	        while(rs.next()){  
	            u=new Supplier();    
	            u.setId(rs.getInt("SupplierID"));
	            u.setName(rs.getString("SupplierName"));           
	        	}
	        ps.close();
			con.close();
	    }
	  catch(Exception e){System.out.println(e);}  
	    value = u.getName();
	    return value;  
	}  
	
	public static int setSupplier(String name){  
		Supplier u=null;  
        int value;

	    try{  
	    	DBConnection db = new DBConnection();
	        Connection con= db.getConnection(); 
	        PreparedStatement ps=con.prepareStatement("SELECT * FROM supplier WHERE SupplierName=?");  
	        ps.setString(1,name);  
	        ResultSet rs=ps.executeQuery();
	        while(rs.next()){  
	            u=new Supplier();    
	            u.setId(rs.getInt("SupplierID"));
	            u.setName(rs.getString("SupplierName"));           
	        	}
	        ps.close();
			con.close();
	    }
	  catch(Exception e){System.out.println(e);}  
	    value = u.getId();
	    return value;  
	}  
}
