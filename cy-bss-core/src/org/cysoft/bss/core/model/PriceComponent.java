package org.cysoft.bss.core.model;

public class PriceComponent {
	
	public static final String CODE_NRC_UT="NRC_UT";
	public static final String CODE_NRC_OFC="NRC_OFC";
	public static final String CODE_USG_QXP="USG_QXP";
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private String code="";
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}

	private String name="";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	private String description="";
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	private long priceTypeId;
	public long getPriceTypeId() {
		return priceTypeId;
	}
	public void setPriceTypeId(long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}
	
	private PriceType priceType=new PriceType();
	public PriceType getPriceType() {
		return priceType;
	}
	public void setPriceType(PriceType priceType) {
		this.priceType = priceType;
	}
	
	private long periodMetricId;
	public long getPeriodMetricId() {
		return periodMetricId;
	}
	public void setPeriodMetricId(long periodMetricId) {
		this.periodMetricId = periodMetricId;
	}

	private String periodMetricName;
	public String getPeriodMetricName() {
		return periodMetricName;
	}
	public void setPeriodMetricName(String periodMetricName) {
		this.periodMetricName = periodMetricName;
	}
	
	@Override
	public String toString() {
		return "PriceComponent [id=" + id + ", code=" + code + ", name=" + name + ", description=" + description
				+ ", priceTypeId=" + priceTypeId + ", priceType=" + priceType + ", periodMetricId=" + periodMetricId
				+ ", periodMetricName=" + periodMetricName + "]";
	}
	
	
}
