package com.testjunit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.annotation.TestAnnoFour;
import com.service.SimpleService;

/**
 * 	@ActiveProfiles("default")
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@ActiveProfiles("sonar") 读取sonar.yml
public class JunitServiceTest {
	
	@Autowired
	@InjectMocks
	private SimpleService simpleService;
	
	/**
	 * 需要mock的DAO
	 */
	@Mock
	private TestAnnoFour testAnnoFour;
	
	private int a;
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println(111);
		/* 
		Mockito.when(deviceClient.deleteByImei(memberId, imei)).thenReturn(rlt1);
		Mockito.when(deviceClient.updateDevices(req)).thenReturn(rlt1);
		Mockito.when(deviceClient.queryDeviceByImei(memberId)).thenReturn(rlt2);
		Mockito.when(deviceClient.queryDevicesList(memberId)).thenReturn(rlt3);
		*/
		Mockito.when(simpleService.getStr(1)).thenReturn("xp_1");
		Mockito.when(simpleService.getStr(2)).thenReturn("xp_2");
	}

	
	@Test
	public void testXp1() {
		a = 1;
		String ret = simpleService.getStr(a);
		System.out.println("testXp1 ret:" + ret);
		Assert.assertTrue("xp".equals(ret));
	}
	
	@Test
	public void testXp2() {
		a = 2;
		String ret = simpleService.getStr(a);
		System.out.println("testXp2 ret：" + ret);
		Assert.assertTrue(!"xp".equals(ret));
	}
	
	
}
