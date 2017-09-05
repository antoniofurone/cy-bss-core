package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.CyBssFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
	
	public void upload(String name,MultipartFile file, 
			String fileType,String entityName,long entityId,
			String note,String visibility) throws CyBssException;
	
	public CyBssFile download(long fileId);
	public CyBssFile get(long fileId);
	
	
	public void remove(long fileId);
	
	public void makePublic(long fileId);
	public void makeReserved(long fileId);
	
	public List<CyBssFile> getByEntity(String entityName,long id);
	public List<CyBssFile> find(String name,String type,String entityName,String visibility);

}
