package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Contact;
import org.cysoft.bss.core.model.ContactType;

public interface ContactDao {

	public List<ContactType> getContactTypeAll();
	public long addType(ContactType contactType) throws CyBssException;
	public void updateType(long id,ContactType contactType) throws CyBssException;
	public void removeType(long id) throws CyBssException;
	public ContactType getType(long id);
	
	public long add(Contact contact) throws CyBssException;
	public void update(long id,Contact contact) throws CyBssException;
	public void remove(long id) throws CyBssException;
	public Contact get(long id);
	public List<Contact> getByEntity(long entityId,String entityName);
}