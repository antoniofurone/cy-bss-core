package org.cysoft.bss.core.service;


public interface InvoiceService {
	
	public long add(long companyId, long tpCompanyId, long personId, 
			String invoiceType);
	
}
