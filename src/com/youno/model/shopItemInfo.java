package com.youno.model;

import java.io.Serializable;

public class shopItemInfo implements Serializable {
	private String shopName;
	private Double score;
	private String nearestArea;
	private String shopLogoUrl;
	private String activtyDes;
	private Double standardPrice;
	private Double discountPrice;
	private int saledAmount;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public String getNearestArea() {
		return nearestArea;
	}

	public void setNearestArea(String nearestArea) {
		this.nearestArea = nearestArea;
	}

	public String getShopLogoUrl() {
		return shopLogoUrl;
	}

	public void setShopLogoUrl(String shopLogoUrl) {
		this.shopLogoUrl = shopLogoUrl;
	}

	public String getActivtyDes() {
		return activtyDes;
	}

	public void setActivtyDes(String activtyDes) {
		this.activtyDes = activtyDes;
	}

	public Double getStandardPrice() {
		return standardPrice;
	}

	public void setStandardPrice(Double standardPrice) {
		this.standardPrice = standardPrice;
	}

	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	public int getSaledAmount() {
		return saledAmount;
	}

	public void setSaledAmount(int saledAmount) {
		this.saledAmount = saledAmount;
	}
	
	public shopItemInfo(){}

	public shopItemInfo(String shopName, Double score, String nearestArea,
			String shopLogoUrl, String activtyDes, Double standardPrice,
			Double discountPrice, int saledAmount) {
		super();
		this.shopName = shopName;
		this.score = score;
		this.nearestArea = nearestArea;
		this.shopLogoUrl = shopLogoUrl;
		this.activtyDes = activtyDes;
		this.standardPrice = standardPrice;
		this.discountPrice = discountPrice;
		this.saledAmount = saledAmount;
	}

}
