/**
 * 
 */
package com.simpsolu.freechat;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;

import com.simpsolu.freechat.api.MessagingApi;
import com.simpsolu.freechat.api.UsernameApi;


@Component
@ApplicationPath("/api")
public class RestApiConfig extends ResourceConfig {
    public RestApiConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(MessagingApi.class);
        register(UsernameApi.class);
    }
}