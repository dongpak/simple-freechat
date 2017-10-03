/**
 * 
 */
package com.simpsolu.freechat;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import com.hazelcast.config.Config;
import com.hazelcast.config.RingbufferConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author dongp
 *
 */
@Configuration
@Order(0)
@Profile("default")
public class InMemoryGridDefaultConfiguration {

	private static Logger logger = LoggerFactory.getLogger(InMemoryGridDefaultConfiguration.class);
	
	@Bean
    public HazelcastInstance instance() {		
        Config config = createConfig(new NetworkHelper().determinInterfaces());
                
        logger.info("Creating InMemory instance ...");
//        RingbufferConfig rbConfig = new RingbufferConfig("simpsolu-freechat-messages")
//        	    .setCapacity(10);        	    
//        	   
//        config.addRingBufferConfig(rbConfig);

        return Hazelcast.newHazelcastInstance(config);
    }
	
	protected Config createConfig(List<String> interfaces) {		
		Config	cfg = new Config();
				
		if (interfaces.isEmpty() == false) {
			cfg.getNetworkConfig().getInterfaces().setEnabled(true);	
			
			for (String addr : interfaces) {
				logger.info("Adding interface: [" + addr + "]");
				
				cfg.getNetworkConfig().getInterfaces().addInterface(addr);
			}
		}
		
		cfg.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
		cfg.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true).addMember("192.168.1.50");
     
		return cfg;
	}
}
