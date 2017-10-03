/**
 * 
 */
package com.simpsolu.freechat;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.hazelcast.core.HazelcastInstance;
import com.simpsolu.freechat.api.MessagingApi;
import com.simpsolu.freechat.api.UsernameApi;


@ConditionalOnBean(HazelcastInstance.class)
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