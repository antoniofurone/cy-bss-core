package org.cysoft.bss.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.dao.SaleDao;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.BillableRevenue;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.model.Sale;
import org.cysoft.bss.core.service.InvoiceService;
import org.cysoft.bss.core.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class SaleServiceImpl extends CyBssServiceBase 
implements SaleService {

	protected SaleDao saleDao=null;
	@Autowired
	public void setSaleDao(SaleDao saleDao){
			this.saleDao=saleDao;
	}
	
	protected BillableDao billableRevenueDao=null;
	@Autowired
	@Qualifier("billableRevenueDao")
	public void setBillableRevenueDao(BillableDao billableRevenueDao){
			this.billableRevenueDao=billableRevenueDao;
	}
	
	protected ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}
	
	protected  FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	protected InvoiceService invoiceService=null;
	@Autowired
	public void setInvoiceService(InvoiceService invoiceService){
			this.invoiceService=invoiceService;
	}
	
	@Override
	public long add(final Sale sale) throws CyBssException {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		Long id=txTemplate.execute(new TransactionCallback<Long>(){

			@Override
			public Long doInTransaction(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				long id;
				try {
					id = saleDao.add(sale);
					sale.setId(id);
					if (sale.getTransactionType().equals(Sale.TRANSACTION_TYPE_BILLABLE))
						addBillable(sale);
			
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
				return id;
			}

		});
		
		return id;
	}

	@Override
	public List<Sale> find(long companyId, long productId, String productName, 
			long customerId, String customerCode,String customerName, 
			long personId, String personCode, String personName, String attrName, String attrValue,
			String fromDate, String toDate) throws CyBssException {
		// TODO Auto-generated method stub
		return saleDao.find(companyId, productId, productName, customerId, customerCode, customerName, personId, personCode, personName, attrName, attrValue, fromDate, toDate);

	}

	@Override
	public void update(final long id, final Sale sale) throws CyBssException {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				
				try {
					
					List<Billable> billables=billableRevenueDao.getByParent(id);
					List<Long> invoiceIds=new ArrayList<Long>();
					for(Billable billable:billables){
						if (!billable.isBilled() && billable.isLinkedToInvoice())
							if (invoiceIds.contains(billable.getInvoiceId()))
								invoiceIds.add(billable.getInvoiceId());
					}
					for(long invoiceId:invoiceIds){
						invoiceService.updateAmounts(Invoice.TYPE_ACTIVE, invoiceId);
					}
					
					billableRevenueDao.removeByParent(id);
					
					List<Billable> billedBillables=billableRevenueDao.getBilledByParent(id);
					
					for(Billable billable:billedBillables){
						
						billable.setQty(billable.getQty()*-1);
						billable.setAmount(billable.getAmount()*-1);
						billable.setVatAmount(billable.getVatAmount()*-1);
						billable.setTotAmount(billable.getTotAmount()*-1);
						
						billable.setBillableType(Billable.TYPE_CANCELLATION);
						billable.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
						
						billableRevenueDao.add(billable);
					}
					
					saleDao.update(id, sale);
					sale.setId(id);
					if (sale.getTransactionType().equals(Sale.TRANSACTION_TYPE_BILLABLE))
						addBillable(sale);
				
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});

	}

	@Override
	public Sale get(long id) {
		// TODO Auto-generated method stub
		return saleDao.get(id);
	}

	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
					
				try {
					
					List<Billable> billables=billableRevenueDao.getByParent(id);
					
					List<CyBssFile> files=fileDao.getByEntity(Sale.ENTITY_NAME,id);
					if (files!=null)
						for(CyBssFile file:files)
							fileDao.remove(file.getId());
					
					billableRevenueDao.removeByParent(id);
					objectDao.removeAttributeValues(id, Sale.ENTITY_NAME);
					saleDao.remove(id);
			
					List<Long> invoiceIds=new ArrayList<Long>();
					for(Billable billable:billables){
						if (!billable.isBilled() && billable.isLinkedToInvoice())
							if (!invoiceIds.contains(billable.getInvoiceId()))
								invoiceIds.add(billable.getInvoiceId());
					}
					
					for(long invoiceId:invoiceIds){
						invoiceService.updateAmounts(Invoice.TYPE_ACTIVE, invoiceId);
					}
				
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});

	}

	
	private void addBillable(Sale sale) throws CyBssException {
		
		BillableRevenue billableRevenue=new BillableRevenue();
		
		billableRevenue.setParentId(sale.getId());
		billableRevenue.setCompanyId(sale.getCompanyId());
		billableRevenue.setProductId(sale.getProductId());
		billableRevenue.setCustomerId(sale.getCustomerId());
		billableRevenue.setPersonId(sale.getPersonId());
		
		billableRevenue.setQty(sale.getQty());
		billableRevenue.setQtyUmId(sale.getQtyUmId());
		billableRevenue.setCurrencyId(sale.getCurrencyId());
		
		billableRevenue.setPrice(sale.getPrice());
		billableRevenue.setAmount(sale.getAmount());
		billableRevenue.setVat(sale.getVat());
		billableRevenue.setVatAmount(sale.getVatAmount());
		billableRevenue.setTotAmount(sale.getAmount()+sale.getVatAmount());
		
		billableRevenue.setDateStart(sale.getDate());
		billableRevenue.setDateEnd(sale.getDate());
		billableRevenue.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
		
		billableRevenue.setComponentId(sale.getComponentId());
		billableRevenue.setBillableType(Billable.TYPE_ACTUAL);
		
		billableRevenueDao.add(billableRevenue);
		
	}

	@Override
	public List<Billable> getBillables(long id) throws CyBssException {
		// TODO Auto-generated method stub
		return billableRevenueDao.getByParent(id);
	}
}
