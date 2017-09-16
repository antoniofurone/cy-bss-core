package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.Purchase;

public interface PurchaseService {
	
	public long add(Purchase purchase) throws CyBssException;
	public List<Purchase> find(long companyId, long productId, String productName,
			long supplierId, String supplierCode, String supplierName,
			long personId, String personCode, String personName,
			String attrName, String attrValue,String fromDate,String toDate) throws CyBssException;
	
	public void update(long id,Purchase purchase) throws CyBssException;
	public Purchase get(long id);
	public void remove(long id) throws CyBssException;
	public List<Billable> getBillables(long id) throws CyBssException;
	
}
