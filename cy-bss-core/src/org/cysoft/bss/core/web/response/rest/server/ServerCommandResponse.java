package org.cysoft.bss.core.web.response.rest.server;

import org.cysoft.bss.core.model.ServerCommand;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ServerCommandResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private ServerCommand command=null;

	public ServerCommand getCommand() {
		return command;
	}

	public void setCommand(ServerCommand command) {
		this.command = command;
	}

	
	

}
