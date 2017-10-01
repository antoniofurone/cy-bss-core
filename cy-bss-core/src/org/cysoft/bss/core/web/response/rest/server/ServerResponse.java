package org.cysoft.bss.core.web.response.rest.server;

import org.cysoft.bss.core.model.Server;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ServerResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private Server server=null;

	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}
	

}
