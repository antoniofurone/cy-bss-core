package org.cysoft.bss.core.model;

public class ServerCommand {
	
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
	
	private String command;
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	private long serverId;
	public long getServerId() {
		return serverId;
	}
	public void setServerId(long serverId) {
		this.serverId = serverId;
	}
	
	private String requestedExecDate;
	public String getRequestedExecDate() {
		return requestedExecDate;
	}
	public void setRequestedExecDate(String requestedExecDate) {
		this.requestedExecDate = requestedExecDate;
	}

	private String executionDate;
	public String getExecutionDate() {
		return executionDate;
	}
	public void setExecutionDate(String executionDate) {
		this.executionDate = executionDate;
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
		return "ServerCommand [id=" + id + ", command=" + command + ", serverId=" + serverId + ", requestedExecDate="
				+ requestedExecDate + ", executionDate=" + executionDate + ", status=" + status + ", result=" + result
				+ "]";
	}
	
	
}
