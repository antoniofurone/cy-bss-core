package org.cysoft.bss.core.web.service.rest;


import org.cysoft.bss.core.bo.Charge;
import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.PriceDao;
import org.cysoft.bss.core.dao.PurchaseDao;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.Purchase;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.purchase.PurchaseListResponse;
import org.cysoft.bss.core.web.response.rest.purchase.PurchaseResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purchase")
@CyBssService(name = "Purchase")
public class PurchaseWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(PurchaseWs.class);
	
	protected PurchaseDao purchaseDao=null;
	@Autowired
	public void setPurchaseDao(PurchaseDao purchaseDao){
			this.purchaseDao=purchaseDao;
	}
	
	protected PriceDao priceDao=null;
	@Autowired
	public void setPriceDao(PriceDao priceDao){
			this.priceDao=priceDao;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public PurchaseResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Purchase purchase
			) throws CyBssException
	{
		PurchaseResponse response=new PurchaseResponse();
		
		logger.info("PurchaseWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Purchase.class))
				return response;
		// end checkGrant 
		
		logger.info(purchase.toString());
		
		
		PriceComponent component=priceDao.getPriceComponent(purchase.getComponentId());
		if (component==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}	
		
		Charge charge=new Charge(component);
		charge.setPrice(purchase.getPrice());
		charge.setPriceTot(purchase.getPriceTot());
		charge.setQty(purchase.getQty());
		charge.setVat(purchase.getVat());
		charge.calc();
		
		purchase.setPrice(charge.getPrice());
		purchase.setPriceTot(charge.getPriceTot());
		purchase.setAmount(charge.getAmount());
		purchase.setVatAmount(charge.getVatAmount());
		
		if (purchase.getTacitRenewal()==null || purchase.getTacitRenewal().equals(""))
			purchase.setTacitRenewal(Purchase.TACIT_RENEWAL_NO);
		if (purchase.getPurchaseType()==null || purchase.getPurchaseType().equals(""))
			purchase.setPurchaseType(Purchase.PURCHASE_TYPE_BILLABLE);
		
		long id=purchaseDao.add(purchase);
		purchase.setId(id);
		response.setPurchase(purchase);
		
		logger.info("PurchaseWs.add() <<<");
		
		return response;
	}
	
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
			@RequestParam(value="fromDate", required=false, defaultValue="") String fromDate,
			@RequestParam(value="toDate", required=false, defaultValue="") String toDate,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("PurchaseWs.find() >>> compayId="+companyId+
				";productId="+productId+";productName="+productName+
				";supplierId="+supplierId+";supplierCode="+supplierCode+";supplierName="+supplierName+
				";personId="+personId+";personCode="+personCode+";personName="+personName+
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
			Integer.class,Integer.class
			))
			return response;
		// end checkGrant 
		
		/*
		List<Product> products=productDao.find(name,categoryId,productTypeId,attrName,attrValue);
		int lsize=products.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setProducts(products.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setProducts(new ArrayList<Product>());
			}
		else
			response.setProducts(products);
		
		*/
		logger.info("PurchaseWs.find() <<< ");
		
		return response;
		
	}
	
	
	/*
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public ProductResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("Product.get() >>> id="+id);
		ProductResponse response=new ProductResponse();
		
		Product product=productDao.get(id);
		if (product!=null)
			response.setProduct(product);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("ProductWs.get() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public ProductResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Product product
			) throws CyBssException
	{
		ProductResponse response=new ProductResponse();
		
		logger.info("ProductWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Product.class))
			return response;
		// end checkGrant 
		
		if (productDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		productDao.update(id, product);
		response.setProduct(productDao.get(id));
		
		logger.info("ProductWs.update() <<<");
		return response;
	}

	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public ProductResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ProductWs.remove() >>> id="+id);
		ProductResponse response=new ProductResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		productDao.remove(id);
	
		logger.info("ProductWs.remove() <<< ");
	
		return response;
	}
	*/
}
