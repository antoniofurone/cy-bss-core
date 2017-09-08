package org.cysoft.bss.core.model;

public class Sale extends CommercialTransaction{
	public static final String ENTITY_NAME="Sale";

	public long getCustomerId() {
		return getTpCompanyId();
	}
	public void setCustomerId(long customerId) {
		setTpCompanyId(customerId);
	}
	
	public String getCustomerCode() {
		return getTpCompanyCode();
	}
	public void setCustomerCode(String customerCode) {
		setTpCompanyCode(customerCode);
	}
	
	public String getCustomerName() {
		return getTpCompanyName();
	}
	public void setCustomerName(String customerName) {
		setTpCompanyName(customerName);
	}
	@Override
	public String toString() {
		return "Sale [getId()=" + getId() + ", getCompanyId()=" + getCompanyId() + ", getCompanyCode()="
				+ getCompanyCode() + ", getCompanyName()=" + getCompanyName() + ", getProductId()=" + getProductId()
				+ ", getProductName()=" + getProductName() + ", getTpCompanyId()=" + getTpCompanyId()
				+ ", getTpCompanyCode()=" + getTpCompanyCode() + ", getTpCompanyName()=" + getTpCompanyName()
				+ ", getPersonId()=" + getPersonId() + ", getPersonCode()=" + getPersonCode()
				+ ", getPersonFirstName()=" + getPersonFirstName() + ", getPersonSecondName()=" + getPersonSecondName()
				+ ", getComponentId()=" + getComponentId() + ", getComponentCode()=" + getComponentCode()
				+ ", getComponentName()=" + getComponentName() + ", getComponentTypeCode()=" + getComponentTypeCode()
				+ ", getComponentTypeName()=" + getComponentTypeName() + ", getQtyUmId()=" + getQtyUmId()
				+ ", getQtyUmSimbol()=" + getQtyUmSimbol() + ", getQty()=" + getQty() + ", getFrequencyId()="
				+ getFrequencyId() + ", getFrequencyName()=" + getFrequencyName() + ", getCurrencyId()="
				+ getCurrencyId() + ", getCurrencyCode()=" + getCurrencyCode() + ", getCurrencyName()="
				+ getCurrencyName() + ", getPrice()=" + getPrice() + ", getPriceTot()=" + getPriceTot()
				+ ", getAmount()=" + getAmount() + ", getVat()=" + getVat() + ", getVatAmount()=" + getVatAmount()
				+ ", getDate()=" + getDate() + ", getDateStart()=" + getDateStart() + ", getDateEnd()=" + getDateEnd()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getTacitRenewal()=" + getTacitRenewal()
				+ ", getTransactionType()=" + getTransactionType() + ", getComponent()=" + getComponent()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}
		
}
