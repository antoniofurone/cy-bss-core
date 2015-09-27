package org.cysoft.bss.core.web.rest;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.BssServiceDao;
import org.cysoft.bss.core.model.BssServOperation;
import org.cysoft.bss.core.model.BssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cybss-service")
public class CyBssServiceWs {
	
	private static final Logger logger = LoggerFactory.getLogger(CyBssServiceWs.class);
	
	
	private BssServiceDao serviceDao=null;
	
	@Autowired
	public void setServiceDao(BssServiceDao serviceDao){
			this.serviceDao=serviceDao;
	}
	
	
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public List<BssService> getAll(){
		
		logger.info("CyBssServiceWs.getAll() >>>");
		
		return serviceDao.getAll();
	}
	
	
	@RequestMapping(value = "/get/{id}",method = RequestMethod.GET)
	public BssService get(@PathVariable("id") String id){
		
		logger.info("CyBssServiceWs.get() >>> id="+id);
		
		BssService serv=serviceDao.get(id);
		serv.setOperations(serviceDao.getServOperations(id));
		return serv;
	}

	@RequestMapping(value = "/getServOperations/{id}",method = RequestMethod.GET)
	public List<BssServOperation> getOperations(@PathVariable("id") String id){
		
		logger.info("CyBssServiceWs.getServOperations() >>> id="+id);
		
		return serviceDao.getServOperations(id);
	}

	@RequestMapping(value = "/getOperation/{id}",method = RequestMethod.GET)
	public BssServOperation getOperation(@PathVariable("id") String id){
		
		logger.info("CyBssServiceWs.getOperation() >>> id="+id);
		
		BssServOperation op=serviceDao.getOperation(id);
		op.setParams(serviceDao.getOpParams(id));
		return op;
	}
	
}
