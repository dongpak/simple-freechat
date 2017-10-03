/**
 * 
 */
package com.simpsolu.freechat;

import javax.ws.rs.ApplicationPath;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.simpsolu.freechat.service.MessagingStorage;
import com.simpsolu.freechat.service.UsernameStorage;
import com.simpsolu.freechat.storage.StorageFactory;
import com.simpsolu.freechat.storage.jpa.JpaStorageFactory;

/**
 * 
 * @author dongp
 *
 */
@SpringBootApplication
@ApplicationPath("/freechat")
public class SimpleFreechatApplication {

	private static Logger logger = LoggerFactory.getLogger(InMemoryGridAwsConfiguration.class);
	
	public static void main(String[] args) {
		SpringApplication.run(SimpleFreechatApplication.class, args);		
	}
	
	@Bean
	protected StorageFactory getStorageFactory() {
		return new JpaStorageFactory();
	}
	
	@Order(2)
	@Bean
	protected MessagingStorage getMessagingStorage() {
		return new MessagingStorage();
	}
}
