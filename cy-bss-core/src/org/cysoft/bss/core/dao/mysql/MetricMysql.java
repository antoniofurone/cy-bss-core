package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.dao.MetricDao;
import org.cysoft.bss.core.model.Currency;
import org.cysoft.bss.core.model.Metric;
import org.cysoft.bss.core.model.MetricScale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class MetricMysql extends CyBssMysqlDao
	implements MetricDao{

	private static final Logger logger = LoggerFactory.getLogger(MetricMysql.class);

	@Override
	public List<Metric> getMetricAll() {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getMetricAll >>>");
			
		String query="select MET_N_METRIC_ID,MET_S_NAME from BSST_MET_METRIC";
			
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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

	@Override
	public List<MetricScale> getMetricScale(long metricId) {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getMetricScale >>>");
		
		String query="select ID,NAME,METRIC_ID,METRIC_NAME,SIMBOL,SCALE ";
		query+="from BSSV_METRIC_SCALE ";
		query+=" where METRIC_ID=?";
			
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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

	
	@Override
	public List<Currency> getCurrencyAll() {
		// TODO Auto-generated method stub
		logger.info("MetricMysql.getCurrencyAll >>>");
		
		String query="select CUR_N_CURRENCY_ID,CUR_S_CODE,CUR_S_NAME from BSST_CUR_CURRENCY";
			
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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
