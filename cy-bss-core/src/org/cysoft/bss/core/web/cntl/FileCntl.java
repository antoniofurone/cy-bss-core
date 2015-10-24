package org.cysoft.bss.core.web.cntl;

import org.cysoft.bss.core.rest.response.file.FileResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileCntl {
	
	private static final Logger logger = LoggerFactory.getLogger(FileCntl.class);
	
	
	@RequestMapping(value="upload",method=RequestMethod.GET)
	public @ResponseBody FileResponse uploadInfo(){
		FileResponse response=new FileResponse();
		response.setResultCode("OK");
		response.setResultDesc("You can uploading a file by posting to this same URL");
		return response;
	}
	
	@RequestMapping(consumes="multipart/form-data",value="upload",method=RequestMethod.POST)
	public @ResponseBody FileResponse upload(
				@RequestParam(value="name", required=false, defaultValue="") String fileName,
				@RequestParam("file") MultipartFile file
			){
		
		FileResponse response=new FileResponse();
		logger.info("File Name:"+fileName);
		
		if (!file.isEmpty()){
			if (fileName.equals(""))
				fileName=file.getOriginalFilename();
			logger.info("Name:"+file.getOriginalFilename());
			logger.info("Content Type:"+file.getContentType());
			logger.info("Size:"+file.getSize());
			
			//file.getInputStream()
			// Set input string in blob column
			
			response.setResultCode("OK");
		}
		else
		{
			response.setResultCode("NOK");
		}
		return response;
	}

}
