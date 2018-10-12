package com.tbims.org;

import com.tbims.bean.IdentiyCheckCacheBean;
import com.tbims.db.entity.SlOrg;
import com.tbims.rpc.entity.RPCException;

public interface OrgInterfaceUtil {
	public void checkTicketCallback(SlOrg slOrg, IdentiyCheckCacheBean identiyCheckCacheBean) throws RPCException;
}
