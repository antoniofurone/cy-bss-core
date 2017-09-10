package org.cysoft.bss.core.dao.mysql;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.model.CompanyDept;
import org.cysoft.bss.core.model.CompanyPerson;
import org.cysoft.bss.core.model.PersonRole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class CompanyMysql extends CyBssMysqlDao
implements CompanyDao{
	private static final Logger logger = LoggerFactory.getLogger(CompanyMysql.class);
	
	@Override
	public long add(final Company company) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.add() >>>");
		
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
	 
		
		logger.info("CompanyMysql.add() <<<");
		return getLastInsertId(jdbcTemplate);
	}

	@Override
	public long addDept(CompanyDept dept) throws CyBssException {
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		
		String cmd="insert into BSST_CDE_COMPANY_DEPT(COM_N_COMPANY_ID,CDE_S_NAME,CDE_S_CODE,CDE_S_ADDRESS,CDE_S_ZIP,CIT_N_CITY_ID,CDE_N_PARENT_DEPT_ID) ";
		cmd+=" values (?,?,?,?,?,?,?)";
		logger.info(cmd+"["+dept+"]");
		
		jdbcTemplate.update(cmd, new Object[]{
					dept.getCompanyId(), dept.getName(), dept.getCode(), 
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
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		
		String query="select ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,COUNTRY,FISCAL_CODE,VAT_CODE,HEAD_DEPT_ID,";
		query+="HEAD_DEPT_CODE,HEAD_DEPT_NAME,GROUP_ID,GROUP_CODE,GROUP_NAME ";
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
		private boolean mManaged=false;
		
		public RowMapperCompany(){
		}
		
		public RowMapperCompany(boolean managed){
			mManaged=managed;
		}

		@Override
		public Company mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			Company company=new Company();
       	
            company.setId(rs.getLong("ID"));
            company.setCode(rs.getString("CODE"));
            company.setName(rs.getString("NAME"));
            company.setAddress(rs.getString("ADDRESS")==null?"":rs.getString("ADDRESS"));
            company.setZipCode(rs.getString("ZIP")==null?"":rs.getString("ZIP"));
            company.setCityId(rs.getLong("CITY_ID"));
            company.setCity(rs.getString("CITY")==null?"":rs.getString("CITY"));
            company.setCountry(rs.getString("COUNTRY")==null?"":rs.getString("COUNTRY"));
            company.setFiscalCode(rs.getString("FISCAL_CODE"));
            company.setVatCode(rs.getString("VAT_CODE"));
            company.setHeadDeptId(rs.getLong("HEAD_DEPT_ID"));
            company.setHeadDeptCode(rs.getString("HEAD_DEPT_CODE"));
            company.setHeadDeptName(rs.getString("HEAD_DEPT_NAME"));
            company.setGroupId(rs.getLong("GROUP_ID"));
            company.setGroupCode(rs.getString("GROUP_CODE"));
            company.setGroupName(rs.getString("GROUP_NAME"));
            if (mManaged){
            	company.setInvoiceLogoId(rs.getLong("MNC_N_INVOICE_LOGO"));
            }
            
            return company;
		}
		
	}


	@Override
	public void update(final long id, final Company company) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.update() >>>");
		
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
			throw new CyBssException(e);
		}
		
		logger.info("CompanyMysql.update() <<<");
	}

	@Override
	public void updateDept(CompanyDept dept) throws CyBssException {
		// TODO Auto-generated method stub
		
		String cmd="update BSST_CDE_COMPANY_DEPT set COM_N_COMPANY_ID=?,CDE_S_NAME=?,CDE_S_CODE=?,CDE_S_ADDRESS=?,";
		cmd+="CDE_S_ZIP=?,CIT_N_CITY_ID=?,CDE_N_PARENT_DEPT_ID=?";
		cmd+=" where CDE_N_DEPT_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+dept+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					dept.getCompanyId(), dept.getName(), dept.getCode(),  
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
		
		
		String cmd="delete from BSST_COM_COMPANY where COM_N_COMPANY_ID=?";
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
				
			
	}

	@Override
	public List<Company> find(String code, String name) {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.find() >>> code="+code+";name="+name);
		
		String query="select  ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,COUNTRY,FISCAL_CODE,VAT_CODE,HEAD_DEPT_ID,";
		query+="HEAD_DEPT_CODE,HEAD_DEPT_NAME,GROUP_ID,GROUP_CODE,GROUP_NAME";
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
		query+=" order by ID";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[code="+code+";name="+name+"]");
		
		List<Company> ret=jdbcTemplate.query(query, parms.toArray(),new RowMapperCompany());
			
		logger.info("CompanyMysql.find() <<<");
		return ret;
	}

	@Override
	public List<PersonRole> getPersonRoleAll(long langId) {
		// TODO Auto-generated method stub
		String query="select  a.CRO_N_ROLE_ID,IFNULL(b.CRL_S_NAME,a.CRO_S_NAME) as CRO_S_NAME,IFNULL(b.CRL_S_DESC,a.CRO_S_DESC) as CRO_S_DESC  from BSST_CRO_COMPANY_PERS_ROLE a";
		query+=" left join BSST_CRL_COMPANY_PERS_ROLE_LANG b on a.CRO_N_ROLE_ID=b.CRO_N_ROLE_ID AND b.LAN_N_LANG_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"["+langId+"]");
		
		List<PersonRole> ret = jdbcTemplate.query(
                query, 
                new Object[] { langId },
                new RowMapper<PersonRole>() {
                    @Override
                    public PersonRole mapRow(ResultSet rs, int rowNum) throws SQLException {
                    	PersonRole companyRole=new PersonRole();
                        
                        companyRole.setId(rs.getLong("CRO_N_ROLE_ID"));
                        companyRole.setName(rs.getString("CRO_S_NAME"));
                        companyRole.setDescription(rs.getString("CRO_S_DESC"));
                        
                        return companyRole;
		            }
                });
		
		
		return ret;
	}

	@Override
	public List<CompanyDept> getDeptAll(long companyId) {
		// TODO Auto-generated method stub
		String query="select ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,COUNTRY,";
		query+="PARENT_DEPT_ID,PARENT_DEPT_CODE,PARENT_DEPT,COMPANY_ID,COMPANY_CODE,COMPANY ";
		query+="from BSSV_COMPANY_DEPT where COMPANY_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[companyId="+companyId);
		
		List<CompanyDept> ret=jdbcTemplate.query(query, new Object[] {companyId},new RowMapperCompanyDept());
		
		return ret;
	}

	private class RowMapperCompanyDept implements RowMapper<CompanyDept>{

		@Override
		public CompanyDept mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			CompanyDept dept=new CompanyDept();
       	
            dept.setId(rs.getLong("ID"));
            dept.setCode(rs.getString("CODE"));
            dept.setName(rs.getString("NAME"));
            dept.setAddress(rs.getString("ADDRESS"));
            dept.setZipCode(rs.getString("ZIP"));
            dept.setCityId(rs.getLong("CITY_ID"));
            dept.setCity(rs.getString("CITY"));
            dept.setCountry(rs.getString("COUNTRY"));
            dept.setParentId(rs.getLong("PARENT_DEPT_ID"));
            dept.setCompanyId(rs.getLong("COMPANY_ID"));
            
            return dept;
		}
		
	}

	
	@Override
	public List<CompanyDept> getSubDept(long deptId) {
		// TODO Auto-generated method stub
		String query="select ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,COUNTRY,";
		query+="PARENT_DEPT_ID,PARENT_DEPT_CODE,PARENT_DEPT,COMPANY_ID,COMPANY_CODE,COMPANY ";
		query+="from BSSV_COMPANY_DEPT where PARENT_DEPT_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[deptId="+deptId);
		
		List<CompanyDept> ret=jdbcTemplate.query(query, new Object[] {deptId},new RowMapperCompanyDept());
		
		return ret;
	}

	@Override
	public void removeDept(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.removeDept() >>>");
		String cmd="delete from BSST_CDE_COMPANY_DEPT ";
		cmd+=" where CDE_N_DEPT_ID=?";
		
		logger.info(cmd+"["+id+"]");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		jdbcTemplate.update(cmd, new Object[]{
				id
				});
		
		logger.info("CompanyMysql.removeDept() <<<");
	}

	@Override
	public void removeDeptByCompany(long id) throws CyBssException{
		logger.info("CompanyMysql.removeDeptByCompany() >>>");
		String cmd="delete from BSST_CDE_COMPANY_DEPT where COM_N_COMPANY_ID=?";
		logger.info(cmd+"["+id+"]");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		jdbcTemplate.update(cmd, new Object[]{
				id
				});
		
		logger.info("CompanyMysql.removeDeptByCompany() <<");
	}
	
	
	@Override
	public CompanyDept getDept(long deptId) {
		// TODO Auto-generated method stub
		String query="select ID,CODE,NAME,ADDRESS,ZIP,CITY_ID,CITY,COUNTRY,";
		query+="PARENT_DEPT_ID,PARENT_DEPT_CODE,PARENT_DEPT,COMPANY_ID,COMPANY_CODE,COMPANY ";
		query+="from BSSV_COMPANY_DEPT where ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[deptId="+deptId);
		
		CompanyDept ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { deptId },
					new RowMapperCompanyDept());
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("CompanyMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		return ret;
	}

	@Override
	public void addPerson(long personId, long deptId, long roleId) {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.addPerson() >>>");
		String cmd="insert into BSST_CPE_COMPANY_PERS(PER_N_PERSON_ID,CRO_N_ROLE_ID,CDE_N_DEPT_ID) ";
		cmd+=" values (?,?,?)";
		logger.info(cmd+"["+personId+","+deptId+","+roleId+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		jdbcTemplate.update(cmd, new Object[]{
				personId,roleId,deptId
				});
		
		
		logger.info("CompanyMysql.addPerson() <<<");
	}

	@Override
	public void removePerson(long personId, long deptId) {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.removePerson() >>>");
		
		String cmd="delete from BSST_CPE_COMPANY_PERS ";
		cmd+=" where PER_N_PERSON_ID=? and CDE_N_DEPT_ID=?";
		
		logger.info(cmd+"["+personId+","+deptId+"]");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		jdbcTemplate.update(cmd, new Object[]{
				personId,deptId
				});
		
		logger.info("CompanyMysql.removePerson() <<<");
	}

	@Override
	public void removePersonByCompany(long id){
		
		logger.info("CompanyMysql.removePersonByCompany() >>>");
		String cmd="delete from BSST_CPE_COMPANY_PERS where CDE_N_DEPT_ID in ";
		cmd+="(select CDE_N_DEPT_ID from BSST_CDE_COMPANY_DEPT where COM_N_COMPANY_ID=?)";
		logger.info(cmd+"["+id+"]");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		jdbcTemplate.update(cmd, new Object[]{
				id
				});
		
		logger.info("CompanyMysql.removePersonByCompany() <<<");
	}
	
	
	@Override
	public PersonRole getPersonRole(long roleId,long langId) {
		// TODO Auto-generated method stub
		String query="select  a.CRO_N_ROLE_ID,IFNULL(b.CRL_S_NAME,a.CRO_S_NAME) as CRO_S_NAME,IFNULL(b.CRL_S_DESC,a.CRO_S_DESC) as CRO_S_DESC  from BSST_CRO_COMPANY_PERS_ROLE a";
		query+=" left join BSST_CRL_COMPANY_PERS_ROLE_LANG b on a.CRO_N_ROLE_ID=b.CRO_N_ROLE_ID AND b.LAN_N_LANG_ID=?";
		query+=" where a.CRO_N_ROLE_ID=?";
		logger.info(query+"["+langId+","+roleId+"]");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());

		PersonRole ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] {langId,roleId},
				new RowMapper<PersonRole>() {
                @Override
                public PersonRole mapRow(ResultSet rs, int rowNum) throws SQLException {
                	PersonRole companyRole=new PersonRole();
                    
                    companyRole.setId(rs.getLong("CRO_N_ROLE_ID"));
                    companyRole.setName(rs.getString("CRO_S_NAME"));
                    companyRole.setDescription(rs.getString("CRO_S_DESC"));
                    
                    return companyRole;
	            }
            });
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("CompanyMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		return ret;

	}

	@Override
	public List<CompanyPerson> getPersonAll(long companyId,long langId) {
		// TODO Auto-generated method stub
		String query="select a.PERSON_ID,a.PERSON_FIRST_NAME,a.PERSON_SECOND_NAME,a.ROLE_ID,IFNULL(b.CRL_S_NAME,a.ROLE_NAME) as ROLE_NAME,a.DEPT_ID,";
		query+="a.DEPT_CODE,a.DEPT_NAME,a.COMPANY_ID,a.COMPANY_CODE,a.COMPANY_NAME ";
		query+="from BSSV_COMPANY_PERSON a ";
		query+="left join BSST_CRL_COMPANY_PERS_ROLE_LANG b on a.ROLE_ID=b.CRO_N_ROLE_ID AND b.LAN_N_LANG_ID=? ";
		query+="where COMPANY_ID=?";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query+"[companyId="+companyId+","+langId+"]");
		
		List<CompanyPerson> ret=jdbcTemplate.query(query, new Object[] {langId,companyId},new RowMapperCompanyPerson());
		
		return ret;
	} 
	
	private class RowMapperCompanyPerson implements RowMapper<CompanyPerson>{

		@Override
		public CompanyPerson mapRow(ResultSet rs, int rownum) throws SQLException {
			// TODO Auto-generated method stub
			CompanyPerson pers=new CompanyPerson();
       	
            pers.setPersonId(rs.getLong("PERSON_ID"));
            pers.setPersonFirstName(rs.getString("PERSON_FIRST_NAME"));
            pers.setPersonSecondName(rs.getString("PERSON_SECOND_NAME"));
            pers.setRoleId(rs.getLong("ROLE_ID"));
            pers.setRoleName(rs.getString("ROLE_NAME"));
            pers.setDeptId(rs.getLong("DEPT_ID"));
            pers.setDeptCode(rs.getString("DEPT_CODE"));
            pers.setDeptName(rs.getString("DEPT_NAME"));
            pers.setCompanyId(rs.getLong("COMPANY_ID"));
            pers.setCompanyCode(rs.getString("COMPANY_CODE"));
            pers.setCompanyName(rs.getString("COMPANY_NAME"));
            
            return pers;
		}
		
	}


	@Override
	public void addSubs(long id, long subsId) {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.addSubs() >>>");
		String cmd="insert into BSST_CGR_COMPANY_GROUP(CGR_N_GROUP_ID,CGR_N_COMPANY_ID) ";
		cmd+=" values (?,?)";
		logger.info(cmd+"["+id+","+subsId+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		jdbcTemplate.update(cmd, new Object[]{
				id,subsId
				});
		
		
		logger.info("CompanyMysql.addSubs() <<<");
	}

	@Override
	public void removeSubs(long id, long subsId) {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.removeSubs() >>>");
		String cmd="delete from BSST_CGR_COMPANY_GROUP where CGR_N_GROUP_ID=? and CGR_N_COMPANY_ID=? ";
		logger.info(cmd+"["+id+","+subsId+"]");
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		jdbcTemplate.update(cmd, new Object[]{
				id,subsId
				});
		
		
		logger.info("CompanyMysql.removeSubs() <<<");
	}

	@Override
	public void addManaged(long id, long invoiceLogoId) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.addManaged() >>>");
		
		String cmd="insert into BSST_MNC_MANAGED_COMPANY(MNC_N_COMPANY_ID,MNC_N_INVOICE_LOGO) ";
		cmd+=" values (?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+invoiceLogoId+"]");
				
		jdbcTemplate.update(cmd, new Object[]{
				id, 
				(invoiceLogoId==0)?null:invoiceLogoId,
				});
		
		logger.info("CompanyMysql.addManaged() <<<");
		}
	

	@Override
	public void updateManaged(long id, long invoiceLogoId) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.updateManaged() >>>");
		
		String cmd="update BSST_MNC_MANAGED_COMPANY set MNC_N_INVOICE_LOGO=? ";
		cmd+="where MNC_N_COMPANY_ID=?";
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(cmd+"["+id+","+invoiceLogoId+"]");
		
		try {
			jdbcTemplate.update(cmd, new Object[]{
					(invoiceLogoId==0)?null:invoiceLogoId,
					id
				});
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		} 
		
		logger.info("CompanyMysql.updateCategory() <<<");
	}

	@Override
	public Company getManaged(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.getManaged() >>>");
		
		// TODO Auto-generated method stub
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		
		String query="select a.ID,a.CODE,a.NAME,a.ADDRESS,a.ZIP,a.CITY_ID,a.CITY,a.COUNTRY,a.FISCAL_CODE,a.VAT_CODE,a.HEAD_DEPT_ID,";
		query+="a.HEAD_DEPT_CODE,a.HEAD_DEPT_NAME,a.GROUP_ID,a.GROUP_CODE,a.GROUP_NAME,b.MNC_N_INVOICE_LOGO ";
		query+="from BSSV_COMPANY a ";
		query+="join BSST_MNC_MANAGED_COMPANY b on a.ID=b.MNC_N_COMPANY_ID ";
		query+="where ID=?";
		
		logger.info(query+"["+id+"]");
		Company ret=null;
		try {
			ret=jdbcTemplate.queryForObject(query, new Object[] { id },
					new RowMapperCompany(true));
		}
		catch(IncorrectResultSizeDataAccessException e){
			logger.info("CompanyMysql.IncorrectResultSizeDataAccessException:"+e.getMessage());
		
		}
		logger.info("CompanyMysql.getManaged() <<<");
		return ret;
		
	}

	@Override
	public List<Company> getManagedAll() {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.getManagedAll() >>>");
		
		String query="select a.ID,a.CODE,a.NAME,a.ADDRESS,a.ZIP,a.CITY_ID,a.CITY,a.COUNTRY,a.FISCAL_CODE,a.VAT_CODE,a.HEAD_DEPT_ID,";
		query+="a.HEAD_DEPT_CODE,a.HEAD_DEPT_NAME,a.GROUP_ID,a.GROUP_CODE,a.GROUP_NAME,b.MNC_N_INVOICE_LOGO ";
		query+="from BSSV_COMPANY a ";
		query+="join BSST_MNC_MANAGED_COMPANY b on a.ID=b.MNC_N_COMPANY_ID ";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(tx.getDataSource());
		logger.info(query);
		
		List<Company> ret = jdbcTemplate.query(
                query, 
                new RowMapperCompany(true));
		
		logger.info("CompanyMysql.getManagedAll() <<<");
		
		return ret;
	}

	@Override
	public void removeManaged(long id) throws CyBssException {
		// TODO Auto-generated method stub
		logger.info("CompanyMysql.removeManaged() >>>");
		
		String cmd="delete from BSST_MNC_MANAGED_COMPANY where MNC_N_COMPANY_ID=?";
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
		
		logger.info("CompanyMysql.removeManaged() <<<");
	}

}
