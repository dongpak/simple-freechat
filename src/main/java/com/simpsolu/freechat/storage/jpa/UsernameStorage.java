/**
 * 
 */
package com.simpsolu.freechat.storage.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.simpsolu.freechat.model.Username;
import com.simpsolu.freechat.storage.DuplicateInStorageException;
import com.simpsolu.freechat.storage.Storage;
import com.simpsolu.freechat.storage.StorageBlock;
import com.simpsolu.freechat.storage.StorageException;


/**
 * 
 * @author dongp
 *
 */
@Repository
public class UsernameStorage implements Storage<Username> {

	@Autowired
	private UsernameRepository	repository;
	
	
	@Override
	public Username store(Username item) throws DuplicateInStorageException, StorageException {
	
		try {
			UsernameEntity	entity = repository.findOne(item.getId());
				
			if (entity == null) {
				return repository.save(createEntityFrom(item));
			}
			
			if (entity.getIpAddress().equals(item.getIpAddress())) {
				return entity;
			}
			
			throw new DuplicateInStorageException();
		}
		catch (DuplicateInStorageException dup) {
			throw dup;
		}
		catch (Throwable t) {
			throw new StorageException("Failed to store username: [" + item.getId() + "]: " + t, t);
		}
	}

	protected UsernameEntity createEntityFrom(Username item) {
		UsernameEntity entity = new UsernameEntity();
		
		entity.copy(item);
		return entity;
	}
	
	@Override
	public Username find(Username sample) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Username update(Username item) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Username archive(Username item) throws StorageException {
		UsernameEntity entity = repository.findOne(item.getId());
		
		if (entity != null) {
			repository.delete(item.getId());
		}
		
		return entity;
	}

	@Override
	public StorageBlock<Username> find(StorageBlock<Username> sample) throws StorageException {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
