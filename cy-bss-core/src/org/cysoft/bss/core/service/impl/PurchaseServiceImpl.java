package org.cysoft.bss.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.dao.PurchaseDao;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.BillableCost;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.model.Purchase;
import org.cysoft.bss.core.service.InvoiceService;
import org.cysoft.bss.core.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PurchaseServiceImpl extends CyBssServiceBase 
	implements PurchaseService 
		
{

	protected PurchaseDao purchaseDao=null;
	@Autowired
	public void setPurchaseDao(PurchaseDao purchaseDao){
			this.purchaseDao=purchaseDao;
	}
	
	protected BillableDao billableCostDao=null;
	@Autowired
	@Qualifier("billableCostDao")
	public void setBillableCostDao(BillableDao billableCostDao){
			this.billableCostDao=billableCostDao;
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
	public long add(final Purchase purchase) throws CyBssException {
		// TODO Auto-generated method stub
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		Long id=txTemplate.execute(new TransactionCallback<Long>(){

			@Override
			public Long doInTransaction(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				long id;
				try {
					id = purchaseDao.add(purchase);
					purchase.setId(id);
					if (purchase.getTransactionType().equals(Purchase.TRANSACTION_TYPE_BILLABLE))
						addBillable(purchase);
			
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
	public List<Purchase> find(long companyId, long productId, String productName, long supplierId, String supplierCode,
			String supplierName, long personId, String personCode, String personName, String attrName, String attrValue,
			String fromDate, String toDate) throws CyBssException {
		// TODO Auto-generated method stub
		return purchaseDao.find(companyId, productId, productName, supplierId, supplierCode, supplierName, personId, personCode, personName, attrName, attrValue, fromDate, toDate);
	}


	@Override
	public void update(final long id, final Purchase purchase) throws CyBssException {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				
				try {
					
					List<Billable> billables=billableCostDao.getByParent(id);
					List<Long> invoiceIds=new ArrayList<Long>();
					for(Billable billable:billables){
						if (!billable.isBilled() && billable.isLinkedToInvoice())
							if (invoiceIds.contains(billable.getInvoiceId()))
								invoiceIds.add(billable.getInvoiceId());
					}
					for(long invoiceId:invoiceIds){
						invoiceService.updateAmounts(Invoice.TYPE_PASSIVE, invoiceId);
					}
					
					billableCostDao.removeByParent(id);
					
					List<Billable> billedBillables=billableCostDao.getBilledByParent(id);
					
					for(Billable billable:billedBillables){
						
						billable.setQty(billable.getQty()*-1);
						billable.setAmount(billable.getAmount()*-1);
						billable.setVatAmount(billable.getVatAmount()*-1);
						billable.setTotAmount(billable.getTotAmount()*-1);
						
						billable.setBillableType(Billable.TYPE_CANCELLATION);
						billable.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
						
						billableCostDao.add(billable);
					}
					
					purchaseDao.update(id, purchase);
					purchase.setId(id);
					if (purchase.getTransactionType().equals(Purchase.TRANSACTION_TYPE_BILLABLE))
						addBillable(purchase);
				
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		});
				
	}


	@Override
	public Purchase get(long id) {
		// TODO Auto-generated method stub
		return purchaseDao.get(id);
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
					
					List<Billable> billables=billableCostDao.getByParent(id);
					
					List<CyBssFile> files=fileDao.getByEntity(Purchase.ENTITY_NAME,id);
					if (files!=null)
						for(CyBssFile file:files)
							fileDao.remove(file.getId());
					
					billableCostDao.removeByParent(id);
					objectDao.removeAttributeValues(id, Purchase.ENTITY_NAME);
					purchaseDao.remove(id);
			
					List<Long> invoiceIds=new ArrayList<Long>();
					for(Billable billable:billables){
						if (!billable.isBilled() && billable.isLinkedToInvoice())
							if (!invoiceIds.contains(billable.getInvoiceId()))
								invoiceIds.add(billable.getInvoiceId());
					}
					for(long invoiceId:invoiceIds){
						invoiceService.updateAmounts(Invoice.TYPE_PASSIVE, invoiceId);
					}
				
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});
	}

	
	private void addBillable(Purchase purchase) throws CyBssException {
		
		BillableCost billableCost=new BillableCost();
		
		billableCost.setParentId(purchase.getId());
		billableCost.setCompanyId(purchase.getCompanyId());
		billableCost.setProductId(purchase.getProductId());
		billableCost.setSupplierId(purchase.getSupplierId());
		billableCost.setPersonId(purchase.getPersonId());
		
		billableCost.setQty(purchase.getQty());
		billableCost.setQtyUmId(purchase.getQtyUmId());
		billableCost.setCurrencyId(purchase.getCurrencyId());
		
		billableCost.setPrice(purchase.getPrice());
		billableCost.setAmount(purchase.getAmount());
		billableCost.setVat(purchase.getVat());
		billableCost.setVatAmount(purchase.getVatAmount());
		billableCost.setTotAmount(purchase.getAmount()+purchase.getVatAmount());
		
		billableCost.setDateStart(purchase.getDate());
		billableCost.setDateEnd(purchase.getDate());
		billableCost.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
		
		billableCost.setComponentId(purchase.getComponentId());
		billableCost.setBillableType(Billable.TYPE_ACTUAL);
		
		billableCostDao.add(billableCost);
		
	}


	@Override
	public List<Billable> getBillables(long id) throws CyBssException {
		// TODO Auto-generated method stub
		return billableCostDao.getByParent(id);
	}
	
}
