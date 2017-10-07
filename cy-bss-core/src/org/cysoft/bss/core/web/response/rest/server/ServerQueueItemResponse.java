package org.cysoft.bss.core.web.response.rest.server;

import org.cysoft.bss.core.model.ServerQueueItem;
import org.cysoft.bss.core.web.response.CyBssResponseAdapter;
import org.cysoft.bss.core.web.response.ICyBssResponse;

public class ServerQueueItemResponse extends CyBssResponseAdapter
implements ICyBssResponse{
	
	private ServerQueueItem item=null;

	public ServerQueueItem getItem() {
		return item;
	}

	public void setItem(ServerQueueItem item) {
		this.item = item;
	}

	

}
