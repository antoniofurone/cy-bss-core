package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.model.AttributeType;
import org.cysoft.bss.core.model.CyBssObject;
import org.cysoft.bss.core.service.ObjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ObjectServiceImpl extends CyBssServiceBase 
implements ObjectService {

	protected ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}
	@Override
	public List<CyBssObject> getObjectAll() {
		// TODO Auto-generated method stub
		return objectDao.getObjectAll();
	}
	@Override
	public CyBssObject getByEntity(String entityName) {
		// TODO Auto-generated method stub
		return objectDao.getByEntity(entityName);
	}
	@Override
	public CyBssObject getByName(String objectName) {
		// TODO Auto-generated method stub
		return objectDao.getByName(objectName);
	}
	@Override
	public CyBssObject get(long id) {
		// TODO Auto-generated method stub
		return objectDao.get(id);
	}
	@Override
	public List<AttributeType> getAttributeTypeAll() {
		// TODO Auto-generated method stub
		return objectDao.getAttributeTypeAll();
	}
	@Override
	public long addAttribute(Attribute attr) {
		// TODO Auto-generated method stub
		return objectDao.addAttribute(attr);
	}
	@Override
	public List<Attribute> getAttributes(long objectId) {
		// TODO Auto-generated method stub
		return objectDao.getAttributes(objectId);
	}
	@Override
	public Attribute getAttribute(long attrId) {
		// TODO Auto-generated method stub
		return objectDao.getAttribute(attrId);
	}
	@Override
	public Attribute getAttributeByName(long objectId, String attrName) {
		// TODO Auto-generated method stub
		return objectDao.getAttributeByName(objectId, attrName);
	}
	@Override
	public void updateAttribute(Attribute attr) {
		// TODO Auto-generated method stub
		objectDao.updateAttribute(attr);
	}
	@Override
	public void removeAttribute(final long attrId) {
		// TODO Auto-generated method stub
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				objectDao.removeAttributeValues(attrId);
				objectDao.removeAttribute(attrId);
			}
		});
		
	}
	@Override
	public void setAttributeValue(long id, long attrId, String value) {
		// TODO Auto-generated method stub
		objectDao.setAttributeValue(id, attrId, value);
	}
	@Override
	public Attribute getAttributeValue(long id, long attrId) {
		// TODO Auto-generated method stub
		return objectDao.getAttributeValue(id, attrId);
	}
	@Override
	public List<Attribute> getAttributeValues(long id, long objectId) {
		// TODO Auto-generated method stub
		return objectDao.getAttributeValues(id, objectId);
	}
	@Override
	public void removeAttributeValue(long id, long attrId) {
		// TODO Auto-generated method stub
		objectDao.removeAttributeValue(id, attrId);
	}
	@Override
	public void removeAttributeValues(long id, String entityName) {
		// TODO Auto-generated method stub
		objectDao.removeAttributeValues(id, entityName);
	}
	

}
