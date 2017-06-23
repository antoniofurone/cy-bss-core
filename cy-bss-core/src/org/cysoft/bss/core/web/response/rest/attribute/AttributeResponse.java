package org.cysoft.bss.core.web.response.rest.attribute;


import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;

public class AttributeResponse extends CyBssResponseAdapter{

	private Attribute attribute=null;

	public Attribute getAttribute() {
		return attribute;
	}

	public void setAttribute(Attribute attribute) {
		this.attribute = attribute;
	}
	
}
