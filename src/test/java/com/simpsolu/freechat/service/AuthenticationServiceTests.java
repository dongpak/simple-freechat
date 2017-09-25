/**
 * 
 */
package com.simpsolu.freechat.service;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.simpsolu.freechat.model.Username;

/**
 * 
 * @author dongp
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationServiceTests {

	private static Logger	logger	= LoggerFactory.getLogger(AuthenticationServiceTests.class);
	
	@Autowired
	private AuthenticationService	testObject;
	
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
		
	}
	
	@Test
	public void testCreateAndVerify() throws Exception {
		Username	expected = new Username();
		
		expected.setId("Dong Pak");
		expected.setIpAddress("0.0.0.0");
		
		String token = testObject.createToken(expected);
		
		Assert.assertNotNull(token);
		Username	actual = testObject.verifyToken(token);
		
		Assert.assertEquals(expected.getId(), actual.getId());
		Assert.assertEquals(expected.getIpAddress(), actual.getIpAddress());
	}

}
