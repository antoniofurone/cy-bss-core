package org.cysoft.bss.core.model;

public class Company {
	
	public static final String ENTITY_NAME="Company";
	
	private long id;
	public long getId() {
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
	
	private String country="";
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	private String fiscalCode="";
	public String getFiscalCode() {
		return fiscalCode;
	}
	public void setFiscalCode(String fiscalCode) {
		this.fiscalCode = fiscalCode;
	}
	
	private String vatCode="";
	public String getVatCode() {
		return vatCode;
	}
	public void setVatCode(String vatCode) {
		this.vatCode = vatCode;
	}
	
	private long headDeptId;
	public long getHeadDeptId() {
		return headDeptId;
	}
	public void setHeadDeptId(long headDeptId) {
		this.headDeptId = headDeptId;
	}
	
	private String headDeptCode="";
	public String getHeadDeptCode() {
		return headDeptCode;
	}
	public void setHeadDeptCode(String headDeptCode) {
		this.headDeptCode = headDeptCode;
	}
	
	private String headDeptName="";
	public String getHeadDeptName() {
		return headDeptName;
	}
	public void setHeadDeptName(String headDeptName) {
		this.headDeptName = headDeptName;
	}
	
	private long groupId;
	public long getGroupId() {
		return groupId;
	}
	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}
	
	private String groupCode="";
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	
	private String groupName="";
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	private long invoiceLogoId;
	public long getInvoiceLogoId() {
		return invoiceLogoId;
	}
	public void setInvoiceLogoId(long invoiceLogoId) {
		this.invoiceLogoId = invoiceLogoId;
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", code=" + code + ", name=" + name + ", address=" + address + ", cityId=" + cityId
				+ ", city=" + city + ", zipCode=" + zipCode + ", country=" + country + ", fiscalCode=" + fiscalCode
				+ ", vatCode=" + vatCode + ", headDeptId=" + headDeptId + ", headDeptCode=" + headDeptCode
				+ ", headDeptName=" + headDeptName + ", groupId=" + groupId + ", groupCode=" + groupCode
				+ ", groupName=" + groupName + ", invoiceLogoId=" + invoiceLogoId + "]";
	}
	
	
}
