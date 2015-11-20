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

	private long userId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}

	private long categoryId;
	public long getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	
	private long personId;
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
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
				+ creationDate + ", statusId=" + statusId + ", userId="
				+ userId + ", categoryId=" + categoryId + ", personId="
				+ personId + ", locationId=" + locationId + ", location="
				+ location +  "]";
	}
	
		
	
}
