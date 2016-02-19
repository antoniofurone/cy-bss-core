package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.PersonRole;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PersonRoleListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<PersonRole> personRoles=null;

	public List<PersonRole> getPersonRoles() {
		return personRoles;
	}

	public void setPersonRoles(List<PersonRole> personRoles) {
		this.personRoles = personRoles;
	}

	
}
