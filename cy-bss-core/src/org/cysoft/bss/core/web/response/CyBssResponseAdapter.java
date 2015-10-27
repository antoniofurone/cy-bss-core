package org.cysoft.bss.core.web.response;

import org.cysoft.bss.core.model.ICyBssConst;

public abstract class CyBssResponseAdapter implements ICyBssResponse{
	
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
	
	private String languageCode=ICyBssConst.LOCALE_EN;
	public String getLanguageCode() {
		return languageCode;
	}
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}
	
	private long userId;
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public void reset(){
		resultCode=ICyBssResultConst.RESULT_OK;resultDesc="OK";
	}
	
}
