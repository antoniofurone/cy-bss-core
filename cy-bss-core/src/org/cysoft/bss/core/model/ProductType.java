package org.cysoft.bss.core.model;


public class ProductType {
	public static final String ENTITY_NAME="ProductType";
	
	private long id;
	private String name;
	private String description;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ProductType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	
	
	
}
