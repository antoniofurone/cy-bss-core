package org.cysoft.bss.core.model;

public class Purchase {
	public static final String ENTITY_NAME="Shopping";
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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

	private long supplierId;
	private String supplierCode="";
	private String supplierName="";
	public long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierCode() {
		return supplierCode;
	}
	public void setSupplierCode(String supplierCode) {
		this.supplierCode = supplierCode;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	private long personId;
	private String personFirstName="";
	private String personSecondName="";
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
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

	private long frequencyId;
	private String frequencyName="";
	public long getFrequencyId() {
		return frequencyId;
	}
	public void setFrequencyId(long frequencyId) {
		this.frequencyId = frequencyId;
	}
	public String getFrequencyName() {
		return frequencyName;
	}
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
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

	public final static String TACIT_RENEWAL_YES="Y";
	public final static String TACIT_RENEWAL_NO="N";
	
	public final static String PURCHASE_TYPE_BILLABLE="B";
	public final static String PURCHASE_TYPE_NO="N";
	public final static String PURCHASE_TYPE_HYPOTESIS="H";
	
	private String tacitRenewal="";
	private String purchaseType="";
	public String getTacitRenewal() {
		return tacitRenewal;
	}
	public void setTacitRenewal(String tacitRenewal) {
		this.tacitRenewal = tacitRenewal;
	}
	public String getPurchaseType() {
		return purchaseType;
	}
	public void setPurchaseType(String purchaseType) {
		this.purchaseType = purchaseType;
	}
	@Override
	public String toString() {
		return "Shopping [id=" + id + ", companyId=" + companyId + ", companyCode=" + companyCode + ", companyName="
				+ companyName + ", productId=" + productId + ", productName=" + productName + ", supplierId="
				+ supplierId + ", supplierCode=" + supplierCode + ", supplierName=" + supplierName + ", personId="
				+ personId + ", personFirstName=" + personFirstName + ", personSecondName=" + personSecondName
				+ ", componentId=" + componentId + ", componentCode=" + componentCode + ", componentName="
				+ componentName + ", componentTypeCode=" + componentTypeCode + ", componentTypeName="
				+ componentTypeName + ", qtyUmId=" + qtyUmId + ", qtyUmSimbol=" + qtyUmSimbol + ", qty=" + qty
				+ ", frequencyId=" + frequencyId + ", frequencyName=" + frequencyName + ", currencyId=" + currencyId
				+ ", currencyCode=" + currencyCode + ", currencyName=" + currencyName + ", price=" + price
				+ ", priceTot=" + priceTot + ", amount=" + amount + ", vat=" + vat + ", vatAmount=" + vatAmount
				+ ", date=" + date + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd + ", updateDate=" + updateDate
				+ ", tacitRenewal=" + tacitRenewal + ", purchaseType=" + purchaseType + "]";
	}
	
}
