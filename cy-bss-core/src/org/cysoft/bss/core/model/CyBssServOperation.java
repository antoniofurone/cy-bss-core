package org.cysoft.bss.core.model;

import java.util.List;

public class CyBssServOperation {
	
	private int id=-1;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	private int servId=-1;
	public int getServId() {
		return servId;
	}

	public void setServId(int servId) {
		this.servId = servId;
	}
	
	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	private String method="";
	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}
	
	private String url="";
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	private String description="";
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	private List<CyBssOperationParam> params=null;
	public List<CyBssOperationParam> getParams() {
		return params;
	}
	public void setParams(List<CyBssOperationParam> params) {
		this.params = params;
	}

	@Override
	public String toString() {
		return "BssServOperation [id=" + id + ", servId=" + servId + ", name="
				+ name + ", method=" + method + ", url=" + url
				+ ", description=" + description + ", params=" + params + "]";
	}


}
