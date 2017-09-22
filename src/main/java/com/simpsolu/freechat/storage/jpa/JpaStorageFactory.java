/**
 * 
 */
package com.simpsolu.freechat.storage.jpa;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import com.simpsolu.freechat.model.Username;
import com.simpsolu.freechat.storage.Storage;
import com.simpsolu.freechat.storage.StorageException;
import com.simpsolu.freechat.storage.StorageFactory;


/**
 * 
 * @author dongp
 *
 */
public class JpaStorageFactory implements StorageFactory {

	@Autowired
	private ApplicationContext	context;
	
	private static Map<Class<?>, Class<? extends Storage<?>>> mappings	= new HashMap<Class<?>, Class<? extends Storage<?>>>();
	static {
		mappings.put(Username.class, UsernameStorage.class);
		
	}
	
	@Override
	public <I> Storage<I> getStorage(Class<I> itemType) throws StorageException {
		
		Class<? extends Storage<?>> storageClass = mappings.get(itemType);
		
		if (storageClass == null) {
			throw new StorageException("No storage found for item type: " + itemType.getName());
		}
		
		try {
			return (Storage<I>) context.getBean(storageClass);
		}
		catch (Exception e) {
			throw new StorageException("Unable to create storage for item type: " + itemType.getName(), e);
		}
	}
}
