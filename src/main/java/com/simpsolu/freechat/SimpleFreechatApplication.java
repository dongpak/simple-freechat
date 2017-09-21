package com.simpsolu.freechat;

import javax.ws.rs.ApplicationPath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.simpsolu.freechat.service.MessagingStorage;
import com.simpsolu.freechat.service.UsernameStorage;

@SpringBootApplication
@ApplicationPath("/freechat")
public class SimpleFreechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleFreechatApplication.class, args);
	}
	
	@Bean
	protected MessagingStorage getMessagingStorage() {
		return new MessagingStorage();
	}
	
	@Bean
	protected UsernameStorage getUsernameStorage() {
		return new UsernameStorage();
	}
}
