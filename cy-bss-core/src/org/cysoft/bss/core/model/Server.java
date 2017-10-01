package org.cysoft.bss.core.model;

public class Server {

	public static final String STATUS_STOPPED="Stopped";
	public static final String STATUS_PAUSE="Pause";
	public static final String STATUS_RUNNING="Running";
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private String nodeId;
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	
	private String status;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	private String ip;
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	private String machine;
	public String getMachine() {
		return machine;
	}
	public void setMachine(String machine) {
		this.machine = machine;
	}
	
	private String startDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	private String stopDate;
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}
	@Override
	public String toString() {
		return "Server [id=" + id + ", nodeId=" + nodeId + ", status=" + status + ", ip=" + ip + ", machine=" + machine
				+ ", startDate=" + startDate + ", stopDate=" + stopDate + "]";
	}
	
	
}
