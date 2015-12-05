package org.cysoft.bss.core.model;

public class TicketCategory {
	long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	String description="";
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "TicketCategory [id=" + id + ", name=" + name + ", description="
				+ description + "]";
	}
	
}
