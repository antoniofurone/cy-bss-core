package org.cysoft.bss.core.web.service.rest;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.model.Language;
import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.file.FileListResponse;
import org.cysoft.bss.core.web.response.rest.LocationListResponse;
import org.cysoft.bss.core.web.response.rest.LocationResponse;
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
@RequestMapping("/location")
@CyBssService(name = "Location")
public class LocationWs extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(LocationWs.class);
	
	protected  LocationDao locationDao=null;
	@Autowired
	public void setLocationDao(LocationDao locationDao){
			this.locationDao=locationDao;
	}
	
	protected FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@CyBssOperation(name = "add")
	public LocationResponse add(
			@RequestHeader("Security-Token") String securityToken,
			@RequestBody Location location
			) throws CyBssException
	{
		LocationResponse response=new LocationResponse();
		
		logger.info("LocationWs.add() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"add",String.class,Location.class))
				return response;
		// end checkGrant 
		
		location.setUserId(response.getUserId());
		long locationId=locationDao.add(location);
		location.setId(locationId);
		response.setLocation(location);
		
		logger.info("LocationWs.add() <<<");
		
		return response;
	}


	@RequestMapping(value = "/{id}/addLang",method = RequestMethod.POST)
	@CyBssOperation(name = "addLang")
	public LocationResponse addLang(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@RequestBody Location location
			) throws CyBssException
	{
		LocationResponse response=new LocationResponse();
		
		logger.info("LocationWs.addLang() >>>");
		
		// checkGrant
		if (!checkGrant(response,securityToken,"addLang",String.class,Long.class,Location.class))
				return response;
		// end checkGrant 
		
		Location loc0=locationDao.get(id, location.getLangId());
		if (loc0==null){
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
			return response;
		}
		locationDao.removeLang(id, location.getLangId());
		locationDao.addLang(location);
		
		logger.info("LocationWs.addLang() <<<");
		
		return response;
	}

	
	@RequestMapping(value = "/{id}/get",method = RequestMethod.GET)
	@CyBssOperation(name = "get")
	public LocationResponse get(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("LocationWs.get() >>> id="+id);
		LocationResponse response=new LocationResponse();
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageDao.getLanguage(response.getLanguageCode());
		else	
			language=languageDao.getLanguage(languageCode);
		
		Location location=locationDao.get(id,language.getId());
		if (location!=null)
			response.setLocation(location);
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("LocationWs.get() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/getFiles",method = RequestMethod.GET)
	@CyBssOperation(name = "getFiles")
	public FileListResponse getFiles(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("LocationWs.getFiles() >>> id="+id);
		FileListResponse response=new FileListResponse();
		
		Language language=languageDao.getLanguage(response.getLanguageCode());		
		Location location=locationDao.get(id,language.getId());
		if (location!=null){
			response.setFiles(fileDao.getByEntity(Location.ENTITY_NAME, id));
		}
		else
			setResult(response, ICyBssResultConst.RESULT_NOT_FOUND, 
					ICyBssResultConst.RESULT_D_NOT_FOUND,response.getLanguageCode());
		
		logger.info("LocationWs.getFiles() <<< ");
		
		return response;
	}
	
	
	@RequestMapping(value = "/{id}/remove",method = RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public LocationResponse remove(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id
			) throws CyBssException{
		
		logger.info("LocationWs.remove() >>> id="+id);
		LocationResponse response=new LocationResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",String.class,Long.class))
			return response;
		// end checkGrant 
		
		locationDao.remove(id);
		
		logger.info("LocationWs.remove() <<< ");
		return response;
	}
	
	@RequestMapping(value = "/{id}/removeLang/{langId}",method = RequestMethod.GET)
	@CyBssOperation(name = "removeLang")
	public LocationResponse removeLang(
			@RequestHeader("Security-Token") String securityToken,
			@PathVariable("id") Long id,
			@PathVariable("langId") Long langId
			) throws CyBssException{
		
		logger.info("LocationWs.removeLang() >>> id="+id+",langId="+langId);
		LocationResponse response=new LocationResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"removeLang",String.class,Long.class,Long.class))
			return response;
		// end checkGrant 
		
		locationDao.removeLang(id,langId);
		
		logger.info("LocationWs.removeLang() <<< ");
		return response;
	}
	
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public LocationListResponse find(
			@RequestHeader(value="Security-Token",required=false, defaultValue="") String securityToken,
			@RequestHeader(value="Language",required=false, defaultValue="") String languageCode,
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="locationType", required=false, defaultValue="") String locationType,
			@RequestParam(value="cityId", required=false, defaultValue="0") Long cityId,
			@RequestParam(value="personId", required=false, defaultValue="0") Long personId,
			@RequestParam(value="fromDate", required=false, defaultValue="") String fromDate,
			@RequestParam(value="toDate", required=false, defaultValue="") String toDate,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("LocationWs.find() >>>" );
		LocationListResponse response=new LocationListResponse();
		
		Language language=null;
		if (languageCode.equals(""))
			language=languageDao.getLanguage(response.getLanguageCode());
		else	
			language=languageDao.getLanguage(languageCode);
		
		
		List<Location> locations=locationDao.find(name, locationType, cityId, personId, fromDate, toDate, language.getId());
		int lsize=locations.size();
		if (offset!=0)
			response.setLocations(locations.subList(offset-1, (offset-1)+(size>lsize?lsize:size)));
		else
			response.setLocations(locations);
		
		logger.info("LocationWs.find() <<< ");
		
		return response;
	}
	
}
