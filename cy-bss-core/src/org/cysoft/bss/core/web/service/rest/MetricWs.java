package org.cysoft.bss.core.web.service.rest;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.model.MetricScale;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.country.CurrencyListResponse;
import org.cysoft.bss.core.web.response.rest.metric.CurrencyResponse;
import org.cysoft.bss.core.web.response.rest.metric.MetricListResponse;
import org.cysoft.bss.core.web.response.rest.metric.MetricResponse;
import org.cysoft.bss.core.web.response.rest.metric.MetricScaleListResponse;
import org.cysoft.bss.core.web.response.rest.metric.MetricScaleResponse;
import org.cysoft.bss.core.web.response.rest.product.ProductTypeResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	// Metric
	
	@RequestMapping(value = "/addMetric",method = RequestMethod.POST)
	@CyBssOperation(name = "addMetric")
	public MetricResponse addMetric(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Metric metric
			) throws CyBssException
	{
		MetricResponse response=new MetricResponse();
		
		logger.info("MetricWs.addMetric() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addMetric",String.class,Metric.class))
				return response;
		// end checkGrant 
		
		logger.info(metric.toString());
		long id=metricDao.addMetric(metric);
		metric.setId(id);
		response.setMetric(metric);
		
		logger.info("MetricWs.addMetric() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/getMetric",method = RequestMethod.GET)
	@CyBssOperation(name = "getMetric")
	public MetricResponse getMetric(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.getMetric() >>> id="+id);
		MetricResponse response=new MetricResponse();
		
		Metric metric=metricDao.getMetric(id);
		if (metric!=null)
			response.setMetric(metric);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("MetricWs.getType() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/updateMetric",method = RequestMethod.POST)
	@CyBssOperation(name = "updateMetric")
	public MetricResponse updateMetric(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Metric metric
			) throws CyBssException
	{
		MetricResponse response=new MetricResponse();
		
		logger.info("MetricWs.updateMetric() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateMetric",String.class,Long.class,Metric.class))
			return response;
		// end checkGrant 
		
		if (metricDao.getMetric(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		metricDao.updateMetric(id, metric);
		response.setMetric(metricDao.getMetric(id));
		
		logger.info("MetricWs.updateMetric() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeMetric",method = RequestMethod.GET)
	@CyBssOperation(name = "removeMetric")
	public ProductTypeResponse removeMetric(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.removeMetric() >>> id="+id);
		ProductTypeResponse response=new ProductTypeResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeMetric",String.class,Long.class))
			return response;
		// end checkGrant 
		
		metricDao.removeMetric(id);
	
		logger.info("MetricWs.removeMetric() <<< ");
	
		return response;
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
	
	// Currency
	
	@RequestMapping(value = "/addCurrency",method = RequestMethod.POST)
	@CyBssOperation(name = "addCurrency")
	public CurrencyResponse addCurrency(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Currency currency
			) throws CyBssException
	{
		CurrencyResponse response=new CurrencyResponse();
		
		logger.info("MetricWs.addCurrency() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addCurrency",String.class,Currency.class))
				return response;
		// end checkGrant 
		
		logger.info(currency.toString());
		long id=metricDao.addCurrency(currency);
		currency.setId(id);
		response.setCurrency(currency);
		
		logger.info("MetricWs.addCurrency() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/getCurrency",method = RequestMethod.GET)
	@CyBssOperation(name = "getCurrency")
	public CurrencyResponse getCurrency(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.getCurrency() >>> id="+id);
		CurrencyResponse response=new CurrencyResponse();
		
		Currency currency=metricDao.getCurrency(id);
		if (currency!=null)
			response.setCurrency(currency);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("MetricWs.getCurrency() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/updateCurrency",method = RequestMethod.POST)
	@CyBssOperation(name = "updateCurrency")
	public CurrencyResponse updateCurrency(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Currency currency
			) throws CyBssException
	{
		CurrencyResponse response=new CurrencyResponse();
		
		logger.info("MetricWs.updateCurrency() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateCurrency",String.class,Long.class,Currency.class))
			return response;
		// end checkGrant 
		
		if (metricDao.getMetric(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		metricDao.updateCurrency(id, currency);
		response.setCurrency(metricDao.getCurrency(id));
		
		logger.info("MetricWs.updateCurrency() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeCurrency",method = RequestMethod.GET)
	@CyBssOperation(name = "removeCurrency")
	public CurrencyResponse removeCurrency(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.removeCurrency() >>> id="+id);
		CurrencyResponse response=new CurrencyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeCurrency",String.class,Long.class))
			return response;
		// end checkGrant 
		
		metricDao.removeCurrency(id);
	
		logger.info("MetricWs.removeCurrency() <<< ");
	
		return response;
	}
	
	
	@RequestMapping(value = "/getCurrencyAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getCurrencyAll")
	public CurrencyListResponse getCurrencyAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken
			) throws CyBssException{
		
		logger.info("MetricWs.getCurrencyAll() >>>");
		CurrencyListResponse response=new CurrencyListResponse(); 
		response.setCurrencies(metricDao.getCurrencyAll());
		return response;
	}
	
	
	// MetricScale
	
	@RequestMapping(value = "/addMetricScale",method = RequestMethod.POST)
	@CyBssOperation(name = "addMetricScale")
	public MetricScaleResponse addMetricScale(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody MetricScale scale
			) throws CyBssException
	{
		MetricScaleResponse response=new MetricScaleResponse();
		
		logger.info("MetricWs.addMetricScale() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addMetricScale",String.class,MetricScale.class))
				return response;
		// end checkGrant 
		
		logger.info(scale.toString());
		long id=metricDao.addMetricScale(scale);
		scale.setId(id);
		response.setMetricScale(scale);
		
		logger.info("MetricWs.addMetricScale() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/getMetricScale",method = RequestMethod.GET)
	@CyBssOperation(name = "getMetricScale")
	public MetricScaleResponse getMetricScale(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.getMetricScale() >>> id="+id);
		MetricScaleResponse response=new MetricScaleResponse();
		
		MetricScale scale=metricDao.getMetricScale(id);
		if (scale!=null)
			response.setMetricScale(scale);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("MetricWs.getMetricScale() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/updateMetricScale",method = RequestMethod.POST)
	@CyBssOperation(name = "updateMetricScale")
	public MetricScaleResponse updateMetricScale(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody MetricScale scale
			) throws CyBssException
	{
		MetricScaleResponse response=new MetricScaleResponse();
		
		logger.info("MetricWs.updateMetricScale() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateMetricScale",String.class,Long.class,MetricScale.class))
			return response;
		// end checkGrant 
		
		if (metricDao.getMetricScale(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		metricDao.updateMetricScale(id, scale);
		response.setMetricScale(metricDao.getMetricScale(id));
		
		logger.info("MetricWs.updateMetricScale() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeMetricScale",method = RequestMethod.GET)
	@CyBssOperation(name = "removeMetricScale")
	public CurrencyResponse removeMetricScale(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.removeMetricScale() >>> id="+id);
		CurrencyResponse response=new CurrencyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeMetricScale",String.class,Long.class))
			return response;
		// end checkGrant 
		
		metricDao.removeMetricScale(id);
	
		logger.info("MetricWs.removeMetricScale() <<< ");
	
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/getMetricScaleAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getMetricScaleAll")
	public MetricScaleListResponse getMetricScaleAll(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("MetricWs.getMetricScaleAll() >>>");
		MetricScaleListResponse response=new MetricScaleListResponse(); 
		response.setMetricScales(metricDao.getMetricScaleAll(id));
		return response;
	}
	
}
