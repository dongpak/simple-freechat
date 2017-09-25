/**
 * 
 */
package com.simpsolu.freechat.api;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;

import com.simpsolu.freechat.service.NotAuthorizedException;



/**
 * 
 * @author dongp
 *
 */
public abstract class BaseApi {

	private Logger	logger;
	
	/**
	 * 
	 * @param logger
	 */
	public BaseApi(Logger logger) {
		this.logger = logger;		
	}
	
	/**
	 * 
	 * @param t
	 * @return
	 */
	protected Response generateErrorResponse(Throwable t) {
		Response	r = null;
		
		if (t instanceof NotAuthorizedException) {
			r = Response.status(Status.UNAUTHORIZED).build();
		}
		else {
			r = Response.serverError().build();
		}
		
		logger.error("Generating " + r.getStatus() + " " + r.getStatusInfo().getReasonPhrase() + " for "+ t, t);	
		
		return r;
	}
}
