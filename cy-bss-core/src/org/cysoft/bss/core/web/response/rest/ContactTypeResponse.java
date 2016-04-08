package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.ContactType;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ContactTypeResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private ContactType contactType=null;

	public ContactType getContactType() {
		return contactType;
	}

	public void setContactType(ContactType contactType) {
		this.contactType = contactType;
	}
	

}
