package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.MetricScale;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class MetricScaleListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<MetricScale> metricScales=null;
	
	public List<MetricScale> getMetricScales() {
		return metricScales;
	}

	public void setMetricScales(List<MetricScale> metricScales) {
		this.metricScales = metricScales;
	}

}
