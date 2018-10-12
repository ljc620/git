<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改终端</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
</head>
<body>
	<div> 
	<form id="fm" method="post" class="bf-from">
			<input name="clientId" id="clientId" type="hidden" value="${sysClient.clientId }"></input>
			<table class="form-table" style="width: 380px;">
				<tr>
					<td class="label">终端类型</td>
					<td >
						<input name="clientType" id="clientType" class="easyui-combobox" style="width:130px;" required="true" value="${sysClient.clientType }" readonly="true"></input>
					</td>
				</tr>
				<tr>
					<td class="label">终端名称</td>
					<td>
						<input name="clientName" id="clientName" class="easyui-textbox" style="width:250px;" required="true" value="${sysClient.clientName }" data-options="validType:'length[1,100]'"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<c:if test="${sysClient.clientType=='1'||sysClient.clientType=='5'}">
					<tr>
						<td class="label">网点名称</td>
						<td>
							<input name="outletId" id="outletId" class="easyui-combobox" style="width:250px;" value="${sysClient.outletId }" readonly="true"></input>
						</td>
					</tr>
				</c:if>
				<c:if test="${sysClient.clientType=='1'}">
					<tr>
						<td class="label">票务处理</td>
						<td >
							<c:if test="${sysClient.regionId!=''&&sysClient.regionId!=null}">
								<input type="checkbox" name="pwclFlag" id="pwclFlag" value="1" checked="" />
							</c:if>
							<c:if test="${sysClient.regionId==''||sysClient.regionId==null}">
								<input type="checkbox" name="pwclFlag" id="pwclFlag" value="1" />
							</c:if>
						</td>
					</tr>
				</c:if>
				<c:if test="${sysClient.clientType=='2'||sysClient.clientType=='3'||sysClient.clientType=='4'||sysClient.clientType=='1'}">
					<tr id="tr2">
						<td class="label">区域名称</td>
						<td>
							<input name="regionId" id="regionId" class="easyui-combobox" style="width:180px;" value="${sysClient.regionId }" readonly="true"></input>
						</td>
					</tr>
				</c:if>
				<c:if test="${sysClient.clientType=='2'}">
					<tr>
						<td class="label">IP地址</td>
						<td>
							<input name="ipAddr" id="ipAddr" class="easyui-textbox" style="width:160px;" value="${sysClient.ipAddr }" data-options="validType:['ip']" ></input>
						</td>
					</tr>
					<tr>
						<td class="label">端口</td>
						<td>
							<input name="port" id="port" class="easyui-textbox" style="width:110px;" value="${sysClient.port }" data-options="validType:'length[1,10]'"></input>
						</td>
					</tr>
				</c:if>	
				<tr>
					<td class="label">状态</td>
					<td>
						<input name="stat" id="stat" class="easyui-combobox" style="width:110px;" value="${sysClient.stat }" required="true"></input>
					</td>
				</tr>
				<c:if test="${sysClient.clientType=='2'||sysClient.clientType=='3'||sysClient.clientType=='5'}">
					<tr>
						<td class="label">授权码</td>
						<td>
							<input name="token" id="token" class="easyui-textbox" style="width:280px;" value="${sysClient.token }" readonly="true" ></input>
						</td>
					</tr>
				</c:if>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	$(function() {
		$('#clientType').combobox({
			url: $ctx+'/comm/listItemsByKey.do?key=CLIENT_TYPE',   
	        method:'get',  
	        valueField:'id',  
	        textField:'text',  
	        multiple:false,  
	        editable:false, 
			required:true,
	        panelHeight:'auto' 
		});
		//终端状态初始化
		$('#stat').combobox({
			valueField : 'id',
			textField : 'text',
			editable : false,
			panelHeight :'auto',
			data : [ {
				id : 'Y',
				text : '启用'
			}, {
				id : 'N',
				text : '停用'
			} ]
		});
		//区域初始化
		$('#regionId').combobox({
			url : $ctx + '/comm/listRegion.do',
			method : 'get',
			valueField : 'regionId',
			textField : 'regionName',
			editable : false,
			panelHeight : 'auto',
			loadFilter : function(data) {
				if($('#clientType').combobox('getValue')!='1') {
					var list = [ {
						regionId : 0,
						regionName : '票务中心'
					} ];
					return $.merge(list, data);
				} else
					return data;
			}
		});
		//网点初始化
		$('#outletId').combobox({
			url : $ctx + '/comm/listOutlet.do',
			method : 'get',
			valueField : 'outletId',
			textField : 'outletName',
			multiple : false,
			editable : false,
			panelHeight : 'auto',
			onLoadSuccess : function(e) {
			},
			onLoadError : function(e) {
				getAjaxError(e);
			},
			formatter : function(rows) {
				var typeName = "";
	
				switch (rows.outletType) {
				case '01':
					typeName = "现场网点";
					break;
				case '02':
					typeName = "自营网点";
					break;
				case '03':
					typeName = "换票网点";
					break;
				case '04':
					typeName = "票务处理";
					break;
				case '05':
					typeName = "实体代理";
					break;
				case '06':
					typeName = " 签约社";
					break;
				}
	
				return typeName+"--"+rows.outletName;
			}
		});
		// 网点终端--票务处理
		$('#pwclFlag').on('click', function(){
			if(this.checked){
				$('#regionId').combobox('reload',$ctx + '/comm/listRegion.do');
				$('#tr2').show();
				$('#regionId').combobox({required: true});
			}else {
				$('#tr2').hide();
				$('#regionId').combobox({required: false});
				$('#regionId').combobox('setValue', '');
			}
		});
		if($('#clientType').combobox('getValue')=='1'){
			$('#regionId').combobox({readonly: false});
			if($('#pwclFlag').is(':checked'))
				$('#tr2').show();
			else
				$('#tr2').hide();
		}
	});
	</script>
</body>
			