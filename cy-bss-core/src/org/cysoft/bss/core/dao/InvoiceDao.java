package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Invoice;

public interface InvoiceDao {
	
	public long add(Invoice invoice) throws CyBssException;
	
	public List<Invoice> find(long companyId, 
			long customerId, String customerCode, String customerName,
			long personId, String personCode, String personName,
			String fromDate,String toDate) throws CyBssException;
	
		
}

