package org.cysoft.bss.core.model;

import java.io.InputStream;

public class CyBssFile {
	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

	private String contentType="";
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	
	@Override
	public String toString() {
		return "CyBssFile [name=" + name + ", contentType=" + contentType + "]";
	}
	
	private InputStream content=null;
	public InputStream getContent() {
		return content;
	}
	public void setContent(InputStream content) {
		this.content = content;
	}
	
}
