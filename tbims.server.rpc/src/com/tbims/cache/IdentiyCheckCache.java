package com.tbims.cache;

import java.util.concurrent.LinkedBlockingDeque;

import com.tbims.bean.IdentiyCheckCacheBean;

/**
 * Title: 身份证检票缓存 <br/>
 * Description:
 * 
 * @ClassName: IdentiyCheckCache
 * @author ydc
 * @date 2017年11月8日 上午9:16:51
 * 
 */
public class IdentiyCheckCache {
	public static LinkedBlockingDeque<IdentiyCheckCacheBean> identiyCheckCacheDeque = new LinkedBlockingDeque<IdentiyCheckCacheBean>();

	public static LinkedBlockingDeque<IdentiyCheckCacheBean> getIdentiyCheckCacheDeque() {
		return identiyCheckCacheDeque;
	}

	public static void setIdentiyCheckCacheDeque(LinkedBlockingDeque<IdentiyCheckCacheBean> identiyCheckCacheDeque) {
		IdentiyCheckCache.identiyCheckCacheDeque = identiyCheckCacheDeque;
	}
	
}
