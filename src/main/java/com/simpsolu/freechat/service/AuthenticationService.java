/**
 * 
 */
package com.simpsolu.freechat.service;


import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.impl.NullClaim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.simpsolu.freechat.model.Username;
import com.simpsolu.freechat.storage.Storage;
import com.simpsolu.freechat.storage.StorageFactory;

/**
 * 
 * @author dongp
 *
 */
@Service
public class AuthenticationService {	
	
	private static final long		EXPIRATIONTIME 	= 864_000_000; // 10 days
	private static final String 	TOKEN_PREFIX 	= "Bearer";
	private static final String 	HEADER_STRING 	= "Authorization";

	private static Logger	logger	= LoggerFactory.getLogger(AuthenticationService.class);
	
	
    @Value("${jwt.secret}")
	private String	secret;
    
    @Value("${jwt.debug}")
    private boolean debug;
	

	/**
	 * 
	 * @param item
	 * @return
	 * @throws Exception
	 */
	public String createToken(Username item) throws Exception {
			    
		String token = JWT.create()
		    		.withIssuer("auth0")
		    		.withClaim("username.id", item.getId())
		    		.withClaim("ipAddress", item.getIpAddress())
		    		.sign(Algorithm.HMAC256(secret));
		item.setToken(token);
		return token;
	}
	
	/**
	 * 
	 * @param request
	 * @return
	 */
	public String getRemoteAddr(HttpServletRequest request) {
		
		if (debug) {
			Enumeration<String> en = request.getHeaderNames();		
			while (en.hasMoreElements()) {
				String key	= en.nextElement();			
				String val	= request.getHeader(key);
			
				logger.info("Header [" + key + "] -> " + val);
			}
		}
		
		String addr = request.getHeader("x-forwarded-for");
		if ((addr != null) && (addr.trim().isEmpty() == false)) {
			return addr;
		}
		
		return request.getRemoteAddr();
	}
		
	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Username verifyToken(HttpServletRequest request) throws Exception {
		String 		auth 		= request.getHeader("Authorization");
		String 		token		= auth.substring(7);
		Username	item		= verifyToken(token);
		
		if (item.getIpAddress().equals(getRemoteAddr(request)) == false) {
			throw new NotAuthorizedException("IP address(" + request.getRemoteAddr() + ") does not match one in token: " + item.getIpAddress());
		}
		return item;
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 * @throws Exception
	 */
	public Username verifyToken(String token) throws Exception {
		try {
			Username	item	= new Username();
			DecodedJWT	jwt		= JWT.require(Algorithm.HMAC256(secret))
									.withIssuer("auth0")
									.build().verify(token);
			
			item.setId(jwt.getClaim("username.id").asString());
			item.setIpAddress(jwt.getClaim("ipAddress").asString());		
			
			return item;
		}
		catch (Throwable t) {
			throw new NotAuthorizedException("Invalid token:" + t, t);
		}
	}
	
	
//	  static Authentication getAuthentication(HttpServletRequest request) {
//	    String token = request.getHeader(HEADER_STRING);
//	    if (token != null) {
//	      // parse the token.
//	      String user = Jwts.parser()
//	          .setSigningKey(SECRET)
//	          .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
//	          .getBody()
//	          .getSubject();
//
//	      return user != null ?
//	          new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
//	          null;
//	    }
//	    return null;
//	  }
}