package com.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.annotation.TestAnnoFour;
import com.service.SimpleService;

@Service
public class SimpleServiceImpl implements SimpleService {
	
    @Autowired
	private TestAnnoFour testAnnoFour;

	@Override
	public String getStr(int a) {
		return a + "-------" + testAnnoFour.getNum();
	}

	@Override
	public void print() {
		System.out.println(testAnnoFour.toString());
	}

	

}
