/**
 * 
 */
package com.simpsolu.freechat.storage.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.simpsolu.freechat.model.Username;


/**
 * 
 * @author dongp
 *
 */
@Entity
@Table(name="username")
public class UsernameEntity extends Username {
	
	@Override
	@Id
	@Column(name="id")
	public String getId() {
		return super.getId();
	}
	
	@Column(name="ip_address")
	@Override
	public String getIpAddress() {		
		return super.getIpAddress();
	}
}
