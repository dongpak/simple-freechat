package com.simpsolu.freechat;

import javax.ws.rs.ApplicationPath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@ApplicationPath("/freechat")
public class SimpleFreechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleFreechatApplication.class, args);
	}
}
