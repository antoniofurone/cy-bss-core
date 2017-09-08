package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Invoice;

public interface InvoiceDao {
	
	public long add(Invoice invoice) throws CyBssException;
	public void update(Invoice invoice) throws CyBssException;
	
	public List<Invoice> find(int number,int year,long companyId, 
			long tpCompanyId, String tpCompanyCode, String tpCompanyName,
			long personId, String personCode, String personName,
			String fromDate,String toDate) throws CyBssException;
	
	public Invoice get(long id);
	public void remove(long id) throws CyBssException;
	public void cancel(long id) throws CyBssException;
	public void lock(long id) throws CyBssException;
	public void unlock(long id) throws CyBssException;
	public void updateNumber(long id, int invoiceNumber) throws CyBssException;
	
}

