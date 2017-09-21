/**
 * 
 */
package com.simpsolu.freechat.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.simpsolu.freechat.model.Message;

/**
 * 
 * @author dongp
 *
 */
public class MessagingStorage {

	private Queue<Message> storage;
	
	public MessagingStorage() {
		storage = new ConcurrentLinkedQueue<Message>();
	}

	/**
	 * 
	 * @param msg
	 */
	public void newMessage(Message msg) {
		msg.setTimestamp(System.currentTimeMillis());
		storage.add(msg);
		if (storage.size() > 200) {
			storage.poll();
		}
	}
	
	/**
	 * 
	 * @param timestamp
	 * @return
	 */
	public List<Message> getMessagesAfter(long timestamp) {
		List<Message>	result = new ArrayList<Message>();
		
		for (Message msg : storage) {
			if (msg.getTimestamp() >= timestamp) {
				result.add(msg);
			}
		}
		
		return result;
	}
}
