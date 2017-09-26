/**
 * 
 */
package com.simpsolu.freechat.api;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsolu.freechat.model.Username;
import com.simpsolu.freechat.service.AuthenticationService;
import com.simpsolu.freechat.service.NotAuthorizedException;
import com.simpsolu.freechat.storage.Storage;
import com.simpsolu.freechat.storage.StorageFactory;



/**
 * 
 * @author dongp
 *
 */
@Component
@Path("/username")
public class UsernameApi extends BaseApi {

	private static Logger	logger = LoggerFactory.getLogger(UsernameApi.class);
	
	
	@PathParam("username") 
	protected String	username;
	
	@Autowired
	private StorageFactory	factory;
	
	@Autowired
	private AuthenticationService	security;
	
	@Context 
	protected HttpServletRequest httpRequest;
	

	/**
	 * 
	 */
	public UsernameApi() {	
		super(logger);
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response newUsername(Username username) {
		Storage<Username>	storage = factory.getStorage(Username.class);
		
		try {
			updateUsername(username);
			username = storage.store(username);
			security.createToken(username);
			
			return Response.ok(username).build();
		}
		catch (Throwable t) {
			return generateErrorResponse(t);
		}
	}
	
	private void updateUsername(Username username) {		
		username.setIpAddress(security.getRemoteAddr(httpRequest));
	}
	
	@DELETE
	@Path("{username}")
	@Produces({MediaType.APPLICATION_JSON})
    public Response deleteUsername() {
		
		logger.info("Archiving username: " + username);
		Storage<Username>	storage = factory.getStorage(Username.class);
		
		try {
			Username	item = security.verifyToken(httpRequest);
			
			if (item.getId().equals(username) == false) {
				throw new NotAuthorizedException("Not allowed to archive user");
			}			
			
			return Response.ok(storage.archive(item)).build();
		}
		catch (Throwable t) {
			return generateErrorResponse(t);
		}
    }
}
