package org.cysoft.bss.core.web.rest;

import java.util.List;

import org.cysoft.bss.core.dao.CyBssServiceDao;
import org.cysoft.bss.core.model.CyBssServOperation;
import org.cysoft.bss.core.model.CyBssService;
import org.cysoft.bss.core.web.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cybss-service")
public class CyBssServiceWs extends CyBssWebServiceAdapter
	implements ICyBssWebService
{
	
	private static final Logger logger = LoggerFactory.getLogger(CyBssServiceWs.class);
	
	
	private CyBssServiceDao serviceDao=null;
	
	@Autowired
	public void setServiceDao(CyBssServiceDao serviceDao){
			this.serviceDao=serviceDao;
	}
	
	
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public List<CyBssService> getAll(){
		
		logger.info("CyBssServiceWs.getAll() >>>");
		
		return serviceDao.getAll();
	}
	
	
	@RequestMapping(value = "/{servId}/get",method = RequestMethod.GET)
	public CyBssService get(@PathVariable("servId") String id){
		
		logger.info("CyBssServiceWs.get() >>> id="+id);
		
		CyBssService serv=serviceDao.get(id);
		serv.setOperations(serviceDao.getServOperations(id));
		return serv;
	}

	@RequestMapping(value = "/{servId}/getServOperations",method = RequestMethod.GET)
	public List<CyBssServOperation> getOperations(@PathVariable("servId") String id){
		
		logger.info("CyBssServiceWs.getServOperations() >>> id="+id);
		
		return serviceDao.getServOperations(id);
	}

	@RequestMapping(value = "/{opId}/getOperation",method = RequestMethod.GET)
	public CyBssServOperation getOperation(@PathVariable("opId") String id){
		
		logger.info("CyBssServiceWs.getOperation() >>> id="+id);
		
		CyBssServOperation op=serviceDao.getOperation(id);
		op.setParams(serviceDao.getOpParams(id));
		return op;
	}
	
}
