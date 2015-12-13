package org.cysoft.bss.core.model;

public class Location {
	public static final String ENTITY_NAME="Location";
	
	private long id;
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	private long langId;
	public long getLangId() {
		return langId;
	}
	public void setLangId(long langId) {
		this.langId = langId;
	}

	private String name="";
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String creationDate="";
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	
	private String description="";
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String locationType="";
	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	
	private double latitude=0.0d;
	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	private double longitude=0.0d;
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	private long cityId;
	public long getCityId() {
		return cityId;
	}
	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
	private long userId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	private String userName="";
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	private long personId;
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	
	private String personFirstName="";
	public String getPersonFirstName() {
		return personFirstName;
	}
	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}

	private String personSecondName="";
	public String getPersonSecondName() {
		return personSecondName;
	}
	public void setPersonSecondName(String personSecondName) {
		this.personSecondName = personSecondName;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", langId=" + langId + ", name=" + name
				+ ", creationDate=" + creationDate + ", description="
				+ description + ", locationType=" + locationType
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", cityId=" + cityId + ", userId=" + userId + ", userName="
				+ userName + ", personId=" + personId + ", personFirstName="
				+ personFirstName + ", personSecondName=" + personSecondName
				+ "]";
	}

	
	
}
