package test;

import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;

import com.webservice.infoQuery.CXFException;
import com.webservice.infoQuery.IInfoQueryProxy;
import com.webservice.infoQuery.SaleCheckEntity;

/**
 * Title: 查询测试 <br/>
 * Description:
 * 
 * @ClassName: SaleTicketTest
 * @author ydc
 * @date 2017年7月25日 下午6:00:47
 * 
 */
public class InfoQueryTest {

	public static void main(String[] args) throws CXFException, RemoteException {
		IInfoQueryProxy a = new IInfoQueryProxy();
		Date data = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(data);
		SaleCheckEntity saleCheckEntity = a.getSaleCheckinByDate("524652255", cal, "111");
		System.out.println(saleCheckEntity.getCheckinCount() + "---" + saleCheckEntity.getSaleCount());
	}
}
