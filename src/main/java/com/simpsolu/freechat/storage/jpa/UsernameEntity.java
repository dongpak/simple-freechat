/**
 * 
 */
package com.simpsolu.freechat.storage.jpa;

import java.util.Date;

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
	
	@Column(name="created_date")
	@Override
	public Date getCreatedDatetime() {		
		return super.getCreatedDatetime();
	}
	
	@Column(name="last_active_date")
	@Override
	public Date getLastActiveDatetime() {
		return super.getLastActiveDatetime();
	}
	
	@Column(name="active")
	@Override
	public Boolean getActive() {
		return super.getActive();
	}
	
}
