package org.cysoft.bss.core.dao;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Purchase;

public interface PurchaseDao {
	
	public long add(Purchase productCategory) throws CyBssException;
		
}

