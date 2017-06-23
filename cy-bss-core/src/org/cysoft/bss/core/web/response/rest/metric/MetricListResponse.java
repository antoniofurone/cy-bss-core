package org.cysoft.bss.core.web.response.rest.metric;

import java.util.List;

import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class MetricListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<Metric> metrics=null;
	

	public List<Metric> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<Metric> metrics) {
		this.metrics = metrics;
	}
	
}
