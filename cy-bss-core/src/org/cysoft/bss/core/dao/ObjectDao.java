package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.model.AttributeType;
import org.cysoft.bss.core.model.CyBssObject;

public interface ObjectDao {
	
	public List<CyBssObject> getObjectAll();
	public CyBssObject getByEntity(String entityName);
	public CyBssObject getByName(String objectName);
	public CyBssObject get(long objectId);
	
	
	public List<AttributeType> getAttributeTypeAll();
	
	public long addAttribute(Attribute attr);
	public List<Attribute> getAttributes(long objectId);
	public Attribute getAttribute(long attrId);
	public Attribute getAttributeByName(long objectId,String attrName);
	public void updateAttribute(Attribute attr);
	public void removeAttribute(long attrId);
	
	public void setAttributeValue(long objInstId,long attrId,String value);
	public Attribute getAttributeValue(long attrId,long objInstId);
	public List<Attribute> getAttributeValues(long objInstId, long objectId);
	public void removeAttributeValue(long objInstId,long attrId);
	public void removeAttributeValues(long objInstId,String entityName);
	public void removeAttributeValues(long attrId);
}