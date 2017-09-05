package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.model.Contact;
import org.cysoft.bss.core.model.ContactType;
import org.cysoft.bss.core.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactServiceImpl extends CyBssServiceBase 
implements ContactService{

	protected ContactDao contactDao=null;
	@Autowired
	public void setContactDao(ContactDao contactDao){
			this.contactDao=contactDao;
	}
	
	@Override
	public List<ContactType> getContactTypeAll() {
		// TODO Auto-generated method stub
		return contactDao.getContactTypeAll();
	}

	@Override
	public long addType(ContactType contactType) throws CyBssException {
		// TODO Auto-generated method stub
		return contactDao.addType(contactType);
	}

	@Override
	public void updateType(long id, ContactType contactType) throws CyBssException {
		// TODO Auto-generated method stub
		contactDao.updateType(id, contactType);
	}

	@Override
	public void removeType(long id) throws CyBssException {
		// TODO Auto-generated method stub
		contactDao.removeType(id);
	}

	@Override
	public ContactType getType(long id) {
		// TODO Auto-generated method stub
		return contactDao.getType(id);
	}

	@Override
	public long add(Contact contact) throws CyBssException {
		// TODO Auto-generated method stub
		return contactDao.add(contact);
	}

	@Override
	public void update(long id, Contact contact) throws CyBssException {
		// TODO Auto-generated method stub
		contactDao.update(id, contact);
	}

	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		contactDao.remove(id);
	}

	@Override
	public void removeByEntityId(long entityId, String entityName) throws CyBssException {
		// TODO Auto-generated method stub
		contactDao.removeByEntityId(entityId, entityName);
	}

	@Override
	public Contact get(long id) {
		// TODO Auto-generated method stub
		return contactDao.get(id);
	}

	@Override
	public List<Contact> getByEntity(long entityId, String entityName) {
		// TODO Auto-generated method stub
		return contactDao.getByEntity(entityId, entityName);
	}

}
