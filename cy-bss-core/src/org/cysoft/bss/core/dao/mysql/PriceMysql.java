package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.PriceDao;
import org.cysoft.bss.core.model.PriceComponent;
import org.cysoft.bss.core.model.PriceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class PriceMysql extends CyBssMysqlDao
	implements PriceDao{

	private static final Logger logger = LoggerFactory.getLogger(PriceMysql.class);

	@Override
	public List<PriceType> getPriceTypeAll() {
		// TODO Auto-generated method stub	
		logger.info("PriceMysql.getPriceTypeAll() >>>");
		
		String query="select PRT_N_PRICE_TYPE_ID,PRT_S_CODE,PRT_S_NAME,PRT_S_DESC from BSST_PRT_PRICE_TYPE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<PriceType> ret = jdbcTemplate.query(
                query, 
                new RowMapperType());
		
		logger.info("PriceMysql.getTypeAll() <<<");
		
		return ret;
	}
	
	
	private class RowMapperType implements RowMapper<PriceType>{

		@Override
		public PriceType mapRow(ResultSet rs, int rownum) throws SQLException {
		// TODO Auto-generated method stub
			PriceType type=new PriceType();
			
			type.setId(rs.getLong("PRT_N_PRICE_TYPE_ID"));
			type.setCode(rs.getString("PRT_S_CODE"));
            type.setName(rs.getString("PRT_S_NAME"));
            type.setDescription(rs.getString("PRT_S_DESC"));
            
            
	        return type;
		}
	}
	

	@Override
	public PriceType getPriceType(long id) {
		// TODO Auto-generated method stub
		logger.info("PriceMysql.getPriceType() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select PRT_N_PRICE_TYPE_ID,PRT_S_CODE,PRT_S_NAME,PRT_S_DESC from BSST_PRT_PRICE_TYPE";
		query+=" where PRT_N_PRICE_TYPE_ID=?";
		
		logger.info(query+"["+id+"]");
		PriceType ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperType());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("PriceMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("PriceMysql.getPriceType() <<<");
		return ret;
	
	}

	@Override
	public List<PriceComponent> getComponentAll() {
		// TODO Auto-generated method stub
		logger.info("PriceMysql.getComponentAll() >>>");
		
		String query="select ID,CODE,NAME,DESCRIPTION,PERIOD_METRIC_ID,PERIOD_METRIC_NAME,TYPE_ID,TYPE_CODE,TYPE_NAME from BSSV_PRICE_COMPONENT";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<PriceComponent> ret = jdbcTemplate.query(
                query, 
                new RowMapperComponent());
		
		logger.info("PriceMysql.getComponentAll() <<<");
		
		return ret;
	}

	private class RowMapperComponent implements RowMapper<PriceComponent>{

		@Override
		public PriceComponent mapRow(ResultSet rs, int rownum) throws SQLException {
		// TODO Auto-generated method stub
			PriceComponent component=new PriceComponent();
			
			component.setId(rs.getLong("ID"));
			component.setCode(rs.getString("CODE"));
			component.setName(rs.getString("NAME"));
			component.setDescription(rs.getString("DESCRIPTION"));
			component.setPeriodMetricId(rs.getLong("PERIOD_METRIC_ID"));
			component.setPeriodMetricName(rs.getString("PERIOD_METRIC_NAME"));
			component.setPriceTypeId(rs.getLong("TYPE_ID"));
			component.getPriceType().setId(rs.getLong("TYPE_ID"));
			component.getPriceType().setCode(rs.getString("TYPE_CODE"));
			component.getPriceType().setName(rs.getString("TYPE_NAME"));
			
	        return component;
		}
	}

	
	
	
	@Override
	public PriceComponent getPriceComponent(long id) {
		// TODO Auto-generated method stub
		logger.info("PriceMysql.getPriceComponent() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select ID,CODE,NAME,DESCRIPTION,PERIOD_METRIC_ID,PERIOD_METRIC_NAME,TYPE_ID,TYPE_CODE,TYPE_NAME from BSSV_PRICE_COMPONENT";
		query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		PriceComponent ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperComponent());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("PriceMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("PriceMysql.getPriceComponent() <<<");
		return ret;
	
	}

	@Override
	public void updateComponent(long id, PriceComponent component) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.updateComponent() >>>");
		
		String cmd="update BSST_PRC_PRICE_COMPONENT set PRC_S_NAME=?,PRC_S_DESC=? ";
		cmd+="where PRC_N_PRICE_COMPONENT_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+component+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					component.getName(),
					(component.getDescription()==null || component.getDescription().equals(""))?null:component.getDescription(),
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ProductMysql.updateComponent() <<<");
	}
	
}
