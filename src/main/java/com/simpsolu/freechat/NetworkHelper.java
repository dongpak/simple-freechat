/**
 * 
 */
package com.simpsolu.freechat;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author dongp
 *
 */
public class NetworkHelper {

	private static Logger logger = LoggerFactory.getLogger(NetworkHelper.class);
	
	/**
	 * 
	 * @return
	 */
	public List<String> determinInterfaces() {
		List<String> list = new ArrayList<String>();
		
		try {
			Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
			while (nets.hasMoreElements()) {
				NetworkInterface net = nets.nextElement();
				
				if (net.isLoopback() 
				||  net.isVirtual() 
				||  net.isPointToPoint() 
				||  net.isUp() == false 
				||  net.getName().startsWith("docker")
				||  net.getName().startsWith("vir")) {				
					continue;
				}
				
				for (InterfaceAddress addr : net.getInterfaceAddresses()) {
					if (addr.getBroadcast() != null) {	// ipv4
						String 		dotNotation = addr.getAddress().getHostAddress();
						String[] 	parts		= dotNotation.split("\\.");
						
						logger.info("Found ipv4 address [" + dotNotation + "] from interface [" + net.getName() + "]");						
						
						list.add(parts[0]+"."+parts[1]+"."+parts[2]+".*");						
					}
				}
			}
		}
		catch (Exception e) {
			logger.error("Error retrieving network interfaces: " + e, e);
		}
		
		return list;
	}
}
