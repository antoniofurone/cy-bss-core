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
	
	private String name="";
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", type=" + locationType
				+ ", latitude=" + latitude + ", longitude=" + longitude
				+ ", cityId=" + cityId + "]";
	}
	
	
}
