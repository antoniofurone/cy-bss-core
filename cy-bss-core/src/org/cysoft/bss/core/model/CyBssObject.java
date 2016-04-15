package org.cysoft.bss.core.model;

public class CyBssObject {
	private long id;
	private String name="";
	private String entityName="";
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
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	@Override
	public String toString() {
		return "CyBssObject [id=" + id + ", name=" + name + ", entityName=" + entityName + "]";
	}

	
}
