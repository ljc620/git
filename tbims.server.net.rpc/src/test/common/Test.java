package test.common;

import java.lang.reflect.InvocationTargetException;
import java.nio.ByteBuffer;

import org.springframework.beans.BeansException;

import com.alibaba.fastjson.JSONArray;
import com.tbims.util.BeanUtils;

public class Test {
	public static void main(String[] args) throws BeansException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		byte[] a1 = "32432".getBytes();

		A a = new A();
		a.setB1(ByteBuffer.wrap(a1));


		
		B b = new B();
		b.setA1(null);
		BeanUtils.copyRPCProperties(a, b);
		System.out.println(JSONArray.toJSONString(b));

	}
}
