/**
 * 
 */
package com.simpsolu.freechat.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsolu.freechat.model.Message;
import com.simpsolu.freechat.service.MessagingStorage;



/**
 * 
 * @author dongp
 *
 */
@Component
@Path("/messaging")
public class MessagingApi {

	@Autowired
	private MessagingStorage	storage;
	
	@QueryParam("timestamp")
	private long timestamp	= 0;
	
	/**
	 * 
	 */
	public MessagingApi() {		
	}

	@GET
	@Produces({MediaType.APPLICATION_JSON})
    public Response getMessages() {
		if (timestamp <= 0) {
			timestamp = System.currentTimeMillis();
		}
		
        return Response.ok(storage.getMessagesAfter(timestamp)).build();
    }
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response newMessage(Message message) {
		storage.newMessage(message);
		return Response.ok(message).build();
	}
}
