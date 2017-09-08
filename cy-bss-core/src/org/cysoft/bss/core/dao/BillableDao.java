package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Billable;

public interface BillableDao {

	public long add(Billable billable) throws CyBssException;
	public List<Billable> getByInvoice(long invoiceId);
	public List<Billable> getBilledByParent(long parentId);
	public List<Billable> getByParent(long parentId);
	public void removeByParent(long parentId);
	public void unbill(long invoiceId,boolean unlink);
	public void bill(long invoiceId);
	public List<Billable> getNotLinked(long companyId, long tpCompanyId, long personId, long currencyId);
	public void link(long invoiceId, long billableId);
	public void unlink(long invoiceId, long billableId);
}

