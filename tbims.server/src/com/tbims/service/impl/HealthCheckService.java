package com.tbims.service.impl;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import com.tbims.service.IHealthCheckService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.UserBean;
import com.zhming.support.exception.DBException;

@Service
public class HealthCheckService extends BaseService implements IHealthCheckService {

	private final Log logger = LogFactory.getLog(getClass());
	
	/**
	* Title: 查询所有闸机的运行状态
	* Description: 
	* @param loginUserBean
	* @return
	*/
	@Override
	public List<Map<String, Object>> listGateState(UserBean loginUserBean) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.region_id,r.region_name,t.client_id,t.client_name,t.ip_addr,t.gate_mode,t.run_stat,t.report_time,");
		sql.append("       case when t.report_time+3/60/24>sysdate then '1' else '0' end Report_Stat");
		sql.append(" from SYS_CLIENT t, SYS_REGION r ");
		sql.append(" where t.client_type ='2' and t.stat='Y' and t.region_id=r.region_id ");
		sql.append(" order by t.region_id,t.client_id ");
		
		List<Map<String, Object>> resultList = dbUtil.queryListToMap("查询自助机和闸机运行状态列表", sql.toString());
		return resultList;
	}

	/**
	* Title: 查询所有自助售票机的运行状态
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listSlfServiceState(UserBean loginUserBean) throws DBException{
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.Outlet_Id,r.Outlet_Name,t.client_id,t.client_name,t.run_stat,t.ticket_num,");
		sql.append("       case when t.report_time+3/60/24>sysdate then '1' else '0' end Report_Stat");
		sql.append(" from SYS_CLIENT t, sys_outlet r ");
		sql.append(" where t.client_type ='5' and t.stat='Y' and t.Outlet_Id=r.Outlet_Id ");
		sql.append(" order by t.Outlet_Id,t.client_id ");
		
		List<Map<String, Object>> resultList = dbUtil.queryListToMap("查询自助机和闸机运行状态列表", sql.toString());
		return resultList;
	}
	/**
	* Title: 查询所有网络代理回调错误计数
	* Description: 
	* @param loginUserBean
	* @return
	*/
	@Override
	public List<Map<String, Object>> listWLAgentCallbackError(UserBean loginUserBean) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.org_id,t.org_name,t.callback_error_count");
		sql.append(" from SL_ORG t where t.org_type='1' and t.org_stat='Y' order by t.org_id");
		
		List<Map<String, Object>> resultList = dbUtil.queryListToMap("查询网络代理回调错误计数列表", sql.toString());
		return resultList;
	}

	/**
	* Title: 查询所有需要检查状态的服务器及服务信息列表
	* Description: 
	* @param loginUserBean
	* @return
	*/
	public List<Map<String, Object>> listCheckServer(UserBean loginUserBean) throws DBException {
		String sql =" select t.cktype,t.host,t.port,t.timeout,t.name from SYS_SERVER t where t.stat='Y' order by t.orderno,t.cktype";
		List<Map<String, Object>> resultList = dbUtil.queryListToMap("查询网络代理回调错误计数列表", sql);
		return resultList;
	}
	
	/**
	* Title: 检查所有服务器及服务的状态
	* Description: 
	* @param loginUserBean
	* @return
	*/
	@Override
	public List<Map<String, Object>> listServerAndServiceState(UserBean loginUserBean, List<Map<String, Object>> checkList) throws DBException {
		for(Map<String, Object> server : checkList){
			try {
				if("1".equals(server.get("cktype"))){
					server.put("CheckState", isHostReachable(server.get("host").toString(), (int)(long)server.get("timeout")));
				} else if("2".equals(server.get("cktype"))) {
					server.put("CheckState", isPortConnectable(server.get("host").toString(), (int)(long)server.get("port"), (int)(long)server.get("timeout")));
				} else {
					server.put("CheckState", "检查类型错误");
				}
			} catch(Exception e) {
				logger.error("检查服务状态出错", e);
				server.put("CheckState", false);
				server.put("Message", e.getMessage());
			}
		}
		return checkList;
	}
	
	/**
	* Title: 重置所有网络代理回调错误计数
	* Description: 
	* @param loginUserBean
	* @return
	*/
	@Override
	public void resetWLAgentCallbackError(UserBean loginUserBean) throws DBException {
		dbUtil.executeSql("", "update SL_ORG t set t.callback_error_count=0 where t.org_type='1'");
	}

	/**
	 * Title: 服务端口可用性检查
	 * Description:
	 * @param host:主机地址
	 * @param port:端口号
	 * @param timeOut:超时时间
	 * @return
	 */
	private boolean isPortConnectable(String host, int port, int timeOut) {
        Socket socket = new Socket();
        try {
            socket.setSoTimeout(timeOut);
            socket.connect(new InetSocketAddress(host, port), timeOut);
            return  true;
        } catch (IOException e) {
        	logger.error("服务端口可用性检查时出错:(Host:" + host + ", Port:" + Integer.toString(port) + "):", e);
            //e.printStackTrace();
            return false;
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
	}

	/**
	 * Title: 主机网络连接检查
	 * Description:
	 * @param host:主机地址
	 * @param timeOut:超时时间
	 * @return
	 */
	private boolean isHostReachable(String host, int timeOut) {
        try {
            return InetAddress.getByName(host).isReachable(timeOut);
        } catch (UnknownHostException e) {
            //e.printStackTrace();
        	logger.error("主机网络连接检查时出错:(Host:" + host + "):", e);
        } catch (IOException e) {
            //e.printStackTrace();
        	logger.error("主机网络连接检查时出错:(Host:" + host + "):", e);
        }
        return false;
	}

	/**
	* Title: 查询所有网络代理待回调门票数
	* Description: 
	* @param loginUserBean
	* @return
	*/
	@Override
	public List<Map<String, Object>> listWLAgentWaitCallbackTicket(UserBean loginUserBean) throws DBException {
		StringBuffer sql = new StringBuffer();
		sql.append(" select org.org_id,org.org_name,nvl(sd.cnt,0) wait_Callback_Ticket_Count ");
		sql.append(" from SL_ORG org left join 						                         ");
		sql.append(" (select sno.org_id,count(*) cnt      		                             ");
		sql.append("      from SL_NETAGENT_ORDER sno,SL_ORDER_DETAIL sod 		             ");
		sql.append("      where sod.order_id=sno.order_id and (sod.org_callback_stat='N' or sod.org_callback_stat='W') ");
		sql.append("      group by sno.org_id) sd					                         ");
		sql.append(" on org.org_id = sd.org_id where org.org_type='1' and org.org_stat='Y'  order by org.org_id  ");
		
		List<Map<String, Object>> resultList = dbUtil.queryListToMap("查询所有网络代理待回调门票数", sql.toString());
		return resultList;
	}
	
	/**
	* Title: 根据机构ID查询待回调门票明细
	* Description: 
	* @param loginUserBean
	* @return
	*/
	@Override
	public List<Map<String, Object>> listWaitCallbackTicketDetail(UserBean loginUserBean, String orgId) throws DBException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<>();
		sql.append(" select sod.order_id,sod.order_detail_id,st.ticket_type_name,sod.identty_id,to_char(sod.check_time,'yyyy-mm-dd hh24:mi:ss') check_time,sod.org_callback_stat ");
		sql.append(" from SL_ORDER_DETAIL sod,SYS_TICKET_TYPE st,SL_NETAGENT_ORDER sno		     ");
		sql.append(" where sod.ticket_type_id=st.ticket_type_id and sod.order_id=sno.order_id and   ");
		sql.append("  (sod.org_callback_stat='N' or sod.org_callback_stat='W') and sno.org_id=:ORGID ");
		params.put("ORGID", orgId);
		
		List<Map<String, Object>> resultList = dbUtil.queryListToMap("根据机构ID查询待回调门票明细", sql.toString(), params);
		return resultList;
	}
	
	/**
	* Title: 跳过指定门票的机构回调
	* Description: 
	* @param loginUserBean
	*/
	@Override
	public void skipTicketCallback(UserBean loginUserBean, String orderDetailId) throws DBException {
		StringBuffer sql = new StringBuffer();
		Map<String, Object> params = new HashMap<>();
		sql.append(" update SL_ORDER_DETAIL sod set sod.org_callback_stat='S',sod.org_callback_time=sysdate where sod.order_detail_id=:DETAILID and sod.org_callback_stat='W'");
		params.put("DETAILID", orderDetailId);
		
		dbUtil.executeSql("跳过指定门票的机构回调", sql.toString(), params);
	}
}
