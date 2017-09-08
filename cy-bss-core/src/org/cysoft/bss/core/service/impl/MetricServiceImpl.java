package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.model.MetricScale;
import org.cysoft.bss.core.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricServiceImpl extends CyBssServiceBase 
implements MetricService{

	protected MetricDao metricDao=null;
	@Autowired
	public void setMetricDao(MetricDao metricDao){
			this.metricDao=metricDao;
	}
	
	@Override
	public long addMetric(Metric metric) throws CyBssException {
		// TODO Auto-generated method stub
		return metricDao.addMetric(metric);
	}

	@Override
	public void removeMetric(long id) throws CyBssException {
		// TODO Auto-generated method stub
		metricDao.removeMetric(id);
	}

	@Override
	public void updateMetric(long id, Metric metric) throws CyBssException {
		// TODO Auto-generated method stub
		metricDao.updateMetric(id, metric);
	}

	@Override
	public Metric getMetric(long id) {
		// TODO Auto-generated method stub
		return metricDao.getMetric(id);
	}

	@Override
	public List<Metric> getMetricAll() {
		// TODO Auto-generated method stub
		return metricDao.getMetricAll();
	}

	@Override
	public long addMetricScale(MetricScale metricScale) throws CyBssException {
		// TODO Auto-generated method stub
		return metricDao.addMetricScale(metricScale);
	}

	@Override
	public void removeMetricScale(long id) throws CyBssException {
		// TODO Auto-generated method stub
		metricDao.removeMetricScale(id);
	}

	@Override
	public void updateMetricScale(long id, MetricScale metricScale) throws CyBssException {
		// TODO Auto-generated method stub
		metricDao.updateMetricScale(id, metricScale);
	}

	@Override
	public MetricScale getMetricScale(long id) {
		// TODO Auto-generated method stub
		return metricDao.getMetricScale(id);
	}

	@Override
	public List<MetricScale> getMetricScaleAll(long metricId) {
		// TODO Auto-generated method stub
		return metricDao.getMetricScaleAll(metricId);
	}

	@Override
	public long addCurrency(Currency currency) throws CyBssException {
		// TODO Auto-generated method stub
		return metricDao.addCurrency(currency);
	}

	@Override
	public void removeCurrency(long id) throws CyBssException {
		// TODO Auto-generated method stub
		metricDao.removeCurrency(id);
	}

	@Override
	public void updateCurrency(long id, Currency currency) throws CyBssException {
		// TODO Auto-generated method stub
		metricDao.updateCurrency(id, currency);
	}

	@Override
	public Currency getCurrency(long id) {
		// TODO Auto-generated method stub
		return metricDao.getCurrency(id);
	}

	@Override
	public List<Currency> getCurrencyAll() {
		// TODO Auto-generated method stub
		return metricDao.getCurrencyAll();
	}

}
