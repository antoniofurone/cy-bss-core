package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.model.AttributeType;
import org.cysoft.bss.core.model.CyBssObject;

public interface ObjectService {
	
	public List<CyBssObject> getObjectAll();
	public CyBssObject getByEntity(String entityName);
	public CyBssObject getByName(String objectName);
	public CyBssObject get(long id);
	
	
	public List<AttributeType> getAttributeTypeAll();
	
	public long addAttribute(Attribute attr);
	public List<Attribute> getAttributes(long objectId);
	public Attribute getAttribute(long attrId);
	public Attribute getAttributeByName(long objectId,String attrName);
	public void updateAttribute(Attribute attr);
	public void removeAttribute(long attrId);
	
	public void setAttributeValue(long id,long attrId,String value);
	public Attribute getAttributeValue(long id,long attrId);
	public List<Attribute> getAttributeValues(long id, long objectId);
	public void removeAttributeValue(long id,long attrId);
	public void removeAttributeValues(long id,String entityName);
	
}
