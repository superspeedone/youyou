package com.youno.model;

public class OrderItemInfo {
	private String shopName;
	private String shopLogoUrl;
	private int orderCounts;
	private String status;
	private String orderDate;
	private int orderTotal;
	
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public int getOrderCounts() {
		return orderCounts;
	}
	public void setOrderCounts(int orderCounts) {
		this.orderCounts = orderCounts;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public int getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(int orderTotal) {
		this.orderTotal = orderTotal;
	}

	public String getShopLogoUrl() {
		return shopLogoUrl;
	}
	public void setShopLogoUrl(String shopLogoUrl) {
		this.shopLogoUrl = shopLogoUrl;
	}
	
	public OrderItemInfo(){}
	
	public OrderItemInfo(String shopName, String shopLogoUrl, int orderCounts,
			String status, String orderDate, int orderTotal) {
		this.shopName = shopName;
		this.shopLogoUrl = shopLogoUrl;
		this.orderCounts = orderCounts;
		this.status = status;
		this.orderDate = orderDate;
		this.orderTotal = orderTotal;
	}	
}
