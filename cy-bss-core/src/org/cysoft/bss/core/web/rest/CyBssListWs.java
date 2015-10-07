package org.cysoft.bss.core.web.rest;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CyBssListDao;
import org.cysoft.bss.core.dao.CyBssServiceDao;
import org.cysoft.bss.core.model.Language;
import org.cysoft.bss.core.model.rest.response.CyBssListGetLanguageAll;
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
@RequestMapping("/cybss-list")
@CyBssService(name = "BssList")
public class CyBssListWs extends CyBssRestServiceAdapter
	implements ICyBssRestService{
	
	private static final Logger logger = LoggerFactory.getLogger(CyBssListWs.class);
	
	
	
	private CyBssListDao listDao=null;
	
	@Autowired
	public void setListDao(CyBssListDao listDao){
			this.listDao=listDao;
	}
	
	
	@RequestMapping(value = "/getLanguageAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getLanguageAll")
	public CyBssListGetLanguageAll getLanguageAll(
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException{
		
		logger.info("CyBssListWs.getLanguageAll() >>>");
		
		
		CyBssListGetLanguageAll response=new CyBssListGetLanguageAll(); 
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getLanguageAll",String.class))
			return response;
		// end checkGrant 
		
		response.setLanguages(listDao.getLanguageAll());
		return response;
	}

}
