package test.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.tbims.rpc.entity.LOGIN_USER_INFO;
import com.tbims.rpc.service.SercurityService;

/**
 * blog http://www.micmiu.com
 *
 */
public class SercurityServiceClientDemo {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8089;
	public static final int TIMEOUT = 30000;

	/**
	 *
	 * @param userName  
	 */
	public void startClient(String userName) {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			// TProtocol protocol = new TCompactProtocol(transport);
			// TProtocol protocol = new TJSONProtocol(transport);
			
			TMultiplexedProtocol mp1=new TMultiplexedProtocol(protocol,"SercurityService");
			
			SercurityService.Client client = new SercurityService.Client(mp1);
			transport.open();
			
			LOGIN_USER_INFO  a=client.login(1100039, "1008", "CFCD208495D565EF66E7DFF9F98764DA");
			System.out.println(a);
			 System.out.println(a.getToken());
		} catch (TTransportException e) {
			System.out.println("--4---"+transport.isOpen()+"--------");
			e.printStackTrace();
		} catch (TException e) {
			System.out.println("--5---"+transport.isOpen()+"--------");
			e.printStackTrace();
		} finally {
			System.out.println("--6---"+transport.isOpen()+"--------");
			if (null != transport) {
				transport.close();
			}
			System.out.println("--7---"+transport.isOpen()+"--------");
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SercurityServiceClientDemo client = new SercurityServiceClientDemo();
		client.startClient("Michael");
	}

}