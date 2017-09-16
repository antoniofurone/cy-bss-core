package org.cysoft.bss.core.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommercialTransaction {
	
	private static final Logger logger = LoggerFactory.getLogger(CommercialTransaction.class);
	
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

	private long tpCompanyId;
	private String tpCompanyCode="";
	private String tpCompanyName="";
	
	protected long getTpCompanyId() {
		return tpCompanyId;
	}
	protected void setTpCompanyId(long tpCompanyId) {
		this.tpCompanyId = tpCompanyId;
	}
	protected String getTpCompanyCode() {
		return tpCompanyCode;
	}
	protected void setTpCompanyCode(String tpCompanyCode) {
		this.tpCompanyCode = tpCompanyCode;
	}
	protected String getTpCompanyName() {
		return tpCompanyName;
	}
	protected void setTpCompanyName(String tpCompanyName) {
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
	
	public final static String TRANSACTION_TYPE_BILLABLE="B";
	public final static String TRANSACTION_TYPE_NO="N";
	public final static String TRANSACTION_TYPE_HYPOTESIS="H";
	
	private String tacitRenewal="";
	public String getTacitRenewal() {
		return tacitRenewal;
	}
	public void setTacitRenewal(String tacitRenewal) {
		this.tacitRenewal = tacitRenewal;
	}
	
	private String transactionType="";
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	
	private PriceComponent component=null;
	public PriceComponent getComponent() {
		return component;
	}
	public void setComponent(PriceComponent component) {
		this.component = component;
	}
	
	private int noBilled;
	public int getNoBilled() {
		return noBilled;
	}
	public void setNoBilled(int noBilled) {
		this.noBilled = noBilled;
	}
	
	@Override
	public String toString() {
		return "CommercialTransaction [id=" + id + ", companyId=" + companyId + ", companyCode=" + companyCode
				+ ", companyName=" + companyName + ", productId=" + productId + ", productName=" + productName
				+ ", tpCompanyId=" + tpCompanyId + ", tpCompanyCode=" + tpCompanyCode + ", tpCompanyName="
				+ tpCompanyName + ", personId=" + personId + ", personCode=" + personCode + ", personFirstName="
				+ personFirstName + ", personSecondName=" + personSecondName + ", componentId=" + componentId
				+ ", componentCode=" + componentCode + ", componentName=" + componentName + ", componentTypeCode="
				+ componentTypeCode + ", componentTypeName=" + componentTypeName + ", qtyUmId=" + qtyUmId
				+ ", qtyUmSimbol=" + qtyUmSimbol + ", qty=" + qty + ", frequencyId=" + frequencyId + ", frequencyName="
				+ frequencyName + ", currencyId=" + currencyId + ", currencyCode=" + currencyCode + ", currencyName="
				+ currencyName + ", price=" + price + ", priceTot=" + priceTot + ", amount=" + amount + ", vat=" + vat
				+ ", vatAmount=" + vatAmount + ", date=" + date + ", dateStart=" + dateStart + ", dateEnd=" + dateEnd
				+ ", updateDate=" + updateDate + ", tacitRenewal=" + tacitRenewal + ", transactionType="
				+ transactionType + ", component=" + component + "]";
	}
	
	public void calcAmounts(){
		
		logger.info("CommercialTransaction.calc() >>>");
		double amountTot=0;
		
		if (priceTot!=0)
			price=priceTot*100/(100+vat);
		else
			priceTot=price+price*vat/100;
	
		amount=price;
		amountTot=priceTot;
			
		
		if (component!=null && component.getPriceType().getCode().equals(PriceType.TYPE_USG)){
			if (component.getCode().equals(PriceComponent.CODE_USG_QXP)){
				amount=qty*price;
				amountTot=qty*priceTot;
				}
		}
			
		vatAmount=amountTot-amount;
		logger.info("CommercialTransaction.calc() <<< ");
	}
}
