package com.weatherdemo.app.domain;

public class Country {
	private int id;
	private int ciId;
	private String coName;
	private String coCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCiId() {
		return ciId;
	}
	public void setCiId(int ciId) {
		this.ciId = ciId;
	}
	public String getCoName() {
		return coName;
	}
	public void setCoName(String coName) {
		this.coName = coName;
	}
	public String getCoCode() {
		return coCode;
	}
	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}
	
}
