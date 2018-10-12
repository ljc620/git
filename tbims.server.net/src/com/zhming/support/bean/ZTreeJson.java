package com.zhming.support.bean;

import java.io.Serializable;

/**
 * Title: ZTree树节点对象 <br/>
 * Description:
 * @ClassName: ZTreeJson
 * @author ydc
 * @date 2016-1-10 上午1:36:47
 * 
 */
public class ZTreeJson implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String id;//当前节点
	private String pId;//父节点
	private String name;//节点名称
	private boolean open;//是否打开
	private boolean nocheck;//是否可选
	private String icon;//自定义图标
	private String iconSkin; //自定义图标样式
	private boolean checked;//是否选中 
	private boolean chkDisabled;//禁用选择框
	private String iconClose;//关闭时图标
	private String iconOpen;//打开时图标
	private String menuUrl;//菜单URL
	private String url;
	
	public ZTreeJson clone() {
		ZTreeJson tree = new ZTreeJson();
		tree.setChecked(checked);
		tree.setIcon(icon);
		tree.setId(id);
		tree.setName(name);
		tree.setNocheck(nocheck);
		tree.setOpen(open);
		tree.setpId(pId);
		tree.setUrl(url);
		return tree;
	}



	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public String getId() {
		return id;
	}



	public void setId(String id) {
		this.id = id;
	}



	public String getpId() {
		return pId;
	}



	public void setpId(String pId) {
		this.pId = pId;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public boolean isOpen() {
		return open;
	}



	public void setOpen(boolean open) {
		this.open = open;
	}



	public boolean isNocheck() {
		return nocheck;
	}



	public void setNocheck(boolean nocheck) {
		this.nocheck = nocheck;
	}



	public String getIcon() {
		return icon;
	}



	public void setIcon(String icon) {
		this.icon = icon;
	}



	public String getIconSkin() {
		return iconSkin;
	}



	public void setIconSkin(String iconSkin) {
		this.iconSkin = iconSkin;
	}



	public boolean isChecked() {
		return checked;
	}



	public void setChecked(boolean checked) {
		this.checked = checked;
	}



	public boolean isChkDisabled() {
		return chkDisabled;
	}



	public void setChkDisabled(boolean chkDisabled) {
		this.chkDisabled = chkDisabled;
	}



	public String getIconClose() {
		return iconClose;
	}



	public void setIconClose(String iconClose) {
		this.iconClose = iconClose;
	}



	public String getIconOpen() {
		return iconOpen;
	}



	public void setIconOpen(String iconOpen) {
		this.iconOpen = iconOpen;
	}



	public String getMenuUrl() {
		return menuUrl;
	}



	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	/******** setter and getter **********/

}