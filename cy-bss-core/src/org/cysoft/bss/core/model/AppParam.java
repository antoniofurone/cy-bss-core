package org.cysoft.bss.core.model;

public class AppParam {

	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String value="";
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	private String type="";
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	private long appId;
	public long getAppId() {
		return appId;
	}
	public void setAppId(long appId) {
		this.appId = appId;
	}
	
	@Override
	public String toString() {
		return "AppParam [name=" + name + ", value=" + value + ", type=" + type
				+ ", appId=" + appId + "]";
	}
	
	
}
