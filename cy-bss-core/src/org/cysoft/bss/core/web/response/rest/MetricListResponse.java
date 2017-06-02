package org.cysoft.bss.core.web.response.rest;

import java.util.List;

import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.model.MetricScale;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class MetricListResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private List<Metric> metrics=null;
	private List<MetricScale> metricScales=null;
	private List<Currency> currencies=null;
	

	public List<Metric> getMetrics() {
		return metrics;
	}

	public void setMetrics(List<Metric> metrics) {
		this.metrics = metrics;
	}
	
	public List<MetricScale> getMetricScales() {
		return metricScales;
	}

	public void setMetricScales(List<MetricScale> metricScales) {
		this.metricScales = metricScales;
	}

	public List<Currency> getCurrencies() {
		return currencies;
	}

	public void setCurrencies(List<Currency> currencies) {
		this.currencies = currencies;
	}

}
