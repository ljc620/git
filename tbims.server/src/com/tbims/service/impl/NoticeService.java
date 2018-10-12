package com.tbims.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.SlNoticeBean;
import com.tbims.entity.SlNotice;
import com.tbims.entity.SysDictionary;
import com.tbims.service.INoticeService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.common.MSG;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;
import com.zhming.support.util.UUIDGenerator;
@Service
public class NoticeService extends BaseService implements INoticeService {

	@Override
	public PageBean<SlNoticeBean> listNotice(UserBean userBean, SlNotice slNotice) {
		Map<String, Object> params = new HashMap<String, Object>();
		String sql = " SELECT T.NOTICE_ID,to_char(T.OPE_TIME,'yyyy-MM-dd')AS OPE_TIME ,T.TITLE,T.CONTENT,T.LEV , USER_NAME AS  OPE_USER_NAME ";
				sql+= "FROM SL_NOTICE T  INNER JOIN SYS_USER US  ON US.USER_ID=T.OPE_USER_ID  ";
		if (StringUtil.isNotNull(slNotice.getTitle())) {
			sql += " AND TITLE LIKE :TITLE ";
			params.put("TITLE", '%'+slNotice.getTitle()+'%');
		}
		if (slNotice.getLev()!=null) {
			sql+=" AND T.LEV =:LEV ";
			params.put("LEV", slNotice.getLev());
		}	
		sql+=" ORDER BY T.OPE_TIME DESC,T.LEV ";
		PageBean<SlNoticeBean> ret = dbUtil.queryPageToBean("查询信息公告", sql, SlNoticeBean.class, userBean.getPageNum(), userBean.getPageSize(),params);
		return ret;
	}

	@Override
	public void addNotice(UserBean userBean, SlNotice slNotice) throws ServiceException {
		slNotice.setNoticeId(UUIDGenerator.getPK());
		slNotice.setOpeUserId(userBean.getUserId());
		slNotice.setOpeTime(new Date());
		if(slNotice.getContent().length()>331){
			throw new  ServiceException(MSG.ERROR,"输入内容最大长度不能超过331");
			}else{
		dbUtil.saveEntity("保存信息公告", slNotice);}
	}

	@Override
	public void delNotice(UserBean userBean, String noticeId) {
		dbUtil.deleteEntity("删除信息公告", SlNotice.class, noticeId);
	}

	@Override
	public String getLev(String noticeId) throws ServiceException {
		String ret="";
		Map<String, Object> map = new HashMap<>();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT V.*");
		sb.append("   FROM SL_NOTICE TV ");
		sb.append("  INNER JOIN SYS_DICTIONARY V ");
		sb.append("     ON TO_CHAR (TV.LEV) = V.TICKET_TYPE_ID ");
		sb.append("  WHERE TV.NOTICE_ID = :NOTICE_ID ");
		map.put("NOTICE_ID",noticeId );
		String sql=sb.toString();
		List<SysDictionary> LevList=dbUtil.queryListToBean("", sql, SysDictionary.class, map);
		for(SysDictionary sysDictionary:LevList){
			ret+=sysDictionary.getItemName()+",";
		}
		if(ret.indexOf(",")!=-1){
			ret=ret.substring(0, ret.length()-1);
		}
		return ret;
	}

	@Override
	public SlNotice showNotice(String noticeId){
		return dbUtil.findById("查询公告", SlNotice.class, noticeId);
	}

	

}
