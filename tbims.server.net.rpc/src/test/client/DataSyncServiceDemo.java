package test.client;

import java.util.ArrayList;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.tbims.rpc.entity.AUTHORIZATION;
import com.tbims.rpc.entity.STR_DELIVERY_APPLY;
import com.tbims.rpc.entity.STR_DELIVERY_APPLY_DETAIL;
import com.tbims.rpc.service.DataSyncService;
import com.tbims.util.UUIDGenerator;

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
	 */
	public void ticketTypeSnyc() {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, "DataSyncService");
			DataSyncService.Client client = new DataSyncService.Client(mp1);
			transport.open();

			AUTHORIZATION auth = new AUTHORIZATION();
			auth.clientId = 110001;
			auth.token = "8aa8a67c5ce21dbb015ce21e176c0004";
			
			
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

 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DataSyncServiceDemo client = new DataSyncServiceDemo();
		client.ticketTypeSnyc();
		//client.ticketApplyQuery();
	}

}