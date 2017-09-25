/**
 * 
 */
package com.simpsolu.freechat.service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;

import com.simpsolu.freechat.model.Username;
import com.simpsolu.freechat.storage.Storage;
import com.simpsolu.freechat.storage.StorageFactory;


/**
 * 
 * @author dongp
 *
 */
public class UsernameStorage {

	private Logger			logger;
	private StorageFactory	factory;
	
	
	/**
	 * 
	 * @param factory
	 */
	public UsernameStorage(Logger logger, StorageFactory factory) {
		this.logger		= logger;
		this.factory 	= factory;
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Username find(Username sample) {
		Storage<Username>	storage = factory.getStorage(Username.class);
		
		return storage.find(sample);
	}
	
	/**
	 * 
	 * @param username
	 */
	public void updateUserActivity(Username item) {
		Storage<Username>	storage = factory.getStorage(Username.class);		
		
		try {			
			item.setLastActiveDatetime(new Date());
			storage.update(item);
		}
		catch (Exception e) {
			logger.warn("Unable to update activity of user: " + item.getId() + ": " + e, e);
		}
	}
}
