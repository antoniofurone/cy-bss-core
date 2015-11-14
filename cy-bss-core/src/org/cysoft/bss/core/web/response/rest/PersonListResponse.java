package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;
import org.cysoft.bss.core.model.Person;

public class PersonListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	List<Person> persons=null;
	public List<Person> getPersons() {
		return persons;
	}
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
	

}
