package org.cysoft.bss.core.web.response.rest.price;

import java.util.List;

import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PriceComponentListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<PriceComponent> components=null;

	public List<PriceComponent> getComponents() {
		return components;
	}

	public void setComponents(List<PriceComponent> components) {
		this.components = components;
	}
	
}
