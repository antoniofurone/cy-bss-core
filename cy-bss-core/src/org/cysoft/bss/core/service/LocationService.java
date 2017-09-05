package org.cysoft.bss.core.service;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Location;

public interface LocationService {
	
	public long add(Location location) throws CyBssException;
	public void update(long id,Location location) throws CyBssException;
	
	public void addUpdLang(Location location) throws CyBssException;
	
	
	public Location get(long id,long langId);
	public void remove(long id) throws CyBssException;
	public void removeLang(long id,long langId) throws CyBssException;
	public List<Location> find(String name,String description,String locationType,long cityId,long personId,
			String fromDate,String toDate,long langId) throws CyBssException;


}
