package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Contact;
import org.cysoft.bss.core.model.ContactType;
import org.cysoft.bss.core.service.ContactService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.contact.ContactResponse;
import org.cysoft.bss.core.web.response.rest.contact.ContactTypeListResponse;
import org.cysoft.bss.core.web.response.rest.contact.ContactTypeResponse;
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
	
	protected ContactService contactService=null;
	@Autowired
	public void setContactService(ContactService contactService){
			this.contactService=contactService;
	}
	
	@RequestMapping(value = "/getContactTypeAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getContactTypeAll")
	public ContactTypeListResponse getContactTypeAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("ContactWs.getContactTypeAll() >>>");
		ContactTypeListResponse response=new ContactTypeListResponse(); 
		response.setContactTypes(contactService.getContactTypeAll());
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
		long id=contactService.addType(contactType);
		contactType.setId(id);
		response.setContactType(contactType);
		
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
		
		if (contactService.getType(id)==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		contactService.updateType(id, contactType);
		response.setContactType(contactType);
	
		logger.info("CompanyWs.updateType() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getType",method = RequestMethod.GET)
	@CyBssOperation(name = "getType")
	public ContactTypeResponse getType(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ContactWs.getType() >>> id="+id);
		ContactTypeResponse response=new ContactTypeResponse();
		
		ContactType contactType=contactService.getType(id);
		if (contactType!=null)
			response.setContactType(contactType);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
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
		
		contactService.removeType(id);
		
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
		
		if (contactService.getType(contact.getContactTypeId())==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		long contactId=contactService.add(contact);
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
		
		if (contactService.getType(contact.getContactTypeId())==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		if (contactService.get(id)==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		contactService.update(id, contact);
		
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
		
		contactService.remove(id);
	
		logger.info("ContactWs.remove() <<< ");
	
		return response;
	}
	
}
