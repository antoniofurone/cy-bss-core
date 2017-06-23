package org.cysoft.bss.core.web.response.rest.attribute;

import java.util.List;

import org.cysoft.bss.core.model.CyBssObject;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;

public class ObjectListResponse extends CyBssResponseAdapter
{
	
	private List<CyBssObject> cyBssObjects=null;

	public List<CyBssObject> getCyBssObjects() {
		return cyBssObjects;
	}

	public void setCyBssObjects(List<CyBssObject> cyBssObjects) {
		this.cyBssObjects = cyBssObjects;
	}

	
}
