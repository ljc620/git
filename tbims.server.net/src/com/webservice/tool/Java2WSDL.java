package com.webservice.tool;

import org.apache.cxf.tools.java2ws.JavaToWS;

import com.webservice.infoQuery.IInfoQuery;
import com.webservice.saleTicket.ISaleTicket;

public class Java2WSDL {
	public static void main(String[] args) {
		String[] args1 = new String[] { "-wsdl", "-d", "./src/com/webservice/client/wsdl", ISaleTicket.class.getName() };
		JavaToWS.main(args1);

		String[] args2 = new String[] { "-wsdl", "-d", "./src/com/webservice/client/wsdl", IInfoQuery.class.getName() };
		JavaToWS.main(args2);

//		String[] args3 = new String[] { "-p","com.webservice.client", "-d", "./src","-all", "./src/com/webservice/client/wsdl/IInfoQuery.wsdl" };
//		WSDLToJava.main(args3);
	}
}
