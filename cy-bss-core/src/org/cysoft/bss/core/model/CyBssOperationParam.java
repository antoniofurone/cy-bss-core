package org.cysoft.bss.core.model;

public class CyBssOperationParam {
	private String name="";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private int operationId;

	public int getOperationId() {
		return operationId;
	}

	public void setOperationId(int operationId) {
		this.operationId = operationId;
	}
	
	private String flagUrl="";

	public String getFlagUrl() {
		return flagUrl;
	}

	public void setFlagUrl(String flagUrl) {
		this.flagUrl = flagUrl;
	}
	
	private String description="";

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	private String required="";

	public String getRequired() {
		return required;
	}

	public void setRequired(String required) {
		this.required = required;
	}

	@Override
	public String toString() {
		return "BssOperationParam [name=" + name + ", operationId="
				+ operationId + ", flagUrl=" + flagUrl + ", description="
				+ description + ", required=" + required + "]";
	}
	
	
}
