package test;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import com.webservice.saleTicket.CXFException;
import com.webservice.saleTicket.ISaleTicketProxy;
import com.webservice.saleTicket.OrderDetail;
import com.webservice.saleTicket.RefundTicket;
import com.webservice.saleTicket.TicketStatus;
import com.webservice.saleTicket.TicketType;

/**
 * Title: 售票测试 <br/>
 * Description:
 * 
 * @ClassName: SaleTicketTest
 * @author ydc
 * @date 2017年7月25日 下午6:00:47
 * 
 */
public class SaleTicketTest {
	public static String orgID = "911201163285736467";
	public static String token = "2c90e4815ecd000f015ecd3aafd5026d";

	public static void getTicketType() {
		ISaleTicketProxy a = new ISaleTicketProxy();
		try {
			TicketType[] t = a.getTicketType(orgID, token, String.valueOf(Math.random()));
			System.out.println(t.length);
		} catch (CXFException e) {
			e.printStackTrace();
			System.out.println(e.getFaultString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void GetTicketStatusByOrderID() {
		ISaleTicketProxy a = new ISaleTicketProxy();
		try {
			TicketStatus[] b = a.getTicketStatusByOrderID(orgID, token, "A0002222007", String.valueOf(Math.random()));
			System.out.println(b.length);
		} catch (CXFException e) {
			e.printStackTrace();
			System.out.println(e.getFaultString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void getTicketCountByID() {
		ISaleTicketProxy a = new ISaleTicketProxy();
		try {
			int b = a.getTicketCountByID(orgID, token, "130183198811110001", String.valueOf(Math.random()));
			System.out.println(b);
		} catch (CXFException e) {
			e.printStackTrace();
			System.out.println(e.getFaultString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Title: 售票<br/>
	 * Description:
	 */
	public static void saleDateSync() {
		ISaleTicketProxy a = new ISaleTicketProxy();
		try {
			String orderID = "A40000000008";
			int ticketCount = 1;
			long realSum = 60;
			Date data = new Date();
			Calendar saleTime = Calendar.getInstance();
			saleTime.setTime(data);
			String payType = "03";
			String payID = "12312313212456543";
			String transq = "98756987987";

			OrderDetail[] saleOrderDetails = new OrderDetail[2];
			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setTicketTypeId("21");
			orderDetail.setIdentityCode("200000000000000008");
			orderDetail.setSalePrice(60);
			orderDetail.setStartDate(saleTime);
			orderDetail.setEndDate(saleTime);
			saleOrderDetails[0] = orderDetail;

			// OrderDetail orderDetail1 = new OrderDetail();
			// orderDetail1.setTicketTypeID("35");
			// orderDetail1.setIdentityCode("130102199005042110");
			// orderDetail1.setSalePrice(150);
			// orderDetail1.setStartDate(saleTime);
			// orderDetail1.setEndDate(saleTime);
			// saleOrderDetails[1] = orderDetail1;

			int b = a.saleDateSync(orgID, token, orderID, ticketCount, realSum, saleTime, saleOrderDetails, payType, payID, transq);
			System.out.println(b);
		} catch (CXFException e) {
			e.printStackTrace();
			System.out.println(e.getFaultString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Title: 退票<br/>
	 * Description:
	 */
	public static void tradeRefund() {
		ISaleTicketProxy a = new ISaleTicketProxy();
		try {
			String orderID = "A90000000005";
			String refundID = "R" + orderID;
			int ticketCount = 1;
			long refundAMTSum = 45;
			Date data = new Date();
			Calendar refundTime = Calendar.getInstance();
			refundTime.setTime(data);
			String payType = "03";
			String payID = "123123132123";
			String transq = "987987987";

			RefundTicket[] refundTickets = new RefundTicket[1];
			RefundTicket refundTicket = new RefundTicket();
			refundTicket.setIdentityCode("130102199005042111");
			refundTicket.setRefundAmt(45);
			refundTickets[0] = refundTicket;

			int b = a.tradeRefund(orgID, token, orderID, refundID, ticketCount, refundAMTSum, refundTime, refundTickets, transq);
			System.out.println(b);
		} catch (CXFException e) {
			e.printStackTrace();
			System.out.println(e.getFaultString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		saleDateSync();
	}
}
