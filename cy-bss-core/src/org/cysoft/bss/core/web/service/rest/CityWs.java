package org.cysoft.bss.core.web.service.rest;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CityDao;
import org.cysoft.bss.core.model.City;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.CityListResponse;
import org.cysoft.bss.core.web.response.rest.CityResponse;
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
@RequestMapping("/city")
@CyBssService(name = "City")
public class CityWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(CityWs.class);
	
	protected CityDao cityDao=null;
	@Autowired
	public void setCityDao(CityDao cityDao){
			this.cityDao=cityDao;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public CityResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody City city
			) throws CyBssException
	{
		CityResponse response=new CityResponse();
		
		logger.info("CityWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,City.class))
			return response;
		// end checkGrant 
				
		logger.info(city.toString());
		
		long id=cityDao.add(city);
		city.setId(id);
		response.setCity(city);
			
		logger.info("CityWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public CityResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CityWs.get() >>> id="+id);
		CityResponse response=new CityResponse();
		
		City city=cityDao.get(id);
		if (city!=null)
			response.setCity(city);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("CityWs.get() <<< ");
		return response;
	}

	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public CityResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody City city
			) throws CyBssException
	{
		CityResponse response=new CityResponse();
		
		logger.info("CityWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,City.class))
			return response;
		// end checkGrant 
		
		if (cityDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		cityDao.update(id, city);
		response.setCity(cityDao.get(id));
		
		logger.info("CityWs.update() <<<");
		return response;
	}
	
	
	@RequestMapping(value = "/getCityAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getCityAll")
	public CityListResponse getCityAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("CityWs.getCityAll() >>>");
		CityListResponse response=new CityListResponse(); 
		response.setCities(cityDao.getCityAll());
		return response;
	}
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public CityListResponse find(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="stateRegion", required=false, defaultValue="") String stateRegion,
			@RequestParam(value="countryId", required=false, defaultValue="0") Long countryId
			) throws CyBssException{

		logger.info("CityWs.find() >>> name="+name+";stateRegion="+stateRegion+";countryId="+countryId);
		CityListResponse response=new CityListResponse();
		
		List<City> cities=cityDao.find(name,stateRegion,countryId);
		response.setCities(cities);
		
		logger.info("CityWs.find() <<< ");
		
		return response;
	}

	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public CityResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CityWs.remove() >>> id="+id);
		CityResponse response=new CityResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		cityDao.remove(id);
	
		logger.info("CityWs.remove() <<< ");
	
		return response;
	}

	
}
