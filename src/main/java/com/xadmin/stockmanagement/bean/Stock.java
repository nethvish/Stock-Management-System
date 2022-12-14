package com.xadmin.stockmanagement.bean;

public class Stock {
	
	private int stockid;
	private String productname;
	private String price;
	private String quantity;
	
	//default constructor 
	public Stock(int stockid, String productname, String price, String quantity) {
		super();
		this.stockid = stockid;
		this.productname = productname;
		this.price = price;
		this.quantity = quantity;
	}
	
	//default constructor 
	public Stock(String productname, String price, String quantity) {
		super();
		this.productname = productname;
		this.price = price;
		this.quantity = quantity;
	}
	
	//getters and setters
	public int getStockid() {
		return stockid;
	}
	
	public void setStockid(int stockid) {
		this.stockid = stockid;
	}
	
	public String getProductname() {
		return productname;
	}
	
	public void setProductname(String productname) {
		this.productname = productname;
	}
	
	public String getPrice() {
		return price;
	}
	
	public void setPrice(String price) {
		this.price = price;
	}
	
	public String getquantity() {
		return quantity;
	}
	
	public void setquantity(String quantity) {
		this.quantity = quantity;
	}
	
}
