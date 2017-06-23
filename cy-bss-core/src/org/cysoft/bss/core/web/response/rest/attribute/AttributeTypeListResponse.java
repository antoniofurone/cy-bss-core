package org.cysoft.bss.core.web.response.rest.attribute;

import java.util.List;

import org.cysoft.bss.core.model.AttributeType;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;

public class AttributeTypeListResponse extends CyBssResponseAdapter{

	private List<AttributeType> attributeTypes=null;

	public List<AttributeType> getAttributeTypes() {
		return attributeTypes;
	}

	public void setAttributeTypes(List<AttributeType> attributeTypes) {
		this.attributeTypes = attributeTypes;
	}
	
}
