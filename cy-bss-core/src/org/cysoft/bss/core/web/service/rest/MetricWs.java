package org.cysoft.bss.core.web.service.rest;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.MetricListResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/metric")
@CyBssService(name = "Metric")
public class MetricWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(MetricWs.class);
	
	protected MetricDao metricDao=null;
	@Autowired
	public void setMetricDao(MetricDao metricDao){
			this.metricDao=metricDao;
	}
	
		
	@RequestMapping(value = "/getMetricAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getMetricAll")
	public MetricListResponse getMetricAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("MetricWs.getMetricAll() >>>");
		MetricListResponse response=new MetricListResponse(); 
		response.setMetrics(metricDao.getMetricAll());
		return response;
	}
	
	
	@RequestMapping(value = "/getCurrencyAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getCurrencyAll")
	public MetricListResponse getCurrencyAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("MetricWs.getCurrencyAll() >>>");
		MetricListResponse response=new MetricListResponse(); 
		response.setCurrencies(metricDao.getCurrencyAll());
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/getMetricScale",method = RequestMethod.GET)
	@CyBssOperation(name = "getMetricScale")
	public MetricListResponse getMetricScale(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.getMetricScaleAll() >>>");
		MetricListResponse response=new MetricListResponse(); 
		response.setMetricScales(metricDao.getMetricScale(id));
		return response;
	}
	
}
