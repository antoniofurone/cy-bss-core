package org.cysoft.bss.core.web.response.rest.server;

import java.util.List;

import org.cysoft.bss.core.model.ServerCommand;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ServerCommandListResponse extends CyBssResponseAdapter
implements ICyBssResponse{

	private List<ServerCommand> commands=null;

	public List<ServerCommand> getCommands() {
		return commands;
	}

	public void setCommands(List<ServerCommand> commands) {
		this.commands = commands;
	}

		
	
}
