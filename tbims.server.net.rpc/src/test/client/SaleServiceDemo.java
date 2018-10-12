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
import com.tbims.rpc.service.SaleService;
import com.tbims.util.UUIDGenerator;

/**
 * blog http://www.micmiu.com
 *
 */
public class SaleServiceDemo {

	public static final String SERVER_IP = "localhost";
	public static final int SERVER_PORT = 8089;
	public static final int TIMEOUT = 1 * 60000; // 1分=60000毫秒

	/**
	 *
	 * @param userName
	 */
	public void ticketApply() {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, "SaleService");
			SaleService.Client client = new SaleService.Client(mp1);
			transport.open();

			AUTHORIZATION auth = new AUTHORIZATION();
			auth.clientId = 111;
			auth.token = "";
			STR_DELIVERY_APPLY delivery_apply = new STR_DELIVERY_APPLY();
			delivery_apply.setApplyId(UUIDGenerator.getPK());
			delivery_apply.setApplyUserId("0001");
			delivery_apply.setOutletId(11);
			delivery_apply.setExamStat("0");
			delivery_apply.setApplyDeliveryTime(1111);

			delivery_apply.setStrDeliveryApplyDetaillist(new ArrayList<STR_DELIVERY_APPLY_DETAIL>());

			STR_DELIVERY_APPLY_DETAIL a = new STR_DELIVERY_APPLY_DETAIL();
			a.setApplyExamId("SQMX000111");
			a.setApplyId(delivery_apply.getApplyId());
			a.setTicketTypeId("001");

			delivery_apply.getStrDeliveryApplyDetaillist().add(a);

			client.ticketApply(auth, delivery_apply);
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

	public void ticketApplyQuery() {
		TTransport transport = null;
		try {
			transport = new TSocket(SERVER_IP, SERVER_PORT, TIMEOUT);
			// 协议要和服务端一致
			TProtocol protocol = new TBinaryProtocol(transport);
			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol, "SaleService");
			SaleService.Client client = new SaleService.Client(mp1);
			transport.open();

			AUTHORIZATION auth = new AUTHORIZATION();
			auth.clientId = 110001;
			auth.token = "";

			// List<STR_DELIVERY_APPLY> a = client.ticketApplyQuery(auth, null, new Date().getTime()-10000000*1000000, new Date().getTime()+10000000*10000000,"0");
			// System.out.println(a.size());
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
		SaleServiceDemo client = new SaleServiceDemo();
		client.ticketApply();
		// client.ticketApplyQuery();
	}

}