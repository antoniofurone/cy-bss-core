package org.cysoft.bss.core.web.service.rest;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.service.InvoiceService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.invoice.InvoiceResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/invoice")
@CyBssService(name = "Invoice")
public class InvoiceWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(InvoiceWs.class);
	
	protected InvoiceService invoiceService=null;
	@Autowired
	public void setInvoiceService(InvoiceService invoiceService){
			this.invoiceService=invoiceService;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public InvoiceResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Invoice invoice
			) throws CyBssException
	{
		InvoiceResponse response=new InvoiceResponse();
		
		logger.info("InvoiceWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Invoice.class))
				return response;
		// end checkGrant 
		
		logger.info(invoice.toString());
		
		if (!invoice.getInvoiceType().equals(Invoice.TYPE_ACTIVE) &&
			!invoice.getInvoiceType().equals(Invoice.TYPE_PASSIVE)){
			setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
					ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
			return response;
		}
		
		invoiceService.add(invoice.getCompanyId(), invoice.getTpCompanyId(), 
				invoice.getPersonId(), invoice.getInvoiceType());
		
		logger.info("InvoiceWs.add() <<<");
		
		return response;
	}
	
	/*
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public PurchaseListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="companyId", required=false, defaultValue="0") Integer companyId,
			@RequestParam(value="productId", required=false, defaultValue="0") Integer productId,
			@RequestParam(value="productName", required=false, defaultValue="") String productName,
			@RequestParam(value="supplierId", required=false, defaultValue="0") Integer supplierId,
			@RequestParam(value="supplierCode", required=false, defaultValue="") String supplierCode,
			@RequestParam(value="supplierName", required=false, defaultValue="") String supplierName,
			@RequestParam(value="personId", required=false, defaultValue="0") Integer personId,
			@RequestParam(value="personCode", required=false, defaultValue="") String personCode,
			@RequestParam(value="personName", required=false, defaultValue="") String personName,
			@RequestParam(value="attrName", required=false, defaultValue="") String attrName,
			@RequestParam(value="attrValue", required=false, defaultValue="") String attrValue,
			@RequestParam(value="fromDate", required=false, defaultValue="") String fromDate,
			@RequestParam(value="toDate", required=false, defaultValue="") String toDate,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("PurchaseWs.find() >>> compayId="+companyId+
				";productId="+productId+";productName="+productName+
				";supplierId="+supplierId+";supplierCode="+supplierCode+";supplierName="+supplierName+
				";personId="+personId+";personCode="+personCode+";personName="+personName+
				";attrName="+attrName+";attrValue="+attrValue+
				";fromDate="+fromDate+";toDate="+toDate
				);
		
		PurchaseListResponse response=new PurchaseListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",
			String.class,
			Integer.class,
			Integer.class,String.class,
			Integer.class,String.class,String.class,
			Integer.class,String.class,String.class,
			String.class,String.class,
			String.class,String.class,
			Integer.class,Integer.class
			))
			return response;
		// end checkGrant 
		
		
		List<Purchase> purchases=purchaseService.find(companyId,productId,productName,
				supplierId,supplierCode,supplierName,
				personId,personCode,personName,
				attrName,attrValue,
				fromDate,toDate);
		int lsize=purchases.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setPurchases(purchases.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setPurchases(new ArrayList<Purchase>());
			}
		else
			response.setPurchases(purchases);
		
		logger.info("PurchaseWs.find() <<< ");
		
		return response;
		
	}
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public PurchaseResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("Purchase.get() >>> id="+id);
		PurchaseResponse response=new PurchaseResponse();
		
		Purchase purchase=purchaseService.get(id);
		if (purchase!=null)
			response.setPurchase(purchase);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("PurchaseWs.get() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public PurchaseResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Purchase purchase
			) throws CyBssException
	{
		PurchaseResponse response=new PurchaseResponse();
		
		logger.info("PurchaseWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Purchase.class))
			return response;
		// end checkGrant 
		
		if (purchaseService.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		PriceComponent component=priceService.getPriceComponent(purchase.getComponentId());
		if (component==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}	
		
		purchase.setComponent(component);
		
		purchase.calcAmounts();
		
		if (purchase.getTacitRenewal()==null || purchase.getTacitRenewal().equals(""))
			purchase.setTacitRenewal(Purchase.TACIT_RENEWAL_NO);
		if (purchase.getTransactionType()==null || purchase.getTransactionType().equals(""))
			purchase.setTransactionType(Purchase.TRANSACTION_TYPE_BILLABLE);
		
		if (purchase.getDate()==null || purchase.getDate().equals(""))
			purchase.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
		
		purchaseService.update(id, purchase);
		response.setPurchase(purchaseService.get(id));
		
		logger.info("PurchaseWs.update() <<<");
		return response;
	}

	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public PurchaseResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PurchaseWs.remove() >>> id="+id);
		PurchaseResponse response=new PurchaseResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		purchaseService.remove(id);
	
		logger.info("ProductWs.remove() <<< ");
	
		return response;
	}
	
	*/
}
