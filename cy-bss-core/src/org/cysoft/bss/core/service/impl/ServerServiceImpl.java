package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ServerDao;
import org.cysoft.bss.core.model.Server;
import org.cysoft.bss.core.model.ServerCommand;
import org.cysoft.bss.core.model.ServerQueueItem;
import org.cysoft.bss.core.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class ServerServiceImpl extends CyBssServiceBase 
implements ServerService{
	
	protected ServerDao serverDao=null;
	@Autowired
	public void setServerDao(ServerDao serverDao){
			this.serverDao=serverDao;
	}

	@Override
	public long add(Server server) throws CyBssException {
		// TODO Auto-generated method stub
		return serverDao.add(server);
	}

	@Override
	public List<Server> getServerAll() {
		// TODO Auto-generated method stub
		return serverDao.getServerAll();
	}

	@Override
	public Server getServer(String nodeId) {
		// TODO Auto-generated method stub
		return serverDao.getServer(nodeId);
	}

	@Override
	public Server getServer(long id) {
		// TODO Auto-generated method stub
		return serverDao.getServer(id);
	}

	@Override
	public void changeStatus(long id, String status) {
		// TODO Auto-generated method stub
		serverDao.changeStatus(id, status);
	}

	@Override
	public void update(long id, Server server) {
		// TODO Auto-generated method stub
		serverDao.update(id, server);
	}

	@Override
	public void remove(final long id) {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				try {
					serverDao.removeCommandsByServer(id);
					serverDao.removeQueueItemsByServer(id);
					serverDao.remove(id);
				} catch (Throwable e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});
		
	}

	@Override
	public long addCommand(ServerCommand serverCommand) throws CyBssException {
		// TODO Auto-generated method stub
		return serverDao.addCommand(serverCommand);
	}

	@Override
	public void removeCommand(long id) {
		// TODO Auto-generated method stub
		serverDao.removeCommand(id);
	}

	@Override
	public void removeCommandsByServer(long serverId) {
		// TODO Auto-generated method stub
		serverDao.removeCommandsByServer(serverId);
	}

	@Override
	public List<ServerCommand> getCommands(long idServer, String status, String dateMinExecution,
			String dateMaxExecution, String dateMinRequestedExe, String dateMaxRequesteExe) {
		// TODO Auto-generated method stub
		return serverDao.getCommands(idServer, status, dateMinExecution, 
				dateMaxExecution, dateMinRequestedExe, dateMaxRequesteExe);
	}

	@Override
	public ServerCommand getCommand(long id) {
		// TODO Auto-generated method stub
		return serverDao.getCommand(id);
	}

	
	@Override
	public void startCommand(long id) {
		// TODO Auto-generated method stub
		serverDao.startCommand(id);
	}

	@Override
	public void endCommand(long id, String result) {
		// TODO Auto-generated method stub
		serverDao.endCommand(id, result);
	}

	@Override
	public long addQueueItem(ServerQueueItem item) throws CyBssException {
		// TODO Auto-generated method stub
		return serverDao.addQueueItem(item);
	}

	@Override
	public void removeQueueItem(long id) {
		// TODO Auto-generated method stub
		serverDao.removeQueueItem(id);
	}

	@Override
	public void removeQueueItemsByServer(long serverId) {
		// TODO Auto-generated method stub
		serverDao.removeQueueItemsByServer(serverId);
	}

	@Override
	public List<ServerQueueItem> getQueueItems(long idServer, String status, String dateMinExecution,
			String dateMaxExecution, String dateMinRequestedExe, String dateMaxRequesteExe) {
		// TODO Auto-generated method stub
		return serverDao.getQueueItems(idServer, status, dateMinExecution, dateMaxExecution, 
				dateMinRequestedExe, dateMaxRequesteExe);
	}

	@Override
	public void lockQueueItem(long id, long idServer) {
		// TODO Auto-generated method stub
		serverDao.lockQueueItem(id, idServer);
	}

	@Override
	public void startRunQueueItem(long id) {
		// TODO Auto-generated method stub
		serverDao.startRunQueueItem(id);
	}

	@Override
	public void endRunQueueItem(long id, String result) {
		// TODO Auto-generated method stub
		serverDao.endRunQueueItem(id, result);
	}

	
	@Override
	public ServerQueueItem getQueueItem(long id) {
		// TODO Auto-generated method stub
		return serverDao.getQueueItem(id);
	}
	

}
