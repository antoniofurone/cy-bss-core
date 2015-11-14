package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Person;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PersonResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	Person person=null;
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	

}
