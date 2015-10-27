package org.cysoft.bss.core.dao;


import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.CyBssFile;
import org.springframework.web.multipart.MultipartFile;

public interface FileDao {
	public void upload(String name,MultipartFile file, 
			String fileType,String entityName,long entityId,String note) throws CyBssException;
	
	public CyBssFile download(long fileId);
	
	public void remove(long fileId);

}
