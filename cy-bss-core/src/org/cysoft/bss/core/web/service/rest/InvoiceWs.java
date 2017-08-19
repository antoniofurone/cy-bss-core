package org.cysoft.bss.core.web.service.rest;


import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.model.Invoice;
import org.cysoft.bss.core.service.InvoiceService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.invoice.InvoiceListResponse;
import org.cysoft.bss.core.web.response.rest.invoice.InvoiceResponse;
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
	
	@RequestMapping(value = "/{invoiceType}/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public InvoiceResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("invoiceType") String invoiceType,
			@RequestBody Invoice invoice
			) throws CyBssException
	{
		InvoiceResponse response=new InvoiceResponse();
		
		logger.info("InvoiceWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,String.class,Invoice.class))
				return response;
		// end checkGrant 
		
		logger.info(invoice.toString());
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
			!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
			setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
					ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
			return response;
		}
		invoice.setInvoiceType(invoiceType.toUpperCase());
		
		
		if (invoice.getDate()==null || invoice.getDate().equals(""))
			invoice.setDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd));
		
		long id=invoiceService.add(invoice);
		invoice.setId(id);
		response.setInvoice(invoice);
		
		logger.info("InvoiceWs.add() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{invoiceType}/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public InvoiceListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("invoiceType") String invoiceType,
			@RequestParam(value="companyId", required=false, defaultValue="0") Integer companyId,
			@RequestParam(value="tpCompanyId", required=false, defaultValue="0") Integer tpCompanyId,
			@RequestParam(value="tpCompanyCode", required=false, defaultValue="") String tpCompanyCode,
			@RequestParam(value="tpCompanyName", required=false, defaultValue="") String tpCompanyName,
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
	
		
		logger.info("InvoiceWs.find() >>> compayId="+companyId+
				";invoiceType="+invoiceType+
				";tpCompanyId="+tpCompanyId+";tpCompanyCode="+tpCompanyCode+";tpCompanyName="+tpCompanyName+
				";personId="+personId+";personCode="+personCode+";personName="+personName+
				";attrName="+attrName+";attrValue="+attrValue+
				";fromDate="+fromDate+";toDate="+toDate
				);
		
		InvoiceListResponse response=new InvoiceListResponse();
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",
			String.class,
			String.class,
			Integer.class,
			Integer.class,String.class,String.class,
			Integer.class,String.class,String.class,
			String.class,String.class,
			String.class,String.class,
			Integer.class,Integer.class
			))
			return response;
		// end checkGrant 
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
				!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
				setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
						ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
				return response;
			}
		
		invoiceType=invoiceType.toUpperCase();
		List<Invoice> invoices=invoiceService.find(invoiceType,companyId,
				tpCompanyId,tpCompanyCode,tpCompanyName,
				personId,personCode,personName,
				attrName,attrValue,
				fromDate,toDate);
		int lsize=invoices.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setInvoices(invoices.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setInvoices(new ArrayList<Invoice>());
			}
		else
			response.setInvoices(invoices);
		
		logger.info("InvoiceWs.find() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{invoiceType}/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public InvoiceResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("invoiceType") String invoiceType
			) throws CyBssException{
		
		logger.info("InvoiceWs.get() >>> id="+id);
		InvoiceResponse response=new InvoiceResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class,String.class))
			return response;
		// end checkGrant 
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
				!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
				setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
						ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
				return response;
			}
		invoiceType=invoiceType.toUpperCase();
		
		Invoice invoice=invoiceService.get(invoiceType, id);
		if (invoice!=null)
			response.setInvoice(invoice);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("InvoiceWs.get() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{invoiceType}/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public InvoiceResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("invoiceType") String invoiceType
			) throws CyBssException{
		
		logger.info("InvoiceWs.remove() >>> id="+id);
		InvoiceResponse response=new InvoiceResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class,String.class))
			return response;
		// end checkGrant 
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
				!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
				setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
						ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
				return response;
			}
		invoiceType=invoiceType.toUpperCase();
		
		
		Invoice invoice=invoiceService.get(invoiceType, id);
		if (invoice==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		invoiceService.remove(invoiceType, id);
		
		logger.info("InvoiceWs.remove() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{invoiceType}/{id}/close",method = RequestMethod.GET)
	@CyBssOperation(name = "close")
	public InvoiceResponse close(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("invoiceType") String invoiceType
			) throws CyBssException{
		
		logger.info("InvoiceWs.close() >>> id="+id);
		InvoiceResponse response=new InvoiceResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"close",String.class,Long.class,String.class))
			return response;
		// end checkGrant 
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
				!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
				setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
						ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
				return response;
			}
		invoiceType=invoiceType.toUpperCase();
		
		Invoice invoice=invoiceService.get(invoiceType, id);
		if (invoice==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		invoiceService.close(invoiceType, id);
		response.setInvoice(invoiceService.get(invoiceType, id));
		
		logger.info("InvoiceWs.close() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{invoiceType}/{id}/getBillables",method = RequestMethod.GET)
	@CyBssOperation(name = "getBillables")
	public InvoiceResponse getBillables(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("invoiceType") String invoiceType
			) throws CyBssException{
		
		logger.info("InvoiceWs.getBillables() >>> id="+id);
		InvoiceResponse response=new InvoiceResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getBillables",String.class,Long.class,String.class))
			return response;
		// end checkGrant 
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
				!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
				setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
						ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
				return response;
			}
		invoiceType=invoiceType.toUpperCase();
		
		Invoice invoice=invoiceService.get(invoiceType, id);
		if (invoice==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		response.setInvoice(invoiceService.get(invoiceType, id));
		response.setBillables(invoiceService.getBillables(invoiceType, id));
		
		logger.info("InvoiceWs.getBillables() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{invoiceType}/{id}/addBillable/{billableId}",method = RequestMethod.GET)
	@CyBssOperation(name = "addBillable")
	public InvoiceResponse addBillable(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("billableId") Long billableId,
			@PathVariable("invoiceType") String invoiceType
			) throws CyBssException{
		
		logger.info("InvoiceWs.addBillable() >>> id="+id+";billableId="+billableId);
		InvoiceResponse response=new InvoiceResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addBillable",String.class,Long.class,Long.class,String.class))
			return response;
		// end checkGrant 
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
				!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
				setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
						ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
				return response;
			}
		invoiceType=invoiceType.toUpperCase();
		
		Invoice invoice=invoiceService.get(invoiceType, id);
		if (invoice==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		if (invoice.getNumber()==0)
			invoiceService.linkBillable(invoiceType, id, billableId);
		
		invoice=invoiceService.get(invoiceType, id);
		if (invoice==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		response.setInvoice(invoice);
		
		logger.info("InvoiceWs.addBillable() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{invoiceType}/{id}/removeBillable/{billableId}",method = RequestMethod.GET)
	@CyBssOperation(name = "removeBillable")
	public InvoiceResponse removeBillable(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("billableId") Long billableId,
			@PathVariable("invoiceType") String invoiceType
			) throws CyBssException{
		
		logger.info("InvoiceWs.removeBillable() >>> id="+id+";billableId="+billableId);
		InvoiceResponse response=new InvoiceResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeBillable",String.class,Long.class,Long.class,String.class))
			return response;
		// end checkGrant 
		
		if (!invoiceType.equalsIgnoreCase(Invoice.TYPE_ACTIVE) &&
				!invoiceType.equalsIgnoreCase(Invoice.TYPE_PASSIVE)){
				setResult(response, ICyBssResultConst.RESULT_INVOICE_TYPE_INVALID, 
						ICyBssResultConst.RESULT_D_INVOICE_TYPE_INVALID,response.getLanguageCode());
				return response;
			}
		invoiceType=invoiceType.toUpperCase();

		Invoice invoice=invoiceService.get(invoiceType, id);
		if (invoice==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}

		if (invoice.getNumber()==0)
			invoiceService.unLinkBillable(invoiceType, id, billableId);
		
		invoice=invoiceService.get(invoiceType, id);
		if (invoice==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		response.setInvoice(invoice);

		
		logger.info("InvoiceWs.removeBillable() <<< ");
		return response;
	}
}
