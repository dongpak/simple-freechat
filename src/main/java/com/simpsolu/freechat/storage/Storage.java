/**
 * 
 */
package com.simpsolu.freechat.storage;


/**
 * 
 * @author dongp
 *
 */
public interface Storage<I> {
	
	/**
	 * 
	 * @param item
	 * @return
	 * @throws StorageException
	 */
	public I store(I item) throws StorageException;
	
	/**
	 * 
	 * @param sample
	 * @return
	 * @throws StorageException
	 */
	public I find(I sample) throws StorageException;
		
	/**
	 * 
	 * @param item
	 * @return
	 * @throws StorageException
	 */
	public I update(I item) throws StorageException;
	
	/**
	 * 
	 * @param item
	 * @return
	 * @throws StorageException
	 */
	public I archive(I item) throws StorageException;
	
	/**
	 * 
	 * @param sample
	 * @return
	 * @throws StorageException
	 */
	public StorageBlock<I> find(StorageBlock<I> sample) throws StorageException;
}