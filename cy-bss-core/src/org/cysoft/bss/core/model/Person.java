package org.cysoft.bss.core.model;

public class Person {
	
	public static final String ENTITY_NAME="Person";
	
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
	
	private String zipCode="";
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	
	private String birthDay="";
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	
	private long birthCityId;
	public long getBirthCityId() {
		return birthCityId;
	}
	public void setBirthCityId(long birtyCityId) {
		this.birthCityId = birtyCityId;
	}
	
	private String birthCity="";
	public String getBirthCity() {
		return birthCity;
	}
	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}
	@Override
	public String toString() {
		return "Person [id=" + id + ", code=" + code + ", firstName=" + firstName + ", secondName=" + secondName
				+ ", gender=" + gender + ", address=" + address + ", zipCode=" + zipCode + ", cityId=" + cityId
				+ ", city=" + city + ", country=" + country + ", fiscalCode=" + fiscalCode + ", birthDay=" + birthDay
				+ ", birthCityId=" + birthCityId + ", birthCity=" + birthCity + "]";
	}
	
	
	
}
