package org.cysoft.bss.core.web.service.rest;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CountryDao;
import org.cysoft.bss.core.model.Country;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.CountryListResponse;
import org.cysoft.bss.core.web.response.rest.CountryResponse;
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
@RequestMapping("/country")
@CyBssService(name = "Country")
public class CountryWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(CountryWs.class);
	
	protected CountryDao countryDao=null;
	@Autowired
	public void setCountryDao(CountryDao countryDao){
			this.countryDao=countryDao;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public CountryResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Country country
			) throws CyBssException
	{
		CountryResponse response=new CountryResponse();
		
		logger.info("CountryWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Country.class))
			return response;
		// end checkGrant 
				
		logger.info(country.toString());
		
		long id=countryDao.add(country);
		country.setId(id);
		response.setCountry(country);
			
		logger.info("CountryWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public CountryResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CountryWs.get() >>> id="+id);
		CountryResponse response=new CountryResponse();
		
		Country country=countryDao.get(id);
		if (country!=null)
			response.setCountry(country);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("CountryWs.get() <<< ");
		return response;
	}

	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public CountryResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Country country
			) throws CyBssException
	{
		CountryResponse response=new CountryResponse();
		
		logger.info("CountryWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Country.class))
			return response;
		// end checkGrant 
		
		if (countryDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		countryDao.update(id, country);
		response.setCountry(countryDao.get(id));
		
		logger.info("CountryWs.update() <<<");
		return response;
	}
	
	
	@RequestMapping(value = "/getCountryAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getCountryAll")
	public CountryListResponse getCountryAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("CountryWs.getCountryAll() >>>");
		CountryListResponse response=new CountryListResponse(); 
		response.setCountries(countryDao.getCountryAll());
		return response;
	}
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public CountryListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="name", required=false, defaultValue="") String name
		) throws CyBssException{

		logger.info("CountryWs.find() >>> name="+name);
		CountryListResponse response=new CountryListResponse();
		
		List<Country> countries=countryDao.find(name);
		response.setCountries(countries);
		
		logger.info("CountryWs.find() <<< ");
		
		return response;
	}

	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public CountryResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CountryWs.remove() >>> id="+id);
		CountryResponse response=new CountryResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		countryDao.remove(id);
	
		logger.info("CountryWs.remove() <<< ");
	
		return response;
	}

	
}
