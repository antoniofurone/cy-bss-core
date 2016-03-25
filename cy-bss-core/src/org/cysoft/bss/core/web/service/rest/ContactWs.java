package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.model.Contact;
import org.cysoft.bss.core.model.ContactType;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.ContactResponse;
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
	
	@RequestMapping(value = "/addType",method = RequestMethod.POST)
	@CyBssOperation(name = "addType")
	public ContactTypeResponse addType(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody ContactType contactType
			) throws CyBssException
	{
		ContactTypeResponse response=new ContactTypeResponse();
		
		logger.info("ContactWs.addType() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addType",String.class,ContactType.class))
			return response;
		// end checkGrant 
				
		//logger.info(company.toString());
		long id=contactDao.addType(contactType);
		contactType.setId(id);
		response.setContanctType(contactType);
		
		logger.info("Contact.addType() <<<");
		
		return response;
	}

	@RequestMapping(value = "/{id}/updateType",method = RequestMethod.POST)
	@CyBssOperation(name = "updateType")
	public ContactTypeResponse updateType(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody ContactType contactType
			) throws CyBssException
	{
		ContactTypeResponse response=new ContactTypeResponse();
		
		logger.info("ContactWs.updateType() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateType",String.class,Long.class,ContactType.class))
			return response;
		// end checkGrant 
		
		if (contactDao.getType(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		contactDao.updateType(id, contactType);
		response.setContanctType(contactType);
	
		logger.info("CompanyWs.updateType() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getType",method = RequestMethod.GET)
	@CyBssOperation(name = "getType")
	public ContactTypeResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ContactWs.getType() >>> id="+id);
		ContactTypeResponse response=new ContactTypeResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getType",String.class,Long.class))
			return response;
		// end checkGrant 
				
		ContactType contactType=contactDao.getType(id);
		if (contactType!=null)
			response.setContanctType(contactType);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("ContactWs.getType() <<< ");
		
		return response;
	}
	

	@RequestMapping(value = "/{id}/removeType",method = RequestMethod.GET)
	@CyBssOperation(name = "removeType")
	public ContactTypeResponse removeType(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ContactWs.removeType() >>> id="+id);
		ContactTypeResponse response=new ContactTypeResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		contactDao.removeType(id);
		
		logger.info("ContactWs.removeType() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public ContactResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Contact contact
			) throws CyBssException
	{
		ContactResponse response=new ContactResponse();
		
		logger.info("ContactWs.add() >>>");
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Contact.class))
			return response;
		// end checkGrant 
		
		if (contactDao.getType(contact.getContactTypeId())==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		long contactId=contactDao.add(contact);
		contact.setId(contactId);
		
		response.setContanct(contact);
		
		logger.info("ContactWs.add() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public ContactResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Contact contact
			) throws CyBssException
	{
		ContactResponse response=new ContactResponse();
		
		logger.info("ContactWs.update() >>> id="+id);
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Contact.class))
			return response;
		// end checkGrant 
		
		if (contactDao.getType(contact.getContactTypeId())==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		if (contactDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		contactDao.update(id, contact);
		
		logger.info("CompanyWs.update() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public ContactResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ContactWs.remove() >>> id="+id);
		ContactResponse response=new ContactResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		contactDao.remove(id);
	
		logger.info("ContactWs.remove() <<< ");
	
		return response;
	}
	
}
