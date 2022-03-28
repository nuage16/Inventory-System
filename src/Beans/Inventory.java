package Beans;

import java.sql.Date;

public class Inventory {

	private int  Stock, Total,Qty;
	private String Code, Name, Brand, Description, Category;
	private Double CostPrice, Markup, SellPrice, ServCh;
	private Boolean Flag;
	
	
	public Inventory() {
		//EMPTY
	}
	
	public Inventory(String code,String name, Double servch, Boolean flag){
        this.Code = code;
        this.Name = name;
        this.ServCh = servch;
        this.Flag = flag;
    }
	
	public Inventory(String code,String name,String brand,String categ, String desc, int stock, Double cost, Double markup, Double sell, Boolean flag){
        this.Code = code;
        this.Name = name;
        this.Brand = brand;
        this.Category = categ;
        this.Description = desc;
        this.CostPrice = cost;
        this.Markup = markup;
        this.Stock = stock;
        this.SellPrice = sell;
        this.Flag = flag;
    }
	
	public Boolean getFlag() {
		return Flag;
	}
	
	public void setFlag(Boolean flag) {
		this.Flag = flag;
	}
	
	public String getId() {
		return Code;
	}
	public void setId(String Code) {
		this.Code = Code;
	}
	
	public int getStock() {
		return Stock;
	}
	public void setStock(int Stock) {
		this.Stock = Stock;
	}
	
	public int getQty() {
		return Qty;
	}
	public void setQty(int qty) {
		this.Qty = qty;
	}
	
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name = name;
	}

	public String getBrand() {
		return Brand;
	}
	public void setBrand(String brand) {
		this.Brand = brand;
	}
	public String getDesc() {
		return Description;
	}
	public void setDesc(String desc) {
		this.Description = desc;
	}
	
	public String getCateg() {
		return Category;
	}
	public void setCateg(String categ) {
		this.Category = categ;
	}
	
	public Double getCost() {
		return CostPrice;
	}
	public void setCost(Double cost) {
		this.CostPrice = cost;
	}
	
	public Double getMarkup() {
		return Markup;
	}
	public void setMarkup(Double mark) {
		this.Markup = mark;
	}
	
	public Double getSell() {
		return SellPrice;
	}
	public void setSell(Double sell) {
		this.SellPrice = sell;
	}
	
	public Double getServCh() {
		return ServCh;
	}
	public void setServCh(Double servch) {
		this.ServCh = servch;
	}
	
	public int getTotal() {
		return Total;
	}
	public void setTotal(int tot) {
		this.Total = tot;
	}
	
	
}

