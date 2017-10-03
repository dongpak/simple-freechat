/**
 * 
 */
package com.simpsolu.freechat.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.ringbuffer.Ringbuffer;
import com.simpsolu.freechat.model.Message;

/**
 * 
 * @author dongp
 *
 */
public class MessagingStorage {

	private static Logger logger = LoggerFactory.getLogger(MessagingStorage.class);
	
	@Autowired
	private HazelcastInstance instance;
	
	
	/**
	 * 
	 * @param msg
	 */
	public void newMessage(Message msg) {
		msg.setTimestamp(System.currentTimeMillis());
		getMessages().add(msg);		
	}
	
	protected Ringbuffer<Message> getMessages() {
		return instance.getRingbuffer("simpsolu-freechat-messages");
	}
	
	/**
	 * 
	 * @param timestamp
	 * @return
	 */
	public List<Message> getMessagesAfter(long timestamp) {
		Ringbuffer<Message>	messages	= getMessages();
		List<Message>		result 		= new ArrayList<Message>();
		
		boolean	readMore	= true;
		
		long tail	= messages.tailSequence();
		long head	= messages.headSequence();
		long curr	= head;
		
		
		while (curr <= tail) {
			
			try {
				Message msg	= messages.readOne(curr);
					
				if (msg.getTimestamp() >= timestamp) {
					result.add(msg);
				}
			}
			catch (Exception e) {
				logger.warn("Exception while reading from the ring buffer:" + e);
			}
							
			curr++;
		}
		
		return result;
	}
}
