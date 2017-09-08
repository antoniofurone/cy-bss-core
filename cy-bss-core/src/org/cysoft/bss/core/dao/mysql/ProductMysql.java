package org.cysoft.bss.core.dao.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		
		logger.info("ProductMysql.removeCategory() <<<");

	}

	@Override
	public List<ProductCategory> getCategoryAll() {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.getCategoryAll() >>>");
		
		String query="select ID,NAME,VAT,METRIC_ID,METRIC_NAME,DESCRIPTION from BSSV_PRODUCT_CATEGORY";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select ID,NAME,VAT,METRIC_ID,METRIC_NAME,DESCRIPTION from BSSV_PRODUCT_CATEGORY";
		query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		ProductCategory ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperCategory());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ProductMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		
		logger.info("ContactMysql.removeType() <<<");
	}

	@Override
	public List<ProductType> getTypeAll() {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.getTypeAll() >>>");
		
		String query="select PTY_N_PRODUCT_TYPE_ID,PTY_S_NAME,PTY_S_DESC from BSST_PTY_PRODUCT_TYPE";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
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
		logger.info("ProductMysql.add() >>>");
		
		String cmd="insert into BSST_PRO_PRODUCT(PRO_S_NAME,PRO_S_DESC,PRO_S_CODE,PCA_N_PRODUCT_CATEGORY_ID,PTY_N_PRODUCT_TYPE_ID,PRO_N_PRODUCT_PARENT_ID,PRO_N_PRODUCER_ID) ";
		cmd+=" values (?,?,?,?,?,?,?)";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+product+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					product.getName(),
					(product.getDescription()==null || product.getDescription().equals(""))?null:product.getDescription(),
					(product.getCode()==null || product.getCode().equals(""))?null:product.getCode(),
					product.getCategoryId(),
					product.getTypeId(),
					(product.getParentId()==0)?null:product.getParentId(),
					(product.getProducerId()==0)?null:product.getProducerId()		
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ProductMysql.add() <<<");
		
		return getLastInsertId(jdbcTemplate);

	}


	@Override
	public List<Product> find(String name, long categoryId, long typeId, String attrName, String attrValue) {
		// TODO Auto-generated method stub
		
		logger.info("ProductMysql.find() >>> name="+name+";categoryId="+categoryId+";typeId="+typeId
				+";attrName="+attrName+";attrValue="+attrValue);
		
		String query="select a.ID,a.NAME,a.DESCRIPTION,a.CODE,a.CATEGORY_ID,a.CATEGORY_NAME,a.METRIC_ID,a.METRIC_NAME,a.VAT,a.TYPE_ID,a.TYPE_NAME,";
		query+="a.PARENT_ID,a.PARENT_NAME,a.PRODUCER_ID,a.PRODUCER_CODE,a.PRODUCER_NAME ";
		query+=" from BSSV_PRODUCT a";
		if (attrName!=null && !attrName.equals("")){
			query+=" join BSSV_ATTRIBUTE_VALUE b on b.OBJINST_ID=a.ID and b.NAME='"+attrName+"' and b.ENTITY='"+Product.ENTITY_NAME+"'";
		}
		if (!name.equals("") || categoryId!=0 || typeId!=0 || !attrName.equals("") || !attrValue.equals(""))
			query+=" WHERE ";
		
		boolean insAnd=false;
		List<Object> parms=new ArrayList<Object>();
		
		if (!name.equals("")){
			if (!name.contains("%"))
				query+=" a.NAME=?";
			else
				query+=" a.NAME like ?";
			insAnd=true;
			parms.add(name);
		}
		if (categoryId!=0){
			query+=(insAnd?" AND":"")+" a.CATEGORY_ID=?";
			insAnd=true;
			parms.add(categoryId);
		}
		if (typeId!=0){
			query+=(insAnd?" AND":"")+" a.TYPE_ID=?";
			insAnd=true;
			parms.add(typeId);
		}
		
		if (!attrName.equals("")){
			if (!attrValue.contains("%"))
				query+=(insAnd?" AND":"")+" b.VALUE=?";
			else
				query+=(insAnd?" AND":"")+" b.VALUE like ?";
			insAnd=true;
			parms.add(attrValue);
		}
		query+=" order by ID";
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[name="+name+";categoryId="+categoryId+";typeId="+typeId
				+";attrName="+attrName+";attrValue="+attrValue+"]");
		
		List<Product> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperProduct());
			
		logger.info("CompanyMysql.find() <<<");
		return ret;
	}

	private class RowMapperProduct implements RowMapper<Product>{

		@Override
		public Product mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Product product=new Product();
       	
			product.setId(rs.getLong("ID"));
			product.setName(rs.getString("NAME"));
			product.setDescription(rs.getString("DESCRIPTION"));
			product.setCode(rs.getString("CODE"));
			product.setCategoryId(rs.getLong("CATEGORY_ID"));
			product.setTypeId(rs.getLong("TYPE_ID"));
			
			product.getCategory().setId(rs.getLong("CATEGORY_ID"));
			product.getCategory().setName(rs.getString("CATEGORY_NAME"));
			product.getCategory().setMetricId(rs.getLong("METRIC_ID"));
			product.getCategory().setMetricName(rs.getString("METRIC_NAME"));
			product.getCategory().setVat(rs.getDouble("VAT"));
			
			product.getProductType().setId(rs.getLong("TYPE_ID"));
			product.getProductType().setName(rs.getString("TYPE_NAME"));
			
			product.setParentId(rs.getLong("PARENT_ID"));
			product.setParentName(rs.getString("PARENT_NAME"));
			
			product.setProducerId(rs.getLong("PRODUCER_ID"));
			product.setProducerCode(rs.getString("PRODUCER_CODE"));
			product.setProducerName(rs.getString("PRODUCER_NAME"));
			
            return product;
		}
	}
	
	
	@Override
	public Product get(long id) {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.get() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select a.ID,a.NAME,a.DESCRIPTION,a.CODE,a.CATEGORY_ID,a.CATEGORY_NAME,a.METRIC_ID,a.METRIC_NAME,a.VAT,a.TYPE_ID,a.TYPE_NAME,";
		query+="a.PARENT_ID,a.PARENT_NAME,a.PRODUCER_ID,a.PRODUCER_CODE,a.PRODUCER_NAME ";
		query+=" from BSSV_PRODUCT a";
		query+=" where ID=?";
		
		logger.info(query+"["+id+"]");
		Product ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperProduct());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("ProductMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("ProductMysql.get() <<<");
		return ret;
	}

	@Override
	public void update(long id, Product product) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.update() >>>");
		
		String cmd="update BSST_PRO_PRODUCT set PRO_S_NAME=?,PRO_S_DESC=?,PRO_S_CODE=?,PCA_N_PRODUCT_CATEGORY_ID=?,";
		cmd+="PTY_N_PRODUCT_TYPE_ID=?,PRO_N_PRODUCT_PARENT_ID=?,PRO_N_PRODUCER_ID=? ";
		cmd+="where PRO_N_PRODUCT_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+product+"]");
	
		try {
			jdbcTemplate.update(cmd, new Object[]{
					product.getName(),
					(product.getDescription()==null || product.getDescription().equals(""))?null:product.getDescription(),
					(product.getCode()==null || product.getCode().equals(""))?null:product.getCode(),
					product.getCategoryId(),
					product.getTypeId(),
					(product.getParentId()==0)?null:product.getParentId(),
					(product.getProducerId()==0)?null:product.getProducerId(),		
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("ProductMysql.updateType() <<<");
	}
	
	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("ProductMysql.remove() >>>");
		String cmd="delete from BSST_PRO_PRODUCT where PRO_N_PRODUCT_ID=?";
		logger.info(cmd+"["+id+"]");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new RuntimeException(e);
		}
		logger.info("ProductMysql.remove() <<<");
	}

}
