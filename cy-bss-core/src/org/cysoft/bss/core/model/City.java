package org.cysoft.bss.core.model;

public class City {
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}

	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String code="";
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	private String stateRegion="";
	public String getStateRegion() {
		return stateRegion;
	}
	public void setStateRegion(String stateRegion) {
		this.stateRegion = stateRegion;
	}
	
	private double latitude;
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	private double longitude;
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	private long countryId;
	public long getCountryId() {
		return countryId;
	}
	public void setCountryId(long countryId) {
		this.countryId = countryId;
	}
	
	private String countryCode="";
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	private String countryName="";
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", code=" + code + ", stateRegion=" + stateRegion + ", latitude="
				+ latitude + ", longitude=" + longitude + ", countryId=" + countryId + ", countryCode=" + countryCode
				+ ", countryName=" + countryName + "]";
	}
	
}
