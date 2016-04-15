package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.model.AttributeType;
import org.cysoft.bss.core.model.AttributeValue;
import org.cysoft.bss.core.model.CyBssObject;

public interface ObjectDao {
	
	public List<CyBssObject> getObjectAll();
	public CyBssObject getByEntity(String entityName);
	public CyBssObject getByName(String objectName);
	
	public List<AttributeType> getAttributeTypeAll();
	
	public long addAttribute(long objectId,Attribute attr);
	public List<Attribute> getAttributes(long objectId);
	public Attribute getAttribute(long attrId);
	public Attribute getByName(long objectId,String attrName);
	public void updateAttribute(Attribute attr);
	public void removeAttribute(long attrId);
	
	public void setAttributeValue(long id,long attrId,String value);
	public AttributeValue getAttributeValue(long id,long attrId);
	public List<AttributeValue> getAttributeValues(long id);
	public void removeAttributeValue(long id,long attrId);
	
}