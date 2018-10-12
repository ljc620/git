package com.zhming.support;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;

import com.tbims.entity.SysGenId;
import com.tbims.entity.SysParemeter;
import com.zhming.support.bean.UserBean;
import com.zhming.support.db.DBUtil;
import com.zhming.support.util.StringUtil;

public class BaseService {

	@Autowired
	protected DBUtil dbUtil;

	/**
	 * Title: 判断当前用户是否拥有权限代码包含超级管理员<br/>
	 * Description:
	 * @param userBean
	 * @param function_cd
	 * @return
	 */
	protected boolean isFunctionBySys(UserBean userBean, String function_cd) {
		if (userBean.getFunctionSet().contains(function_cd)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * Title: 根据规则生成序列值<br/>
	 * Description:
	 * @param seqRule
	 * @param len
	 * @return
	 */
	protected synchronized String getSeq(String seqRule, int len) {
		SysGenId tbGenId = dbUtil.findById("查询生成序列表", SysGenId.class, seqRule);
		if (tbGenId == null) {
			tbGenId = new SysGenId();
			tbGenId.setSeqVal(new BigDecimal(1));
			tbGenId.setSeqRule(seqRule);
			dbUtil.saveEntity("保存序列", tbGenId);
		} else {
			tbGenId.setSeqVal(tbGenId.getSeqVal().add(new BigDecimal(1)));
			dbUtil.updateEntity("更新序列", tbGenId);
		}
		return tbGenId.getSeqRule() + StringUtil.stringFillOrCut(String.valueOf(tbGenId.getSeqVal()), "0", len, 1);
	}
	/**
	 * 
	 * Title: 生成团队订单号<br/>
	 * Description:
	 * @param seqRule
	 * @param len
	 * @return
	 */
	protected synchronized String getSeqByTeam(String seqRule, int len) {
		SysGenId tbGenId = dbUtil.findById("查询生成序列表", SysGenId.class, seqRule);
		if (tbGenId == null) {
			tbGenId = new SysGenId();
			tbGenId.setSeqVal(new BigDecimal(1));
			tbGenId.setSeqRule(seqRule);
			dbUtil.saveEntity("保存序列", tbGenId);
		} else {
			tbGenId.setSeqVal(tbGenId.getSeqVal().add(new BigDecimal(1)));
			dbUtil.updateEntity("更新序列", tbGenId);
		}

		String r = StringUtil.convertString((int) (Math.random() * 900) + 100);// 生成三位随机数
		String seqVal = StringUtil.convertString(tbGenId.getSeqVal().intValue()) + r;
		return tbGenId.getSeqRule() + StringUtil.stringFillOrCut(seqVal, "0", len, 1);
	}

	/**
	 * 
	 * Title: 根据参数Id获取参数值<br/>
	 * Description:
	 * @param paremeterId
	 * @return
	 */
	protected String getParemeterVal(String paremeterId) {
		SysParemeter sysParemeter = dbUtil.findById("根据参数ID获取参数值", SysParemeter.class, paremeterId);
		String val = sysParemeter.getParemeterVal();
		return val;
	}
}
