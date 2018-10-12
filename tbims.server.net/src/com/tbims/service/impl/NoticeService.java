package com.tbims.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tbims.bean.SlNoticeBean;
import com.tbims.entity.SlNotice;
import com.tbims.service.INoticeService;
import com.zhming.support.BaseService;
import com.zhming.support.bean.PageBean;
import com.zhming.support.bean.UserBean;
import com.zhming.support.util.StringUtil;
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
		sql += " ORDER BY  T.OPE_TIME DESC,T.LEV  ";
		PageBean<SlNoticeBean> ret = dbUtil.queryPageToBean("查询信息公告", sql, SlNoticeBean.class, userBean.getPageNum(), userBean.getPageSize(),params);
		return ret;
	}

	@Override
	public SlNotice showNotice(String noticeId){
		return dbUtil.findById("查询公告", SlNotice.class, noticeId);
	}

}
