package org.cysoft.bss.core.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.dao.SaleDao;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;
import org.cysoft.bss.core.model.Sale;
import org.cysoft.bss.core.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class SaleServiceImpl extends CTServiceBase 
implements SaleService {

	protected SaleDao saleDao=null;
	@Autowired
	public void setSaleDao(SaleDao saleDao){
			this.saleDao=saleDao;
	}
	
	@Autowired
	@Qualifier("billableRevenueDao")
	public void setBillableRevenueDao(BillableDao billableRevenueDao){
			this.billableDao=billableRevenueDao;
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
					calcBillable(sale,Invoice.TYPE_ACTIVE,false);
					
					updateOld(sale);
					
					addToServerQueue(sale.getId(), Sale.ENTITY_NAME);
					
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
			
					sale.setId(id);
					saleDao.update(id, sale);
					calcBillable(sale,Invoice.TYPE_ACTIVE,false);
					
					updateOld(sale);

					addToServerQueue(sale.getId(), Sale.ENTITY_NAME);
					
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
					
					List<CyBssFile> files=fileDao.getByEntity(Sale.ENTITY_NAME,id);
					if (files!=null)
						for(CyBssFile file:files)
							fileDao.remove(file.getId());
					
					Sale sale=new Sale();
					sale.setId(id);
					calcBillable(sale,Invoice.TYPE_ACTIVE,true);
					
					objectDao.removeAttributeValues(id, Sale.ENTITY_NAME);
					saleDao.remove(id);
			
					addToServerQueue(id, Sale.ENTITY_NAME);
				
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});

	}
	
	private void updateOld(Sale sale) throws CyBssException {
	
		if (sale.getOldId()!=0){
			Sale oldSale=saleDao.get(sale.getOldId());
			if (oldSale==null)
				throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_SALE_OLD_NOT_FOUND));
			
			PriceComponent component=priceService.getPriceComponent(oldSale.getComponentId());
			if (component==null)
				throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
			
			oldSale.setComponent(component);
			
			if (sale.getComponent().getPriceType().getCode().equals(PriceType.TYPE_RC) && 
				oldSale.getComponent().getPriceType().getCode().equals(sale.getComponent().getPriceType().getCode())){
				
				Date dateStart;
				try {
					dateStart = CyBssUtility.tryStringToDate(sale.getDateStart());
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				Date dateClose=CyBssUtility.addDayToDate(dateStart, -1);
				oldSale.setDateClose(CyBssUtility.dateToString(dateClose, CyBssUtility.DATE_yyyy_MM_dd));
				oldSale.setNewId(sale.getId());
				
				calcBillable(oldSale,Invoice.TYPE_ACTIVE,false);
				saleDao.update(oldSale.getId(), oldSale);
				
				addToServerQueue(oldSale.getId(), Sale.ENTITY_NAME);
			}
		}

	}

	@Override
	public List<Billable> getBillables(long id) throws CyBssException {
		// TODO Auto-generated method stub
		return billableDao.getByParent(id);
	}
	
		
		
}
