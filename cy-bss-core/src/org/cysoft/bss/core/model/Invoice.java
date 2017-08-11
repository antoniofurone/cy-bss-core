package org.cysoft.bss.core.model;

import java.util.ArrayList;
import java.util.List;

public class Invoice {
	
	public static final String TYPE_ACTIVE="A";
	public static final String TYPE_PASSIVE="P";
	private String invoiceType="A";
	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}
	
	private long id;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	private int year;
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	
	private int number;
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	
	private String stringNumber;
	public String getStringNumber() {
		return stringNumber;
	}
	public void setStringNumber(String stringNumber) {
		this.stringNumber = stringNumber;
	}
	
	private long companyId;
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	
	private long tpCompanyId;
	public long getTpCompanyId() {
		return tpCompanyId;
	}
	public void setTpCompanyId(long tpCompanyId) {
		this.tpCompanyId = tpCompanyId;
	}
	
	private long personId;
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	
	private long currencyId;
	public long getCurrencyId() {
		return currencyId;
	}
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}
	
	private double amount;
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	private double vatAmount;
	public double getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(double vatAmount) {
		this.vatAmount = vatAmount;
	}
	
	private double totAmount;
	public double getTotAmount() {
		return totAmount;
	}
	public void setTotAmount(double totAmount) {
		this.totAmount = totAmount;
	}
	
	private String cancelled="N";
	public String getCancelled() {
		return cancelled;
	}
	public void setCancelled(String cancelled) {
		this.cancelled = cancelled;
	}

	private String note="";
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	private List<Billable> billables=new ArrayList<Billable>();
	protected List<Billable> getBillables() {
		return billables;
	}

	protected void setBillables(List<Billable> billables) {
		this.billables = billables;
	}

	@Override
	public String toString() {
		return "Invoice [invoiceType=" + invoiceType + ", id=" + id + ", date=" + date + ", year=" + year + ", number="
				+ number + ", stringNumber=" + stringNumber + ", companyId=" + companyId + ", tpCompanyId="
				+ tpCompanyId + ", personId=" + personId + ", currencyId=" + currencyId + ", amount=" + amount
				+ ", vatAmount=" + vatAmount + ", totAmount=" + totAmount + ", cancelled=" + cancelled + ", note="
				+ note + ", billables=" + billables + "]";
	}
	
	
	

	
}
