package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.model.MetricScale;

public interface MetricDao {
	
	public long addMetric(Metric metric) throws CyBssException;
	public void removeMetric(long id) throws CyBssException;
	public void updateMetric(long id,Metric metric) throws CyBssException;
	public Metric getMetric(long id);
	public List<Metric> getMetricAll();
	
	public long addMetricScale(MetricScale metricScale) throws CyBssException;
	public void removeMetricScale(long id) throws CyBssException;
	public void updateMetricScale(long id,MetricScale metricScale) throws CyBssException;
	public MetricScale getMetricScale(long id);
	public List<MetricScale> getMetricScaleAll(long metricId);
	
	
	public long addCurrency(Currency currency) throws CyBssException;
	public void removeCurrency(long id) throws CyBssException;
	public void updateCurrency(long id,Currency currency) throws CyBssException;
	public Currency getCurrency(long id);
	public List<Currency> getCurrencyAll();
	
}
