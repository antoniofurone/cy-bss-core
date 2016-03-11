package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.ContactType;

public interface ContactDao {

	public List<ContactType> getContactTypeAll();
	public long add(ContactType contactType) throws CyBssException;
	public void update(long id,ContactType contactType) throws CyBssException;
	public void remove(long id) throws CyBssException;
	public ContactType get(long id);
	
}