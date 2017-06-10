package org.cysoft.bss.core.web.response.rest;

import org.cysoft.bss.core.model.MetricScale;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class MetricScaleResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private MetricScale metricScale=null;

	public MetricScale getMetricScale() {
		return metricScale;
	}

	public void setMetricScale(MetricScale metricScale) {
		this.metricScale = metricScale;
	}
				
	
}
