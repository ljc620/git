<%@ tag body-content="empty" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<%@ attribute name="id" type="java.lang.String" required="true" description="combo的id"%>
<%@ attribute name="options" type="java.lang.String" required="true" description="easyui combo选项"%>
<%@ attribute name="name" type="java.lang.String" required="true" description="combo的name"%>
<%@ attribute name="textField" type="java.lang.String" required="true" description="数显示的中文"%>
<%@ attribute name="idField" type="java.lang.String" required="true" description="树数据的id"%>
<%@ attribute name="url" type="java.lang.String" required="true" description="树加载数据的url"%>
<%@ attribute name="pidField" type="java.lang.String" required="true" description="树数据的pid"%>
<%@ attribute name="isParentLevel" type="java.lang.String" required="true" description="是否有级联的下级"%>
<%@ attribute name="childLevelId" type="java.lang.String" required="false" description="级联的下级菜单的id"%>

<select  id='${id}' class="easyui-combobox" name='${name}' data-options="${options}"></select>
<ul id="${id}tree" class="ztree" ></ul>

<script type="text/javascript">
$(document).ready(function(){
	var check = '${checked}';
	if('${isParentLevel}' == 'true'){
		$('#${id}').combobox({
			onChange:function(){
				$("#${childLevelId}").combobox('clear');
			}
		});
	}
	
	$('#${id}tree').appendTo($('#${id}').combo('panel'));
	var setting = {
			data:{
				key:{name:'${textField}'},
				simpleData:{
					enable: true,
					idKey: '${idField}',
					pIdKey: '${pidField}',
					rootPId: null
				}
			},
			async:{url:'${path}/${url}',enable:true},
			view:{showIcon:false,showLine:true},
			callback:{
				onClick:function(event,treeId,treeNode){
					$("#${id}").combo("setText",treeNode['${textField}']);
					$("#${id}").combo("setValue",treeNode['${idField}']);
					$("#${id}").combo("hidePanel");
					
				},
			  beforeClick:function(treeId, treeNode, clickFlag) {
				var openflag=treeNode.open;
				if (treeNode.isParent) {
					return false;
				}
				else{
				return true;
				}
			}
			}
			
	};
	$.fn.zTree.init($("#${id}tree"),setting);
});
	
</script>