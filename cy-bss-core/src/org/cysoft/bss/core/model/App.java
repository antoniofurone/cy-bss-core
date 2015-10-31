package org.cysoft.bss.core.model;

public class App {
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
	
	private String descr="";
	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	@Override
	public String toString() {
		return "App [id=" + id + ", name=" + name + ", descr=" + descr + "]";
	}
	
}
