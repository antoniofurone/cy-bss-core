package org.cysoft.bss.core.model;

public abstract class Billable {

	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private long parentId;
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	private long invoiceId;
	public long getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(long invoiceId) {
		this.invoiceId = invoiceId;
	}
	public boolean isLinkedToInvoice(){
		return invoiceId!=0?true:false;
	}

	private long companyId;
	private String companyCode="";
	private String companyName="";
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	private long productId;
	private String productName;
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}

	private long tpCompanyId;
	private String tpCompanyCode="";
	private String tpCompanyName="";
	
	public long getTpCompanyId() {
		return tpCompanyId;
	}
	public void setTpCompanyId(long tpCompanyId) {
		this.tpCompanyId = tpCompanyId;
	}
	public String getTpCompanyCode() {
		return tpCompanyCode;
	}
	public void setTpCompanyCode(String tpCompanyCode) {
		this.tpCompanyCode = tpCompanyCode;
	}
	public String getTpCompanyName() {
		return tpCompanyName;
	}
	public void setTpCompanyName(String tpCompanyName) {
		this.tpCompanyName = tpCompanyName;
	}
	
	private long personId;
	private String personCode="";
	private String personFirstName="";
	private String personSecondName="";
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public String getPersonCode() {
		return personCode;
	}
	public void setPersonCode(String personCode) {
		this.personCode = personCode;
	}
	public String getPersonFirstName() {
		return personFirstName;
	}
	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}
	public String getPersonSecondName() {
		return personSecondName;
	}
	public void setPersonSecondName(String personSecondName) {
		this.personSecondName = personSecondName;
	}

	private long componentId;
	private String componentCode="";
	private String componentName="";
	private String componentTypeCode="";
	private String componentTypeName="";
	public long getComponentId() {
		return componentId;
	}
	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}
	public String getComponentCode() {
		return componentCode;
	}
	public void setComponentCode(String componentCode) {
		this.componentCode = componentCode;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getComponentTypeCode() {
		return componentTypeCode;
	}
	public void setComponentTypeCode(String componentTypeCode) {
		this.componentTypeCode = componentTypeCode;
	}
	public String getComponentTypeName() {
		return componentTypeName;
	}
	public void setComponentTypeName(String componentTypeName) {
		this.componentTypeName = componentTypeName;
	}

	private long qtyUmId;
	private String qtyUmSimbol="";
	private double qty;
	public long getQtyUmId() {
		return qtyUmId;
	}
	public void setQtyUmId(long qtyUmId) {
		this.qtyUmId = qtyUmId;
	}
	public String getQtyUmSimbol() {
		return qtyUmSimbol;
	}
	public void setQtyUmSimbol(String qtyUmSimbol) {
		this.qtyUmSimbol = qtyUmSimbol;
	}
	public double getQty() {
		return qty;
	}
	public void setQty(double qty) {
		this.qty = qty;
	}

	private long currencyId;
	private String currencyCode="";
	private String currencyName="";
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	public String getCurrencyCode() {
		return currencyCode;
	}
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	public String getCurrencyName() {
		return currencyName;
	}
	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	private double price;
	private double priceTot;
	private double amount;
	private double vat;
	private double vatAmount;
	private double totAmount;
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getPriceTot() {
		return priceTot;
	}
	public void setPriceTot(double priceTot) {
		this.priceTot = priceTot;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	public double getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(double totAmount) {
		this.totAmount = totAmount;
	}

	private String date;
	private String dateStart;
	private String dateEnd;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDateStart() {
		return dateStart;
	}
	public void setDateStart(String dateStart) {
		this.dateStart = dateStart;
	}
	public String getDateEnd() {
		return dateEnd;
	}
	public void setDateEnd(String dateEnd) {
		this.dateEnd = dateEnd;
	}

	private String updateDate="";
	public String getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
	
	
	public static final String TYPE_ACTUAL="A";
	public static final String TYPE_CANCELLATION="C";

	private String billableType="";
	public String getBillableType() {
		return billableType;
	}
	public void setBillableType(String billableType) {
		this.billableType = billableType;
	}
	
	private String billed="";
	public String getBilled() {
		return billed;
	}
	public void setBilled(String billed) {
		this.billed = billed;
	}
	public boolean isBilled(){
		return billed.equalsIgnoreCase("Y")?true:false;
	}
	
	
	private PriceComponent component=null;
	public PriceComponent getComponent() {
		return component;
	}
	public void setComponent(PriceComponent component) {
		this.component = component;
	}
	@Override
	public String toString() {
		return "Billable [id=" + id + ", parentId=" + parentId + ", invoiceId=" + invoiceId + ", companyId=" + companyId
				+ ", companyCode=" + companyCode + ", companyName=" + companyName + ", productId=" + productId
				+ ", productName=" + productName + ", tpCompanyId=" + tpCompanyId + ", tpCompanyCode=" + tpCompanyCode
				+ ", tpCompanyName=" + tpCompanyName + ", personId=" + personId + ", personCode=" + personCode
				+ ", personFirstName=" + personFirstName + ", personSecondName=" + personSecondName + ", componentId="
				+ componentId + ", componentCode=" + componentCode + ", componentName=" + componentName
				+ ", componentTypeCode=" + componentTypeCode + ", componentTypeName=" + componentTypeName + ", qtyUmId="
				+ qtyUmId + ", qtyUmSimbol=" + qtyUmSimbol + ", qty=" + qty + ", currencyId=" + currencyId
				+ ", currencyCode=" + currencyCode + ", currencyName=" + currencyName + ", price=" + price
				+ ", priceTot=" + priceTot + ", amount=" + amount + ", vat=" + vat + ", vatAmount=" + vatAmount
				+ ", totAmount=" + totAmount + ", date=" + date + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
				+ ", updateDate=" + updateDate + ", billableType=" + billableType + ", billed=" + billed
				+ ", component=" + component + "]";
	}
	
	
	
}
