package test.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.RPCException;
import com.tbims.rpc.service.DataSyncService;

/**
 * blog http://www.micmiu.com
 *
 */
public class DataSyncServiceDemo {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8089;
	public static final int TIMEOUT = 1*60000; // 1分=60000毫秒

	/**
	 *
	 * @param userName
	 * @throws InterruptedException 
	 */
	public void ticketTypeSnyc() throws InterruptedException {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, "DataSyncService");
			final DataSyncService.Client client = new DataSyncService.Client(mp1);
			transport.open();

			final AUTHORIZATION auth = new AUTHORIZATION();
			auth.clientId = 1304001;
			auth.token = "8aa8a67c5d500127015d5003965c0009";
			
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						Thread.sleep(60);
						client.blacklistSnyc(auth, 11L);
					} catch (RPCException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}).run();;
			Thread.sleep(60);
			client.ticketTypeSnyc(auth, 11L);
			
			
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (null != transport) {
				transport.close();
			}
		}
	}
	
	public void queryClient() throws InterruptedException {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, "DataSyncService");
			final DataSyncService.Client client = new DataSyncService.Client(mp1);
			transport.open();

			final AUTHORIZATION auth = new AUTHORIZATION();
			auth.clientId = 1100039;
			auth.token = "8aa8a67c5da62182015da6219ebb0002";
			
			client.queryClient(auth, "2", "", "", "14,11");
			
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} finally {
			if (null != transport) {
				transport.close();
			}
		}
	}

 
	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		DataSyncServiceDemo client = new DataSyncServiceDemo();
		client.queryClient();
		//client.ticketApplyQuery();
	}

}