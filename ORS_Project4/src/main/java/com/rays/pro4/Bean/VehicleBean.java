package com.rays.pro4.Bean;

import java.util.Date;

public class VehicleBean extends BaseBean {
	private String number;
	private Date purchaseDate;
	private long insuranceAmount;
	private String colour;
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	public long getInsuranceAmount() {
		return insuranceAmount;
	}
	public void setInsuranceAmount(long insuranceAmount) {
		this.insuranceAmount = insuranceAmount;
	}
	public String getColour() {
		return colour;
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	@Override
	public String getkey() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
