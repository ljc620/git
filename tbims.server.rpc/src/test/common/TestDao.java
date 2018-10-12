package test.common;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tbims.db.entity.SlOrder;
import com.tbims.db.util.DBUtil;
import com.tbims.db.util.impl.DBUtilImpl;

public class TestDao {

	public static void main(String[] args) {

		ApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { "spring-common.xml", "spring-hibernate.xml" });
		DBUtil dbUtil = factory.getBean(DBUtilImpl.class);

		Session session = null;
		// 售票信息上传(ZG-自助购票或XXC-现场扫码支付 按RFID卡或XC-现场扫码支付)
		try {
			session = dbUtil.getSessionByTransaction();
			
			SlOrder slOrder =dbUtil.queryFirstForBean("", "select * from sl_order ", SlOrder.class);
			SlOrder slOrder1 =dbUtil.queryFirstForBean("", "select * from sl_order ", SlOrder.class);

			dbUtil.commit(session);
		} finally {
			dbUtil.close(session);
		}
	}

}
