package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.BillableCost;

public interface BillableCostDao {

	public long add(BillableCost billableCost) throws CyBssException;
	public List<BillableCost> getUnbilledBySupplier(long supplierId) throws CyBssException;
	public List<BillableCost> getUnbilledByPerson(long personId) throws CyBssException;
	public List<BillableCost> getBilled(long purchaseId) throws CyBssException;
	public BillableCost get(long id);
	public void remove(long id) throws CyBssException;
}

