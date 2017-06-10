package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class MetricResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private Metric metric=null;

	public Metric getMetric() {
		return metric;
	}

	public void setMetric(Metric metric) {
		this.metric = metric;
	}

	
			
	
}
