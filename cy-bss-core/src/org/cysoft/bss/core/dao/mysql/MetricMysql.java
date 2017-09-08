package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.model.MetricScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MetricMysql extends CyBssMysqlDao
	implements MetricDao{

	private static final Logger logger = LoggerFactory.getLogger(MetricMysql.class);
	
	// Metric
	@Override
	public long addMetric(Metric metric) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.addMetric() >>>");
		
		String cmd="insert into BSST_MET_METRIC(MET_S_NAME) ";
		cmd+=" values (?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+metric+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					metric.getName(),
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("MetricMysql.addMetric() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}


	@Override
	public void removeMetric(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.removeMetric() >>>");
		
		String cmd="delete from BSST_MET_METRIC where MET_N_METRIC_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		
		logger.info("MetricMysql.removeMetric() <<<");

	}


	@Override
	public void updateMetric(long id, Metric metric) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.updateMetric() >>>");
		
		String cmd="update BSST_MET_METRIC set MET_S_NAME=? ";
		cmd+="where MET_N_METRIC_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+metric+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					metric.getName(),
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("MetricMysql.updateMetric() <<<");
	}


	@Override
	public Metric getMetric(long id) {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getMetric() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select MET_N_METRIC_ID,MET_S_NAME from BSST_MET_METRIC";
		query+=" where MET_N_METRIC_ID=?";
		
		logger.info(query+"["+id+"]");
		Metric ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperMetric());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("MetricMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("MetricMysql.getMetric() <<<");
		return ret;
	}

	
	@Override
	public List<Metric> getMetricAll() {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getMetricAll >>>");
			
		String query="select MET_N_METRIC_ID,MET_S_NAME from BSST_MET_METRIC";
			
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
			
		List<Metric> ret = jdbcTemplate.query(
                query, 
                new RowMapperMetric());
		
		logger.info("MetricMysql.getMetricAll <<<");
		
		return ret;
	}

	private class RowMapperMetric implements RowMapper<Metric>{

		@Override
		public Metric mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Metric metric=new Metric();
			
			metric.setId(rs.getLong("MET_N_METRIC_ID"));
            metric.setName(rs.getString("MET_S_NAME"));
            
	        return metric;
		}
	}

	// Metric Scale
	@Override
	public long addMetricScale(MetricScale scale) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.addMetricScale() >>>");
		
		String cmd="insert into BSST_MES_METRIC_SCALE(MET_N_METRIC_ID,MES_S_NAME,MES_S_SIMBOL,MES_N_SCALE) ";
		cmd+=" values (?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+scale+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					scale.getMetricId(),
					scale.getName(),
					scale.getSimbol(),
					scale.getScale()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("MetricMysql.addMetricScale() <<<");
		
		return getLastInsertId(jdbcTemplate);

	}


	@Override
	public void removeMetricScale(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.removeMetricScale() >>>");
		
		String cmd="delete from BSST_MES_METRIC_SCALE where MES_N_METRIC_SCALE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		
		logger.info("MetricMysql.removeMetricScale() <<<");

	}


	@Override
	public void updateMetricScale(long id, MetricScale scale) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.updateMetricScale() >>>");
		
		String cmd="update BSST_MES_METRIC_SCALE set MET_N_METRIC_ID=?, MES_S_NAME=?, MES_S_SIMBOL=?, MES_N_SCALE=? ";
		cmd+="where MES_N_METRIC_SCALE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+scale+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					scale.getMetricId(),
					scale.getName(),
					scale.getSimbol(),
					scale.getScale(),
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("MetricMysql.updateMetric() <<<");

	}


	@Override
	public MetricScale getMetricScale(long id) {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getMetricScale() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select ID,NAME,METRIC_ID,METRIC_NAME,SIMBOL,SCALE";
		query+=" from BSSV_METRIC_SCALE ";
		query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		MetricScale ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperMetricScale());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("MetricMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("MetricMysql.getMetricScale() <<<");
		return ret;

	}
	
	
	@Override
	public List<MetricScale> getMetricScaleAll(long metricId) {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getMetricScale >>>");
		
		String query="select ID,NAME,METRIC_ID,METRIC_NAME,SIMBOL,SCALE ";
		query+="from BSSV_METRIC_SCALE ";
		query+=" where METRIC_ID=?";
			
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
			
		List<MetricScale> ret = jdbcTemplate.query(
                query,
                new Object[] {metricId},
                new RowMapperMetricScale());
		
		logger.info("MetricMysql.getMetricScale <<<");
		
		return ret;
	}


	private class RowMapperMetricScale implements RowMapper<MetricScale>{

		@Override
		public MetricScale mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			MetricScale metricScale=new MetricScale();
			
			metricScale.setId(rs.getLong("ID"));
            metricScale.setName(rs.getString("NAME"));
            metricScale.setMetricId(rs.getLong("METRIC_ID"));
            metricScale.setMetricName(rs.getString("METRIC_NAME"));
            metricScale.setSimbol(rs.getString("SIMBOL"));
            metricScale.setScale(rs.getDouble("SCALE"));
            
	        return metricScale;
		}
	}

	// Currency
	
	@Override
	public long addCurrency(Currency currency) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.addCurrency() >>>");
		
		String cmd="insert into BSST_CUR_CURRENCY(CUR_S_CODE,CUR_S_NAME) ";
		cmd+=" values (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+currency+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					currency.getCode(),
					currency.getName()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("MetricMysql.addCurrency() <<<");
		return getLastInsertId(jdbcTemplate);
	}


	@Override
	public void removeCurrency(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.removeCurrency() >>>");
		
		String cmd="delete from BSST_CUR_CURRENCY where CUR_N_CURRENCY_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		
		logger.info("MetricMysql.removeCurrency() <<<");

	}


	@Override
	public void updateCurrency(long id, Currency currency) throws CyBssException {
		// TODO Auto-generated method stub
	
		logger.info("MetricMysql.updateCurrency() >>>");
		
		String cmd="update BSST_CUR_CURRENCY set CUR_S_CODE=?, CUR_S_NAME=? ";
		cmd+="where CUR_N_CURRENCY_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+currency+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					currency.getCode(),
					currency.getName(),
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("MetricMysql.updateCurrency() <<<");

	}


	@Override
	public Currency getCurrency(long id) {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getCurrency() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select CUR_N_CURRENCY_ID,CUR_S_CODE,CUR_S_NAME from BSST_CUR_CURRENCY";
		query+=" where CUR_N_CURRENCY_ID=?";
		
		logger.info(query+"["+id+"]");
		Currency ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperCurrency());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("MetricMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("MetricMysql.getCurrency() <<<");
		return ret;
	}

	
	
	@Override
	public List<Currency> getCurrencyAll() {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getCurrencyAll >>>");
		
		String query="select CUR_N_CURRENCY_ID,CUR_S_CODE,CUR_S_NAME from BSST_CUR_CURRENCY";
			
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
			
		List<Currency> ret = jdbcTemplate.query(
                query, 
                new RowMapperCurrency());
		
		logger.info("MetricMysql.getCurrencyAll <<<");
		
		return ret;
	}
	
	
	private class RowMapperCurrency implements RowMapper<Currency>{

		@Override
		public Currency mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Currency currency=new Currency();
			
			currency.setId(rs.getLong("CUR_N_CURRENCY_ID"));
			currency.setCode(rs.getString("CUR_S_CODE"));
            currency.setName(rs.getString("CUR_S_NAME"));
            
	        return currency;
		}
	}

		
		
}
