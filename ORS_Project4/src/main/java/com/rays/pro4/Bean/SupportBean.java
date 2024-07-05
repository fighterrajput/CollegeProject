package com.rays.pro4.Bean;

import java.util.Date;

public class SupportBean extends BaseBean {
	
	private String technician;
	private long ticketNo;
	private Date date;
	private String type;
	
	public String getTechnician() {
		return technician;
	}
	public void setTechnician(String technician) {
		this.technician = technician;
	}
	public long getTicketNo() {
		return ticketNo;
	}
	public void setTicketNo(long ticketNo) {
		this.ticketNo = ticketNo;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
