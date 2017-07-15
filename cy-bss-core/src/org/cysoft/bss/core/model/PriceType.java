package org.cysoft.bss.core.model;

public class PriceType {
	
	public final static String TYPE_RC="RC";
	public final static String TYPE_NRC="NRC";
	public final static String TYPE_USG="USG";
	
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
	
	private String description="";
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "PriceType [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description + "]";
	}
	
}
