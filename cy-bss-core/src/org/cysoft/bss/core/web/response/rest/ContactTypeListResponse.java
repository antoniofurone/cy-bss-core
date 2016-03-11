package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.ContactType;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ContactTypeListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<ContactType> contactTypes=null;

	public List<ContactType> getContactTypes() {
		return contactTypes;
	}

	public void setContactTypes(List<ContactType> contactTypes) {
		this.contactTypes = contactTypes;
	}
	

}
