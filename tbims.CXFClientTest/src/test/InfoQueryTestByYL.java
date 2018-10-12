package test;

import java.rmi.RemoteException;
import java.util.concurrent.Callable;

import com.webservice.saleTicket.CXFException;
import com.webservice.saleTicket.ISaleTicketProxy;
import com.webservice.saleTicket.TicketType;

/**
 * Title: 查询压力测试 <br/>
 * Description:
 * 
 * @ClassName: SaleTicketTest
 * @author ydc
 * @date 2017年7月25日 下午6:00:47
 * 
 */
public class InfoQueryTestByYL implements Callable<Object> {

	private String taskNum;

	public InfoQueryTestByYL(String taskNum) {
		this.taskNum = taskNum;
	}

	@Override
	public Object call() {
		ISaleTicketProxy a = new ISaleTicketProxy();
		TicketType[] rets = null;
		try {
			for (int i = 1; i < 1000; i++) {
				rets = a.getTicketType("1", "2", "3");
				System.out.println(taskNum + rets[0].getTicketTypeName());
			}

		} catch (CXFException e) {
			e.printStackTrace();
			System.out.println(e.getFaultString());
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return rets;
	}
}
