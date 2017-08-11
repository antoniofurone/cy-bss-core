package org.cysoft.bss.core.dao.mysql;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.InvoiceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InvoiceMysql extends CyBssMysqlDao
	implements InvoiceDao{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceMysql.class);

	@Override
	public long add(long companyId, long tpCompanyId, long personId, String invoiceType) throws CyBssException {
		// TODO Auto-generated method stub
		return 0;
	}
	
		
}
