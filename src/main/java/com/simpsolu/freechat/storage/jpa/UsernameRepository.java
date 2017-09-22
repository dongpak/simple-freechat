/**
 * 
 */
package com.simpsolu.freechat.storage.jpa;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;


/**
 * 
 * @author dongp
 *
 */
public interface UsernameRepository extends CrudRepository<UsernameEntity, String>, JpaSpecificationExecutor<UsernameEntity> {

}
