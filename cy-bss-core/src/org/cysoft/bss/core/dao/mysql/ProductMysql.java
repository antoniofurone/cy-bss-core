package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.ProductDao;
import org.cysoft.bss.core.model.Product;
import org.cysoft.bss.core.model.ProductCategory;
import org.cysoft.bss.core.model.ProductType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ProductMysql extends CyBssMysqlDao
	implements ProductDao{

	private static final Logger logger = LoggerFactory.getLogger(ProductMysql.class);
	
	
	@Override
	public long addCategory(ProductCategory category) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.addCategory() >>>");
		
		String cmd="insert into BSST_PCA_PRODUCT_CATEGORY(PCA_S_NAME,PCA_N_VAT,MET_N_METRIC_ID,PCA_S_DESC) ";
		cmd+=" values (?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+category+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					category.getName(),
					category.getVat(),
					category.getMetricId(),
					(category.getDescription()==null || category.getDescription().equals(""))?null:category.getDescription()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ProductMysql.addCategory() <<<");
		
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public void removeCategory(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.removeCategory() >>>");
		
		String cmd="delete from BSST_PCA_PRODUCT_CATEGORY where PCA_N_PRODUCT_CATEGORY_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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
		
		logger.info("ProductMysql.removeCategory() <<<");

	}

	@Override
	public List<ProductCategory> getCategoryAll() {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.getCategoryAll() >>>");
		
		String query="select ID,NAME,VAT,METRIC_ID,METRIC_NAME,DESCRIPTION from BSSV_PRODUCT_CATEGORY";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(query);
		
		List<ProductCategory> ret = jdbcTemplate.query(
                query, 
                new RowMapperCategory());
		
		logger.info("ProductMysql.getCategoryAll() <<<");
		
		return ret;
	}
	
	
	private class RowMapperCategory implements RowMapper<ProductCategory>{

		@Override
		public ProductCategory mapRow(ResultSet rs, int rownum) throws SQLException {
		// TODO Auto-generated method stub
			ProductCategory category=new ProductCategory();
			
			category.setId(rs.getLong("ID"));
            category.setName(rs.getString("NAME"));
            category.setVat(rs.getDouble("VAT"));
            category.setMetricId(rs.getLong("METRIC_ID"));
            category.setMetricName(rs.getString("METRIC_NAME"));
            category.setDescription(rs.getString("DESCRIPTION"));
            
            
	        return category;
		}
		
	}

	@Override
	public ProductCategory getCategory(long id) {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.getCategory() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		String query="select ID,NAME,VAT,METRIC_ID,METRIC_NAME,DESCRIPTION from BSSV_PRODUCT_CATEGORY";
		query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		ProductCategory ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperCategory());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("CityMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("ProductMysql.getCategory() <<<");
		return ret;
	}

	@Override
	public void updateCategory(long id, ProductCategory category) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.updateCategory() >>>");
		
		String cmd="update BSST_PCA_PRODUCT_CATEGORY set PCA_S_NAME=?,PCA_N_VAT=?,MET_N_METRIC_ID=?,PCA_S_DESC=? ";
		cmd+="where PCA_N_PRODUCT_CATEGORY_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+category+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					category.getName(),
					category.getVat(),
					category.getMetricId(),
					(category.getDescription()==null || category.getDescription().equals(""))?null:category.getDescription(),
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ProductMysql.updateCategory() <<<");
	}

	
	@Override
	public long addType(ProductType productType) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.addType() >>>");
		
		String cmd="insert into BSST_PTY_PRODUCT_TYPE(PTY_S_NAME,PTY_S_DESC) ";
		cmd+=" values (?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+productType+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					productType.getName(),
					(productType.getDescription()==null || productType.getDescription().equals(""))?null:productType.getDescription()
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ProductMysql.addType() <<<");
		
		return getLastInsertId(jdbcTemplate);

	}

	@Override
	public void removeType(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.removeType() >>>");
		
		String cmd="delete from BSST_PTY_PRODUCT_TYPE where PTY_N_PRODUCT_TYPE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
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
		
		logger.info("ContactMysql.removeType() <<<");

	}

	@Override
	public List<ProductType> getTypeAll() {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.getTypeAll() >>>");
		
		String query="select PTY_N_PRODUCT_TYPE_ID,PTY_S_NAME,PTY_S_DESC from BSST_PTY_PRODUCT_TYPE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(query);
		
		List<ProductType> ret = jdbcTemplate.query(
                query, 
                new RowMapperType());
		
		logger.info("ProductMysql.getTypeAll() <<<");
		
		return ret;
	}
	
	private class RowMapperType implements RowMapper<ProductType>{

		@Override
		public ProductType mapRow(ResultSet rs, int rownum) throws SQLException {
		// TODO Auto-generated method stub
			ProductType type=new ProductType();
			
			type.setId(rs.getLong("PTY_N_PRODUCT_TYPE_ID"));
            type.setName(rs.getString("PTY_S_NAME"));
            type.setDescription(rs.getString("PTY_S_DESC"));
            
            
	        return type;
		}
		
	}


	@Override
	public ProductType getType(long id) {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.getType() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		String query="select PTY_N_PRODUCT_TYPE_ID,PTY_S_NAME,PTY_S_DESC from BSST_PTY_PRODUCT_TYPE where PTY_N_PRODUCT_TYPE_ID=?";
		
		logger.info(query+"["+id+"]");
		ProductType ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperType());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ProductMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("ProductMysql.getType() <<<");
		return ret;

	}
	
	@Override
	public void updateType(long id, ProductType type) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.updateType() >>>");
		
		String cmd="update BSST_PTY_PRODUCT_TYPE set PTY_S_NAME=?,PTY_S_DESC=? ";
		cmd+="where PTY_N_PRODUCT_TYPE_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(cmd+"["+type+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					type.getName(),
					(type.getDescription()==null || type.getDescription().equals(""))?null:type.getDescription(),
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ProductMysql.updateType() <<<");

	}
	
	// Product
	@Override
	public long add(Product product) throws CyBssException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void update(long id, Product product) throws CyBssException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Product> find(String name, long categoryId, long typeId, String attrName, String attrValue) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Product get(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(long id) throws CyBssException {
		// TODO Auto-generated method stub
		
	}

	

}
