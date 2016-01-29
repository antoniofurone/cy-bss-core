package org.cysoft.bss.core.model;

public class CompanyDept {
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private long companyId;
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	private String code="";
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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

	private String city="";
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	private String zipCode="";
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
	private long parentId=0;
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
	
	@Override
	public String toString() {
		return "CompanyDept [id=" + id + ", companyId=" + companyId + ", code=" + code + ", name=" + name + ", address="
				+ address + ", cityId=" + cityId + ", city=" + city + ", zipCode=" + zipCode + ", parentId=" + parentId
				+ "]";
	}
	
	

}
