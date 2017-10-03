/**
 * 
 */
package com.simpsolu.freechat;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import com.hazelcast.config.Config;
import com.hazelcast.config.XmlConfigBuilder;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

/**
 * 
 * @author dongp
 *
 */
@Configuration
@Order(0)
@Profile("aws")
public class InMemoryGridAwsConfiguration {

	private static Logger logger = LoggerFactory.getLogger(InMemoryGridAwsConfiguration.class);
	
	@Bean
    public HazelcastInstance instance() {
        Config config = createConfig(new NetworkHelper().determinInterfaces());
                
        // config.setManagedContext(managedContext());
        
        logger.info("Creating InMemory instance ...");
        return Hazelcast.newHazelcastInstance(config);
    }
	
	protected Config createConfig(List<String> interfaces) {		
		XmlConfigBuilder	builder = new XmlConfigBuilder();
				
		builder.getProperties().setProperty("inMemoryGrid.secret", System.getProperty("inMemoryGrid.secret", "password"));;
        
		Config cfg = builder.build();
		
		if (interfaces.isEmpty() == false) {
			cfg.getNetworkConfig().getInterfaces().setEnabled(true);	
			
			for (String addr : interfaces) {
				logger.info("Adding interface: [" + addr + "]");
				
				cfg.getNetworkConfig().getInterfaces().addInterface(addr);
			}
		}
				
		return cfg;
	}
}
