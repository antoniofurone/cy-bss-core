package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.model.MetricScale;

public interface MetricDao {
	
	public List<Metric> getMetricAll();
	public List<MetricScale> getMetricScale(long metricId);
	public List<Currency> getCurrencyAll();
	

}
