package com.zhming.support.util;

public class Test {

	@org.junit.Test
	public void testResUtil(){
		ResourcerUtil resUtil = new ResourcerUtil("frame");
		System.out.println(resUtil.get("sys.init.password","1"));
	}
	
	public void testProUtil(){
		
	}
}
