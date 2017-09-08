package org.cysoft.bss.core.web.service.rest;


import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Contact;
import org.cysoft.bss.core.model.Person;
import org.cysoft.bss.core.service.ContactService;
import org.cysoft.bss.core.service.PersonService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.contact.ContactListResponse;
import org.cysoft.bss.core.web.response.rest.person.PersonListResponse;
import org.cysoft.bss.core.web.response.rest.person.PersonResponse;
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
@RequestMapping("/person")
@CyBssService(name = "Person")
public class PersonWs extends CyBssWebServiceAdapter
	implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(PersonWs.class);
	
	
	protected PersonService personService=null;
	@Autowired
	public void setPersonService(PersonService personService){
			this.personService=personService;
	}
	
	protected ContactService contactService=null;
	@Autowired
	public void setContactService(ContactService contactService){
			this.contactService=contactService;
	}
	
	private String generateCode(Person p){
		String firstName=p.getFirstName().replaceAll(" ", "").toUpperCase();
		String secondName=p.getSecondName().replaceAll(" ", "").toUpperCase();
		return 
				(secondName.length()>=3?secondName.substring(0,3):secondName)+
				(firstName.length()>=3?firstName.substring(0,3):firstName);
	}
	
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public PersonResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Person person
			) throws CyBssException
	{
		PersonResponse response=new PersonResponse();
		
		logger.info("PersonWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Person.class))
				return response;
		// end checkGrant 
		
		
		logger.info(person.toString());
		
		if (person.getCode().equals("")){
			person.setCode(generateCode(person));
			logger.info("candidate code="+person.getCode());
			
			int suffix=0; 
			while (personService.getByCode(person.getCode())!=null){
				suffix++;
				person.setCode(person.getCode()+suffix);
			}
			logger.info("code="+person.getCode());
		}
		
		if (personService.getByCode(person.getCode())!=null){
			setResult(response, ICyBssResultConst.RESULT_PERSCODE_USED, 
					ICyBssResultConst.RESULT_D_PERSCODE_USED,response.getLanguageCode());
			return response;
		}
		
		personService.add(person);
		response.setPerson(personService.getByCode(person.getCode()));
		
		logger.info("PersonWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public PersonResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Person person
			) throws CyBssException
	{
		PersonResponse response=new PersonResponse();
		
		logger.info("PersonWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Person.class))
			return response;
		// end checkGrant 
		
		
		if (personService.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		personService.update(id, person);
		response.setPerson(personService.get(id));
		
		logger.info("PersonWs.update() <<<");
		
		return response;
	}
	
	
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public PersonResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PersonWs.get() >>> id="+id);
		PersonResponse response=new PersonResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class))
			return response;
		// end checkGrant 
				
		Person person=personService.get(id);
		if (person!=null)
			response.setPerson(person);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("PersonWs.get() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/getByCode",method = RequestMethod.GET)
	@CyBssOperation(name = "getByCode")
	public PersonResponse getByCode(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="code", required=true) String code
			) throws CyBssException{
		
		logger.info("PersonWs.getByCode() >>> personId="+code);
		PersonResponse response=new PersonResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getByCode",String.class,String.class))
			return response;
		// end checkGrant 
				
		Person person=personService.getByCode(code);
		if (person!=null)
			response.setPerson(person);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("PersonWs.getByCode() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public PersonResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PersonWs.remove() >>> id="+id);
		PersonResponse response=new PersonResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		personService.remove(id);
		
		logger.info("PersonWs.remove() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/getContactAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getContactAll")
	public ContactListResponse getContactAll(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("PersonWs.getContactAll() >>> id="+id);
		ContactListResponse response=new ContactListResponse();
	
		// checkGrant
		if (!checkGrant(response,securityToken,"getContactAll",String.class,Long.class))
			return response;
		// end checkGrant 
				
		List<Contact> contacts=contactService.getByEntity(id, Person.ENTITY_NAME);
		response.setContacts(contacts);
		
		logger.info("PersonWs.getContactAll() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public PersonListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="code", required=false, defaultValue="") String code,
			@RequestParam(value="firstName", required=false, defaultValue="") String firstName,
			@RequestParam(value="secondName", required=false, defaultValue="") String secondName,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("PersonWs.find() >>> code="+code+";firstName="+firstName+";secondName="+secondName);
		PersonListResponse response=new PersonListResponse();
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",String.class,String.class,String.class,String.class,Integer.class,Integer.class))
			return response;
		// end checkGrant 
		
		
		List<Person> persons=personService.find(code,firstName,secondName);
		int lsize=persons.size();
		
		if (offset!=0){
			if (offset<=lsize)
				response.setPersons(persons.subList(offset-1, ((offset-1)+size)>lsize?lsize:(offset-1)+size));
			else
				response.setPersons(new ArrayList<Person>());
			}
		else
			response.setPersons(persons);
		
		logger.info("PersonWs.find() <<< ");
		
		return response;
	}
	
		
}
