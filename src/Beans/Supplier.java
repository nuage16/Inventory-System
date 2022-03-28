package Beans;

import java.sql.Date;

 public class Supplier {

	private int ID,Code;
	private String Name, Address, ContactNo, OtherInfo;
	
	
	public Supplier() {
		//EMPTY
	}
		
	public Supplier(int code, String name, String addr, String contact, String othInfo) {
        this.Code = code;
		this.Name = name;
        this.Address = addr;
        this.ContactNo = contact;
        this.OtherInfo = othInfo;
       
	}
	
	
	public int getId() {
		return ID;
	}
	public void setId(int id) {
		this.ID = id;
	}
	

	public int getCode() {
		return Code;
	}
	public void setCode(int code) {
		this.Code = code;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}

	public String getAddress() {
		return Address;
	}
	public void setAddress(String addr) {
		this.Address = addr;
	}
	
	public String getContact() {
		return ContactNo;
	}
	public void setContact(String contact) {
		this.ContactNo = contact;
	}
	
	public String getInfo() {
		return OtherInfo;
	}
	public void setInfo(String info) {
		this.OtherInfo = info;
	}
	
	
}

