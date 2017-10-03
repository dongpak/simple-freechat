/**
 * 
 */
package com.simpsolu.freechat.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.simpsolu.freechat.model.Message;
import com.simpsolu.freechat.model.Username;
import com.simpsolu.freechat.service.AuthenticationService;
import com.simpsolu.freechat.service.MessagingStorage;
import com.simpsolu.freechat.service.NotAuthorizedException;
import com.simpsolu.freechat.service.UsernameStorage;
import com.simpsolu.freechat.storage.Storage;
import com.simpsolu.freechat.storage.StorageFactory;



/**
 * 
 * @author dongp
 *
 */
@Component
@Path("/messaging")
public class MessagingApi extends BaseApi {

	private static Logger	logger = LoggerFactory.getLogger(MessagingApi.class);
	
	@Autowired
	private StorageFactory	factory;
	
	@Autowired
	private AuthenticationService	security;
	
	@Context 
	protected HttpServletRequest httpRequest;
	
	@Autowired
	private MessagingStorage	storage;
	
	@QueryParam("timestamp")
	private long timestamp	= 0;
	
	
	
	/**
	 * 
	 */
	public MessagingApi() {	
		super(logger);
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
		
		try {
			Username	username	= security.verifyToken(httpRequest);
		
			if (username.getId().equals(message.getUsername())) {
				storage.newMessage(message);
				new UsernameStorage(logger, factory).updateUserActivity(username);

				return Response.ok(message).build();
			}
			else {
				throw new NotAuthorizedException("Username does not match");
			}
		}		
		catch (Throwable t) {
			return generateErrorResponse(t);
		}
	}	
}
