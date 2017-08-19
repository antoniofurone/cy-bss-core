package org.cysoft.bss.core.dao.mysql;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.model.Billable;

public class BillableRevenueMysql  extends CyBssMysqlDao
implements BillableDao{

	@Override
	public long add(Billable billable) throws CyBssException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Billable> getByInvoice(long invoiceId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeByPurchase(long purchaseId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unbill(long invoiceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void bill(long invoiceId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Billable> getNotLinked(long customerId, long tpCustomerId, long personId, long currencyId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void link(long invoiceId, long billableId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unlink(long invoiceId, long billableId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Billable> getBilledByPurchase(long purchaseId) {
		// TODO Auto-generated method stub
		return null;
	}

}
