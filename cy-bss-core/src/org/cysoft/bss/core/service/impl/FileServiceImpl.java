package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl extends CyBssServiceBase 
implements FileService {

	protected  FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	@Override
	public void upload(String name, MultipartFile file, String fileType, String entityName, long entityId, String note,
			String visibility) throws CyBssException {
		// TODO Auto-generated method stub
		fileDao.upload(name, file, fileType, entityName, entityId, note, visibility);
	}

	@Override
	public CyBssFile download(long fileId) {
		// TODO Auto-generated method stub
		return fileDao.download(fileId);
	}

	@Override
	public CyBssFile get(long fileId) {
		// TODO Auto-generated method stub
		return fileDao.get(fileId);
	}

	@Override
	public void remove(long fileId) {
		// TODO Auto-generated method stub
		fileDao.remove(fileId);
	}

	@Override
	public void makePublic(long fileId) {
		// TODO Auto-generated method stub
		fileDao.makePublic(fileId);
	}

	@Override
	public void makeReserved(long fileId) {
		// TODO Auto-generated method stub
		fileDao.makeReserved(fileId);
	}

	@Override
	public List<CyBssFile> getByEntity(String entityName, long id) {
		// TODO Auto-generated method stub
		return fileDao.getByEntity(entityName, id);
	}

	@Override
	public List<CyBssFile> find(String name, String type, String entityName, String visibility) {
		// TODO Auto-generated method stub
		return fileDao.find(name, type, entityName, visibility);
	}

}
