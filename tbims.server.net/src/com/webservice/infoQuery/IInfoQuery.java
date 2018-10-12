package com.webservice.infoQuery;

import java.util.Date;

import javax.jws.WebService;

import com.webservice.CXFException;
import com.webservice.entity.SaleCheckEntity;

/**
 * Title: 售票、检票信息查询接口 <br/>
 * Description:
 * 
 * @ClassName: IInfoQuery
 * @author ydc
 * @date 2017年7月25日 下午3:08:55
 * 
 */
@WebService
public interface IInfoQuery {

	/**
	 * Title:日售票检票汇总查询接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param selDate 查询日期
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	public SaleCheckEntity getSaleCheckinByDate(String token, Date selDate, String transq) throws CXFException;

	/**
	 * Title:当日售票检票查询接口 <br/>
	 * Description:
	 * 
	 * @param orgID 机构ID（由票务系统分配）
	 * @param token 授权码（由票务系统分配）
	 * @param transq 流水号（全局唯一），由请求方提供，必须保证所有接口调用都不重复
	 * @return
	 */
	public SaleCheckEntity getCurrentSaleCheckin(String token, String transq) throws CXFException;
}
