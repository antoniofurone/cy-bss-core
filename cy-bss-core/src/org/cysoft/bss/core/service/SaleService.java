package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.Sale;

public interface SaleService {
	
	public long add(Sale sale) throws CyBssException;
	public List<Sale> find(long companyId, long productId, String productName,
			long customerId, String customerCode, String customerName,
			long personId, String personCode, String personName,
			String attrName, String attrValue,String fromDate,String toDate) throws CyBssException;
	
	public void update(long id,Sale sale) throws CyBssException;
	public Sale get(long id);
	public void remove(long id) throws CyBssException;
	public List<Billable> getBillables(long id) throws CyBssException;
	
	

}
