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
public class JaxRSConfig extends ResourceConfig {
    public JaxRSConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        register(MessagingApi.class);
        register(UsernameApi.class);
    }
}