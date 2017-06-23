package org.cysoft.bss.core.web.response.rest.attribute;

import java.util.List;

import org.cysoft.bss.core.model.Attribute;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;

public class AttributeListResponse extends CyBssResponseAdapter{

	private List<Attribute> attributes=null;

	public List<Attribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<Attribute> attributes) {
		this.attributes = attributes;
	}
	
}
