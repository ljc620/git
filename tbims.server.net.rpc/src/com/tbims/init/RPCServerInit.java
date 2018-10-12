package com.tbims.init;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.TMultiplexedProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.tbims.cache.ConfigUtil;
import com.tbims.rpc.service.DataSyncService;
import com.tbims.rpc.service.SaleService;
import com.tbims.rpc.service.SercurityService;

/**
 * Title: rpc服务初始化 <br/>
 * Description:
 * @ClassName: RPCServerInit
 * @author ydc
 * @date 2017年6月14日 下午2:52:11
 * 
 */
@Component
public class RPCServerInit {
	private static final Log logger = LogFactory.getLog(ServerMain.class);

	public static final int SERVER_PORT = Integer.valueOf(ConfigUtil.configs.get("sys.rpc.port"));

	private TServerTransport serverTransport;

	@Autowired
	public SercurityService.Iface sercurityServiceImpl;

	@Autowired
	public SaleService.Iface saleServiceImpl;

	@Autowired
	public DataSyncService.Iface dataSyncServiceImpl;

	/**
	 * Title:rpc服务初始化&启动 <br/>
	 * Description:
	 */
	public void startServer() {
		try {
			logger.info("rpc thrift 开始启动...");
			// 使用线程池模式,最小默认为5，最大为int最大值
			// 初始化参数，clientTimeout:0不超时(毫秒) 60*1000*10 = 10分钟
			serverTransport = new TServerSocket(SERVER_PORT, 60*1000*10);
			TMultiplexedProcessor mprocessor = new TMultiplexedProcessor();
			TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);
			tArgs.processor(mprocessor);

			// 采用二进制方式进行数据传输
			// tArgs.protocolFactory(new TCompactProtocol.Factory());
			// tArgs.protocolFactory(new TJSONProtocol.Factory()); TSimpleServer
			tArgs.protocolFactory(new TBinaryProtocol.Factory());

			// SercurityServiceImpl sercurityServiceImpl = SpringContextUtil.getBean(SercurityServiceImpl.class);

			// 注册服务
			mprocessor.registerProcessor("SercurityService", new SercurityService.Processor<SercurityService.Iface>(sercurityServiceImpl));
			mprocessor.registerProcessor("SaleService", new SaleService.Processor<SaleService.Iface>(saleServiceImpl));
			mprocessor.registerProcessor("DataSyncService", new DataSyncService.Processor<DataSyncService.Iface>(dataSyncServiceImpl));

			TServer server = new TThreadPoolServer(tArgs);
			logger.info("rpc thrift 开始监听...监听端口:[" + SERVER_PORT + "]");
			server.serve();
			logger.info("处理成功");
		} catch (Exception e) {
			logger.error("RPC 服务启动失败", e);
		}
	}

	/**
	 * Title:服务关闭 <br/>
	 * Description:
	 */
	public void close() {
		try {
			if (serverTransport != null) {
				logger.info("rpc thrift 开始关闭...");
				serverTransport.close();
				logger.info("rpc thrift 关闭成功...");
			}
		} catch (Exception e) {
			logger.error("RPC 服务关闭失败", e);
		}
	}

}