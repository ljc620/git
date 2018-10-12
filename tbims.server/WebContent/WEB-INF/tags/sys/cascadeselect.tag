<%@ tag body-content="empty" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ attribute name="id" type="java.lang.String" required="true" description="combo的id"%>
<%@ attribute name="options" type="java.lang.String" required="true" description="easyui combo选项"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="combo的name"%>
<%@ attribute name="textField" type="java.lang.String" required="true" description="数显示的中文"%>
<%@ attribute name="valueField" type="java.lang.String" required="true" description="树数据的id"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树加载数据的url"%>
<%@ attribute name="paramName" type="java.lang.String" required="true" description="对应后台参数名字"%>
<%@ attribute name="parentId" type="java.lang.String" required="true" description="级联上级的id"%>
<%@ attribute name="isParentLevel" type="java.lang.String" required="true" description="是否有级联的下级"%>
<%@ attribute name="childLevelId" type="java.lang.String" required="false" description="级联的下级菜单的id"%>

<input id='${id}' name='${name}' data-options="${options}"/>
<script type="text/javascript">
$(document).ready(function(){
	if('${isParentLevel}' == 'true'){
		$('#${id}').combobox({
			onChange:function(){
				$("#${childLevelId}").combobox('clear');
			}
		});
	}
	
	$("#${id}").combobox({
		valueField:'${valueField}',
		textField:'${textField}',
		onShowPanel:function(){
			$("#${id}").combobox("validate");
			if($("#${parentId}").combobox("getValue")!=""){
				var sep='?';
				if('${url}'.indexOf('?') != -1){
					sep='&';
				}
				$("#${id}").combobox({
					url:'${path}/${url}'+sep+'${paramName}='+$("#${parentId}").combobox("getValue")
				});
			} else {
				$("#${id}").combobox('loadData', []);
			}
		}
	});
});
	
</script>