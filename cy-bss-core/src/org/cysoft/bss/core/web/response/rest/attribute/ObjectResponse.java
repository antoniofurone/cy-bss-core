package org.cysoft.bss.core.web.response.rest.attribute;

import org.cysoft.bss.core.model.CyBssObject;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ObjectResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	CyBssObject object=null;

	public CyBssObject getObject() {
		return object;
	}

	public void setObject(CyBssObject object) {
		this.object = object;
	}
	
	
}
