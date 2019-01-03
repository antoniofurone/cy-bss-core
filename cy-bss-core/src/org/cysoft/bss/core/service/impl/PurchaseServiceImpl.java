package org.cysoft.bss.core.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.dao.PurchaseDao;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;
import org.cysoft.bss.core.model.Purchase;
import org.cysoft.bss.core.service.PurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class PurchaseServiceImpl extends CTServiceBase
	implements PurchaseService 
		
{

	protected PurchaseDao purchaseDao=null;
	@Autowired
	public void setPurchaseDao(PurchaseDao purchaseDao){
			this.purchaseDao=purchaseDao;
	}
	
	@Autowired
	@Qualifier("billableCostDao")
	public void setBillableCostDao(BillableDao billableCostDao){
			this.billableDao=billableCostDao;
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
					calcBillable(purchase,Invoice.TYPE_PASSIVE,false);
					
					updateOld(purchase);
					
					addToServerQueue(purchase.getId(), Purchase.ENTITY_NAME);
					
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
					purchaseDao.update(id, purchase);
					purchase.setId(id);
					calcBillable(purchase,Invoice.TYPE_PASSIVE,false);
					
					updateOld(purchase);
					
					addToServerQueue(purchase.getId(), Purchase.ENTITY_NAME);
					
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
					
					List<CyBssFile> files=fileDao.getByEntity(Purchase.ENTITY_NAME,id);
					if (files!=null)
						for(CyBssFile file:files)
							fileDao.remove(file.getId());
					
					Purchase purchase=new Purchase();
					purchase.setId(id);
					calcBillable(purchase,Invoice.TYPE_PASSIVE,true);
					
					objectDao.removeAttributeValues(id, Purchase.ENTITY_NAME);
					purchaseDao.remove(id);
			
					addToServerQueue(id, Purchase.ENTITY_NAME);
				
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});
	}
	
	private void updateOld(Purchase purchase) throws CyBssException {
		
		if (purchase.getOldId()!=0){
			Purchase oldPurchase=purchaseDao.get(purchase.getOldId());
			if (oldPurchase==null)
				throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_PURCHASE_OLD_NOT_FOUND));
			
			PriceComponent component=priceService.getPriceComponent(oldPurchase.getComponentId());
			if (component==null)
				throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
			
			oldPurchase.setComponent(component);
			
			if (purchase.getComponent().getPriceType().getCode().equals(PriceType.TYPE_RC) && 
				oldPurchase.getComponent().getPriceType().getCode().equals(purchase.getComponent().getPriceType().getCode())){
			
				Date dateStart;
				try {
					dateStart = CyBssUtility.tryStringToDate(purchase.getDateStart());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				Date dateClose=CyBssUtility.addDayToDate(dateStart, -1);
				oldPurchase.setDateClose(CyBssUtility.dateToString(dateClose, CyBssUtility.DATE_yyyy_MM_dd));
				oldPurchase.setNewId(purchase.getId());
				
				calcBillable(oldPurchase,Invoice.TYPE_PASSIVE,false);
				purchaseDao.update(oldPurchase.getId(), oldPurchase);
				
				addToServerQueue(oldPurchase.getId(), Purchase.ENTITY_NAME);
			}
		}

	}

	@Override
	public List<Billable> getBillables(long id) throws CyBssException {
		// TODO Auto-generated method stub
		return billableDao.getByParent(id);
	}
	
}
