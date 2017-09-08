package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.dao.CyBssServiceDao;
import org.cysoft.bss.core.model.CyBssOperationParam;
import org.cysoft.bss.core.model.CyBssServOperation;
import org.cysoft.bss.core.model.CyBssService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CyBssServiceMysql extends CyBssMysqlDao 
	implements CyBssServiceDao{

	
	private static final Logger logger = LoggerFactory.getLogger(CyBssServiceMysql.class);
	
	
	@Override
	public List<CyBssService> getAll() {
		// TODO Auto-generated method stub
		logger.info("BssServiceMysql.getAll() >>>");
		
		String query="select BSV_N_SERVICE_ID,BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL from BSST_BSV_SERVICE order by BSV_S_SERVICE_NAME";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<CyBssService> ret = jdbcTemplate.query(
                query, 
                new RowMapper<CyBssService>() {
                    @Override
                    public CyBssService mapRow(ResultSet rs, int rowNum) throws SQLException {
                        CyBssService serv=new CyBssService();
                        
                        serv.setId(rs.getInt("BSV_N_SERVICE_ID"));
                        serv.setName(rs.getString("BSV_S_SERVICE_NAME"));
                        serv.setUrl(rs.getString("BSV_S_SERVICE_URL"));
                        
                        return serv;
		            }
                });
		
		
        
		logger.info("BssServiceMysql.getAll() <<<");
		
		return ret;
	}


	@Override
	public CyBssService get(String id){
		// TODO Auto-generated method stub
		logger.info("BssServiceMysql.get() >>>");
		
		String query="select BSV_N_SERVICE_ID,BSV_S_SERVICE_NAME,BSV_S_SERVICE_URL from BSST_BSV_SERVICE where BSV_N_SERVICE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+id+"]");
		
		CyBssService ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapper<CyBssService>() {
            @Override
            public CyBssService mapRow(ResultSet rs, int rowNum) throws SQLException {
            	CyBssService serv=new CyBssService();
                
                serv.setId(rs.getInt("BSV_N_SERVICE_ID"));
                serv.setName(rs.getString("BSV_S_SERVICE_NAME"));
                serv.setUrl(rs.getString("BSV_S_SERVICE_URL"));
                
                return serv;
            }
        });
		
		
		logger.info("BssServiceMysql.get() <<<");
		return ret;
	}


	@Override
	public List<CyBssServOperation> getServOperations(String id) {
		// TODO Auto-generated method stub
		logger.info("BssServiceMysql.getServOperations() >>>");
		
		String query="select BSO_N_OPERATION_ID,BSV_N_SERVICE_ID,BSO_S_NAME,BSO_S_METHOD,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION";
		query+=" from BSST_BSO_SERVICE_OPERATION where BSV_N_SERVICE_ID=? order by BSO_S_NAME";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+id+"]");
		
		List<CyBssServOperation> ret = jdbcTemplate.query(
                query, new Object[] { id },
                new RowMapper<CyBssServOperation>() {
                    @Override
                    public CyBssServOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	CyBssServOperation servOp=new CyBssServOperation();
                        
                        servOp.setId(rs.getInt("BSO_N_OPERATION_ID"));
                        servOp.setServId(rs.getInt("BSV_N_SERVICE_ID"));
                        servOp.setName(rs.getString("BSO_S_NAME"));
                        servOp.setMethod(rs.getString("BSO_S_METHOD"));
                        servOp.setUrl(rs.getString("BSO_S_OPERATION_URL"));
                        servOp.setDescription(rs.getString("BSO_S_DESCRIPTION"));
                         
                        return servOp;
		            }
                });
		
		
        
		logger.info("BssServiceMysql.getServOperations() <<<");
		
		return ret;
	}


	@Override
	public CyBssServOperation getOperation(String id) {
		// TODO Auto-generated method stub
		logger.info("BssServiceMysql.getOperation() >>>");
		
		String query="select BSO_N_OPERATION_ID,BSV_N_SERVICE_ID,BSO_S_NAME,BSO_S_METHOD,BSO_S_OPERATION_URL,BSO_S_DESCRIPTION";
		query+=" from BSST_BSO_SERVICE_OPERATION where BSO_N_OPERATION_ID=?";
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+id+"]");
		
		CyBssServOperation ret=jdbcTemplate.queryForObject(query, new Object[] { id },new RowMapper<CyBssServOperation>() {
            @Override
            public CyBssServOperation mapRow(ResultSet rs, int rowNum) throws SQLException {
            	CyBssServOperation servOp=new CyBssServOperation();
            	
            	servOp.setId(rs.getInt("BSO_N_OPERATION_ID"));
                servOp.setServId(rs.getInt("BSV_N_SERVICE_ID"));
                servOp.setName(rs.getString("BSO_S_NAME"));
                servOp.setMethod(rs.getString("BSO_S_METHOD"));
                servOp.setUrl(rs.getString("BSO_S_OPERATION_URL"));
                servOp.setDescription(rs.getString("BSO_S_DESCRIPTION"));
                
                return servOp;
            }
        });
		
		
		logger.info("BssServiceMysql.getOperation() <<<");
		return ret;
	}


	@Override
	public List<CyBssOperationParam> getOpParams(String idOp) {
		// TODO Auto-generated method stub
		logger.info("BssServiceMysql.getOpParams() >>>");
		
		String query="select BOP_PARAM_NAME,BSO_N_OPERATION_ID,BOP_C_FLG_URL,BOP_S_DESCRIPTION,BOP_C_REQUIRED,BOP_S_TYPE ";
		query+=" from BSST_BOP_OPERATION_PARAM where BSO_N_OPERATION_ID=? ORDER BY BOP_N_SHOW_ORDER";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+idOp+"]");
		
		List<CyBssOperationParam> ret = jdbcTemplate.query(
                query, new Object[] { idOp },
                new RowMapper<CyBssOperationParam>() {
                    @Override
                    public CyBssOperationParam mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	CyBssOperationParam param=new CyBssOperationParam();
                        
                        param.setName(rs.getString("BOP_PARAM_NAME"));
                        param.setOperationId(rs.getInt("BSO_N_OPERATION_ID"));
                        param.setFlagUrl(rs.getString("BOP_C_FLG_URL"));
                        param.setDescription(rs.getString("BOP_S_DESCRIPTION"));
                        param.setRequired(rs.getString("BOP_C_REQUIRED"));
                        param.setType(rs.getString("BOP_S_TYPE"));
                         
                        return param;
		            }
                });
		
		
        
		logger.info("BssServiceMysql.getOpParams() <<<");
		
		return ret;
	}

	

}
