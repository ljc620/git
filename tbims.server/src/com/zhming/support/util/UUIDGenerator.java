package com.zhming.support.util;

import java.io.Serializable;
import java.net.InetAddress;
import java.util.Random;

/**
* Title: 主键生成类  <br/>
* Description: 
* @ClassName: UUIDGenerator
* @author ydc
* @date 2016-1-7 下午5:19:50
* 
*/
public class UUIDGenerator {

	private static final int IP;

	static {
		int ipadd;
		try {
			ipadd = BytesHelper.toInt(InetAddress.getLocalHost().getAddress());
			//System.out.println(InetAddress.getLocalHost().getAddress());
			//System.out.println(ipadd);
		} catch (Exception e) {
			ipadd = 0;
		}
		IP = ipadd;
	}

	private static short counter = (short) 0;

	private static final int JVM = (int) (System.currentTimeMillis() >>> 8);

	public UUIDGenerator() {
	}

	/**
	 * Unique across JVMs on this machine (unless they load this class in the
	 * same quater second - very unlikely)
	 */
	protected int getJVM() {
		return JVM;
	}

	/**
	 * Unique in a millisecond for this JVM instance (unless there are >
	 * Short.MAX_VALUE instances created in a millisecond)
	 */
	protected short getCount() {
		synchronized (UUIDGenerator.class) {
			if (counter < 0)
				counter = 0;
			return counter++;
		}
	}

	/**
	 * Unique in a local network
	 */
	protected int getIP() {
		return IP;
	}

	/**
	 * Unique down to millisecond
	 */
	protected short getHiTime() {
		return (short) (System.currentTimeMillis() >>> 32);
	}

	protected int getLoTime() {
		return (int) System.currentTimeMillis();
	}

	private static final String SEPERATOR = "";

	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace(8 - formatted.length(), 8, formatted);
		return buf.toString();
	}

	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace(4 - formatted.length(), 4, formatted);
		return buf.toString();
	}

	public Serializable generate() {
		return new StringBuffer(36).append(format(getIP())).append(UUIDGenerator.SEPERATOR).append(format(getJVM())).append(UUIDGenerator.SEPERATOR).append(format(getHiTime())).append(UUIDGenerator.SEPERATOR).append(format(getLoTime())).append(UUIDGenerator.SEPERATOR).append(format(getCount()))
				.toString();
	}

	public static String getPK() {
		return new UUIDGenerator().generate().toString();
	}
	
	public static String getRandomString(int length){
		return getRandomString(length, false);
	}
	
	public static String getRandomString(int length, boolean hasSpecChar) {
	    String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
	    String baseSpec = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~!@#$%^&*()_+=-[]{}|;:/.,<>?";
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();   
	    for (int i = 0; i < length; i++) {   
	        if(hasSpecChar)
	        	sb.append(baseSpec.charAt(random.nextInt(baseSpec.length())));
	        else
	        	sb.append(base.charAt(random.nextInt(base.length())));
	    }   
	    return sb.toString();
	}

	public static void main(String[] args)   {
		new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 1111111; i++){
					//System.out.print( UUIDGenerator.getPK() + "==" + UUIDGenerator.counter);
					System.out.print(UUIDGenerator.getRandomString(32)+"\r\n");
				}
			}
		}.run();
	}

}
