package org.cysoft.bss.core.dao.mysql;

import java.util.List;

import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.model.AttributeType;
import org.cysoft.bss.core.model.AttributeValue;
import org.cysoft.bss.core.model.CyBssObject;

public class ObjectMysql extends CyBssMysqlDao
implements ObjectDao{

	@Override
	public List<CyBssObject> getObjectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CyBssObject getByEntity(String entityName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CyBssObject getByName(String objectName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttributeType> getAttributeTypeAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long addAttribute(long objectId, Attribute attr) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Attribute> getAttributes(long objectId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute getAttribute(long attrId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Attribute getByName(long objectId, String attrName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAttribute(Attribute attr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeAttribute(long attrId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAttributeValue(long id, long attrId, String value) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AttributeValue getAttributeValue(long id, long attrId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AttributeValue> getAttributeValues(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeAttributeValue(long id, long attrId) {
		// TODO Auto-generated method stub
		
	}

}
