package org.cysoft.bss.core.web.service.rest;

import java.util.List;

import org.cysoft.bss.core.model.CyBssServOperation;
import org.cysoft.bss.core.model.CyBssService;
import org.cysoft.bss.core.service.CyBssServiceService;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
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
	
	private CyBssServiceService serviceService=null;
	@Autowired
	public void setServiceService(CyBssServiceService serviceService){
			this.serviceService=serviceService;
	}
	
	
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	public List<CyBssService> getAll(){
		
		logger.info("CyBssServiceWs.getAll() >>>");
		
		return serviceService.getAll();
	}
	
	
	@RequestMapping(value = "/{servId}/get",method = RequestMethod.GET)
	public CyBssService get(@PathVariable("servId") String id){
		
		logger.info("CyBssServiceWs.get() >>> id="+id);
		
		CyBssService serv=serviceService.get(id);
		serv.setOperations(serviceService.getServOperations(id));
		return serv;
	}

	@RequestMapping(value = "/{servId}/getServOperations",method = RequestMethod.GET)
	public List<CyBssServOperation> getOperations(@PathVariable("servId") String id){
		
		logger.info("CyBssServiceWs.getServOperations() >>> id="+id);
		
		return serviceService.getServOperations(id);
	}

	@RequestMapping(value = "/{opId}/getOperation",method = RequestMethod.GET)
	public CyBssServOperation getOperation(@PathVariable("opId") String id){
		
		logger.info("CyBssServiceWs.getOperation() >>> id="+id);
		
		CyBssServOperation op=serviceService.getOperation(id);
		op.setParams(serviceService.getOpParams(id));
		return op;
	}
	
}
