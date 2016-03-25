package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Contact;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ContactResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private Contact contact=null;

	public Contact getContanct() {
		return contact;
	}

	public void setContanct(Contact contact) {
		this.contact = contact;
	}
	

}
