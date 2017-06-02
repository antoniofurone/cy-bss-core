package org.cysoft.bss.core.model;

public class Metric {
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
	
	@Override
	public String toString() {
		return "Metric [id=" + id + ", name=" + name + "]";
	}

}
