package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.common.CyBssUtility;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.dao.TicketDao;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.model.Ticket;
import org.cysoft.bss.core.model.TicketCategory;
import org.cysoft.bss.core.model.TicketStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.annotation.Transactional;

public class TicketMysql extends CyBssMysqlDao
	implements TicketDao{

	private static final Logger logger = LoggerFactory.getLogger(TicketMysql.class);
	
	protected  LocationDao locationDao=null;
	@Autowired
	public void setLocationDao(LocationDao locationDao){
			this.locationDao=locationDao;
	}
	
	protected  FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	@Override
	public synchronized long add(Ticket ticket) throws CyBssException {
		// TODO Auto-generated method stub
		
		String cmd="insert into BSST_TIC_TICKET(TIC_S_TEXT,TIC_D_CREATION_DATE,USR_N_USER_ID,TCA_N_CATEGORY_ID,PER_N_PERSON_ID,LOC_N_LOCATION_ID)";
		cmd+=" values ";
		cmd+=" (?,now(),?,?,?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+ticket+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					ticket.getText(), ticket.getUserId(),  
					(ticket.getCategoryId()==0)?null:ticket.getCategoryId(),
					(ticket.getPersonId()==0)?null:ticket.getPersonId(),
					(ticket.getLocationId()==0)?null:ticket.getLocationId()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		String query="SELECT LAST_INSERT_ID()";
		Long ret=jdbcTemplate.queryForObject(query, new Object[] { },new RowMapper<Long>() {
            @Override
            public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
            	return rs.getLong(1);
            }
        });
		
		return ret;
	}


	@Override
	public Ticket get(long id,long langId) {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		
		String query="select  ID,TEXT,CREATION_DATE,STATUS_ID,IFNULL(c.TSL_S_NAME,STATUS_NAME) AS STATUS_NAME,USER_ID,USER_NAME,CATEGORY_ID,IFNULL(b.TCL_S_NAME,CATEGORY_NAME) AS CATEGORY_NAME,";
		query+="PERSON_ID,PERSON_FIRST_NAME,PERSON_SECOND_NAME,LOCATION_ID";
		query+=" from BSSV_TICKET a";
		query+=" left join BSST_TCL_TICKET_CATEGORY_LANG b on a.CATEGORY_ID=b.TCA_N_CATEGORY_ID AND b.LAN_N_LANG_ID=?";
		query+=" left join BSST_TSL_TICKET_STATUS_LANG c on a.STATUS_ID=c.TST_N_STATUS_ID AND c.LAN_N_LANG_ID=?";
	 	query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		Ticket ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { langId,langId,id },new RowMapperTicket());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("UserMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		return ret;

	}
	
	@Override
	@Transactional
	public void update(long id, Ticket ticket,long langId) throws CyBssException {
		// TODO Auto-generated method stub
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		Ticket oldVersion=get(id, langId);
		if (oldVersion.getLocationId()!=0){
			Location location=locationDao.get(oldVersion.getLocationId(),langId);
			if (location.getLocationType().equals(Ticket.ENTITY_NAME)){
			   String cmd="update BSST_TIC_TICKET set LOC_N_LOCATION_ID=? where TIC_N_TICKET_ID=?";
			   logger.info(cmd+"["+id+","+ticket+"]");
			   try {
					jdbcTemplate.update(cmd, new Object[]{
							null,id
						});
					} catch (DataAccessException  e) {
					// TODO Auto-generated catch block
					logger.error(e.toString());
					throw new CyBssException(e);
				}
			   
			   locationDao.remove(location.getId());
			}
		}
		
		if (ticket.getLocation()!=null){
			long locationId=locationDao.add(ticket.getLocation());
			ticket.setLocationId(locationId);
		}
		
		
		String cmd="update BSST_TIC_TICKET set TIC_S_TEXT=?,TCA_N_CATEGORY_ID=?,PER_N_PERSON_ID=?,";
		cmd+="LOC_N_LOCATION_ID=?";
		if (ticket.getStatusId()!=0)
			cmd+=",TST_N_STATUS_ID=?";
		cmd+=" where TIC_N_TICKET_ID=?";
		
		logger.info(cmd+"["+id+","+ticket+"]");
		
		List<Object> parms=new ArrayList<Object>();
		parms.add(ticket.getText());
		parms.add((ticket.getCategoryId()==0)?null:ticket.getCategoryId());
		parms.add((ticket.getPersonId()==0)?null:ticket.getPersonId());
		parms.add((ticket.getLocationId()==0)?null:ticket.getLocationId());
		if (ticket.getStatusId()!=0)
			parms.add(ticket.getStatusId());
		parms.add(id);
		
		try {
			jdbcTemplate.update(cmd, parms.toArray());
			} catch (DataAccessException  e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
	
	}
	
	@Override
	public List<Ticket> find(String text, long categoryId, long statusId,
			long personId,String fromDate,String toDate,long langId) throws CyBssException {
		// TODO Auto-generated method stub
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
				
				
		String query="select  ID,TEXT,CREATION_DATE,STATUS_ID,IFNULL(c.TSL_S_NAME,STATUS_NAME) AS STATUS_NAME,USER_ID,USER_NAME,CATEGORY_ID,IFNULL(b.TCL_S_NAME,CATEGORY_NAME) AS CATEGORY_NAME,";
		query+="PERSON_ID,PERSON_FIRST_NAME,PERSON_SECOND_NAME,LOCATION_ID";
		query+=" from BSSV_TICKET a";
		query+=" left join BSST_TCL_TICKET_CATEGORY_LANG b on a.CATEGORY_ID=b.TCA_N_CATEGORY_ID AND b.LAN_N_LANG_ID=?";
		query+=" left join BSST_TSL_TICKET_STATUS_LANG c on a.STATUS_ID=c.TST_N_STATUS_ID AND c.LAN_N_LANG_ID=?";
	 	
		
		if (!text.equals("") || categoryId!=0 || statusId!=0 || personId!=0 || !fromDate.equals("") || !toDate.equals("") )
			query+=" WHERE ";
		boolean insAnd=false;
		
		List<Object> parms=new ArrayList<Object>();
		parms.add(langId);
		parms.add(langId);
		
		if (!text.equals("")){
			if (!text.contains("%"))
				query+=" TEXT=?";
			else
				query+=" TEXT like ?";
			insAnd=true;
			parms.add(text);
		}
		
		if (categoryId!=0){
			query+=(insAnd?" AND":"")+" CATEGORY_ID=?";
			insAnd=true;
			parms.add(categoryId);
		}
		
		if (statusId!=0){
			query+=(insAnd?" AND":"")+" STATUS_ID=?";
			insAnd=true;
			parms.add(statusId);
		}
		
		if (personId!=0){
			query+=(insAnd?" AND":"")+" PERSON_ID=?";
			insAnd=true;
			parms.add(personId);
		}
		
		if (!fromDate.equals("")){
			query+=(insAnd?" AND":"")+" CREATION_DATE>=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(fromDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}

		if (!toDate.equals("")){
			query+=(insAnd?" AND":"")+" DATE_SUB(CREATION_DATE,INTERVAL 1 DAY)<=?";
			insAnd=true;
			try {
				parms.add(CyBssUtility.dateChangeFormat(toDate, CyBssUtility.DATE_yyyy_MM_dd));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				throw new CyBssException(e);
			}
		}
			
		logger.info(query+"[text="+text+";categoryId="+categoryId+";statusId="+statusId
				+";personId="+personId+";fromDate="+fromDate+";toDate="+toDate
				+"]");
		
		List<Ticket> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperTicket());
		
		
		
		return ret;
	}

	
	
	
	private class RowMapperTicket implements RowMapper<Ticket>{

		@Override
		public Ticket mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Ticket ticket=new Ticket();
            
			ticket.setId(rs.getLong("ID"));
			ticket.setText(rs.getString("TEXT"));
			ticket.setCreationDate(rs.getString("CREATION_DATE"));
			
			ticket.setStatusId(rs.getLong("STATUS_ID"));
			ticket.setStatusName(rs.getString("STATUS_NAME"));
			
			ticket.setUserId(rs.getLong("USER_ID"));
			ticket.setUserName(rs.getString("USER_NAME"));
			
			ticket.setCategoryId(rs.getLong("CATEGORY_ID"));
			ticket.setCategoryName(rs.getString("CATEGORY_NAME"));
			
			ticket.setPersonId(rs.getLong("PERSON_ID"));
			ticket.setPersonFirstName(rs.getString("PERSON_FIRST_NAME"));
			ticket.setPersonSecondName(rs.getString("PERSON_SECOND_NAME"));
			
			ticket.setLocationId(rs.getLong("LOCATION_ID"));
			
            return ticket;
		}
		
	}

	@Override
	public List<TicketCategory> getCategoryAll(long langId) {
		// TODO Auto-generated method stub
		
		String query="select a.TCA_N_CATEGORY_ID,IFNULL(b.TCL_S_NAME,a.TCA_S_NAME) AS TCA_S_NAME,IFNULL(b.TCL_S_DESC,a.TCA_S_DESC) AS TCA_S_DESC from BSST_TCA_TICKET_CATEGORY a";
		query+=" left join BSST_TCL_TICKET_CATEGORY_LANG b on a.TCA_N_CATEGORY_ID=b.TCA_N_CATEGORY_ID AND LAN_N_LANG_ID="+langId;
	 	
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(query);
		
		List<TicketCategory> ret = jdbcTemplate.query(
                query, 
                new RowMapper<TicketCategory>() {
                    @Override
                    public TicketCategory mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	TicketCategory category=new TicketCategory();
                        
                        category.setId(rs.getInt("TCA_N_CATEGORY_ID"));
                        category.setName(rs.getString("TCA_S_NAME"));
                        category.setDescription(rs.getString("TCA_S_DESC"));
                        
                        return category;
		            }
                });
		
		
        
		
		return ret;

	}


	@Override
	public List<TicketStatus> getNextStates(long stateId, long langId) {
		// TODO Auto-generated method stub
		
		String query="SELECT a.TWF_N_END_STATUS_ID,IFNULL(c.TSL_S_NAME,b.TST_S_NAME) AS STATUS_NAME,IFNULL(c.TSL_S_DESC,b.TST_S_DESC) as STATUS_DESC";
		query+=" from BSST_TWF_TICKET_WORKFLOW a";
		query+=" left join BSST_TST_TICKET_STATUS b on a.TWF_N_END_STATUS_ID=b.TST_N_STATUS_ID ";
		query+=" left join BSST_TSL_TICKET_STATUS_LANG c on a.TWF_N_END_STATUS_ID=c.TST_N_STATUS_ID AND c.LAN_N_LANG_ID=?";
	 	query+=" WHERE a.TWF_N_START_STATUS_ID=?";
		

		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(query);
		
		List<TicketStatus> ret = jdbcTemplate.query(
                query, 
                new Object[] { langId,stateId },
                new RowMapper<TicketStatus>() {
                    @Override
                    public TicketStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	TicketStatus status=new TicketStatus();
                        
                    	status.setId(rs.getInt("TWF_N_END_STATUS_ID"));
                    	status.setName(rs.getString("STATUS_NAME"));
                    	status.setDescription(rs.getString("STATUS_DESC"));
                        
                        return status;
		            }
                });
		
	 	
		
		return ret;
	}

	
	@Override
	@Transactional
	public void changeStatus(Ticket ticket,long newStatus,long userId,String note,long langId) throws CyBssException {
		// TODO Auto-generated method stub
		long oldStatus=ticket.getStatusId();
		ticket.setStatusId(newStatus);
		this.update(ticket.getId(), ticket, langId);
		
		String cmd="insert into BSST_TTR_TICKET_TRACE(TIC_N_TICKET_ID,TWF_N_START_STATUS_ID,TWF_N_END_STATUS_ID,TTR_D_TRANS_DATE,";
		cmd+="USR_N_USER_ID,TTR_S_NOTE) values (?,?,?,now(),?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+ticket+","+newStatus+","+userId+","+note+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					ticket.getId(),oldStatus,ticket.getStatusId(),userId,note  
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
	}

	@Transactional
	@Override
	public void remove(long id,long langId) throws CyBssException {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		Ticket ticket=get(id, langId);
		if (ticket==null) return;
		
		Location location=null;
		if (ticket.getLocationId()!=0){
			location=locationDao.get(ticket.getLocationId(),langId);
		}
		
		List<CyBssFile> files=fileDao.getByEntity(Ticket.ENTITY_NAME,id);
		if (files!=null)
			for(CyBssFile file:files){
				String cmd="delete from BSST_FIL_FILE where FILE_N_FILE_ID=? ";
				logger.info(cmd+"["+file.getId()+"]");
				
				jdbcTemplate.update(cmd, new Object[]{
						file.getId()	
					});
				
			}
		
		String cmd="delete from BSST_TTR_TICKET_TRACE where TIC_N_TICKET_ID=?";
		logger.info(cmd+"["+id+"]");
		try {
			jdbcTemplate.update(cmd, new Object[]{
					id  
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		cmd="delete from BSST_TIC_TICKET where TIC_N_TICKET_ID=?";
		logger.info(cmd+"["+id+"]");
		try {
			jdbcTemplate.update(cmd, new Object[]{
					id  
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		
		if (location!=null && location.getLocationType().equals(Ticket.ENTITY_NAME)){
			cmd="delete from BSST_LOC_LOCATION where LOC_N_LOCATION_ID=?";
			logger.info(cmd+"["+id+"]");
			try {
				jdbcTemplate.update(cmd, new Object[]{
						id
					});
			} catch (DataAccessException e) {
				// TODO Auto-generated catch block
				logger.error(e.toString());
				throw new CyBssException(e);
			}
			
		}
		
	}
	
} 
