package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Invoice;

public interface PassiveInvoiceDao {
	
	public long add(Invoice invoice) throws CyBssException;
	
	public List<Invoice> find(long companyId, 
			long supplierId, String supplierCode, String supplierName,
			long personId, String personCode, String personName,
			String fromDate,String toDate) throws CyBssException;
	
	
		
}

