/**
 * 
 */
package com.simpsolu.freechat.storage;


/**
 * 
 * @author dongp
 *
 */
public interface StorageFactory {
	
	/**
	 * 
	 * @param itemType
	 * @return
	 * @throws StorageException
	 */
	public <I> Storage<I> getStorage(Class<I> itemType) throws StorageException;
}