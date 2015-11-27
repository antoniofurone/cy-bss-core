package org.cysoft.bss.core.model;

public class Ticket {
	public static final String ENTITY_NAME="Ticket";
	
	long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private String text="";
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	private String creationDate="";
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	
	private long statusId;
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	
	private String statusName="";
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	private long categoryId;
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	private String categoryName="";
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
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

	private long locationId;
	public long getLocationId() {
		return locationId;
	}
	public void setLocationId(long locationId) {
		this.locationId = locationId;
	}
	
	private Location location=null;
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "Ticket [id=" + id + ", text=" + text + ", creationDate="
				+ creationDate + ", statusId=" + statusId + ", statusName="
				+ statusName + ", userId=" + userId + ", userName=" + userName
				+ ", categoryId=" + categoryId + ", categoryName="
				+ categoryName + ", personId=" + personId
				+ ", personFirstName=" + personFirstName
				+ ", personSecondName=" + personSecondName + ", locationId="
				+ locationId + ", location=" + location + "]";
	}
	
		
	
}
