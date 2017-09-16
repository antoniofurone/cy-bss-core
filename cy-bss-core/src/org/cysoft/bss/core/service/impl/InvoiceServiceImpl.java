package org.cysoft.bss.core.service.impl;

import java.util.List;
import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.dao.InvoiceDao;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class InvoiceServiceImpl extends CyBssServiceBase 
	implements InvoiceService 
		
{
	protected InvoiceDao passiveInvoiceDao=null;
	@Autowired
	@Qualifier("passiveInvoiceDao")
	public void setPassiveInvoiceDao(InvoiceDao passiveInvoiceDao){
			this.passiveInvoiceDao=passiveInvoiceDao;
	}
	
	protected InvoiceDao invoiceDao=null;
	@Autowired
	@Qualifier("invoiceDao")
	public void setInvoiceDao(InvoiceDao invoiceDao){
			this.invoiceDao=invoiceDao;
	}
	
	protected BillableDao billableCostDao=null;
	@Autowired
	@Qualifier("billableCostDao")
	public void setBillableCostDao(BillableDao billableCostDao){
			this.billableCostDao=billableCostDao;
	}
	
	protected BillableDao billableRevenueDao=null;
	@Autowired
	@Qualifier("billableRevenueDao")
	public void setBillableRevenueDao(BillableDao billableRevenueDao){
			this.billableRevenueDao=billableRevenueDao;
	}
	
	
	@Override
	public long add(Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		if (invoice.getInvoiceType().equals(Invoice.TYPE_PASSIVE))
			return passiveInvoiceDao.add(invoice);
		else
			return invoiceDao.add(invoice);
	}

	@Override
	public List<Invoice> find(String invoiceType, int number,int year,long companyId, long tpCompanyId, String tpCompanyCode,
			String tpCompanyName, long personId, String personCode, String personName, String attrName,
			String attrValue, String fromDate, String toDate) throws CyBssException {
		// TODO Auto-generated method stub
		if (invoiceType.equals(Invoice.TYPE_PASSIVE))
			return passiveInvoiceDao.find(number,year,companyId,tpCompanyId,tpCompanyCode,tpCompanyName,
					personId,personCode,personName,
					fromDate,toDate);
		else
			return invoiceDao.find(number,year,companyId,tpCompanyId,tpCompanyCode,tpCompanyName,
					personId,personCode,personName,
					fromDate,toDate);
	}

	@Override
	public Invoice get(String invoiceType,long id) {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
		
		Invoice invoice=_invoiceDao.get(id);
		if (invoice!=null)
			invoice.setBillables(_billableDao.getByInvoice(invoice.getId()));
		
		return invoice;
	}

	@Override
	public void remove(final String invoiceType,final long id) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
		
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				_billableDao.unbill(id,true);
				
				try {
				if (invoice.isClosed())
					_invoiceDao.cancel(id);
				
				else
					_invoiceDao.remove(id);
			
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});
		
	}


	@Override
	public void lock(String invoiceType, final long id) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
		
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				_billableDao.bill(id);
				try {
					_invoiceDao.lock(id);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});

	}

	@Override
	public void unlock(String invoiceType, final long id) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
		
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				_billableDao.unbill(id,false);
				try {
					_invoiceDao.unlock(id);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});
	}
	

	@Override
	public List<Billable> getBillables(String invoiceType, long id) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
		
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		return _billableDao.getNotLinked(invoice.getCompanyId(), invoice.getTpCompanyId(), 
				invoice.getPersonId(), invoice.getCurrencyId());
	}


	@Override
	public void linkBillable(final String invoiceType, final long id, final long billableId) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
		
				try {
					final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
					
					_billableDao.link(id, billableId);
					_update(invoiceType,invoice);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});
	
	}

	@Override
	public void unLinkBillable(final String invoiceType, final long id, final long billableId) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
		
				try {
					final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
					
					_billableDao.unlink(id, billableId);
					_update(invoiceType,invoice);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});
	
	}

	
	private void _update(String invoiceType,Invoice invoice) throws CyBssException{
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final BillableDao _billableDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?billableCostDao:billableRevenueDao;
		
		invoice.setAmount(0);
		invoice.setVatAmount(0);
		invoice.setTotAmount(0);
		
		List<Billable> billables=_billableDao.getByInvoice(invoice.getId());
		for (Billable billable:billables){
			if (invoice.getCurrencyId()!=billable.getCurrencyId())
				throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_CURRENCY_DIFF));
			
			invoice.setAmount(invoice.getAmount()+billable.getAmount());
			invoice.setVatAmount(invoice.getVatAmount()+billable.getVatAmount());
			invoice.setTotAmount(invoice.getAmount()+invoice.getVatAmount());
		}
		
		_invoiceDao.update(invoice);
		
	}


	@Override
	public void update(long id, Invoice invoice) throws CyBssException {
		// TODO Auto-generated method stub
		invoice.setId(id);
		_update(invoice.getInvoiceType(),invoice);
	}


	@Override
	public void updateAmounts(String invoiceType, long id) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		_update(invoiceType,invoice);
	}


	@Override
	public void updateNumber(String invoiceType,long id,int invoiceNumber) throws CyBssException {
		// TODO Auto-generated method stub
		final InvoiceDao _invoiceDao=invoiceType.equals(Invoice.TYPE_PASSIVE)?passiveInvoiceDao:invoiceDao;
		final Invoice invoice=_invoiceDao.get(id);
		if (invoice==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		_invoiceDao.updateNumber(id, invoiceNumber);
	}
	
}
