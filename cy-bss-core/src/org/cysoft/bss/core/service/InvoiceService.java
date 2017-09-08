package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.Invoice;

public interface InvoiceService {
	
	public long add(Invoice invoice) throws CyBssException;
	
	public void update(long id,Invoice invoice) throws CyBssException;
	public void updateAmounts(String invoiceType,long id) throws CyBssException;
	public void updateNumber(String invoiceType,long id,int invoiceNumber) throws CyBssException;
	public List<Invoice> find(String invoiceType,int number,int year,long companyId, 
			long tpCompanyId, String tpCompanyCode, String tpCompanyName,
			long personId, String personCode, String personName,
			String attrName, String attrValue,String fromDate,String toDate) throws CyBssException;
	public Invoice get(String invoiceType,long id);
	public void remove(String invoiceType,long id) throws CyBssException;
	public void lock(String invoiceType,long id) throws CyBssException;
	public void unlock(String invoiceType,long id) throws CyBssException;
	public List<Billable> getBillables(String invoiceType,long id) throws CyBssException;
	public void linkBillable(String invoiceType,long id,long billableId) throws CyBssException;
	public void unLinkBillable(String invoiceType,long id,long billableId) throws CyBssException;
	
}
