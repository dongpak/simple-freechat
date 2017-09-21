/**
 * 
 */
package com.simpsolu.freechat.service;

import java.util.HashSet;
import java.util.Set;

import com.simpsolu.freechat.model.Username;


/**
 * 
 * @author dongp
 *
 */
public class UsernameStorage {

	// -- fields
	private Set<Username> storage;
	
	/**
	 * 
	 */
	public UsernameStorage() {
		storage = new HashSet<Username>();
	}

	/**
	 * 
	 * @param msg
	 */
	public boolean addNewUsername(Username username) {
		synchronized (storage) {
			return storage.add(username);
		}
	}
	
	public void removeUsername(Username username) {
		synchronized (storage) {
			storage.remove(username);
		}
	}
}
