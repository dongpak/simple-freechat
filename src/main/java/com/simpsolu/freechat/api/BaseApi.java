/**
 * 
 */
package com.simpsolu.freechat.api;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;



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
		
		r = Response.serverError().build();
		
		logger.error("Generating " + r.getStatus() + " " + r.getStatusInfo().getReasonPhrase() + " for "+ t, t);	
		
		return r;
	}
}
