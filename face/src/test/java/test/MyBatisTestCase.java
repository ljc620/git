package test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.tbims.face.dao.SecondinRegInfoDao;
import com.tbims.face.entity.SecondinRegInfo;


public class MyBatisTestCase {
	
	ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext(
			"spring-web.xml",
			"spring-service.xml",
			"spring-mybatis.xml");
	}
	@Test
	public void testDataSource(){
		DataSource ds = ctx.getBean(
			"dataSource", DataSource.class);
		System.out.println(ds); 
	}
	@Test
	public void testSqlSessionFactory(){
		SqlSessionFactory factory=
			ctx.getBean("sqlSessionFactory",
			SqlSessionFactory.class);
		System.out.println(factory);
	}
	
	@Test
	public void testMapperScanner(){
		MapperScannerConfigurer scanner=
			ctx.getBean("mapperScanner",
			MapperScannerConfigurer.class);
		System.out.println(scanner); 
	}
	
	@Test
	public void testSaveUser(){
		/*UserDao dao = ctx.getBean(
			"userDao", UserDao.class);
		User user=new User("123", "123",
				"123");
		dao.saveUser(user);*/
		
		/* dao = ctx.getBean("clientDao",ClientDao.class);
		
		Client c = dao.findByClient(new Client(2011404,"2c90e4815e1c5420015e1c72d24e00e0"));
		System.out.println(c.toString());*/
		SecondinRegInfoDao sriDao = ctx.getBean("secondinRegInfoDao",SecondinRegInfoDao.class);
		
		SecondinRegInfo sri = new SecondinRegInfo();
		sri.setRegId("4cf8cd68-5593-49c0-9079-3f9f1bcb2861");
		List<SecondinRegInfo> sris = sriDao.find(sri, 1);
		for(SecondinRegInfo s : sris){
			System.out.println(s.toString());
		}
		
	/*	sri.setRegId(UUID.randomUUID().toString());
		sri.setInsertTime(new Date());
		sriDao.save(sri)*/;
		/*List<Map<String,String>> fingers = sriDao.findAllFinger();
		for(Map<String,String> m : fingers) {*/
			
			/*String regid = m.get("regId");
			String fingerId1 = m.get("fingerId1");
			String fingerId2 = m.get("fingerId2");
			System.out.println("**********");
			System.out.println(regid);
			System.out.println(fingerId1);
			System.out.println(fingerId2);
		}
		*/
	}
	
	
	
}











