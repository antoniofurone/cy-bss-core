package org.cysoft.bss.core.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.BillableCostDao;
import org.cysoft.bss.core.dao.InvoiceDao;
import org.cysoft.bss.core.dao.PassiveInvoiceDao;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceServiceImpl extends CyBssServiceImpl 
	implements InvoiceService 
		
{

	protected PassiveInvoiceDao passiveInvoiceDao=null;
	@Autowired
	public void setPassiveInvoiceDao(PassiveInvoiceDao passiveInvoiceDao){
			this.passiveInvoiceDao=passiveInvoiceDao;
	}
	
	protected InvoiceDao invoiceDao=null;
	@Autowired
	public void setInvoiceDao(InvoiceDao invoiceDao){
			this.invoiceDao=invoiceDao;
	}
	
	protected BillableCostDao billableCostDao=null;
	@Autowired
	public void setBillableCostDao(BillableCostDao billableCostDao){
			this.billableCostDao=billableCostDao;
	}
	
	@Override
	public long add(Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		if (invoice.getInvoiceType().equals(Invoice.TYPE_PASSIVE))
			return passiveInvoiceDao.add(invoice);
		else
			return 0;
	}

	@Override
	public List<Invoice> find(String invoiceType, long companyId, long tpCompanyId, String tpCompanyCode,
			String tpCompanyName, long personId, String personCode, String personName, String attrName,
			String attrValue, String fromDate, String toDate) throws CyBssException {
		// TODO Auto-generated method stub
		if (invoiceType.equals(Invoice.TYPE_PASSIVE))
			return passiveInvoiceDao.find(companyId,tpCompanyId,tpCompanyCode,tpCompanyName,
					personId,personCode,personName,
					fromDate,toDate);
		else
			return new ArrayList<Invoice>();
	}

	
}
