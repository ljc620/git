package com.junit.test.dao;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tbims.entity.SlOrder;
import com.zhming.support.db.DBUtil;
import com.zhming.support.db.impl.DBUtilImpl;

public class TestDao {

	public static void main(String[] args) {

		ApplicationContext factory = new ClassPathXmlApplicationContext(new String[] { "spring-common.xml", "spring-hibernate.xml" });
		DBUtil dbUtil = factory.getBean(DBUtilImpl.class);

		Session session = null;
		// 售票信息上传(ZG-自助购票或XXC-现场扫码支付 按RFID卡或XC-现场扫码支付)
		try {
			session = dbUtil.getSessionByTransaction();
			
			int num=dbUtil.executeSql("", "update error_tickettype_detail set TICKET_TYPE_ID=TICKET_TYPE_ID ");
			System.out.println(num);
			dbUtil.commit(session);
			
			
			
		} finally {
			dbUtil.close(session);
		}
	}

}
