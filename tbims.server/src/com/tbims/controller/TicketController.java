package com.tbims.controller;

import java.util.List;
import java.util.Map;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.tbims.service.ITicketService;
import com.zhming.support.BaseController;
import com.zhming.support.annontion.ControlAspect;
import com.zhming.support.annontion.ControllerException;
import com.zhming.support.common.MSG;
import com.zhming.support.common.OperType;
import com.zhming.support.exception.BaseException;
import com.zhming.support.exception.ServiceException;
import com.zhming.support.util.StringUtil;

/**
 * 
 * Title: 门票出入库-门票入库管理 <br/>
 * Description:
 * 
 * @ClassName: TicketController
 * @author ly
 * @date 2017年9月1日 下午3:29:24
 *
 */
@RestController
@RequestMapping("/ticket/")
public class TicketController extends BaseController {
	@Autowired
	private ITicketService ticketService;

	/**
	 * 
	 * Title: 导入数据文件<br/>
	 * Description:
	 * 
	 * @param xmlFilePath
	 * @throws Exception
	 */
	@RequestMapping(value = "loadDataFromXML")
	@ControlAspect(funtionCd = "i2_delivery_in", operType = OperType.ADD, havPrivs=true)
	@ControllerException(type = "page")
	public ModelAndView loadDataFromXML(MultipartFile xmlFilePath) throws Exception {
		ModelAndView mv = new ModelAndView("pages/delivery/imp");
		String batchId = ticketService.loadDataFromXML(getLoginUserBean(), xmlFilePath);
		mv.addObject("batchId", batchId);
		mv.addObject("MSG", batchId + ":数据导入成功");
		return mv;
	}

	/**
	 * 
	 * Title: 查询箱信息<br/>
	 * Description:
	 * 
	 * @param batchId
	 * @return
	 */
	@RequestMapping(value = "listChest")
	@ControlAspect(funtionCd = "查询临时箱信息", operType = OperType.QUERY)
	@ControllerException
	public List<Map<String, Object>> listChest() {
		return ticketService.listChest(getLoginUserBean());
	}

	/**
	 * 
	 * Title: <br/>
	 * Description:
	 * 
	 * @param chestIds
	 * @param batchId
	 * @throws ServiceException
	 */
	@RequestMapping(value = "checkChest")
	@ControlAspect(funtionCd = "核对箱号", operType = OperType.QUERY)
	@ControllerException
	public void checkChest(String chestIds, String batchId) throws ServiceException {
		ticketService.checkChest(chestIds, batchId);
	}

	/**
	 * 
	 * Title: 获取未审核数量<br/>
	 * Description:
	 * 
	 * @param batchId
	 * @param flag
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "getChestTmpNum")
	@ControlAspect(funtionCd = "获取未审核数量", operType = OperType.QUERY)
	@ControllerException
	public int getChestTmpNum(String batchId, String flag) throws Exception {
		if (StringUtil.isNull(batchId)) {
			throw new Exception("批次号不能为空");
		}
		return ticketService.getChestTmpNum(batchId, "N");
	}

	/**
	 * 
	 * Title: 数据保存至正式表<br/>
	 * Description:
	 * 
	 * @param batchId
	 * @throws Exception
	 */
	@RequestMapping(value = "saveInfo")
	@ControlAspect(funtionCd = "i2_delivery_in", operType = OperType.ADD, havPrivs=true)
	@ControllerException
	public void saveInfo(String batchId) throws Exception {
		if (StringUtil.isNull(batchId)) {
			throw new Exception("批次号不能为空");
		}
		try {
			ticketService.saveInfo(getLoginUserBean(), batchId);
		} catch (ConstraintViolationException e) {
			throw new BaseException(MSG.DB_ERROR, "提交的数据已存在");
		}
	}

	@RequestMapping(value = "deleteChestTmp")
	@ControlAspect(funtionCd = "删除入库临时表", operType = OperType.DELETE)
	@ControllerException
	public void deleteChestTmp() throws Exception {
		ticketService.deleteChestTmp(getLoginUserBean());
	}
}
