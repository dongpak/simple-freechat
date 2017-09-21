/**
 * 
 */
package com.simpsolu.freechat.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpsolu.freechat.model.Username;
import com.simpsolu.freechat.service.UsernameStorage;



/**
 * 
 * @author dongp
 *
 */
@Component
@Path("/username")
public class UsernameApi {

	@PathParam("username") 
	protected String	username;
	
	@Autowired
	private UsernameStorage	storage;
	
	/**
	 * 
	 */
	public UsernameApi() {		
	}

	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	public Response newUsername(Username username) {
		if (storage.addNewUsername(username)) {
			return Response.ok(username).build();
		}
		
		// fail or exists
		return Response.status(Status.UNAUTHORIZED).build();
	}
	
	
	@DELETE
	@Path("{username}")
	@Produces({MediaType.APPLICATION_JSON})
    public Response deleteUsername() {
		storage.removeUsername(new Username(username));
        return Response.ok().build();
    }
}
