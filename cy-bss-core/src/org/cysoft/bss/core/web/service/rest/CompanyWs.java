package org.cysoft.bss.core.web.service.rest;


import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.CompanyDao;
import org.cysoft.bss.core.dao.PersonDao;
import org.cysoft.bss.core.model.Company;
import org.cysoft.bss.core.model.CompanyDept;
import org.cysoft.bss.core.model.CompanyPerson;
import org.cysoft.bss.core.model.Language;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.rest.CompanyDeptListResponse;
import org.cysoft.bss.core.web.response.rest.CompanyDeptResponse;
import org.cysoft.bss.core.web.response.rest.CompanyListResponse;
import org.cysoft.bss.core.web.response.rest.CompanyPersonListResponse;
import org.cysoft.bss.core.web.response.rest.CompanyPersonResponse;
import org.cysoft.bss.core.web.response.rest.CompanyResponse;
import org.cysoft.bss.core.web.response.rest.PersonRoleListResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
@CyBssService(name = "Company")
public class CompanyWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(CompanyWs.class);
	
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
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public CompanyResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Company company
			) throws CyBssException
	{
		CompanyResponse response=new CompanyResponse();
		
		logger.info("CompanyWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Company.class))
			return response;
		// end checkGrant 
				
		//logger.info(company.toString());
		
		if (company.getCode().equals("")){
			company.setCode(generateCode(company));
			logger.info("candidate code="+company.getCode());
			
			int suffix=0; 
			while (companyDao.getByCode(company.getCode())!=null){
				suffix++;
				company.setCode(company.getCode()+suffix);
			}
			logger.info("code="+company.getCode());
		}
		
		if (companyDao.getByCode(company.getCode())!=null){
			setResult(response, ICyBssResultConst.RESULT_COMPANYCODE_USED, 
					ICyBssResultConst.RESULT_D_COMPANYCODE_USED,response.getLanguageCode());
			return response;
		}
		
		long id=companyDao.add(company);
		company.setId(id);
		response.setCompany(company);
		
		logger.info("CompanyWs.add() <<<");
		
		return response;
	}
	
	private String generateCode(Company company){
		String name=company.getName().replaceAll(" ", "").toUpperCase();
		return 
				(name.length()>=3?name.substring(0,3):name);
	}
	
	@RequestMapping(value = "/addDept",method = RequestMethod.POST)
	@CyBssOperation(name = "addDept")
	public CompanyDeptResponse addDept(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody CompanyDept dept
			) throws CyBssException
	{
		CompanyDeptResponse response=new CompanyDeptResponse();
		
		logger.info("CompanyWs.addDept() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addDept",String.class,CompanyDept.class))
			return response;
		// end checkGrant 
				
		//logger.info(company.toString());
		
		CompanyDept parentDept=companyDao.getDept(dept.getParentId());
		if (parentDept==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		dept.setCompanyId(parentDept.getCompanyId());
		long id=companyDao.addDept(dept);
		dept.setId(id);
		response.setCompanyDept(dept);
		
		logger.info("CompanyWs.addDept() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/addSubs/{subsId}",method = RequestMethod.GET)
	@CyBssOperation(name = "addSubs")
	public CompanyResponse addSubs(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("subsId") Long subsId
			) throws CyBssException
	{
		CompanyResponse response=new CompanyResponse();
		
		logger.info("CompanyWs.addSubs() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addSubs",String.class,Long.class,Long.class))
			return response;
		// end checkGrant 
				
		//logger.info(company.toString());
		
		Company company=companyDao.get(id);
		if (company==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		company=companyDao.get(subsId);
		if (company==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		companyDao.addSubs(id, subsId);
		
		logger.info("CompanyWs.addSubs() <<<");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeSubs/{subsId}",method = RequestMethod.GET)
	@CyBssOperation(name = "removeSubs")
	public CompanyResponse removeSubs(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("subsId") Long subsId
			) throws CyBssException
	{
		CompanyResponse response=new CompanyResponse();
		
		logger.info("CompanyWs.removeSubs() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeSubs",String.class,Long.class,Long.class))
			return response;
		// end checkGrant 
				
		//logger.info(company.toString());
		companyDao.removeSubs(id, subsId);
		
		logger.info("CompanyWs.removeSubs() <<<");
		return response;
	}

	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public CompanyResponse get(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CompanyWs.get() >>> id="+id);
		CompanyResponse response=new CompanyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"get",String.class,Long.class))
			return response;
		// end checkGrant 
				
		Company company=companyDao.get(id);
		if (company!=null)
			response.setCompany(company);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("CompanyWs.get() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/getByCode",method = RequestMethod.GET)
	@CyBssOperation(name = "getByCode")
	public CompanyResponse getByCode(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="code", required=true) String code
			) throws CyBssException{
		
		logger.info("CompanyWs.getByCode() >>> personId="+code);
		CompanyResponse response=new CompanyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getByCode",String.class,String.class))
			return response;
		// end checkGrant 
				
		Company company=companyDao.getByCode(code);
		if (company!=null)
			response.setCompany(company);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("CompanyWs.getByCode() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getDeptAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getDeptAll")
	public CompanyDeptListResponse getDeptAll(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CompanyWs.getDeptAll() >>> id="+id);
		CompanyDeptListResponse response=new CompanyDeptListResponse();
	
		// checkGrant
		if (!checkGrant(response,securityToken,"getDeptAll",String.class,Long.class))
			return response;
		// end checkGrant 
				
		List<CompanyDept> depts=companyDao.getDeptAll(id);
		response.setCompanyDepts(depts);
		
		logger.info("CompanyWs.getDeptAll() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/getPersonAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getPersonAll")
	public CompanyPersonListResponse getPersonAll(
			@RequestHeader("Security-Token") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CompanyWs.getPersonAll() >>> id="+id);
		CompanyPersonListResponse response=new CompanyPersonListResponse();
	
		// checkGrant
		if (!checkGrant(response,securityToken,"getPersonAll",String.class,String.class,Long.class))
			return response;
		// end checkGrant 
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageDao.getLanguage(response.getLanguageCode());
		else	
			language=languageDao.getLanguage(languageCode);
	
		List<CompanyPerson> companyPersons=companyDao.getPersonAll(id, language.getId());
		response.setCompanyPersons(companyPersons);
		
		logger.info("CompanyWs.getPersonAll() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{deptId}/getSubDept",method = RequestMethod.GET)
	@CyBssOperation(name = "getSubDept")
	public CompanyDeptListResponse getSubDept(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("deptId") Long deptId
			) throws CyBssException{
		
		logger.info("CompanyWs.getSubDept() >>> deptId="+deptId);
		CompanyDeptListResponse response=new CompanyDeptListResponse();
	
		// checkGrant
		if (!checkGrant(response,securityToken,"getSubDept",String.class,Long.class))
			return response;
		// end checkGrant 
		
		List<CompanyDept> depts=companyDao.getSubDept(deptId);
		response.setCompanyDepts(depts);
		
		logger.info("CompanyWs.getSubDept() <<< ");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/update",method = RequestMethod.POST)
	@CyBssOperation(name = "update")
	public CompanyResponse update(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Company company
			) throws CyBssException
	{
		CompanyResponse response=new CompanyResponse();
		
		logger.info("CompanyWs.update() >>> id="+id);
		
		// checkGrant
		if (!checkGrant(response,securityToken,"update",String.class,Long.class,Company.class))
			return response;
		// end checkGrant 
		
		if (companyDao.get(id)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		companyDao.update(id, company);
		response.setCompany(companyDao.get(id));
		
		logger.info("CompanyWs.update() <<<");
		
		return response;
	}
	
	@RequestMapping(value = "/{id}/updateDept",method = RequestMethod.POST)
	@CyBssOperation(name = "updateDept")
	public CompanyDeptResponse updateDept(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long deptId,
			@RequestBody CompanyDept companyDept
			) throws CyBssException
	{
		CompanyDeptResponse response=new CompanyDeptResponse();
		
		logger.info("CompanyWs.updateDept() >>> deptId="+deptId);
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"updateDept",String.class,Long.class,CompanyDept.class))
			return response;
		// end checkGrant 
		
		
		CompanyDept parentDept=companyDao.getDept(companyDept.getParentId());
		if (parentDept==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		
		if (companyDao.getDept(deptId)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		companyDept.setCompanyId(parentDept.getCompanyId());
		companyDao.updateDept(companyDept);
		response.setCompanyDept(companyDao.getDept(deptId));
	
		logger.info("CompanyWs.updateDept() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{deptId}/addPerson",method = RequestMethod.POST)
	@CyBssOperation(name = "addPerson")
	public CompanyPersonResponse addPerson(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("deptId") Long deptId,
			@RequestBody CompanyPerson companyPerson
			) throws CyBssException
	{
		CompanyPersonResponse response=new CompanyPersonResponse();
		
		logger.info("CompanyWs.addPerson() >>> deptId="+deptId);
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addPerson",String.class,Long.class,CompanyPerson.class))
			return response;
		// end checkGrant 
		
		if (companyDao.getDept(deptId)==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		if (personDao.get(companyPerson.getPersonId())==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		if (companyDao.getPersonRole(companyPerson.getPersonId(),
				languageDao.getLanguage(response.getLanguageCode()).getId())==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
			}
		
		
		companyDao.addPerson(companyPerson.getPersonId(),
				companyPerson.getDeptId(),companyPerson.getRoleId());
		companyPerson.setDeptId(deptId);
		response.setCompanyPerson(companyPerson);
		logger.info("CompanyWs.addPerson() <<<");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public CompanyResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("CompanyWs.remove() >>> id="+id);
		CompanyResponse response=new CompanyResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		companyDao.remove(id);
		
		logger.info("CompanyWs.remove() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{deptId}/removeDept",method = RequestMethod.GET)
	@CyBssOperation(name = "removeDept")
	public CompanyDeptResponse removeDept(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("deptId") Long deptId
			) throws CyBssException{
		
		logger.info("CompanyWs.removeDept() >>> deptId="+deptId);
		CompanyDeptResponse response=new CompanyDeptResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeDept",String.class,Long.class))
			return response;
		// end checkGrant 
		
		companyDao.removeDept(deptId);
	
		logger.info("CompanyWs.removeDept() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/{deptId}/removePerson/{personId}",method = RequestMethod.GET)
	@CyBssOperation(name = "removePerson")
	public CompanyPersonResponse removePerson(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("deptId") Long deptId,
			@PathVariable("personId") Long personId
			) throws CyBssException{
		
		logger.info("CompanyWs.removePerson() >>> deptId="+deptId+";personId="+personId);
		CompanyPersonResponse response=new CompanyPersonResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removePerson",String.class,Long.class,Long.class))
			return response;
		// end checkGrant 
		
		companyDao.removePerson(personId,deptId);
	
		logger.info("CompanyWs.removePerson() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public CompanyListResponse find(
			@RequestHeader("Security-Token") String securityToken,
			@RequestParam(value="code", required=false, defaultValue="") String code,
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("CompanyWs.find() >>> code="+code+";name="+name);
		CompanyListResponse response=new CompanyListResponse();
		
		
		// checkGrant
		if (!checkGrant(response,securityToken,"find",String.class,String.class,String.class,Integer.class,Integer.class))
			return response;
		// end checkGrant 
		
		
		List<Company> companies=companyDao.find(code,name);
		int lsize=companies.size();
		if (offset!=0)
			response.setCompanies(companies.subList(offset-1, (offset-1)+(size>lsize?lsize:size)));
		else
			response.setCompanies(companies);
		
		logger.info("CompanyWs.find() <<< ");
		
		return response;
	}

	@RequestMapping(value = "/getPersonRoleAll",method = RequestMethod.GET)
	@CyBssOperation(name = "getPersonRoleAll")
	public PersonRoleListResponse getPersonRoleAll(
			@RequestHeader("Security-Token") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode
			) throws CyBssException{
		
		logger.info("CompanyWs.getPersonRoleAll() >>>");
		PersonRoleListResponse response=new PersonRoleListResponse(); 
		
		// checkGrant
		if (!checkGrant(response,securityToken,"getPersonRoleAll",String.class,String.class))
			return response;
		// end checkGrant 
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageDao.getLanguage(response.getLanguageCode());
		else	
			language=languageDao.getLanguage(languageCode);
		
		response.setPersonRoles(companyDao.getPersonRoleAll(language.getId()));
		return response;
	}

}
