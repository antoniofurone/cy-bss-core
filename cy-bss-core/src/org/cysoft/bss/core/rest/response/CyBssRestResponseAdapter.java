package org.cysoft.bss.core.rest.response;

public abstract class CyBssRestResponseAdapter implements ICyBssRestResponse{
	
	private String resultCode=ICyBssResultConst.RESULT_OK;
	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	private String resultDesc="OK";
	public String getResultDesc() {
		return resultDesc;
	}
	public void setResultDesc(String resultDesc) {
		this.resultDesc = resultDesc;
	}
	
	
}
