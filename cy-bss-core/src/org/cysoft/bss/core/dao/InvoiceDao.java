package org.cysoft.bss.core.dao;

import org.cysoft.bss.core.common.CyBssException;

public interface InvoiceDao {
	
	public long add(long companyId, long tpCompanyId, long personId, 
			String invoiceType) throws CyBssException;
	
		
}

