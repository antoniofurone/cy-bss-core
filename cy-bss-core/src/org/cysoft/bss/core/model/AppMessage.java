package org.cysoft.bss.core.model;

public class AppMessage {
	private String id="";
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	private long languageId;
	public long getLanguageId() {
		return languageId;
	}
	
	public void setLanguageId(long languageId) {
		this.languageId = languageId;
	}
	
	private String text="";
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
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
		return "AppMessage [id=" + id + ", languageId=" + languageId
				+ ", text=" + text + ", appId=" + appId + "]";
	}
	
	
}
