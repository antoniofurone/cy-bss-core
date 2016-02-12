package org.cysoft.bss.core.web.service.file;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.web.annotation.CyBssOperation;
import org.cysoft.bss.core.web.annotation.CyBssService;
import org.cysoft.bss.core.web.response.ICyBssResultConst;
import org.cysoft.bss.core.web.response.file.FileListResponse;
import org.cysoft.bss.core.web.response.file.FileResponse;
import org.cysoft.bss.core.web.service.CyBssWebServiceAdapter;
import org.cysoft.bss.core.web.service.ICyBssWebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
@CyBssService(name = "File")
public class FileCntl extends CyBssWebServiceAdapter
implements ICyBssWebService{
	
	private static final Logger logger = LoggerFactory.getLogger(FileCntl.class);
	
	private static final int BUFFER_SIZE = 4096;
	
	FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	@RequestMapping(value="upload",method=RequestMethod.GET)
	public @ResponseBody FileResponse uploadInfo(){
		FileResponse response=new FileResponse();
		response.setResultCode(ICyBssResultConst.RESULT_NOK);
		response.setResultDesc("You can uploading a file by posting to this same URL");
		return response;
	}
	
	@RequestMapping(consumes="multipart/form-data",value="upload",method=RequestMethod.POST)
	@CyBssOperation(name = "upload")
	public @ResponseBody FileResponse upload(
				@RequestParam(value="name", required=false, defaultValue="") String fileName,
				@RequestParam(value="fileType",required=false, defaultValue="") String fileType,
				@RequestParam("file") MultipartFile file,
				@RequestParam(value="entityName",required=false, defaultValue="") String entityName,
				@RequestParam(value="entityId",required=false, defaultValue="0") long entityId,
				@RequestParam(value="note",required=false, defaultValue="") String note,
				@RequestParam(value="visibility",required=false, defaultValue="P") String visibility,
				@RequestHeader("Security-Token") String securityToken
			) throws CyBssException{
		
		FileResponse response=new FileResponse();
		
		
		Method[] meths=this.getClass().getMethods();
		Method method=null;
		for(Method m:meths){
			if (m.getName().equals("upload")){
				method=m;
				break;
			}
		}
		
		if (!checkGrant(response,method,securityToken,0))
			return response;
	
		
		logger.info("Security Token:"+securityToken);
		logger.info("File Name:"+fileName);
		
		if (!file.isEmpty()){
			if (fileName.equals(""))
				fileName=file.getOriginalFilename();
			
			logger.info("Name:"+file.getOriginalFilename());
			logger.info("Content Type:"+file.getContentType());
			logger.info("Size:"+file.getSize());
			
			try {
				fileDao.upload(fileName,file, fileType, entityName, entityId, note, visibility);
			} catch (CyBssException e) {
				// TODO Auto-generated catch block
				response.setResultCode(ICyBssResultConst.RESULT_NOK);
				response.setResultDesc(e.getMessage());
				return response;
			}
		}
		else
		{
			setResult(response, ICyBssResultConst.RESULT_FILE_EMPTY, 
					ICyBssResultConst.RESULT_D_FILE_EMPTY,response.getLanguageCode());
			return response;
		}
		return response;
	}

	@RequestMapping(value="/{id}/download",method=RequestMethod.GET)
	public void download(
			HttpServletResponse response,
			@PathVariable("id") Long fileId
			) throws CyBssException{
		
		logger.info("fileId="+fileId);
		CyBssFile file=fileDao.get(fileId);
		if (!file.getVisibility().equals(CyBssFile.VISIBILITY_PUBLIC))
			throw new CyBssException("File <"+fileId+"-"+file.getName()+"> is reserved");
		
		file=fileDao.download(fileId);
		response.setContentType(file.getContentType());
		
		int fileLen;
		try {
			fileLen=file.getContent().available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		response.setContentLength(fileLen);
		response.setHeader("Content-Disposition", 
				String.format("attachment; filename=\"%s\"",  file.getName()));
		
	
        try {
        	OutputStream outStream = response.getOutputStream();
        	
        	byte[] buffer = new byte[BUFFER_SIZE];
	        int bytesRead = -1;
	 
        	while ((bytesRead = file.getContent().read(buffer)) != -1) {
			    outStream.write(buffer, 0, bytesRead);
    		}
	    	
        	file.getContent().close();
			outStream.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
    		logger.error(e.toString());
			throw new CyBssException(e);
		}
        
	}
	
	@RequestMapping(value="/{token}/{id}/download",method=RequestMethod.GET)
	@CyBssOperation(name = "reservedDownload")
	public void reservedDownload(
			HttpServletResponse response,
			@PathVariable("token") String token,
			@PathVariable("id") Long fileId
			) throws CyBssException{
		logger.info("fileId="+fileId);
		
		// checkGrant
		FileResponse fileResponse=new FileResponse();
		if (!checkGrant(fileResponse,token,"reservedDownload",HttpServletResponse.class,String.class,Long.class))
			throw new CyBssException(fileResponse.getResultCode()+"-"+fileResponse.getResultDesc());
		// end checkGrant 
		
		CyBssFile file=fileDao.download(fileId);
		response.setContentType(file.getContentType());
		int fileLen;
		try {
			fileLen=file.getContent().available();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.toString());
			throw new CyBssException(e);
		}
		response.setContentLength(fileLen);
		response.setHeader("Content-Disposition", 
				String.format("attachment; filename=\"%s\"",  file.getName()));
		
	
        try {
        	OutputStream outStream = response.getOutputStream();
        	
        	byte[] buffer = new byte[BUFFER_SIZE];
	        int bytesRead = -1;
	 
        	while ((bytesRead = file.getContent().read(buffer)) != -1) {
			    outStream.write(buffer, 0, bytesRead);
    		}
	    	
        	file.getContent().close();
			outStream.close();
        } catch (IOException e) {
			// TODO Auto-generated catch block
    		logger.error(e.toString());
			throw new CyBssException(e);
		}
        
	}
	
	@RequestMapping(value="/{id}/remove",method=RequestMethod.GET)
	@CyBssOperation(name = "remove")
	public @ResponseBody FileResponse remove(
			@PathVariable("id") Long fileId,
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException {
	
		FileResponse response=new FileResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",Long.class,String.class))
			return response;
		// end checkGrant 
				
		
		fileDao.remove(fileId);
		return response;
	}
	
	
	@RequestMapping(value="/{id}/makePublic",method=RequestMethod.GET)
	@CyBssOperation(name = "makePublic")
	public @ResponseBody FileResponse makePublic(
			@PathVariable("id") Long fileId,
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException {
	
		FileResponse response=new FileResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",Long.class,String.class))
			return response;
		// end checkGrant 
				
		
		fileDao.makePublic(fileId);
		return response;
	}
	
	@RequestMapping(value="/{id}/makeReserved",method=RequestMethod.GET)
	@CyBssOperation(name = "makeReserved")
	public @ResponseBody FileResponse makeReserved(
			@PathVariable("id") Long fileId,
			@RequestHeader("Security-Token") String securityToken
			) throws CyBssException {
	
		FileResponse response=new FileResponse();
		
		// checkGrant
		if (!checkGrant(response,securityToken,"remove",Long.class,String.class))
			return response;
		// end checkGrant 
				
		
		fileDao.makeReserved(fileId);
		return response;
	}
	
	@RequestMapping(value = "/find",method = RequestMethod.GET)
	@CyBssOperation(name = "find")
	public @ResponseBody FileListResponse find(
			@RequestParam(value="name", required=false, defaultValue="") String name,
			@RequestParam(value="type", required=false, defaultValue="") String type,
			@RequestParam(value="entityName", required=false, defaultValue="") String entityName,
			@RequestParam(value="visibility", required=false, defaultValue="") String visibility,
			@RequestParam(value="offset", required=false, defaultValue="0") Integer offset,
			@RequestParam(value="size", required=false, defaultValue="100") Integer size
			) throws CyBssException{
		
		logger.info("FileCntl.find() >>> name="+name+";type="+type+
				";entityName="+entityName+";visibility="+visibility);
		FileListResponse response=new FileListResponse();
		
		List<CyBssFile> files=fileDao.find(name,type,entityName,visibility);
		int lsize=files.size();
		if (offset!=0)
			response.setFiles(files.subList(offset-1, (offset-1)+(size>lsize?lsize:size)));
		else
			response.setFiles(files);

		logger.info("FileCntl.find() <<< ");
		return response;
	}
}
