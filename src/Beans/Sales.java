package Beans;

import java.sql.Date;

public class Sales {

	private int ItemSold, TotalRend,TransCode;
	private String ProdCode, ProdName, Brand, Category, Description;
	private double CostPrice, Markup, ServCh,TotSale,TotServSale,SellPrice;
	private Date DateSold, DateRend;
	private boolean Flag;
	
	
	public Sales() {
		//EMPTY
	}
		
	public Sales(int trans, String ProdCode,String name, double serv, Date sDate, boolean flag) {
		this.TransCode = trans;
		this.ProdCode = ProdCode;
        this.ProdName = name;
        this.ServCh = serv;
        this.DateRend = sDate;
        this.Flag = flag;
	}
	
    
	
	public Sales(String ProdCode,String name,String brand,String desc,String categ, Double cost, Double markup, Date sDate, int sold, Double tSales, Boolean flag) {
		this.ProdCode = ProdCode;
        this.ProdName = name;
        this.Brand = brand;
        this.Description = desc;
        this.Category = categ;
        this.CostPrice = cost;
        this.Markup = markup;
        this.DateSold = sDate;
        this.ItemSold = sold;
        this.TotSale = tSales;
        this.Flag = flag;
	}
	
	//ItemSold ProdProdCode TransProdCode ProdName Category Price DateSold TotSale
	public Sales(int trans, String ProdCode, String name, String categ, Double price, Date dsold, int qty, Double tsale,Boolean flag) {
		this.TransCode = trans;
		this.ProdCode = ProdCode;
		this.ItemSold = qty;
		this.ProdName = name;
		this.Category = categ;
        this.SellPrice = price;
        this.TotSale = tsale;
        this.DateSold = dsold;
        this.Flag = flag;
		
	}
	
	public Boolean getFlag() {
		return Flag;
	}
	
	public void setFlag(Boolean flag) {
		this.Flag = flag;
	}
	
	
	public int getTrans() {
		return TransCode;
	}
	public void setTrans(int trans) {
		this.TransCode = trans;
	}
	
	public String getId() {
		return ProdCode;
	}
	public void setId(String code) {
		this.ProdCode = code;
	}
	
	public String getName() {
		return ProdName;
	}
	public void setName(String name) {
		this.ProdName = name;
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
	
	public Double getSell() {
		return SellPrice;
	}
	public void setSell(Double sell) {
		this.SellPrice = sell;
	}
	
	public Double getMarkup() {
		return Markup;
	}
	public void setMarkup(Double mark) {
		this.Markup = mark;
	}
		
	public Double getServCh() {
		return ServCh;
	}
	public void setServCh(Double servch) {
		this.ServCh = servch;
	}
	
	public int getTotalRend() {
		return TotalRend;
	}
	public void setTotalRend(int tot) {
		this.TotalRend = tot;
	}
	
	public Double getTSales() {
		return TotSale;
	}
	public void setTSales(Double tot) {
		this.TotSale = tot;
	}
	
	public Double getRSales(Object obj) {
		TotServSale = Double.parseDouble(obj.toString()) * TotalRend;
		return TotServSale;
	}
	public void setRSales(Double tot) {
		this.TotServSale = tot;
	}
	
	public int getSold() {
		return ItemSold;
	}
	public void setSold(int tot) {
		this.ItemSold = tot;
	}
	
	public Date getDSold() {
		return DateSold;
	}
	public void setDSold(Date day) {
		this.DateSold = day;
	}

	public Date getDrend() {
		return DateRend;
	}
	public void setDrend(Date day) {
		this.DateRend = day;
	}
}

