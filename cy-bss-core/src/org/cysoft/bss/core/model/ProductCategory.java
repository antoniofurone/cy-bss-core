package org.cysoft.bss.core.model;


public class ProductCategory {
	public static final String ENTITY_NAME="ProductCategory";
	
	private long id;
	private String name;
	private double vat;
	private long metricId;
	private String metricName;
	private String description;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public long getMetricId() {
		return metricId;
	}
	public void setMetricId(long metricId) {
		this.metricId = metricId;
	}
	public String getMetricName() {
		return metricName;
	}
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "ProductCategory [id=" + id + ", name=" + name + ", vat=" + vat + ", metricId=" + metricId
				+ ", metricName=" + metricName + ", description=" + description + "]";
	}
	
	
}
