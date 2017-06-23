package org.cysoft.bss.core.web.service.rest;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.PriceDao;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.price.PriceComponentListResponse;
import org.cysoft.bss.core.web.response.rest.price.PriceComponentResponse;
import org.cysoft.bss.core.web.response.rest.price.PriceTypeListResponse;
import org.cysoft.bss.core.web.response.rest.price.PriceTypeResponse;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/price")
@CyBssService(name = "Price")
public class PriceWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{

	private static final Logger logger = LoggerFactory.getLogger(PriceWs.class);
	
	protected PriceDao priceDao=null;
	@Autowired
	public void setPriceDao(PriceDao priceDao){
			this.priceDao=priceDao;
	}
	
	// PriceType
	@RequestMapping(value = "/getPriceTypeAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getPriceTypeAll")
	public PriceTypeListResponse getPriceTypeAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("PriceWs.getPriceTypeAll() >>>");
		PriceTypeListResponse response=new PriceTypeListResponse(); 
		response.setTypes(priceDao.getPriceTypeAll());
		return response;
	}
	
	@RequestMapping(value = "/{id}/getPriceType",method = RequestMethod.GET)
	@CyBssOperation(name = "getPriceType")
	public PriceTypeResponse getCategory(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PriceWs.getPriceType() >>> id="+id);
		PriceTypeResponse response=new PriceTypeResponse();
		
		PriceType priceType=priceDao.getPriceType(id);
		if (priceType!=null)
			response.setType(priceType);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("PriceWs.getPriceType() <<< ");
		return response;
	}
	
	
	// PriceComponent
	@RequestMapping(value = "/getPriceComponentAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getPriceComponentAll")
	public PriceComponentListResponse getPriceComponentAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("PriceWs.getPriceComponentAll() >>>");
		PriceComponentListResponse response=new PriceComponentListResponse(); 
		response.setComponents(priceDao.getComponentAll());
		return response;
	}
	
	@RequestMapping(value = "/{id}/getPriceComponent",method = RequestMethod.GET)
	@CyBssOperation(name = "getPriceComponent")
	public PriceComponentResponse getPriceComponent(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PriceWs.getPriceComponent() >>> id="+id);
		PriceComponentResponse response=new PriceComponentResponse();
		
		PriceComponent priceComponent=priceDao.getPriceComponent(id);
		if (priceComponent!=null)
			response.setComponent(priceComponent);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("PriceWs.getPriceComponent() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/updatePriceComponent",method = RequestMethod.POST)
	@CyBssOperation(name = "updatePriceComponent")
	public PriceComponentResponse updatePriceComponent(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody PriceComponent component
			) throws CyBssException
	{
		PriceComponentResponse response=new PriceComponentResponse();
		
		logger.info("PriceWs.updatePriceComponent() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updatePriceComponent",String.class,Long.class,PriceComponent.class))
			return response;
		// end checkGrant 
		
		if (priceDao.getPriceComponent(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		priceDao.updateComponent(id, component);
		response.setComponent(priceDao.getPriceComponent(id));
		
		logger.info("PriceWs.updatePriceComponent() <<<");
		return response;
	}
}
