package org.cysoft.bss.core.web.service.rest;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Server;
import org.cysoft.bss.core.service.ServerService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.server.ServerListResponse;
import org.cysoft.bss.core.web.response.rest.server.ServerResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/server")
@CyBssService(name = "Server")
public class ServerWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	
	private static final Logger logger = LoggerFactory.getLogger(ServerWs.class);
	
	protected ServerService serverService=null;
	@Autowired
	public void setServerService(ServerService serverService){
			this.serverService=serverService;
	}
	
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public ServerResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Server server
			) throws CyBssException
	{
		ServerResponse response=new ServerResponse();
		logger.info("ServerWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Server.class))
			return response;
		// end checkGrant 
				
		logger.info(server.toString());
				
		long id=serverService.add(server);
		server.setId(id);
		response.setServer(server);
				
		logger.info("ServerWs.add() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/getAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getAll")
	public ServerListResponse getAll(
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException{
		
		logger.info("Server.getAll() >>> ");
		ServerListResponse response=new ServerListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getAll",String.class))
			return response;
		// end checkGrant 
		
		response.setServers(serverService.getServerAll());
		logger.info("Server.getAll() <<< ");
		
		return response;
	}
	

}
