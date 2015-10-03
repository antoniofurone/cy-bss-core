package org.cysoft.bss.core.model.rest.response;

public abstract class CyBssRestResponseAdapter implements ICyBssRestResponse{
	
	private String resultCode="";
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	private String resultDesc="";
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	
}
