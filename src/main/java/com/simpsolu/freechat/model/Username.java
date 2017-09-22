/**
 * 
 */
package com.simpsolu.freechat.model;

import java.util.Date;

/**
 * 
 * @author dongp
 *
 */
public class Username {

	private String	id;
	private String	ipAddress;
	private Boolean	active;
	private Date	lastActiveDatetime;
	private Date	createdDatetime;


	/**
	 * 
	 */
	public Username() {		
	}
	
	/**
	 * 
	 * @param value
	 */
	public Username(String id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the lastActiveDatetime
	 */
	public Date getLastActiveDatetime() {
		return lastActiveDatetime;
	}

	/**
	 * @param lastActiveDatetime the lastActiveDatetime to set
	 */
	public void setLastActiveDatetime(Date lastActiveDatetime) {
		this.lastActiveDatetime = lastActiveDatetime;
	}

	/**
	 * @return the createdDatetime
	 */
	public Date getCreatedDatetime() {
		return createdDatetime;
	}

	/**
	 * @param createdDatetime the createdDatetime to set
	 */
	public void setCreatedDatetime(Date createdDatetime) {
		this.createdDatetime = createdDatetime;
	}
	
	/**
	 * 
	 * @param source
	 */
	public void copy(Username source) {
		setId(source.getId());
		setIpAddress(source.getIpAddress());
		setActive(source.getActive());
		setLastActiveDatetime(source.getLastActiveDatetime());
		setCreatedDatetime(source.getCreatedDatetime());
	}
}
