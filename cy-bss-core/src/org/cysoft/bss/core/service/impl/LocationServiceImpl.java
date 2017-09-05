package org.cysoft.bss.core.service.impl;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.dao.FileDao;
import org.cysoft.bss.core.dao.LocationDao;
import org.cysoft.bss.core.dao.ObjectDao;
import org.cysoft.bss.core.model.CyBssFile;
import org.cysoft.bss.core.model.Location;
import org.cysoft.bss.core.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class LocationServiceImpl extends CyBssServiceBase 
implements LocationService{
	
	
	protected  LocationDao locationDao=null;
	@Autowired
	public void setLocationDao(LocationDao locationDao){
			this.locationDao=locationDao;
	}
	
	
	protected  FileDao fileDao=null;
	@Autowired
	public void setFileDao(FileDao fileDao){
			this.fileDao=fileDao;
	}
	
	protected ObjectDao objectDao=null;
	@Autowired
	public void setObjectDao(ObjectDao objectDao){
			this.objectDao=objectDao;
	}
	

	@Override
	public long add(Location location) throws CyBssException {
		// TODO Auto-generated method stub
		return locationDao.add(location);
	}

	@Override
	public void update(long id, Location location) throws CyBssException {
		// TODO Auto-generated method stub
		locationDao.update(id, location);
	}

	@Override
	public void addUpdLang(Location location) throws CyBssException {
		// TODO Auto-generated method stub
		locationDao.addUpdLang(location);
	}

	@Override
	public Location get(long id, long langId) {
		// TODO Auto-generated method stub
		return locationDao.get(id, langId);
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
					List<CyBssFile> files=fileDao.getByEntity(Location.ENTITY_NAME,id);
					if (files!=null)
						for(CyBssFile file:files)
							fileDao.remove(file.getId());
					
					locationDao.removeLang(id);
					objectDao.removeAttributeValues(id, Location.ENTITY_NAME);
					locationDao.remove(id);
					
				} catch (CyBssException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException(e);
				}
				
			}
			
		});
	}

	@Override
	public void removeLang(long id, long langId) throws CyBssException {
		// TODO Auto-generated method stub
		locationDao.removeLang(id, langId);
	}

	@Override
	public List<Location> find(String name, String description, String locationType, long cityId, long personId,
			String fromDate, String toDate, long langId) throws CyBssException {
		// TODO Auto-generated method stub
		return locationDao.find(name, description, locationType, cityId, personId, fromDate, toDate, langId);
	}

}