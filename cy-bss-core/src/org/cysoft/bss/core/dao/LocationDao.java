package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Location;

public interface LocationDao {
	public long add(Location location) throws CyBssException;
	public void addLang(Location location) throws CyBssException;
	
	public Location get(long id,long langId);
	public void remove(long id) throws CyBssException;
	public void removeLang(long id,long langId) throws CyBssException;
	public List<Location> find(String name,String locationType,long cityId,long personId,long langId) throws CyBssException;
}
