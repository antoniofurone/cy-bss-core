package org.cysoft.bss.core.web.response.rest.server;

import java.util.List;

import org.cysoft.bss.core.model.Server;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ServerListResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	private List<Server> servers=null;

	public List<Server> getServers() {
		return servers;
	}

	public void setServers(List<Server> servers) {
		this.servers = servers;
	}
	
	
	
}
