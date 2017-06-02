package org.cysoft.bss.core.model;

public class MetricScale {
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private long metricId;
	public long getMetricId() {
		return metricId;
	}
	public void setMetricId(long metricId) {
		this.metricId = metricId;
	}
	
	private String metricName="";
	public String getMetricName() {
		return metricName;
	}
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	
	private String simbol="";
	public String getSimbol() {
		return simbol;
	}
	public void setSimbol(String simbol) {
		this.simbol = simbol;
	}
	
	private double scale=0.0d;
	public double getScale() {
		return scale;
	}
	public void setScale(double scale) {
		this.scale = scale;
	}
	
	
	@Override
	public String toString() {
		return "MetricScale [id=" + id + ", name=" + name + ", metricId=" + metricId + ", metricName=" + metricName
				+ ", simbol=" + simbol + ", scale=" + scale + "]";
	}
	
		
}
