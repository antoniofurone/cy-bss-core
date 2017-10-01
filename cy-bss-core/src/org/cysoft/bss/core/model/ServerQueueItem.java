package org.cysoft.bss.core.model;

public class ServerQueueItem {
	
	public static final String STATUS_PENDING="Pending";
	public static final String STATUS_EXECUTED="Executed";
	public static final String STATUS_RUNNING="Running";
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private long objectId;
	public long getObjectId() {
		return objectId;
	}
	public void setObjectId(long objectId) {
		this.objectId = objectId;
	}
	
	private String objectName;
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	private long objectInstId;
	public long getObjectInstId() {
		return objectInstId;
	}
	public void setObjectInstId(long objectInstId) {
		this.objectInstId = objectInstId;
	}
	
	private long serverId;
	public long getServerId() {
		return serverId;
	}
	public void setServerId(long serverId) {
		this.serverId = serverId;
	}
	
	
	private String serverNodeId;
	public String getServerNodeId() {
		return serverNodeId;
	}
	public void setServerNodeId(String serverNodeId) {
		this.serverNodeId = serverNodeId;
	}

	private String requestedExecDate;
	public String getRequestedExecDate() {
		return requestedExecDate;
	}
	public void setRequestedExecDate(String requestedExecDate) {
		this.requestedExecDate = requestedExecDate;
	}

	private String startExecDate;
	public String getStartExecDate() {
		return startExecDate;
	}
	public void setStartExecDate(String startExecDate) {
		this.startExecDate = startExecDate;
	}
	
	private String endExecDate;
	public String getEndExecDate() {
		return endExecDate;
	}
	public void setEndExecDate(String endExecDate) {
		this.endExecDate = endExecDate;
	}
	
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	private String result;
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	@Override
	public String toString() {
		return "ServerQueueItem [id=" + id + ", objectId=" + objectId + ", objectName=" + objectName + ", objectInstId="
				+ objectInstId + ", serverId=" + serverId + ", serverNodeId=" + serverNodeId + ", requestedExecDate="
				+ requestedExecDate + ", startExecDate=" + startExecDate + ", endExecDate=" + endExecDate + ", status="
				+ status + ", result=" + result + "]";
	}
	
	
	
}
