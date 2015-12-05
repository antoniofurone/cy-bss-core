package org.cysoft.bss.core.model;

public class TicketChangeStatus {
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	private long statusId;
	public long getStatusId() {
		return statusId;
	}
	public void setStatusId(long statusId) {
		this.statusId = statusId;
	}
	private String note="";
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return "TicketChangeStatus [statusId=" + statusId + ", note=" + note
				+ "]";
	}
	
	
}
