package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.ServerDao;
import org.cysoft.bss.core.model.Server;
import org.cysoft.bss.core.model.ServerCommand;
import org.cysoft.bss.core.model.ServerQueueItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ServerMysql extends CyBssMysqlDao
	implements ServerDao {
	
	

	private static final Logger logger = LoggerFactory.getLogger(ServerMysql.class);
	
	@Override
	public long add(Server server) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ServerMySql.add() >>>");
		
		String cmd="insert into BSST_SER_SERVER(SER_S_NODE_ID)";
		cmd+=" values (?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+server+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					server.getNodeId()	
					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ServerMySql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public List<Server> getServerAll() {
		// TODO Auto-generated method stub
		
		String query="select SER_N_SERVER_ID,SER_S_NODE_ID,SER_S_IP,SER_S_MACHINE,";
		query+="SER_S_STATUS,SER_D_START_DATE,SER_D_STOP_DATE";
		query+=" from BSST_SER_SERVER";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		List<Server> ret=jdbcTemplate.query(query, new RowMapperServer());
				
		return ret;
	}
	
	
	private class RowMapperServer implements RowMapper<Server>{

		@Override
		public Server mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Server server=new Server();
			
			server.setId(rs.getLong("SER_N_SERVER_ID"));
			server.setNodeId(rs.getString("SER_S_NODE_ID"));
			server.setIp(rs.getString("SER_S_IP")==null?"":rs.getString("SER_S_IP"));
			server.setMachine(rs.getString("SER_S_MACHINE")==null?"":rs.getString("SER_S_MACHINE"));
			server.setStatus(rs.getString("SER_S_STATUS"));
			server.setStartDate(rs.getString("SER_D_START_DATE")==null?"":rs.getString("SER_D_START_DATE"));
			server.setStopDate(rs.getString("SER_D_STOP_DATE")==null?"":rs.getString("SER_D_STOP_DATE"));
			
			return server;
		}
	}

	

	@Override
	public Server getServer(String nodeId) {
		// TODO Auto-generated method stub
		logger.info("ServerMysql.getServer() >>>");
		
		String query="select SER_N_SERVER_ID,SER_S_NODE_ID,SER_S_IP,SER_S_MACHINE,";
		query+="SER_S_STATUS,SER_D_START_DATE,SER_D_STOP_DATE";
		query+=" from BSST_SER_SERVER where SER_S_NODE_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+nodeId+"]");
		
		Server ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { nodeId },new RowMapperServer());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ServerMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		
		logger.info("ServerMysql.getServer() <<<");
		return ret;
	
	}

	@Override
	public Server getServer(long id) {
		// TODO Auto-generated method stub
		logger.info("ServerMysql.getServer() >>>");
		
		String query="select SER_N_SERVER_ID,SER_S_NODE_ID,SER_S_IP,SER_S_MACHINE,";
		query+="SER_S_STATUS,SER_D_START_DATE,SER_D_STOP_DATE";
		query+=" from BSST_SER_SERVER where SER_N_SERVER_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+id+"]");
		
		Server ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapperServer());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ServerMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		
		logger.info("ServerMysql.getServer() <<<");
		return ret;
	}

	
	@Override
	public void remove(long id) {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_SER_SERVER where SER_N_SERVER_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{id});
		 
		
	}
	
	@Override
	public void update(long id, Server server) {
		// TODO Auto-generated method stub
		String cmd="update BSST_SER_SERVER set SER_S_NODE_ID=?,SER_S_IP=?,SER_S_MACHINE=?";
		cmd+="	where SER_N_SERVER_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+server+"]");
		
		
		List<Object> parms=new ArrayList<Object>();
		parms.add(server.getNodeId());
		parms.add((server.getIp()==null || server.getIp().equals(""))?null:server.getIp());
		parms.add((server.getMachine()==null || server.getMachine().equals(""))?null:server.getMachine());
		parms.add(id);
		
		jdbcTemplate.update(cmd, parms.toArray());
		
	}

	@Override
	public void changeStatus(long id,String status) {
		// TODO Auto-generated method stub
		String cmd="update BSST_SER_SERVER set SER_S_STATUS=?";
		
		if (status.equals(Server.STATUS_RUNNING))
			cmd+=",SER_D_START_DATE=now()";
		else
			if (status.equals(Server.STATUS_STOPPED))
				cmd+=",SER_D_STOP_DATE=now()";
			
		cmd+="	where SER_N_SERVER_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+status+"]");
		
		jdbcTemplate.update(cmd, new Object[]{status,id});
	}

		
	
	@Override
	public long addCommand(ServerCommand serverCommand) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ServerMySql.addCommand() >>>");
		
		
		if (serverCommand.getRequestedExecDate()==null || serverCommand.getRequestedExecDate().equals(""))
			serverCommand.setRequestedExecDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd_HH_mm_ss));
		
		String cmd="insert into BSST_SEC_SERVER_COMMAND(SEC_S_COMMAND,SER_N_SERVER_ID,SEC_D_REQUESTED_EXEC_DATE)";
		cmd+=" values (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+serverCommand+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					serverCommand.getCommand(),
					serverCommand.getServerId(),
					serverCommand.getRequestedExecDate()
					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ServerMySql.addCommand() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public void removeCommand(long id) {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_SEC_SERVER_COMMAND where SEC_N_SERVER_COMMAND_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{id});
		
	}
	
	@Override
	public void removeCommandsByServer(long serverId) {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_SEC_SERVER_COMMAND where SER_N_SERVER_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+serverId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{serverId});
		
		
	}

	@Override
	public List<ServerCommand> getCommands(long idServer, String status, String dateMinExecution,
			String dateMaxExecution, String dateMinRequestedExe, String dateMaxRequesteExe) {
		// TODO Auto-generated method stub
		
		String query="select SEC_N_SERVER_COMMAND_ID,SEC_S_COMMAND,SER_N_SERVER_ID,SEC_D_REQUESTED_EXEC_DATE,";
		query+="SEC_D_EXECUTION_DATE,SEC_S_STATUS,SEC_S_RESULT ";
		query+="from BSST_SEC_SERVER_COMMAND";
		
		if (idServer!=0 ||  
			(status!=null && !status.equals("")) ||
			(dateMinExecution!=null && !dateMinExecution.equals("")) ||
			(dateMaxExecution!=null && !dateMaxExecution.equals("")) ||
			(dateMinRequestedExe!=null && !dateMinRequestedExe.equals("")) ||
			(dateMaxRequesteExe!=null && !dateMaxRequesteExe.equals("")) 
			)
			query+=" WHERE ";
			
			boolean insAnd=false;
			List<Object> parms=new ArrayList<Object>();
			if (idServer!=0){
				query+=(insAnd?" AND":"")+" SER_N_SERVER_ID=?";
				insAnd=true;
				parms.add(idServer);
			}
			if (status!=null && !status.equals("")){
				query+=(insAnd?" AND":"")+" SEC_S_STATUS=?";
				insAnd=true;
				parms.add(status);
			}
			if (dateMinExecution!=null && !dateMinExecution.equals("")){
				query+=(insAnd?" AND":"")+" SEC_D_EXECUTION_DATE>=?";
				insAnd=true;
				parms.add(dateMinExecution);
			}
			if (dateMaxExecution!=null && !dateMaxExecution.equals("")){
				query+=(insAnd?" AND":"")+" SEC_D_EXECUTION_DATE<=?";
				insAnd=true;
				parms.add(dateMaxExecution);
			}
			if (dateMinRequestedExe!=null && !dateMinRequestedExe.equals("")){
				query+=(insAnd?" AND":"")+" SEC_D_REQUESTED_EXEC_DATE>=?";
				insAnd=true;
				parms.add(dateMinRequestedExe);
			}
			if (dateMaxRequesteExe!=null && !dateMaxRequesteExe.equals("")){
				query+=(insAnd?" AND":"")+" SEC_D_REQUESTED_EXEC_DATE<=?";
				insAnd=true;
				parms.add(dateMaxRequesteExe);
			}
			
			query+=" order by SEC_D_REQUESTED_EXEC_DATE";
		
			JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
			logger.info(query+"[idServer="+idServer+";status="+status+";dateMinExecution="+dateMinExecution
					+";dateMaxExecution="+dateMaxExecution+";dateMinRequestedExe="+dateMinRequestedExe+";dateMaxRequesteExe="+dateMaxRequesteExe
					+"]");
			
			List<ServerCommand> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperServerCommand());
			return ret;
	}
	
	@Override
	public ServerCommand getCommand(long id) {
		// TODO Auto-generated method stub
		logger.info("ServerMysql.getCommand() >>>");
		
		String query="select SEC_N_SERVER_COMMAND_ID,SEC_S_COMMAND,SER_N_SERVER_ID,SEC_D_REQUESTED_EXEC_DATE,";
		query+="SEC_D_EXECUTION_DATE,SEC_S_STATUS,SEC_S_RESULT ";
		query+="from BSST_SEC_SERVER_COMMAND ";
		query+="where SEC_N_SERVER_COMMAND_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+id+"]");
		
		ServerCommand ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapperServerCommand());
		
		logger.info("ServerMysql.getCommand() <<<");
		return ret;

	}


	class RowMapperServerCommand implements RowMapper<ServerCommand> {

		@Override
		public ServerCommand mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			ServerCommand command=new ServerCommand();
			
			command.setId(rs.getLong("SEC_N_SERVER_COMMAND_ID"));
			command.setCommand(rs.getString("SEC_S_COMMAND"));
			command.setServerId(rs.getLong("SER_N_SERVER_ID"));
			command.setRequestedExecDate(rs.getString("SEC_D_REQUESTED_EXEC_DATE"));
			command.setExecutionDate(rs.getString("SEC_D_EXECUTION_DATE")==null?"":rs.getString("SEC_D_EXECUTION_DATE"));
			command.setStatus(rs.getString("SEC_S_STATUS"));
			command.setResult(rs.getString("SEC_S_RESULT")==null?"":rs.getString("SEC_S_RESULT"));

			return command;
		}

	}
	
	@Override
	public void startCommand(long id) {
		// TODO Auto-generated method stub
		String cmd="update BSST_SEC_SERVER_COMMAND set SEC_S_STATUS=?, SEC_D_EXECUTION_DATE=now() where SEC_N_SERVER_COMMAND_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{ServerCommand.STATUS_RUNNING,id});
		
	}

	@Override
	public void endCommand(long id,String result) {
		// TODO Auto-generated method stub
		String cmd="update BSST_SEC_SERVER_COMMAND set SEC_S_STATUS=?, SEC_S_RESULT=? where SEC_N_SERVER_COMMAND_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{ServerCommand.STATUS_EXECUTED,result,id});
		
	}
	

	@Override
	public long addQueueItem(ServerQueueItem item) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ServerMySql.addQueueItem() >>>");
		
		
		if (item.getRequestedExecDate()==null || item.getRequestedExecDate().equals(""))
			item.setRequestedExecDate(CyBssUtility.dateToString(CyBssUtility.getCurrentDate(),CyBssUtility.DATE_yyyy_MM_dd_HH_mm_ss));
		
		String cmd="insert into BSST_SEQ_SERVER_QUEUE(OBJ_N_OBJECT_ID,SEQ_N_OBJ_INST_ID,SEQ_D_REQUESTED_EXEC_DATE)";
		cmd+=" values (?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+item+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					item.getObjectId(),
					item.getObjectInstId(),
					item.getRequestedExecDate()
					});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ServerMySql.addQueueItem() <<<");
		
		return getLastInsertId(jdbcTemplate);

	}

	@Override
	public void removeQueueItem(long id) {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_SEQ_SERVER_QUEUE where SEQ_N_QUEUE_ITEM_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{id});
		
	}

	@Override
	public void removeQueueItemsByServer(long serverId) {
		// TODO Auto-generated method stub
		String cmd="delete from BSST_SEQ_SERVER_QUEUE where SER_N_SERVER_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+serverId+"]");
		
		jdbcTemplate.update(cmd, new Object[]{serverId});

	}
	
	@Override
	public List<ServerQueueItem> getQueueItems(long idServer, String status, String dateMinExecution,
			String dateMaxExecution, String dateMinRequestedExe, String dateMaxRequesteExe) {
		// TODO Auto-generated method stub
		
		String query="select a.SEQ_N_QUEUE_ITEM_ID, a.OBJ_N_OBJECT_ID,c.OBJ_S_NAME,a.SEQ_N_OBJ_INST_ID,";
		query+="a.SER_N_SERVER_ID, b.SER_S_NODE_ID,a.SEQ_D_REQUESTED_EXEC_DATE,	a.SEQ_D_START_EXEC_DATE,";
		query+="a.SEQ_D_END_EXEC_DATE,a.SEQ_S_STATUS,a.SEQ_S_RESULT ";
		query+="from BSST_SEQ_SERVER_QUEUE a ";
		query+="left join BSST_SER_SERVER b on b.SER_N_SERVER_ID=a.SER_N_SERVER_ID ";
		query+="join BSST_OBJ_OBJECT c on c.OBJ_N_OBJECT_ID=a.OBJ_N_OBJECT_ID ";
		
		if (idServer!=0 ||  
			(status!=null && !status.equals("")) ||
			(dateMinExecution!=null && !dateMinExecution.equals("")) ||
			(dateMaxExecution!=null && !dateMaxExecution.equals("")) ||
			(dateMinRequestedExe!=null && !dateMinRequestedExe.equals("")) ||
			(dateMaxRequesteExe!=null && !dateMaxRequesteExe.equals("")) 
			)
			query+=" WHERE ";
			
			boolean insAnd=false;
			List<Object> parms=new ArrayList<Object>();
			if (idServer!=0){
				query+=(insAnd?" AND":"")+" a.SER_N_SERVER_ID=?";
				insAnd=true;
				parms.add(idServer);
			}
			if (status!=null && !status.equals("")){
				query+=(insAnd?" AND":"")+" a.SEQ_S_STATUS=?";
				insAnd=true;
				parms.add(status);
			}
			if (dateMinExecution!=null && !dateMinExecution.equals("")){
				query+=(insAnd?" AND":"")+" a.SEQ_D_START_EXEC_DATE>=?";
				insAnd=true;
				parms.add(dateMinExecution);
			}
			if (dateMaxExecution!=null && !dateMaxExecution.equals("")){
				query+=(insAnd?" AND":"")+" a.SEQ_D_START_EXEC_DATE<=?";
				insAnd=true;
				parms.add(dateMaxExecution);
			}
			if (dateMinRequestedExe!=null && !dateMinRequestedExe.equals("")){
				query+=(insAnd?" AND":"")+" a.SEQ_D_REQUESTED_EXEC_DATE>=?";
				insAnd=true;
				parms.add(dateMinRequestedExe);
			}
			if (dateMaxRequesteExe!=null && !dateMaxRequesteExe.equals("")){
				query+=(insAnd?" AND":"")+" a.SEQ_D_REQUESTED_EXEC_DATE<=?";
				insAnd=true;
				parms.add(dateMaxRequesteExe);
			}
			
			query+=" order by SEQ_D_REQUESTED_EXEC_DATE";
		
			JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
			logger.info(query+"[idServer="+idServer+";status="+status+";dateMinExecution="+dateMinExecution
					+";dateMaxExecution="+dateMaxExecution+";dateMinRequestedExe="+dateMinRequestedExe+";dateMaxRequesteExe="+dateMaxRequesteExe
					+"]");
			
			List<ServerQueueItem> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperServerQueueItem());
			return ret;

	}
	
	@Override
	public ServerQueueItem getQueueItem(long id) {
		// TODO Auto-generated method stub
		logger.info("ServerMysql.getQueueItem() >>>");
		
		String query="select a.SEQ_N_QUEUE_ITEM_ID, a.OBJ_N_OBJECT_ID,c.OBJ_S_NAME,a.SEQ_N_OBJ_INST_ID,";
		query+="a.SER_N_SERVER_ID, b.SER_S_NODE_ID,a.SEQ_D_REQUESTED_EXEC_DATE,	a.SEQ_D_START_EXEC_DATE,";
		query+="a.SEQ_D_END_EXEC_DATE,a.SEQ_S_STATUS,a.SEQ_S_RESULT ";
		query+="from BSST_SEQ_SERVER_QUEUE a ";
		query+="left join BSST_SER_SERVER b on b.SER_N_SERVER_ID=a.SER_N_SERVER_ID ";
		query+="join BSST_OBJ_OBJECT c on c.OBJ_N_OBJECT_ID=a.OBJ_N_OBJECT_ID ";
		query+="where a.SEQ_N_QUEUE_ITEM_ID=?";
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+id+"]");
		
		ServerQueueItem ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapperServerQueueItem());
		
		logger.info("ServerMysql.getQueueItem() <<<");
		return ret;
	}

	
	class RowMapperServerQueueItem implements RowMapper<ServerQueueItem> {

		@Override
		public ServerQueueItem mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			ServerQueueItem item=new ServerQueueItem();
			
			item.setId(rs.getLong("SEQ_N_QUEUE_ITEM_ID"));
			item.setObjectId(rs.getLong("OBJ_N_OBJECT_ID"));
			item.setObjectName(rs.getString("OBJ_S_NAME"));
			item.setObjectInstId(rs.getLong("SEQ_N_OBJ_INST_ID"));
			
			item.setServerId(rs.getLong("SER_N_SERVER_ID"));
			item.setServerNodeId(rs.getString("SER_S_NODE_ID"));
			item.setRequestedExecDate(rs.getString("SEQ_D_REQUESTED_EXEC_DATE"));
			
			item.setStartExecDate(rs.getString("SEQ_D_START_EXEC_DATE")==null?"":rs.getString("SEQ_D_START_EXEC_DATE"));
			item.setEndExecDate(rs.getString("SEQ_D_END_EXEC_DATE")==null?"":rs.getString("SEQ_D_END_EXEC_DATE"));
			
			item.setStatus(rs.getString("SEQ_S_STATUS"));
			item.setResult(rs.getString("SEQ_S_RESULT")==null?"":rs.getString("SEQ_S_RESULT"));

			return item;
		}

	}

	@Override
	public void lockQueueItem(long id, long idServer) {
		// TODO Auto-generated method stub
		String cmd="update BSST_SEQ_SERVER_QUEUE set SER_N_SERVER_ID=? where SEQ_N_QUEUE_ITEM_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+idServer+","+id+"]");
		
		jdbcTemplate.update(cmd, new Object[]{idServer,id});
	}

	@Override
	public void startRunQueueItem(long id) {
		// TODO Auto-generated method stub
		String cmd="update BSST_SEQ_SERVER_QUEUE set SEQ_S_STATUS=?, SEQ_D_START_EXEC_DATE=now() where SEQ_N_QUEUE_ITEM_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+"]");
		jdbcTemplate.update(cmd, new Object[]{ServerCommand.STATUS_RUNNING,id});
	}

	@Override
	public void endRunQueueItem(long id,String result) {
		// TODO Auto-generated method stub
		String cmd="update BSST_SEQ_SERVER_QUEUE set SEQ_S_STATUS=?, SEQ_D_END_EXEC_DATE=now(), SEQ_S_RESULT=? where SEQ_N_QUEUE_ITEM_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+result+"]");
		
		jdbcTemplate.update(cmd, new Object[]{ServerCommand.STATUS_EXECUTED,result,id});
		
	}
	
		
}
