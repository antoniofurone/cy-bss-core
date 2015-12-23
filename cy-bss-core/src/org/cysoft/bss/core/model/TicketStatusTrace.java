package org.cysoft.bss.core.model;

public class TicketStatusTrace {
	
	private long ticketId;
	private long startStatusId;
	private String startStatusName="";
	private long endStatusId;
	private String endStatusName="";
	private long userId;
	private String userName="";
	private String dateTrans="";
	private String note="";
	
	public long getTicketId() {
		return ticketId;
	}
	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}
	public long getStartStatusId() {
		return startStatusId;
	}
	public void setStartStatusId(long startStatusId) {
		this.startStatusId = startStatusId;
	}
	public String getStartStatusName() {
		return startStatusName;
	}
	public void setStartStatusName(String startStatusName) {
		this.startStatusName = startStatusName;
	}
	public long getEndStatusId() {
		return endStatusId;
	}
	public void setEndStatusId(long endStatusId) {
		this.endStatusId = endStatusId;
	}
	public String getEndStatusName() {
		return endStatusName;
	}
	public void setEndStatusName(String endStatusName) {
		this.endStatusName = endStatusName;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getDateTrans() {
		return dateTrans;
	}
	public void setDateTrans(String dateTrans) {
		this.dateTrans = dateTrans;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	
	@Override
	public String toString() {
		return "TicketStatusTrace [ticketId=" + ticketId + ", startStatusId="
				+ startStatusId + ", startStatusName=" + startStatusName
				+ ", endStatusId=" + endStatusId + ", endStatusName="
				+ endStatusName + ", userId=" + userId + ", userName="
				+ userName + ", dateTrans=" + dateTrans + ", note=" + note
				+ "]";
	}

	
	
	
	
}
