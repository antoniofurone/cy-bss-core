package org.cysoft.bss.core.web.service.rest;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.LanguageListResponse;
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
@RequestMapping("/language")
@CyBssService(name = "Language")
public class LanguageWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(LanguageWs.class);
	private LanguageDao listDao=null;
	
	@Autowired
	public void setListDao(LanguageDao listDao){
			this.listDao=listDao;
	}
	
	
	@RequestMapping(value = "/getLanguageAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getLanguageAll")
	public LanguageListResponse getLanguageAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("CyBssListWs.getLanguageAll() >>>");
		LanguageListResponse response=new LanguageListResponse(); 
		response.setLanguages(listDao.getLanguageAll());
		return response;
	}

}
