package Home;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnection {
	
	
	public static Connection getConnection(){
	    Connection con=null;  
	    try{  
	        Class.forName("com.mysql.cj.jdbc.Driver");  
	        con = DriverManager.getConnection("jdbc:mysql://localhost/projdb?usesUnicode"
	        	+ "=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
			System.out.print("\nDatabase is connected");
	    }catch(Exception e){System.out.println(e);}  
	    return con;  
	}
		
}
