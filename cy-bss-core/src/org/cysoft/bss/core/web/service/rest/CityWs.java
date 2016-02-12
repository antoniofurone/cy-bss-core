package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CityDao;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.CityListResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

}
