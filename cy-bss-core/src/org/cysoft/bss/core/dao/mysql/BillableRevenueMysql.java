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

}
