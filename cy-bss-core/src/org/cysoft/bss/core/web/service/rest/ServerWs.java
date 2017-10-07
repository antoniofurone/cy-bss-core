package org.cysoft.bss.core.web.service.rest;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Server;
import org.cysoft.bss.core.model.ServerCommand;
import org.cysoft.bss.core.model.ServerQueueItem;
import org.cysoft.bss.core.service.ServerService;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.rest.server.ServerCommandListResponse;
import org.cysoft.bss.core.web.response.rest.server.ServerCommandResponse;
import org.cysoft.bss.core.web.response.rest.server.ServerListResponse;
import org.cysoft.bss.core.web.response.rest.server.ServerQueueItemListResponse;
import org.cysoft.bss.core.web.response.rest.server.ServerQueueItemResponse;
import org.cysoft.bss.core.web.response.rest.server.ServerResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public ServerResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ServerWs.get() >>> id="+id);
		ServerResponse response=new ServerResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class))
			return response;
		// end checkGrant 
		
		Server server=serverService.getServer(id);
		if (server!=null)
			response.setServer(server);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("ServerWs.get() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public ServerResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ServerWs.remove() >>> id="+id);
		ServerResponse response=new ServerResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		serverService.remove(id);
	
		logger.info("ServerWs.remove() <<< ");
	
		return response;
	}

	
	// Commands
	@RequestMapping(value = "/addCommand",method = RequestMethod.POST)
	@CyBssOperation(name = "addCommand")
	public ServerCommandResponse addCommand(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody ServerCommand command
			) throws CyBssException
	{
		ServerCommandResponse response=new ServerCommandResponse();
		logger.info("ServerWs.addCommand() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addCommand",String.class,ServerCommand.class))
			return response;
		// end checkGrant 
				
		logger.info(command.toString());
		
		Server server=serverService.getServer(command.getServerId());
		if (server==null){
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
	
				
		long id=serverService.addCommand(command);
		command.setId(id);
		response.setCommand(command);
				
		logger.info("ServerWs.addCommand() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/getCommands",method = RequestMethod.GET)
	@CyBssOperation(name = "getCommands")
	public ServerCommandListResponse getCommands(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="serverId", required=false, defaultValue="0") Integer serverId,
			@RequestParam(value="status", required=false, defaultValue="") String status,
			@RequestParam(value="dateMinExecution", required=false, defaultValue="") String dateMinExecution,
			@RequestParam(value="dateMaxExecution", required=false, defaultValue="") String dateMaxExecution,
			@RequestParam(value="dateMinRequestedExe", required=false, defaultValue="") String dateMinRequestedExe,
			@RequestParam(value="dateMaxRequestedExe", required=false, defaultValue="") String dateMaxRequestedExe
			) throws CyBssException{
		
		logger.info("ServerWs.getCommands() >>> serverId="+serverId+
				";staus="+status+
				";dateMinExecutione="+dateMinExecution+";dateMaxExecution="+dateMaxExecution+
				";dateMinRequestedExe="+dateMinRequestedExe+";dateMaxRequestedExe="+dateMaxRequestedExe
				);
		
		ServerCommandListResponse response=new ServerCommandListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getCommands",
			String.class,
			Integer.class,
			String.class,
			String.class,String.class,
			String.class,String.class
			))
			return response;
		// end checkGrant 
		
		
		List<ServerCommand> commands=serverService.getCommands(serverId, status, dateMinExecution, dateMaxExecution, 
				dateMinRequestedExe, dateMaxRequestedExe);
		response.setCommands(commands);
		
		logger.info("ServerWs.getCommands() <<< ");
		
		return response;
		
		}
	
	@RequestMapping(value = "/{id}/getCommand",method = RequestMethod.GET)
	@CyBssOperation(name = "getCommand")
	public ServerCommandResponse getCommand(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ServerWs.getCommand() >>> id="+id);
		ServerCommandResponse response=new ServerCommandResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getCommand",String.class,Long.class))
			return response;
		// end checkGrant 
		
		ServerCommand command=serverService.getCommand(id);
		if (command!=null)
			response.setCommand(command);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("ServerWs.getCommand() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeCommand",method = RequestMethod.GET)
	@CyBssOperation(name = "removeCommand")
	public ServerCommandResponse removeCommand(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ServerWs.removeCommand() >>> id="+id);
		ServerCommandResponse response=new ServerCommandResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeCommand",String.class,Long.class))
			return response;
		// end checkGrant 
		
		serverService.removeCommand(id);
		logger.info("ServerWs.removeCommand() <<< ");
		return response;
	}
	
	// Queue Items
	@RequestMapping(value = "/addQueueItem",method = RequestMethod.POST)
	@CyBssOperation(name = "addQueueItem")
	public ServerQueueItemResponse addQueueItem(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody ServerQueueItem item
			) throws CyBssException
	{
		ServerQueueItemResponse response=new ServerQueueItemResponse();
		logger.info("ServerWs.addQueueItem() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addQueueItem",String.class,ServerQueueItem.class))
			return response;
		// end checkGrant 
				
		logger.info(item.toString());
				
		long id=serverService.addQueueItem(item);
		item.setId(id);
		response.setItem(item);
				
		logger.info("ServerWs.addQueueItem() <<<");
		
		return response;
	}

	@RequestMapping(value = "/getQueueItems",method = RequestMethod.GET)
	@CyBssOperation(name = "getQueueItems")
	public ServerQueueItemListResponse getQueueItems(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="serverId", required=false, defaultValue="0") Integer serverId,
			@RequestParam(value="status", required=false, defaultValue="") String status,
			@RequestParam(value="dateMinExecution", required=false, defaultValue="") String dateMinExecution,
			@RequestParam(value="dateMaxExecution", required=false, defaultValue="") String dateMaxExecution,
			@RequestParam(value="dateMinRequestedExe", required=false, defaultValue="") String dateMinRequestedExe,
			@RequestParam(value="dateMaxRequestedExe", required=false, defaultValue="") String dateMaxRequestedExe
			) throws CyBssException{
		
		logger.info("ServerWs.getQueueItems() >>> serverId="+serverId+
				";staus="+status+
				";dateMinExecutione="+dateMinExecution+";dateMaxExecution="+dateMaxExecution+
				";dateMinRequestedExe="+dateMinRequestedExe+";dateMaxRequestedExe="+dateMaxRequestedExe
				);
		
		ServerQueueItemListResponse response=new ServerQueueItemListResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getQueueItems",
			String.class,
			Integer.class,
			String.class,
			String.class,String.class,
			String.class,String.class
			))
			return response;
		// end checkGrant 
		
		
		List<ServerQueueItem> items=serverService.getQueueItems(serverId, status, dateMinExecution, 
				dateMaxExecution, dateMinRequestedExe, dateMaxRequestedExe);
		response.setItems(items);
		
		logger.info("ServerWs.getQueueItems() <<< ");
		
		return response;
		
		}
	
	@RequestMapping(value = "/{id}/getQueueItem",method = RequestMethod.GET)
	@CyBssOperation(name = "getQueueItem")
	public ServerQueueItemResponse getQueueItem(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ServerWs.getQueueItem() >>> id="+id);
		ServerQueueItemResponse response=new ServerQueueItemResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getQueueItem",String.class,Long.class))
			return response;
		// end checkGrant 
		
		ServerQueueItem item=serverService.getQueueItem(id);
		if (item!=null)
			response.setItem(item);
		else
			setResult(response, ICyBssMessageConst.RESULT_NOT_FOUND, 
					ICyBssMessageConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
	
		logger.info("ServerWs..getQueueItem() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeQueueItem",method = RequestMethod.GET)
	@CyBssOperation(name = "removeQueueItem")
	public ServerQueueItemResponse removeQueueItem(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("ServerWs.removeQueueItem() >>> id="+id);
		ServerQueueItemResponse response=new ServerQueueItemResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeQueueItem",String.class,Long.class))
			return response;
		// end checkGrant 
		
		serverService.removeQueueItem(id);
		logger.info("ServerWs.removeQueueItem() <<< ");
		return response;
	}
	
}
