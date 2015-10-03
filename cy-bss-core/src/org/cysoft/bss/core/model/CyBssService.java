package org.cysoft.bss.core.model;

import java.util.List;

public class CyBssService {
	
	private int id=-1;
	private String name="";
	private String url="";
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	private List<CyBssServOperation> operations=null;
	public List<CyBssServOperation> getOperations() {
		return operations;
	}
	public void setOperations(List<CyBssServOperation> operations) {
		this.operations = operations;
	}
	@Override
	public String toString() {
		return "BssService [id=" + id + ", name=" + name + ", url=" + url
				+ ", operations=" + operations + "]";
	}
	
}
