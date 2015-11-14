package org.cysoft.bss.core.model;

public class Person {
	
	private long id;
	public  long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private String code="";
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	private String firstName="";
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	private String secondName="";
	public String getSecondName() {
		return secondName;
	}
	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}
	
	private String gender="";
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	
	private String address="";
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	private long cityId;
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
	private String fiscalCode="";
	public String getFiscalCode() {
		return fiscalCode;
	}
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	
	private String birtyDay="";
	public String getBirtyDay() {
		return birtyDay;
	}
	public void setBirtyDay(String birtyDay) {
		this.birtyDay = birtyDay;
	}
	
	private long birtyCityId;
	public long getBirtyCityId() {
		return birtyCityId;
	}
	public void setBirtyCityId(long birtyCityId) {
		this.birtyCityId = birtyCityId;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", code=" + code + ", firstName="
				+ firstName + ", secondName=" + secondName + ", gender="
				+ gender + ", address=" + address + ", cityId=" + cityId
				+ ", fiscalCode=" + fiscalCode + ", birtyDay=" + birtyDay
				+ ", birtyCityId=" + birtyCityId + "]";
	}
	
	
}
