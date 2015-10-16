package org.cysoft.bss.core.rest.response;

public interface ICyBssRestResponse {
	
	public String getResultCode();
	public void setResultCode(String resultCode);
	public String getResultDesc();
	public void setResultDesc(String resultDesc);
	
	public void setLanguageCode(String langCode);
	public String getLanguageCode();
	
	public void setUserId(long id);
	public long getUserId();
	
}
