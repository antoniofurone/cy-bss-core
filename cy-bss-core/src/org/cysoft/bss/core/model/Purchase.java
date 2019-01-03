package org.cysoft.bss.core.model;

public class Purchase extends CT{
	public static final String ENTITY_NAME="Purchase";

	public long getSupplierId() {
		return getTpCompanyId();
	}
	public void setSupplierId(long supplierId) {
		setTpCompanyId(supplierId);
	}
	
	public String getSupplierCode() {
		return getTpCompanyCode();
	}
	public void setSupplierCode(String supplierCode) {
		setTpCompanyCode(supplierCode);
	}
	
	public String getSupplierName() {
		return getTpCompanyName();
	}
	public void setSupplierName(String supplierName) {
		setTpCompanyName(supplierName);
	}
	
	@Override
	public String toString() {
		return "Purchase [getId()=" + getId() + ", getCompanyId()=" + getCompanyId() + ", getCompanyCode()="
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
