<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新增终端</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${path}/js/common/commcheck.js"></script>
</head>
<body>
	<div> 
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 380px;">
				<tr>
					<td class="label">终端类型</td>
					<td>
						<input name="clientType" id="clientType" class="easyui-combobox" style="width:130px;"/>
					</td>
				</tr>	
				<tr>
					<td class="label">终端名称</td>
					<td >
						<input name="clientName" id="clientName" class="easyui-textbox" style="width:250px;" required="true" data-options="validType:'length[1,100]'"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr id="tr1" style="display:none;">
					<td class="label">网点名称</td>
					<td >
						<input name="outletId" id="outletId" class="easyui-combobox" style="width:250px;" required="true"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr id="trFlag" style="display:none;">
					<td class="label">票务处理</td>
					<td >
						<input type="checkbox" name="pwclFlag" id="pwclFlag" value="1"   />
					</td>
				</tr>
				<tr id="tr2" style="display:none;">
					<td class="label">区域名称</td>
					<td >
						<input name="regionId" id="regionId" class="easyui-combobox" style="width:180px;" ></input>
					</td>
				</tr>
				<tr id="tr3" style="display:none;">
					<td class="label">IP地址</td>
					<td>
						<input name="ipAddr" id="ipAddr" class="easyui-textbox" style="width:160px;" data-options="validType:['ip']" ></input>
					</td>
				</tr>
				<tr id="tr4" style="display:none;">
					<td class="label">端口</td>
					<td>
						<input name="port" id="port" class="easyui-textbox" style="width:110px;" data-options="validType:'length[1,10]'" ></input>
					</td>
				</tr>
				<tr>
					<td class="label">状态</td>
					<td>
						<select name="stat" id="stat" class="easyui-combobox" required="true" style="width:110px;"	editable="false" panelHeight='auto' >
							<option value="Y">启用</option>
							<option value="N">停用</option>
						</select>
						   
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
	$(function() {
		//区域初始化
		$('#regionId').combobox({
			method : 'get',
			valueField : 'regionId',
			textField : 'regionName',
			editable : false,
			panelHeight:'auto',
			onLoadError : function(e) {
				getAjaxError(e);
			} 
		});
		$('#tr1').show();
		$('#clientType').combobox({
			url: $ctx+'/comm/listItemsByKey.do?key=CLIENT_TYPE',   
	        method:'get',  
	        valueField:'id',  
	        textField:'text',  
	        multiple:false,  
	        editable:false, 
			required:true,
	        panelHeight:'auto',
	        onLoadSuccess:function(){
	    		$('#clientType').combobox('setValue',1);
	        }
		});
		$('#pwclFlag').on('click', function(){
			if(this.checked){
				$('#regionId').combobox('reload',$ctx + '/comm/listRegion.do');
				$('#tr2').show();
				$('#regionId').combobox({required: true});
			}else {
				$('#tr2').hide();
				$('#regionId').combobox({required: false});
			}
		});
		$('#clientType').combobox({
			onChange:function(newValue, oldValue){
				$('#regionId').combobox('clear'); 
				if(newValue=='1'||newValue=='5'){
					$('#tr1').show();
					$('#tr2').hide();
					$('#tr3').hide();
					$('#tr4').hide();
					$('#outletId').combobox({required: true});
					$('#regionId').combobox({required: false});
					$('#ipAddr').textbox({required: false});
					$('#port').textbox({required: false});
					if(newValue=='1'){
						$('#trFlag').show();
					}else
						$('#trFlag').hide();
				}
				if(newValue=='2'){
					$('#regionId').combobox('reload',$ctx + '/comm/listRegion.do'); 
					$('#tr2').show();
					$('#tr3').show();
					$('#tr4').show();
					$('#tr1').hide();
					$('#trFlag').hide();
					$('#regionId').combobox({required: true});
					$('#ipAddr').textbox({required: true});
					$('#port').textbox({required: true});
					$('#outletId').combobox({required: false});
				}
				if(newValue=='3'||newValue=='4'){
					$('#tr2').show();
					$('#tr1').hide();
					$('#tr3').hide();
					$('#tr4').hide();
					$('#trFlag').hide();
					$('#regionId').combobox({required: true});
					$('#outletId').combobox({required: false});
					$('#ipAddr').textbox({required: false});
					$('#port').textbox({required: false});
					if(newValue=='3'){
						$('#regionId').combobox('reload',$ctx + '/comm/listRegion.do'); 
					}
					if(newValue=='4'){
						$('#regionId').combobox('reload', $ctx + '/comm/listRegionN.do'); 
						$('#regionId').combobox('setValue',0);
					}
				}
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
	});
	</script>
</body>
			