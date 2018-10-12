<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改用户</title>
<%@ include file="/common.jsp"%>
</head>
<body>
	<div>
		<form id="fm" method="post" class="bf-from">
			<table class="form-table" style="width: 680px;">
				<tr>
					<td class="label">用户编号</td>
					<td>
						<input name="userId" class="easyui-textbox bf-nochange" required="true" value="${sysUser.userId}" readonly="true"></input>
					</td>

					<td class="label">用户姓名</td>
					<td>
						<input name="userName" class="easyui-textbox" required="true" value="${sysUser.userName}"></input>
						<span class="starStyle">*</span>
					</td>
				</tr>
				<tr>
					<td class="label">岗位</td>
					<td>
						<input name="positionCode" id="positionCode" class="easyui-combobox" required="true"  value="${sysUser.positionCode}"></input>
						<span class="starStyle">*</span>
					</td>
					<td class="label">性别</td>
					<td>
						<input name="gender" id="gender" class="easyui-combobox" value="${sysUser.gender}"></input>
					</td>
				</tr>
				<tr>
					<td class="label">所属网点</td>
					<td colspan="3">
						<input name="outletId" id="outletId" class="easyui-combobox" style="width: 200px;" value="${sysUser.outletId}"></input>
						<label style="color: red;">&nbsp;*票务中心用户不用添加</label>
					</td>
				</tr>
				<tr>
					<td class="label">所属部门</td>
					<td colspan="3">
						<input name="department" class="easyui-textbox" style="width: 430px;" value="${sysUser.department}"></input>
					</td>
				</tr>
				<tr>
					<td class="label">证件类型</td>
					<td>
						<input name="cardType" id="cardType" class="easyui-combobox" value="${sysUser.cardType}"></input>
					</td>
					<td class="label" style="width: 120px;">证件号码</td>
					<td>
						<input name="cardId" class="easyui-textbox" value="${sysUser.cardId}"></input>
					</td>
				</tr>
				<tr>
					<td class="label">邮箱</td>
					<td>
						<input name="mail"  class="easyui-textbox" data-options="validType:['mail']" value="${sysUser.mail}"></input>
					</td>
					<td class="label">联系电话</td>
					<td>
						<input name="tel" class="easyui-textbox" data-options="validType:['mobile']" value="${sysUser.tel}"></input>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		$(function() {
			$('#positionCode').combobox({
				url : $ctx + '/comm/listItemsByKey.do?key=POSITION_CODE',
				method : 'get',
				valueField : 'id',
				textField : 'text',
				multiple : false,
				editable : false,
				required : true,
				panelHeight:'auto',
				onChange:function(newValue, oldValue){
					if(newValue=='01'){
						$('#outletId').combobox("setValue",'');	
						$('#outletId').combobox("readonly",true);	
					}else{
						$('#outletId').combobox("readonly",false);	
					}
				}
			});

			$('#gender').combobox({
				valueField : 'id',
				textField : 'text',
				editable : false,
				width : 120,
				data : [ {
					'id' : '1',
					'text' : '男'
				}, {
					'id' : '2',
					'text' : '女'
				} ]
			});

			$('#cardType').combobox({
				url : $ctx + '/comm/listItemsByKey.do?key=ID_CARD_TYPE',
				method : 'get',
				valueField : 'id',
				textField : 'text',
				multiple : false,
				editable : false
			});

			$('#outletId').combobox({
				url : $ctx + '/comm/listOutlet.do',
				method : 'get',
				valueField : 'outletId',
				textField : 'outletName',
				multiple : false,
				editable : false,
				loadFilter : function(data) {
					var outletLists = [ {
						outletId : '',
						outletType : '',
						outletName : '--无--'
					} ];
					return $.merge(outletLists, data);
				},
				onLoadError : function(e) {
					getAjaxError(e);
				},
				onChange:function(newValue,oldValue){
					if(newValue!=''){
						if($('#positionCode').combobox('getValue')=='01'){
							$('#outletId').combobox("setValue",'');	
						}
					}
				},
				formatter : function(rows) {
					var typeName = "";
					
					if(rows.outletType==''){
						return rows.outletName;
					}
					
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