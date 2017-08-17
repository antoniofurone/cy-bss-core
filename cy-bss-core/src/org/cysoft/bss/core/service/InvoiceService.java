package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Invoice;

public interface InvoiceService {
	
	public long add(Invoice invoice) throws CyBssException;
	public List<Invoice> find(String invoiceType,long companyId, 
			long tpCompanyId, String tpCompanyCode, String tpCompanyName,
			long personId, String personCode, String personName,
			String attrName, String attrValue,String fromDate,String toDate) throws CyBssException;
	public Invoice get(String invoiceType,long id);
	public void remove(String invoiceType,long id) throws CyBssException;
	public void close(String invoiceType,long id) throws CyBssException;
}
