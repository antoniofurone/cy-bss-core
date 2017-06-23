package org.cysoft.bss.core.web.response.rest.price;

import java.util.List;

import org.cysoft.bss.core.model.PriceType;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class PriceTypeListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<PriceType> types=null;

	public List<PriceType> getTypes() {
		return types;
	}

	public void setTypes(List<PriceType> types) {
		this.types = types;
	}
	

}
