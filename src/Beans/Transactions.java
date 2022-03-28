package Beans;

import java.sql.Date;

public class Transactions {
	
		private int SupplierID, Qty, TransID, Stock;
		private String  ProdCode, SupplierName;
		private double TotAmt, CostPrice, Discount;
		private Date Date;
		private Boolean Flag;
		
		public Transactions() {
			//EMPTY
		}
			
		//FOR PRODUCT TRANSACTIONS
		public Transactions(int trans, int qty, double amt, Date date, Boolean flag, double discount) {
			this.TransID = trans;
			this.Qty = qty;
			this.TotAmt = amt;
			this.Discount = discount;
			this.Date = date;
			this.Flag = flag;
		}
		
		//FOR SUPPLIER TRANSACTIONS
		public Transactions(int trans, int suppId, int qty, Double amt, Date date, Boolean flag) {
			this.TransID = trans;
			this.SupplierID = suppId;
			this.Qty = qty;
			this.TotAmt = amt;
			this.Date = date;
			this.Flag = flag;
		}
		
		public int getStock() {
			return Stock;
		}
		public void setStock(int Stock) {
			this.Stock = Stock;
		}
		
		public double getCost() {
			return CostPrice;
		}
		
		public void setCost(double cost) {
			this.CostPrice = cost;
		}
		public int getID() {
			return TransID;
		}
		public void setID(int trans) {
			this.TransID = trans;
		}
		
		public String getProdId() {
			return ProdCode;
		}
		public void setProdId(String code) {
			this.ProdCode = code;
		}
		
		public int getSuppid() {
			return SupplierID;
		}
		public void setSuppId(int id) {
			this.SupplierID = id;
		}
		
		public String getName() {
			return SupplierName;
		}
		
		public void setName(String name) {
			this.SupplierName = name;
		}
		
		public Double getAmt() {
			return TotAmt;
		}
		public void setAmt(Double tot) {
			this.TotAmt = tot;
		}
				
		public int getQty() {
			return Qty;
		}
		public void setQty(int tot) {
			this.Qty = tot;
		}
		
		public Date getDate() {
			return Date;
		}
		public void setDate(Date day) {
			this.Date = day;
		}
		
		public Boolean getFlag() {
			return  Flag;
		}
		
		public void setFlag(Boolean flag) {
			this.Flag = flag;
		}

		public double getDiscount() {
			return  Discount;
		}
		
		public void setDiscount(double discount) {
			this.Discount = discount;
		}
	}


