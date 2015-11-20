package org.cysoft.bss.core.dao;

import org.cysoft.bss.core.common.CyBssException;
import org.cysoft.bss.core.model.Location;

public interface LocationDao {
	public long add(Location location) throws CyBssException;
	public Location get(long id);
}
