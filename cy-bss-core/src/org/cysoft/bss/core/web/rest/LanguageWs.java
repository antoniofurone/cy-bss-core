package org.cysoft.bss.core.web.rest;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.LanguageDao;
import org.cysoft.bss.core.rest.response.language.LanguageListResponse;
import org.cysoft.bss.core.web.rest.annotation.CyBssOperation;
import org.cysoft.bss.core.web.rest.annotation.CyBssService;
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
public class LanguageWs extends CyBssRestServiceAdapter
	implements ICyBssRestService{
	
	private static final Logger logger = LoggerFactory.getLogger(LanguageWs.class);
	
	
	
	private LanguageDao listDao=null;
	
	@Autowired
	public void setListDao(LanguageDao listDao){
			this.listDao=listDao;
	}
	
	
	@RequestMapping(value = "/getLanguageAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getLanguageAll")
	public LanguageListResponse getLanguageAll(
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException{
		
		logger.info("CyBssListWs.getLanguageAll() >>>");
		
		
		LanguageListResponse response=new LanguageListResponse(); 
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getLanguageAll",String.class))
			return response;
		// end checkGrant 
		
		response.setLanguages(listDao.getLanguageAll());
		return response;
	}

}
