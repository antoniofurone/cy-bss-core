package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.dao.ContactDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.dao.PersonDao;
import org.cysoft.bss.core.message.ICyBssMessageConst;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.model.CompanyDept;
import org.cysoft.bss.core.model.CompanyPerson;
import org.cysoft.bss.core.model.PersonRole;
import org.cysoft.bss.core.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class CompanyServiceImpl extends CyBssServiceBase 
implements CompanyService{
	
	protected CompanyDao companyDao=null;
	@Autowired
	public void setCompanyDao(CompanyDao companyDao){
			this.companyDao=companyDao;
	}
	
	protected PersonDao personDao=null;
	@Autowired
	public void setPersonDao(PersonDao personDao){
			this.personDao=personDao;
	}
	
	protected ContactDao contactDao=null;
	@Autowired
	public void setContactDao(ContactDao contactDao){
			this.contactDao=contactDao;
	}
	
	protected ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}
	
	@Override
	public long add(final Company company) throws CyBssException {
		// TODO Auto-generated method stub
		
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		Long id=txTemplate.execute(new TransactionCallback<Long>(){

			@Override
			public Long doInTransaction(TransactionStatus txStatus) {
				// TODO Auto-generated method stub
				Long id;
				try {
					id = companyDao.add(company);
					
					CompanyDept companyDept=new CompanyDept();
					companyDept.setCode(company.getCode());
					companyDept.setCompanyId(id);
					companyDept.setName(company.getName());
					companyDept.setAddress(company.getAddress());
					companyDept.setZipCode(company.getZipCode());
					companyDept.setCityId(company.getCityId());
					
					Long headDeptId=companyDao.addDept(companyDept);
					company.setHeadDeptId(headDeptId);
					
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
				return id;
			}
			
		});
		
		return id;
	}

	@Override
	public long addDept(CompanyDept dept) throws CyBssException {
		// TODO Auto-generated method stub
		CompanyDept parentDept=companyDao.getDept(dept.getParentId());
		if (parentDept==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		dept.setCompanyId(parentDept.getCompanyId());
		return companyDao.addDept(dept);
	}

	@Override
	public void addPerson(CompanyPerson companyPerson,String languageCode) throws CyBssException {
		// TODO Auto-generated method stub
		
		if (companyDao.getDept(companyPerson.getDeptId())==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		if (personDao.get(companyPerson.getPersonId())==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		if (companyDao.getPersonRole(companyPerson.getRoleId(),
				languageDao.getLanguage(languageCode).getId())==null)
			throw new CyBssException(msgSource.getMessage(ICyBssMessageConst.RESULT_D_NOT_FOUND));
		
		companyDao.addPerson(companyPerson.getPersonId(), companyPerson.getDeptId(), companyPerson.getRoleId());
	}

	@Override
	public void addSubs(long id, long subsId) {
		// TODO Auto-generated method stub
		companyDao.addSubs(id, subsId);
	}

	@Override
	public void update(final long id, final Company company) throws CyBssException {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				try {
					companyDao.update(id, company);
					
					Company _company=companyDao.get(id);
					CompanyDept dept=new CompanyDept();
					dept.setCode(_company.getCode());
					dept.setCompanyId(id);
					dept.setName(_company.getName());
					dept.setAddress(_company.getAddress());
					dept.setZipCode(_company.getZipCode());
					dept.setCityId(_company.getCityId());
					dept.setParentId(0);
					dept.setId(_company.getHeadDeptId());
					
					companyDao.updateDept(dept);
					
					} catch (CyBssException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						throw new RuntimeException(e);
					}
				}
			});
	}

	@Override
	public void updateDept(CompanyDept dept) throws CyBssException {
		// TODO Auto-generated method stub
		companyDao.updateDept(dept);
	}

	@Override
	public List<CompanyDept> getDeptAll(long companyId) {
		// TODO Auto-generated method stub
		return companyDao.getDeptAll(companyId);
	}

	@Override
	public List<CompanyDept> getSubDept(long deptId) {
		// TODO Auto-generated method stub
		return companyDao.getSubDept(deptId);
	}

	@Override
	public List<CompanyPerson> getPersonAll(long companyId, long langId) {
		// TODO Auto-generated method stub
		return companyDao.getPersonAll(companyId, langId);
	}

	@Override
	public List<Company> find(String code, String name) {
		// TODO Auto-generated method stub
		return companyDao.find(code, name);
	}

	@Override
	public void remove(final long id) throws CyBssException {
		// TODO Auto-generated method stub
		TransactionTemplate txTemplate=new TransactionTemplate(tx);
		txTemplate.execute(new TransactionCallbackWithoutResult(){

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus arg0) {
				// TODO Auto-generated method stub
				try {
					companyDao.removePersonByCompany(id);
					companyDao.removeDeptByCompany(id);
					contactDao.removeByEntityId(id, Company.ENTITY_NAME);
					objectDao.removeAttributeValues(id, Company.ENTITY_NAME);
					companyDao.remove(id);

				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}	
			}
		});	
	}

	@Override
	public void removeDept(long deptId) throws CyBssException {
		// TODO Auto-generated method stub
		companyDao.removeDept(deptId);
	}

	@Override
	public void removePerson(long personId, long deptId) {
		// TODO Auto-generated method stub
		companyDao.removePerson(personId, deptId);
	}

	@Override
	public void removeSubs(long id, long subsId) {
		// TODO Auto-generated method stub
		companyDao.removeSubs(id, subsId);
	}

	@Override
	public Company getByCode(String code) {
		// TODO Auto-generated method stub
		return companyDao.getByCode(code);
	}

	@Override
	public Company get(long id) {
		// TODO Auto-generated method stub
		return companyDao.get(id);
	}

	@Override
	public CompanyDept getDept(long deptId) {
		// TODO Auto-generated method stub
		return companyDao.getDept(deptId);
	}

	@Override
	public List<PersonRole> getPersonRoleAll(long langId) {
		// TODO Auto-generated method stub
		return companyDao.getPersonRoleAll(langId);
	}

	@Override
	public PersonRole getPersonRole(long roleId, long langId) {
		// TODO Auto-generated method stub
		return companyDao.getPersonRole(roleId, langId);
	}

	@Override
	public void addManaged(long id, long invoiceLogoId) throws CyBssException {
		// TODO Auto-generated method stub
		companyDao.addManaged(id, invoiceLogoId);
	}

	@Override
	public void updateManaged(long id, long invoiceLogoId) throws CyBssException {
		// TODO Auto-generated method stub
		companyDao.updateManaged(id, invoiceLogoId);
	}

	@Override
	public Company getManaged(long id) throws CyBssException {
		// TODO Auto-generated method stub
		return companyDao.getManaged(id);
	}

	@Override
	public List<Company> getManagedAll() {
		// TODO Auto-generated method stub
		return companyDao.getManagedAll();
	}

	@Override
	public void removeManaged(long id) throws CyBssException {
		// TODO Auto-generated method stub
		companyDao.removeManaged(id);
	}

}
