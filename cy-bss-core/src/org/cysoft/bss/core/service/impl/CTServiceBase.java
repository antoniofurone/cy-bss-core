package org.cysoft.bss.core.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.BillableDao;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.CT;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;
import org.cysoft.bss.core.model.Sale;
import org.cysoft.bss.core.model.ServerQueueItem;
import org.cysoft.bss.core.service.InvoiceService;
import org.cysoft.bss.core.service.PriceService;
import org.cysoft.bss.core.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class CTServiceBase extends CyBssServiceBase{
	
	protected BillableDao billableDao=null;
	
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
	
	protected PriceService priceService=null;
	@Autowired
	public void setPriceService(PriceService priceService){
			this.priceService=priceService;
	}
	
	protected ServerService serverService=null;
	@Autowired
	public void setServerService(ServerService serverService){
			this.serverService=serverService;
	}
	

	protected void calcBillable(CT ct, String invoiceType, boolean cancel) throws CyBssException {
		
		List<Billable> billables=billableDao.getByParent(ct.getId());
		
		List<Long> invoiceIds=new ArrayList<Long>();
		for(Billable billable:billables){
			if (!billable.isBilled() && billable.isLinkedToInvoice())
				if (!invoiceIds.contains(billable.getInvoiceId()))
					invoiceIds.add(billable.getInvoiceId());
		}
		
		billableDao.removeByParent(ct.getId());
		
		for(long invoiceId:invoiceIds)
			invoiceService.updateAmounts(invoiceType, invoiceId);
		
		if (cancel)
			return;
		
		List<Billable> cancelBillables=billableDao.getBilledByParent(ct.getId());
		for(Billable billable:cancelBillables){
			
			
			billable.setQty(billable.getQty()*-1);
			billable.setAmount(billable.getAmount()*-1);
			billable.setVatAmount(billable.getVatAmount()*-1);
			billable.setTotAmount(billable.getTotAmount()*-1);
			
			billable.setPrice(billable.getAmount());
			billable.setPriceTot(billable.getPrice()+billable.getVatAmount());
			billable.setVat(billable.getVatAmount()/billable.getPrice()*100);
			
			
			billable.setBillableType(Billable.TYPE_CANCELLATION);
			billable.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
		}
		
		
		List<Billable> actualBillables=getActualBillable(ct);
		List<Billable> insBillables=new ArrayList<Billable>(); 
		insBillables.addAll(cancelBillables);	
		for(Billable actBill:actualBillables){
			boolean compensed=false;
			for(Billable cancBill:cancelBillables){
				try {
					Date cancDateMax=CyBssUtility.tryStringToDate(cancBill.getDateEnd());
					Date actDateMax=CyBssUtility.tryStringToDate(actBill.getDateEnd());
					
					PriceComponent cancComponent=priceService.getPriceComponent(cancBill.getComponentId());
					if (cancComponent==null)
						throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
					
					if ((!actDateMax.after(cancDateMax) || cancComponent.getPriceType().getCode().equals(PriceType.TYPE_NRC) || cancComponent.getPriceType().getCode().equals(PriceType.TYPE_USG)) &&
						cancBill.getParentId()==actBill.getParentId() &&
						cancBill.getCompanyId()==actBill.getCompanyId() &&
						cancBill.getProductId()==actBill.getProductId() &&
						cancBill.getTpCompanyId()==actBill.getTpCompanyId() &&
						cancBill.getPersonId()==actBill.getPersonId() &&
						cancBill.getComponentId()==actBill.getComponentId() &&
						cancBill.getQtyUmId()==actBill.getQtyUmId() &&
						cancBill.getCurrencyId()==actBill.getCurrencyId()) 
						{
						
							
							cancBill.setQty(cancBill.getQty()+actBill.getQty());
							cancBill.setAmount(cancBill.getAmount()+actBill.getAmount());
							cancBill.setVatAmount(cancBill.getVatAmount()+actBill.getVatAmount());
							cancBill.setTotAmount(cancBill.getTotAmount()+actBill.getTotAmount());
							
							cancBill.setPrice(cancBill.getAmount());
							cancBill.setPriceTot(cancBill.getPrice()+cancBill.getVatAmount());
							cancBill.setVat(cancBill.getVatAmount()/cancBill.getPrice()*100);
							
						
							compensed=true;
							break;
						}
						
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new CyBssException(e);
				}
				
				
				
			}
			if (!compensed)
				insBillables.add(actBill);
		}
		
		for(Billable insBill:insBillables){
			if (!insBill.getBillableType().equals(Billable.TYPE_CANCELLATION) || 
				insBill.getTotAmount()>0.1 || insBill.getTotAmount()<-0.1)
				billableDao.add(insBill);
		}
		
		
	}
	
	private List<Billable> getActualBillable(CT ct) throws CyBssException{
		
		List<Billable> ret=new ArrayList<Billable>();
		
		if (!ct.getTransactionType().equals(Sale.TRANSACTION_TYPE_BILLABLE))
			return ret;
		
		if (ct.getComponent().getPriceType().getCode().equals(PriceType.TYPE_NRC)||
			ct.getComponent().getPriceType().getCode().equals(PriceType.TYPE_USG)) {
			
			Billable billable=new Billable();
		
			billable.setParentId(ct.getId());
			billable.setCompanyId(ct.getCompanyId());
			billable.setProductId(ct.getProductId());
			billable.setTpCompanyId(ct.getTpCompanyId());
			billable.setPersonId(ct.getPersonId());
		
			billable.setQty(ct.getQty());
			billable.setQtyUmId(ct.getQtyUmId());
			billable.setCurrencyId(ct.getCurrencyId());
		
			billable.setPrice(ct.getPrice());
			billable.setAmount(ct.getAmount());
			billable.setVat(ct.getVat());
			billable.setVatAmount(ct.getVatAmount());
			billable.setTotAmount(ct.getAmount()+ct.getVatAmount());
		
			billable.setDateStart(ct.getDate());
			billable.setDateEnd(ct.getDate());
			billable.setDate(ct.getDate());
		
		
			billable.setComponentId(ct.getComponentId());
			billable.setBillableType(Billable.TYPE_ACTUAL);
			
			ret.add(billable);
		}
		else
			if (ct.getComponent().getPriceType().getCode().equals(PriceType.TYPE_RC)){
				
				List<String> dates=null;
				Date dateStart=null;
				Date dateEnd=null;
				
				try {
					
					dateStart=CyBssUtility.tryStringToDate(ct.getDateStart());
					dateEnd=ct.getDateClose()!=null && !ct.getDateClose().equals("")?CyBssUtility.tryStringToDate(ct.getDateClose()):
						CyBssUtility.tryStringToDate(ct.getDateEnd());
					
					dates = CyBssUtility.getMonths(dateStart,dateEnd);
				
					for(String date:dates){
						Billable billable=new Billable();
						
						billable.setParentId(ct.getId());
						billable.setCompanyId(ct.getCompanyId());
						billable.setProductId(ct.getProductId());
						billable.setTpCompanyId(ct.getTpCompanyId());
						billable.setPersonId(ct.getPersonId());
					
						billable.setQty(ct.getQty());
						billable.setQtyUmId(ct.getQtyUmId());
						billable.setCurrencyId(ct.getCurrencyId());
						billable.setPrice(ct.getPrice());
						
						double ratio=1.0;
						billable.setDate(ct.getDate());
						
						if (date.equals(CyBssUtility.dateToString(dateStart, CyBssUtility.DATE_yyyyMM)) && 
							date.equals(CyBssUtility.dateToString(dateEnd, CyBssUtility.DATE_yyyyMM))) {
							
							billable.setDateStart(ct.getDateStart());
							billable.setDateEnd(ct.getDateEnd());
							
							int lastDay=CyBssUtility.getDay(CyBssUtility.getLastDayOfMonth(dateEnd));
							ratio=(double)(CyBssUtility.getDateDifferenceInDays(dateStart, dateEnd)+1)/lastDay;
						}
						else
							if (date.equals(CyBssUtility.dateToString(dateStart, CyBssUtility.DATE_yyyyMM))){
								
								billable.setDateStart(ct.getDateStart());
								
								Date lastDayOfMonth=CyBssUtility.getLastDayOfMonth(CyBssUtility.stringToDate(date+"01", CyBssUtility.DATE_yyyyMMdd));
								billable.setDateEnd(CyBssUtility.dateToString(lastDayOfMonth,CyBssUtility.DATE_yyyy_MM_dd));
								
								int lastDay=CyBssUtility.getDay(lastDayOfMonth);
								ratio=(double)(CyBssUtility.getDateDifferenceInDays(dateStart, lastDayOfMonth)+1)/lastDay;
							}
							else
								if (date.equals(CyBssUtility.dateToString(dateEnd, CyBssUtility.DATE_yyyyMM))){
									Date firstDayOfMonth=CyBssUtility.stringToDate(date+"01", CyBssUtility.DATE_yyyyMMdd);
									billable.setDateStart(CyBssUtility.dateToString(firstDayOfMonth,CyBssUtility.DATE_yyyy_MM_dd));
									billable.setDateEnd(ct.getDateEnd());
								
									int lastDay=CyBssUtility.getDay(CyBssUtility.getLastDayOfMonth(dateEnd));
									ratio=(double)(CyBssUtility.getDateDifferenceInDays(firstDayOfMonth, dateEnd)+1)/lastDay;
								}
								else
									{
										Date firstDayOfMonth=CyBssUtility.stringToDate(date+"01", CyBssUtility.DATE_yyyyMMdd);
										billable.setDateStart(CyBssUtility.dateToString(firstDayOfMonth,CyBssUtility.DATE_yyyy_MM_dd));
										Date lastDayOfMonth=CyBssUtility.getLastDayOfMonth(CyBssUtility.stringToDate(date+"01", CyBssUtility.DATE_yyyyMMdd));
										billable.setDateEnd(CyBssUtility.dateToString(lastDayOfMonth,CyBssUtility.DATE_yyyy_MM_dd));
									}
						
						billable.setAmount(ct.getAmount()*ratio);
						billable.setVat(ct.getVat());
						billable.setVatAmount(ct.getVatAmount()*ratio);
						billable.setTotAmount(ct.getAmount()*ratio+ct.getVatAmount()*ratio);
			
						billable.setComponentId(ct.getComponentId());
						billable.setBillableType(Billable.TYPE_ACTUAL);
						
						ret.add(billable);
						
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new CyBssException(e);
				}
			}
		
		return ret;
	}
	
	protected void addToServerQueue(long id, String entityName) throws CyBssException{
		ServerQueueItem queueItem=new ServerQueueItem();
		queueItem.setObjectId(objectDao.getByEntity(entityName).getId());
		queueItem.setObjectInstId(id);
		
		serverService.addQueueItem(queueItem);
	}

}
