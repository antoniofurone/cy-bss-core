package org.cysoft.bss.core.web.service.rest;

import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Billable;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.Sale;
import org.cysoft.bss.core.service.PriceService;
import org.cysoft.bss.core.service.SaleService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.sale.SaleListResponse;
import org.cysoft.bss.core.web.response.rest.sale.SaleResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sale")
@CyBssService(name = "Sale")
public class SaleWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(SaleWs.class);
	
	protected PriceService priceService=null;
	@Autowired
	public void setPriceService(PriceService priceService){
			this.priceService=priceService;
	}
	
	protected SaleService saleService=null;
	@Autowired
	public void setSaleService(SaleService saleService){
			this.saleService=saleService;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public SaleResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Sale sale
			) throws CyBssException
	{
		SaleResponse response=new SaleResponse();
		
		logger.info("SaleWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Sale.class))
				return response;
		// end checkGrant 
		
		logger.info(sale.toString());
		
		PriceComponent component=priceService.getPriceComponent(sale.getComponentId());
		if (component==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}	
		
		sale.setComponent(component);
		sale.calcAmounts();
		
		if (sale.getTacitRenewal()==null || sale.getTacitRenewal().equals(""))
			sale.setTacitRenewal(Sale.TACIT_RENEWAL_NO);
		if (sale.getTransactionType()==null || sale.getTransactionType().equals(""))
			sale.setTransactionType(Sale.TRANSACTION_TYPE_BILLABLE);
		
		if (sale.getDate()==null || sale.getDate().equals(""))
			sale.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
		
		long id=saleService.add(sale);
		sale.setId(id);
		response.setSale(sale);
		
		logger.info("SaleWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public SaleListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="companyId", required=false, defaultValue="0") Integer companyId,
			@RequestParam(value="productId", required=false, defaultValue="0") Integer productId,
			@RequestParam(value="productName", required=false, defaultValue="") String productName,
			@RequestParam(value="customerId", required=false, defaultValue="0") Integer customerId,
			@RequestParam(value="customerCode", required=false, defaultValue="") String customerCode,
			@RequestParam(value="customerName", required=false, defaultValue="") String customerName,
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
		
		logger.info("SaleWs.find() >>> compayId="+companyId+
				";productId="+productId+";productName="+productName+
				";customerId="+customerId+";customerCode="+customerCode+";customerName="+customerName+
				";personId="+personId+";personCode="+personCode+";personName="+personName+
				";attrName="+attrName+";attrValue="+attrValue+
				";fromDate="+fromDate+";toDate="+toDate
				);
		
		SaleListResponse response=new SaleListResponse();
		
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
		
		
		List<Sale> sales=saleService.find(companyId,productId,productName,
				customerId,customerCode,customerName,
				personId,personCode,personName,
				attrName,attrValue,
				fromDate,toDate);
		int lsize=sales.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setSales(sales.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setSales(new ArrayList<Sale>());
			}
		else
			response.setSales(sales);
		
		logger.info("SaleWs.find() <<< ");
		
		return response;
		
	}
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public SaleResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("SaleWs.get() >>> id="+id);
		SaleResponse response=new SaleResponse();
		
		Sale sale=saleService.get(id);
		if (sale!=null)
			response.setSale(sale);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("SaleWs.get() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public SaleResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Sale sale
			) throws CyBssException
	{
		SaleResponse response=new SaleResponse();
		
		logger.info("SaleWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Sale.class))
			return response;
		// end checkGrant 
		
		if (saleService.get(id)==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		PriceComponent component=priceService.getPriceComponent(sale.getComponentId());
		if (component==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}	
		
		sale.setComponent(component);
		
		sale.calcAmounts();
		
		if (sale.getTacitRenewal()==null || sale.getTacitRenewal().equals(""))
			sale.setTacitRenewal(Sale.TACIT_RENEWAL_NO);
		if (sale.getTransactionType()==null || sale.getTransactionType().equals(""))
			sale.setTransactionType(Sale.TRANSACTION_TYPE_BILLABLE);
		
		if (sale.getDate()==null || sale.getDate().equals(""))
			sale.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
		
		saleService.update(id, sale);
		response.setSale(saleService.get(id));
		
		logger.info("SaleWs.update() <<<");
		return response;
	}

	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public SaleResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("SaleWs.remove() >>> id="+id);
		SaleResponse response=new SaleResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		List<Billable> billables=saleService.getBillables(id);
		for (Billable billable:billables){
			if (billable.isBilled()){
				setResult(response, ICyBssMessageConst.RESULT_SALE_NOCANCEL, 
						ICyBssMessageConst.RESULT_D_SALE_NOCANCEL,response.getLanguageCode());
				return response;
			}
		}
		
		saleService.remove(id);
	
		logger.info("SaleWs.remove() <<< ");
	
		return response;
	}
	
	
}
