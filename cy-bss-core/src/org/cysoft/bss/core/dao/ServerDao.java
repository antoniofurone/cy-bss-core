package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Server;
import org.cysoft.bss.core.model.ServerCommand;
import org.cysoft.bss.core.model.ServerQueueItem;

public interface ServerDao {
	
	public long add(Server server) throws CyBssException;
	public List<Server> getServerAll();
	
	public Server getServer(String nodeId);
	public Server getServer(long id); 
	
	public void changeStatus(long id,String status);
	
	public void update(long id, Server server);
	public void remove(long id);
	
	
	// Commands
	public long addCommand(ServerCommand serverCommand) throws CyBssException;
	public void removeCommand(long id);
	public void removeCommandsByServer(long serverId);
	
	public List<ServerCommand> getCommands(long idServer,String status,
			String dateMinExecution,String dateMaxExecution,
			String dateMinRequestedExe,String dateMaxRequesteExe);
	
	public ServerCommand getCommand(long id);
	public void startCommand(long id);
	public void endCommand(long id,String result);

	// Queue
	public long addQueueItem(ServerQueueItem item) throws CyBssException;
	public void removeQueueItem(long id);
	public void removeQueueItemsByServer(long serverId);
	
	public List<ServerQueueItem> getQueueItems(long idServer,String status,
			String dateMinExecution,String dateMaxExecution,
			String dateMinRequestedExe,String dateMaxRequesteExe);
	
	public ServerQueueItem getQueueItem(long id);
	public void lockQueueItem(long id,long idServer);
	public void startRunQueueItem(long id);
	public void endRunQueueItem(long id,String result);
	
}
