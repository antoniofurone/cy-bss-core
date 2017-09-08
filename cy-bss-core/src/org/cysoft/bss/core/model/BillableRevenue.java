package org.cysoft.bss.core.model;

public class BillableRevenue extends Billable {
	
	public static final String ENTITY_NAME="BillableRevenue";
	
	public long getCustomerId() {
		return getTpCompanyId();
	}
	public void setCustomerId(long CustomerId) {
		setTpCompanyId(CustomerId);
	}
	
	public String getCustomerCode() {
		return getTpCompanyCode();
	}
	public void setCustomerCode(String CustomerCode) {
		setTpCompanyCode(CustomerCode);
	}
	
	public String getCustomerName() {
		return getTpCompanyName();
	}
	public void setCustomerName(String CustomerName) {
		setTpCompanyName(CustomerName);
	}
	@Override
	public String toString() {
		return "BillableRevenue [getId()=" + getId() + ", getParentId()=" + getParentId() + ", getInvoiceId()="
				+ getInvoiceId() + ", isLinkedToInvoice()=" + isLinkedToInvoice() + ", getCompanyId()=" + getCompanyId()
				+ ", getCompanyCode()=" + getCompanyCode() + ", getCompanyName()=" + getCompanyName()
				+ ", getProductId()=" + getProductId() + ", getProductName()=" + getProductName()
				+ ", getTpCompanyId()=" + getTpCompanyId() + ", getTpCompanyCode()=" + getTpCompanyCode()
				+ ", getTpCompanyName()=" + getTpCompanyName() + ", getPersonId()=" + getPersonId()
				+ ", getPersonCode()=" + getPersonCode() + ", getPersonFirstName()=" + getPersonFirstName()
				+ ", getPersonSecondName()=" + getPersonSecondName() + ", getComponentId()=" + getComponentId()
				+ ", getComponentCode()=" + getComponentCode() + ", getComponentName()=" + getComponentName()
				+ ", getComponentTypeCode()=" + getComponentTypeCode() + ", getComponentTypeName()="
				+ getComponentTypeName() + ", getQtyUmId()=" + getQtyUmId() + ", getQtyUmSimbol()=" + getQtyUmSimbol()
				+ ", getQty()=" + getQty() + ", getCurrencyId()=" + getCurrencyId() + ", getCurrencyCode()="
				+ getCurrencyCode() + ", getCurrencyName()=" + getCurrencyName() + ", getPrice()=" + getPrice()
				+ ", getPriceTot()=" + getPriceTot() + ", getAmount()=" + getAmount() + ", getVat()=" + getVat()
				+ ", getVatAmount()=" + getVatAmount() + ", getTotAmount()=" + getTotAmount() + ", getDate()="
				+ getDate() + ", getDateStart()=" + getDateStart() + ", getDateEnd()=" + getDateEnd()
				+ ", getUpdateDate()=" + getUpdateDate() + ", getBillableType()=" + getBillableType() + ", getBilled()="
				+ getBilled() + ", isBilled()=" + isBilled() + ", getComponent()=" + getComponent() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

	
}
