package org.cysoft.bss.core.dao;

import java.util.List;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Country;

public interface CountryDao {
	public long add(Country country) throws CyBssException;
	public List<Country> getCountryAll();
	
	public List<Country> find(String name);
	
	public void update(long id,Country country) throws CyBssException;
	public Country get(long id);
	public void remove(long id) throws CyBssException;
}
