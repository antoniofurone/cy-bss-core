package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Billable;

public interface BillableDao {

	public long add(Billable billable) throws CyBssException;
	public List<Billable> getByInvoice(long invoiceId);
	public void removeByPurchase(long purchaseId);
	public void unbill(long invoiceId);
	public void bill(long invoiceId);
}

