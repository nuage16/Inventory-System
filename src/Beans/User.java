package Beans;

public class User {

	int ID;
	String Name, Password;
	
	public User(){
		
	}
	
	public int getId() {
		return ID;
	}
	public void setId(int id) {
		this.ID = id;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}

	public String getPassw() {
		return Password;
	}
	public void setPass(String pass) {
		this.Password = pass;
	}
}
