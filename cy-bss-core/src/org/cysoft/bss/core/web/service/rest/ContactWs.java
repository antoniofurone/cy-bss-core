package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.model.ContactType;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.ContactTypeListResponse;
import org.cysoft.bss.core.web.response.rest.ContactTypeResponse;
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
@RequestMapping("/contact")
@CyBssService(name = "Contact")
public class ContactWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(ContactWs.class);
	
	protected ContactDao contactDao=null;
	@Autowired
	public void setContactDao(ContactDao contactDao){
			this.contactDao=contactDao;
	}
	
	@RequestMapping(value = "/getContactTypeAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getContactTypeAll")
	public ContactTypeListResponse getContactTypeAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("ContactWs.getContactTypeAll() >>>");
		ContactTypeListResponse response=new ContactTypeListResponse(); 
		response.setContactTypes(contactDao.getContactTypeAll());
		return response;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public ContactTypeResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody ContactType contactType
			) throws CyBssException
	{
		ContactTypeResponse response=new ContactTypeResponse();
		
		logger.info("ContactWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,ContactType.class))
			return response;
		// end checkGrant 
		
				
		//logger.info(company.toString());
		long id=contactDao.add(contactType);
		contactType.setId(id);
		response.setContanctType(contactType);
		
		logger.info("Contact.add() <<<");
		
		return response;
	}

	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public ContactTypeResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody ContactType contactType
			) throws CyBssException
	{
		ContactTypeResponse response=new ContactTypeResponse();
		
		logger.info("ContactWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,ContactType.class))
			return response;
		// end checkGrant 
		/*
		if (companyDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		companyDao.update(id, company);
		response.setCompany(companyDao.get(id));
		*/
		logger.info("CompanyWs.update() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public ContactTypeResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ContactWs.get() >>> id="+id);
		ContactTypeResponse response=new ContactTypeResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class))
			return response;
		// end checkGrant 
		/*		
		Company company=companyDao.get(id);
		if (company!=null)
			response.setCompany(company);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		*/
		logger.info("ContactWs.get() <<< ");
		
		return response;
	}
	

	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public ContactTypeResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ContactWs.remove() >>> id="+id);
		ContactTypeResponse response=new ContactTypeResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		//companyDao.remove(id);
		
		logger.info("ContactWs.remove() <<< ");
		return response;
	}
	
}
