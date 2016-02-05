package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.model.CompanyDept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class CompanyMysql extends CyBssMysqlDao
implements CompanyDao{
	private static final Logger logger = LoggerFactory.getLogger(CompanyMysql.class);
	
	@Override
	public long add(final Company company) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.add() >>>");
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		Long id=txTemplate.execute(new TransactionCallback<Long>(){

			@Override
			public Long doInTransaction(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				String cmd="insert into BSST_COM_COMPANY(COM_S_CODE,COM_S_NAME,COM_S_ADDRESS,COM_S_ZIP,CIT_N_CITY_ID,COM_S_FISCAL_CODE,COM_S_VAT_CODE) ";
				cmd+=" values (?,?,?,?,?,?,?)";
				JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
				logger.info(cmd+"["+company+"]");
				
				jdbcTemplate.update(cmd, new Object[]{
						company.getCode(), company.getName(), 
						(company.getAddress()==null || company.getAddress().equals(""))?null:company.getAddress(),
						(company.getZipCode()==null || company.getZipCode().equals(""))?null:company.getZipCode(),
						(company.getCityId()==0)?null:company.getCityId(),
						(company.getFiscalCode()==null || company.getFiscalCode().equals(""))?null:company.getFiscalCode(),
						(company.getVatCode()==null || company.getVatCode().equals(""))?null:company.getVatCode()		
					});
			 
			
				Long id=getLastInsertId(jdbcTemplate);
				
				CompanyDept companyDept=new CompanyDept();
				companyDept.setCompanyId(id);
				companyDept.setName(company.getName());
				companyDept.setAddress(company.getAddress());
				companyDept.setZipCode(company.getZipCode());
				companyDept.setCityId(company.getCityId());
				
				try {
					addDept(companyDept);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
				Long headDeptId=getLastInsertId(jdbcTemplate);
				company.setHeadDeptId(headDeptId);
				
				//txStatus.setRollbackOnly();
				//throw new RuntimeException();
				return id;
			}
			
		});
		logger.info("CompanyMysql.add() <<<");
		return id;
	}

	@Override
	public long addDept(CompanyDept dept) throws CyBssException {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		
		String cmd="insert into BSST_CDE_COMPANY_DEPT(COM_N_COMPANY_ID,CDE_S_NAME,CDE_S_ADDRESS,CDE_S_ZIP,CIT_N_CITY_ID,CDE_N_PARENT_DEPT_ID) ";
		cmd+=" values (?,?,?,?,?,?)";
		logger.info(cmd+"["+dept+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
					dept.getCompanyId(), dept.getName(), 
					(dept.getAddress()==null || dept.getAddress().equals(""))?null:dept.getAddress(),
					(dept.getZipCode()==null || dept.getZipCode().equals(""))?null:dept.getZipCode(),
					(dept.getCityId()==0)?null:dept.getCityId(),
					(dept.getParentId()==0)?null:dept.getParentId()	
				});
		
		return getLastInsertId(jdbcTemplate);
		
	}

	@Override
	public Company getByCode(String code) {
		// TODO Auto-generated method stub
		return this.getBy(code, true);
	}

	@Override
	public Company get(long id) {
		// TODO Auto-generated method stub
		return this.getBy((new Long(id)).toString(), false);
	}

	
	private Company getBy(String id,boolean bCode) {
		// TODO Auto-generated method stub
		
		logger.info("CompanyMysql.getBy() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		
		
		String query="select ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,FISCAL_CODE,VAT_CODE,HEAD_DEPT_ID ";
		query+="from BSSV_COMPANY";
		if (bCode)
			query+=" where CODE=?";
		else
			query+=" where ID=?";
		
		logger.info(query+"["+id+",****]");
		Company ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { bCode?id:Long.parseLong(id) },
					new RowMapperCompany());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("CompanyMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("CompanyMysql.getBy() <<<");
		return ret;
	}
	
	
	private class RowMapperCompany implements RowMapper<Company>{

		@Override
		public Company mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Company company=new Company();
       	
            company.setId(rs.getLong("ID"));
            company.setCode(rs.getString("CODE"));
            company.setName(rs.getString("NAME"));
            company.setAddress(rs.getString("ADDRESS"));
            company.setZipCode(rs.getString("ZIP"));
            company.setCityId(rs.getLong("CITY_ID"));
            company.setCity(rs.getString("CITY"));
            company.setFiscalCode(rs.getString("FISCAL_CODE"));
            company.setVatCode(rs.getString("VAT_CODE"));
            company.setHeadDeptId(rs.getLong("HEAD_DEPT_ID"));
            
            return company;
		}
		
	}


	@Override
	public void update(final long id, final Company company) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.update() >>>");
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				String cmd="update BSST_COM_COMPANY set COM_S_CODE=?,COM_S_NAME=?,COM_S_ADDRESS=?,";
				cmd+="COM_S_ZIP=?,CIT_N_CITY_ID=?,COM_S_FISCAL_CODE=?,COM_S_VAT_CODE=?";
				cmd+=" where COM_N_COMPANY_ID=?";
				
				JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
				logger.info(cmd+"["+id+","+company+"]");
				
				try {
					jdbcTemplate.update(cmd, new Object[]{
							company.getCode(), company.getName(),  
							(company.getAddress()==null || company.getAddress().equals(""))?null:company.getAddress(),
							(company.getZipCode()==null || company.getZipCode().equals(""))?null:company.getZipCode(),
							(company.getCityId()==0)?null:company.getCityId(),
							(company.getFiscalCode()==null || company.getFiscalCode().equals(""))?null:company.getFiscalCode(),
							(company.getVatCode()==null || company.getVatCode().equals(""))?null:company.getVatCode(),
							id		
						});
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					logger.error(e.toString());
					throw new RuntimeException(e);
				}
				
				Company company=get(id);
				
				CompanyDept dept=new CompanyDept();
				dept.setCompanyId(id);
				dept.setName(company.getName());
				dept.setAddress(company.getAddress());
				dept.setZipCode(company.getZipCode());
				dept.setCityId(company.getCityId());
				dept.setParentId(0);
				dept.setId(company.getCityId());
				
				try {
					updateDept(dept);
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}
			
		});

		
		logger.info("CompanyMysql.update() <<<");

	}

	@Override
	public void updateDept(CompanyDept dept) throws CyBssException {
		// TODO Auto-generated method stub
		
		String cmd="update BSST_CDE_COMPANY_DEPT set COM_N_COMPANY_ID=?,CDE_S_NAME=?,CDE_S_ADDRESS=?,";
		cmd+="CDE_S_ZIP=?,CIT_N_CITY_ID=?,CDE_N_PARENT_DEPT_ID=?";
		cmd+=" where CDE_N_DEPT_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+dept+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					dept.getCompanyId(), dept.getName(),  
					(dept.getAddress()==null || dept.getAddress().equals(""))?null:dept.getAddress(),
					(dept.getZipCode()==null || dept.getZipCode().equals(""))?null:dept.getZipCode(),
					(dept.getCityId()==0)?null:dept.getCityId(),
					(dept.getParentId()==0)?null:dept.getParentId(),		
					dept.getId()		
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.update() >>>");
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				String cmd="delete from BSST_CDE_COMPANY_DEPT where COM_N_COMPANY_ID=?";
				JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
				logger.info(cmd+"["+id+"]");
				
				try {
					jdbcTemplate.update(cmd, new Object[]{
							id
						});
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					logger.error(e.toString());
					throw new RuntimeException(e);
				}
				
				cmd="delete from BSST_COM_COMPANY where COM_N_COMPANY_ID=?";
				logger.info(cmd+"["+id+"]");
				
				try {
					jdbcTemplate.update(cmd, new Object[]{
							id
						});
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					logger.error(e.toString());
					throw new RuntimeException(e);
				}
				
			}
		});	
	}

	@Override
	public List<Company> find(String code, String name) {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.find() >>> code="+code+";name="+name);
		
		String query="select  ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,FISCAL_CODE,VAT_CODE,HEAD_DEPT_ID";
		query+=" from BSSV_COMPANY";
		if (!code.equals("") || !name.equals(""))
			query+=" WHERE ";
		boolean insAnd=false;
		
		List<Object> parms=new ArrayList<Object>();
		
		if (!code.equals("")){
			if (!code.contains("%"))
				query+=" CODE=?";
			else
				query+=" CODE like ?";
			insAnd=true;
			parms.add(code);
		}
		if (!name.equals("")){
			if (!name.contains("%"))
				query+=(insAnd?" AND":"")+" NAME=?";
			else
				query+=(insAnd?" AND":"")+" NAME like ?";
			insAnd=true;
			parms.add(name);
		}
		
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		logger.info(query+"[code="+code+";name="+name+"]");
		
		List<Company> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperCompany());
			
		logger.info("CompanyMysql.find() <<<");
		return ret;
	}
	
	
}
