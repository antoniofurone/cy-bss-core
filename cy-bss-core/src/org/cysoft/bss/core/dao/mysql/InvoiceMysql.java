package org.cysoft.bss.core.dao.mysql;


import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.InvoiceDao;
import org.cysoft.bss.core.model.Invoice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceMysql extends CyBssMysqlDao
	implements InvoiceDao{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceMysql.class);

	@Override
	public long add(Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Invoice> find(long companyId, long customerId, String customerCode, String customerName, long personId,
			String personCode, String personName, String fromDate, String toDate)
					throws CyBssException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Invoice get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(long id) throws CyBssException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel(long id) throws CyBssException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close(long id) throws CyBssException{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		
	}

		
		
}
