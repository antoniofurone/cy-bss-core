package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Person;

public interface PersonDao {
	public void add(Person person) throws CyBssException;
	public Person getByCode(String code);
	public Person get(long id);
	public void update(long id,Person person) throws CyBssException;
	public void remove(long id) throws CyBssException;
	public List<Person> find(String code,String firstName,String secondName);
	
	
}
