/**
 * 
 */
package com.simpsolu.freechat;

import javax.ws.rs.ApplicationPath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

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

	public static void main(String[] args) {
		SpringApplication.run(SimpleFreechatApplication.class, args);
	}
	
	@Bean
	protected StorageFactory getStorageFactory() {
		return new JpaStorageFactory();
	}
	
	@Bean
	protected MessagingStorage getMessagingStorage() {
		return new MessagingStorage();
	}
}
