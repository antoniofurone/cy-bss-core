package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.model.CyBssObject;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.attribute.AttributeListResponse;
import org.cysoft.bss.core.web.response.rest.attribute.AttributeResponse;
import org.cysoft.bss.core.web.response.rest.attribute.AttributeTypeListResponse;
import org.cysoft.bss.core.web.response.rest.attribute.ObjectListResponse;
import org.cysoft.bss.core.web.response.rest.attribute.ObjectResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
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
@RequestMapping("/object")
@CyBssService(name = "Object")
public class ObjectWs extends CyBssWebServiceAdapter
{
	
	private static final Logger logger = LoggerFactory.getLogger(ObjectWs.class);
	
	protected  ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}
	
	@RequestMapping(value = "/addAttribute",method = RequestMethod.POST)
	@CyBssOperation(name = "addAttribute")
	public AttributeResponse addAttribute(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Attribute attribute
			) throws CyBssException
	{
		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.addAttribute() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addAttribute",String.class,Attribute.class))
			return response;
		// end checkGrant 
				
	
		if (objectDao.get(attribute.getObjectId())==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		long id=objectDao.addAttribute(attribute);
		attribute.setId(id);
		response.setAttribute(attribute);
			
		logger.info("ObjectWs.addAttribute() <<<");
		
		return response;
	}
	
	
	
	@RequestMapping(value = "/getObjectAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getObjectAll")
	public ObjectListResponse getObjectAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("ObjectWs.getObjectAll() >>>");
		ObjectListResponse response=new ObjectListResponse(); 
		response.setCyBssObjects(objectDao.getObjectAll());
		return response;
	}
	
	@RequestMapping(value = "/getObjectByName/{name}",method = RequestMethod.GET)
	@CyBssOperation(name = "getObjectByName")
	public ObjectResponse getObjectByName(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("name") String name
			) throws CyBssException{
		
		logger.info("ObjectWs.getObjectByName() >>>");
		ObjectResponse response=new ObjectResponse(); 
		
		CyBssObject object=objectDao.getByName(name);
		if (object==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		response.setObject(object);
		return response;
	}
	
	@RequestMapping(value = "/getObjectByEntity/{entityName}",method = RequestMethod.GET)
	@CyBssOperation(name = "getObjectByEntity")
	public ObjectResponse getObjectByEntity(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("entityName") String entityName
			) throws CyBssException{
		
		logger.info("ObjectWs.getObjectByEntityName() >>>");
		ObjectResponse response=new ObjectResponse(); 
		
		CyBssObject object=objectDao.getByEntity(entityName);
		if (object==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		response.setObject(object);
		return response;
	}
	
	
	@RequestMapping(value = "/getAttributeTypeAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getAttributeTypeAll")
	public AttributeTypeListResponse getAttributeTypeAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("ObjectWs.getAttributeTypeAll() >>>");
		AttributeTypeListResponse response=new AttributeTypeListResponse(); 
		response.setAttributeTypes(objectDao.getAttributeTypeAll());
		return response;
	}


	@RequestMapping(value = "/{id}/getAttributes",method = RequestMethod.GET)
	@CyBssOperation(name = "getAttributes")
	public AttributeListResponse getAttributes(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("id") Long objectId
			) throws CyBssException
	{
		AttributeListResponse response=new AttributeListResponse();
		
		logger.info("ObjectWs.getAttributes() >>>");
		response.setAttributes(objectDao.getAttributes(objectId));
		logger.info("ObjectWs.getAttributes() <<<");
		
		return response;
	}

	
	@RequestMapping(value = "/{attrId}/getAttribute",method = RequestMethod.GET)
	@CyBssOperation(name = "getAttribute")
	public AttributeResponse getAttribute(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("attrId") Long attrId
			) throws CyBssException
	{
		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.getAttribute() >>>");
		response.setAttribute(objectDao.getAttribute(attrId));
		logger.info("ObjectWs.getAttribute() <<<");
		
		return response;
	}

	
	@RequestMapping(value = "/{id}/getAttributeByName/{name}",method = RequestMethod.GET)
	@CyBssOperation(name = "getAttributeByName")
	public AttributeResponse getAttributeByName(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("name") String name
			) throws CyBssException
	{
		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.getAttributeByName() >>>");
		response.setAttribute(objectDao.getAttributeByName(id, name));
		logger.info("ObjectWs.getAttributeByName() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/updateAttribute",method = RequestMethod.POST)
	@CyBssOperation(name = "updateAttribute")
	public AttributeResponse updateAttribute(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Attribute attribute
			) throws CyBssException
	{
		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.updateAttribute() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateAttribute",String.class,Attribute.class))
			return response;
		// end checkGrant 
	
		if (objectDao.getAttribute(attribute.getId())==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		if (objectDao.get(attribute.getObjectId())==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		objectDao.updateAttribute(attribute);
		response.setAttribute(attribute);
			
		logger.info("ObjectWs.updateAttribute() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{attrId}/removeAttribute",method = RequestMethod.GET)
	@CyBssOperation(name = "removeAttribute")
	public AttributeResponse removeAttribute(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("attrId") Long attrId
			) throws CyBssException
	{

		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.removeAttribute() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeAttribute",String.class,Long.class))
			return response;
		// end checkGrant 
				
		if (objectDao.getAttribute(attrId)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		objectDao.removeAttribute(attrId);
			
		logger.info("ObjectWs.removeAttribute() <<<");
		
		return response;
		
	}
	
	@RequestMapping(value = "/setAttributeValue",method = RequestMethod.POST)
	@CyBssOperation(name = "setAttributeValue")
	public AttributeResponse setAttributeValue(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Attribute attrValue
			) throws CyBssException
	{
		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.setAttributeValue() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"setAttributeValue",String.class,Attribute.class))
			return response;
		// end checkGrant 
				
		Attribute attribute=objectDao.getAttribute(attrValue.getId());
		if (attribute==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		objectDao.setAttributeValue(attrValue.getObjInstId(), 
				attrValue.getId(), attrValue.getValue());
	
		attribute.setObjInstId(attrValue.getObjInstId());
		attribute.setValue(attrValue.getValue());
		response.setAttribute(attribute);
		
		logger.info("ObjectWs.setAttributeValue() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{attrId}/getAttributeValue/{objInstId}",method = RequestMethod.GET)
	@CyBssOperation(name = "getAttributeValue")
	public AttributeResponse getAttributeValue(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("attrId") Long attrId,
			@PathVariable("objInstId") Long objInstId
			) throws CyBssException
	{
		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.getAttributeValue() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getAttributeValue",String.class,Long.class,Long.class))
			return response;
		// end checkGrant 
				
		
		response.setAttribute(objectDao.getAttributeValue(objInstId, attrId));
		logger.info("ObjectWs.getAttributeValue() <<<");
		
		return response;
	}

	@RequestMapping(value = "/{attrId}/removeAttributeValue/{objInstId}",method = RequestMethod.GET)
	@CyBssOperation(name = "removeAttributeValue")
	public AttributeResponse removeAttributeValue(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("attrId") 	Long attrId,
			@PathVariable("objInstId") 	Long objInstId
			) throws CyBssException
	{
		AttributeResponse response=new AttributeResponse();
		
		logger.info("ObjectWs.removeAttributeValue() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeAttributeValue",String.class,Long.class,Long.class))
			return response;
		// end checkGrant
		
		objectDao.removeAttributeValue(objInstId,attrId);
		
		logger.info("ObjectWs.getAttributeValue() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{objectId}/getAttributeValues/{objInstId}",method = RequestMethod.GET)
	@CyBssOperation(name = "getAttributeValues")
	public AttributeListResponse getAttributeValues(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("objectId") 	Long objectId,
			@PathVariable("objInstId") 	Long objInstId
			) throws CyBssException
	{
		AttributeListResponse response=new AttributeListResponse();
		
		logger.info("ObjectWs.getAttributeValues() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getAttributeValues",String.class,Long.class,Long.class))
			return response;
		// end checkGrant 
				
		
		response.setAttributes(objectDao.getAttributeValues(objInstId, objectId));
		logger.info("ObjectWs.getAttributeValues() <<<");
		
		return response;
	}
	
}
