/**
 * 设备分类树形控件-带单选 赋值给#equTypeId选中id
 */
var setting = {
	check : {
		enable : true,
		chkboxType : {
			"Y" : "s",
			"N" : "s"
		}
	},
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick : beforeClick,
		onCheck : onCheck
	}
};
var regionsetting = {
	check : {
		enable : true,
		chkboxType : {
			"Y" : "s",
			"N" : "s"
		}
	},
	view : {
		dblClickExpand : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		beforeClick :regionbeforeClick,
		onCheck : regiononCheck
	}
};
var ticketsetting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "s",
				"N" : "s"
			}
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick :ticketbeforeClick,
			onCheck : ticketonCheck
		}
	};
var empsetting = {
		check : {
			enable : true,
			chkboxType : {
				"Y" : "s",
				"N" : "s"
			}
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			beforeClick :empbeforeClick,
			onCheck : emponCheck
		}
	};
var zNodes = [];
function beforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("venueTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
function regionbeforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("regionTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
function ticketbeforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("ticketTypeTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
function empbeforeClick(treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("empTree");
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("venueTree"), nodes = zTree
			.getCheckedNodes(true), nname = "";
	v = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		// if(!nodes[i].isParent){
		nname += nodes[i].name + ",";
		v += nodes[i].id + ",";
		// }
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (nname.length > 0)
		nname = nname.substring(0, nname.length - 1);
	$("#venueId").val(v);
	$("#venueId").combobox("setValue", v);
	$("#venueId").combobox("setText", nname);
}
function regiononCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("regionTree"), nodes = zTree
			.getCheckedNodes(true), nname = "";
	v = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		// if(!nodes[i].isParent){
		nname += nodes[i].name + ",";
		v += nodes[i].id + ",";
		// }
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (nname.length > 0)
		nname = nname.substring(0, nname.length - 1);
	$("#regionId").val(v);
	$("#regionId").combobox("setValue", v);
	$("#regionId").combobox("setText", nname);
}
function ticketonCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("ticketTypeTree"), nodes = zTree
			.getCheckedNodes(true), nname = "";
	v = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		// if(!nodes[i].isParent){
		nname += nodes[i].name + ",";
		v += nodes[i].id + ",";
		// }
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (nname.length > 0)
		nname = nname.substring(0, nname.length - 1);
	$("#ticketTypeId").val(v);
	$("#ticketTypeId").combobox("setValue", v);
	$("#ticketTypeId").combobox("setText", nname);
}

function emponCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("empTree"), nodes = zTree
			.getCheckedNodes(true), nname = "";
	v = "";
	for (var i = 0, l = nodes.length; i < l; i++) {
		// if(!nodes[i].isParent){
		nname += nodes[i].name + ",";
		v += nodes[i].id + ",";
		// }
	}
	if (v.length > 0)
		v = v.substring(0, v.length - 1);
	if (nname.length > 0)
		nname = nname.substring(0, nname.length - 1);
	$("#empId").val(v);
	$("#empId").combobox("setValue", v);
	$("#empId").combobox("setText", nname);
}


function initSeletedCheckBox(path,treetype,id,name) {
	var myurl="";
	//场馆
	if(treetype==0){
		if(id!=null&&name!=null){
			$("#venueId").combobox("setValue", id);
			$("#venueId").combobox("setText", name);
		}
		myurl=path + '/comm/initVenueJson.do?venueId='+id;
	}
	//区域
	else if(treetype==1){
		if(id!=null&&name!=null){
			$("#regionId").combobox("setValue", id);
			$("#regionId").combobox("setText", name);
		}
		myurl=path + '/comm/initRegionJson.do?regionId='+id;
	}
	//票种
	else if(treetype==2){
		if(id!=null&&name!=null){
			$("#ticketTypeId").combobox("setValue", id);
			$("#ticketTypeId").combobox("setText", name);
		}
		myurl=path + '/comm/initTicketTypeJson.do?ticketTypeId='+id;
	}
	//员工
	else{
		if(id!=null&&name!=null){
			$("#empId").combobox("setValue", id);
			$("#empId").combobox("setText", name);
		}
		myurl=path + '/comm/empList.do?empId='+id;
	}
	$.ajax({
		url : myurl,
		type : "post",
		datatype : "json",
		success : function(data) {
			zNodes = data;
			// 场馆
			if (treetype == 0) {
				$.fn.zTree.init($("#venueTree"), setting, zNodes);
				var treeObj = $.fn.zTree.getZTreeObj("venueTree");
				if (treeObj) {
					var fc = treeObj.getNodes()[0];
					if (fc.isParent) {
						treeObj.expandNode(fc, true, false, true);
						var nodes = fc.children;
						for (var i = 0; i < nodes.length; i++) { // 设置节点展开
							treeObj.expandNode(nodes[i], true, false, true);
						}
					}
				}
			}
			// 区域
			else if (treetype == 1){
				$.fn.zTree.init($("#regionTree"), regionsetting, zNodes);
				var orgtreeObj = $.fn.zTree.getZTreeObj("regionTree");
				var orgfc = orgtreeObj.getNodes()[0];
				if (orgfc.isParent) {
					orgtreeObj.expandNode(orgfc, true, false, true);
					var orgnodes = orgfc.children;
					for (var i = 0; i < orgnodes.length; i++) { // 设置节点展开
						orgtreeObj.expandNode(orgnodes[i], true, false, true);
					}
				}
			}
			//票种
			else  if (treetype == 2){
				$.fn.zTree.init($("#ticketTypeTree"), ticketsetting, zNodes);
				var orgtreeObj = $.fn.zTree.getZTreeObj("ticketTypeTree");
				var orgfc = orgtreeObj.getNodes()[0];
				if (orgfc.isParent) {
					orgtreeObj.expandNode(orgfc, true, false, true);
					var orgnodes = orgfc.children;
					for (var i = 0; i < orgnodes.length; i++) { // 设置节点展开
						orgtreeObj.expandNode(orgnodes[i], true, false, true);
					}
				}
			}
			//员工
			else{
				$.fn.zTree.init($("#empTree"), empsetting, zNodes);
				var orgtreeObj = $.fn.zTree.getZTreeObj("empTree");
				var orgfc = orgtreeObj.getNodes()[0];
				if (orgfc.isParent) {
					orgtreeObj.expandNode(orgfc, true, false, true);
					var orgnodes = orgfc.children;
					for (var i = 0; i < orgnodes.length; i++) { // 设置节点展开
						orgtreeObj.expandNode(orgnodes[i], true, false, true);
					}
				}
			
			}

		},
		error : function(XMLHttpRequest, textStatus, errorThrown) {
			alert("加载失败");
			alert(XMLHttpRequest.status);
			alert(XMLHttpRequest.readyState);
			alert(textStatus);
		}
	});
}