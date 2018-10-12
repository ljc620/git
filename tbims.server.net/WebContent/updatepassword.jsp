<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="myel" uri="/elFunction"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>密码修改</title>
<%@ include file="/common.jsp"%>
<script type="text/javascript">
//判断输入密码的类型  
function CharMode(iN) {
	if (iN >= 48 && iN <= 57) // 数字
		return 1;
	if (iN >= 65 && iN <= 90) // 大写
		return 2;
	if (iN >= 97 && iN <= 122) // 小写
		return 4;
	else
		return 8;
}
// bitTotal函数
// 计算密码模式
function bitTotal(num) {
	modes = 0;
	for (i = 0; i < 4; i++) {
		if (num & 1)
			modes++;
		num >>>= 1;
	}
	return modes;
}
// 返回强度级别
function checkStrong(sPW) {
	if (sPW.length <= 4)
		return 0; // 密码太短
	Modes = 0;
	for (i = 0; i < sPW.length; i++) {
		// 密码模式
		Modes |= CharMode(sPW.charCodeAt(i));
	}
	return bitTotal(Modes);
}

// 显示颜色
function pwStrength() {
	var pwd = $("#password").textbox("getText");
	O_color = "#eeeeee";
	L_color = "#FF0000";
	M_color = "#FF9900";
	H_color = "#33CC00";
	if (pwd == null || pwd == '') {
		Lcolor = Mcolor = Hcolor = O_color;
	} else {
		S_level = checkStrong(pwd);
		switch (S_level) {
		case 0:
			Lcolor = Mcolor = Hcolor = O_color;
		case 1:
			Lcolor = L_color;
			Mcolor = Hcolor = O_color;
			break;
		case 2:
			Lcolor = Mcolor = M_color;
			Hcolor = O_color;
			break;
		default:
			Lcolor = Mcolor = Hcolor = H_color;
		}
	}
	$("#strength_L").css("background-color", Lcolor);
	$("#strength_M").css("background-color", Mcolor);
	$("#strength_H").css("background-color", Hcolor);
}
function saveEdit() {
	if (!$('#fm').form('validate')) {
		return false;
	}
	/*var S_level = checkStrong($("#repassword").textbox("getValue"));
	if(S_level < 2) {
		alert("密码过于简单，密码至少得包含数字、小写字母、大写字母或特殊字符中的两种。");
		return false;
	}*/
	$.messager.confirm('提示', '确定提交吗？', function(r) {
		if (r) {
			$.messager.progress({
				text : '正在处理,请稍候...'
			});
			$('#fm').form('submit', {
				url : $ctx + "/usermng/updatePassword.do",
				onSubmit : function() {
					var repassword = $("#repassword").textbox("getValue");
					var password = $("#oldpass").textbox("getValue");
					if (repassword == password) {
						alert("新密码与旧密码不能相同");
						$.messager.progress("close");
						return false;
					}

					return $('#fm').form('validate');
				},
				success : function(e) {
					$.messager.progress("close");
					if (!getFormError(e)) {
						return false;
					}

					$.messager.alert('提示', '修改成功,需要重新登录!', "info", function() {
						parent.location.href = $ctx + "/sys/logout.do";
					});
				}
			});
		}
	});
}

$(function() {
	$("#password").textbox({
		inputEvents : $.extend({}, $.fn.textbox.defaults.inputEvents, {
			keyup : function(e) {
				pwStrength();
				return true;
			}
		})
	});
});
</script>
<style type="text/css">
.form-table1 {
	
}

.form-table1 td {
	height: 35px;
}

.form-table1 td.label {
	width: 70px;
}
</style>
</head>
<body>
	<div align="center" style="padding-top: 30px;">
		<div class="easyui-panel" style="width: 400px; padding: 20px 20px 6px 20px">
			<form id="fm" method="post">
				<div style="color:red;font-weight:bold;line-height:40px;font-size:12pt;">${message}</div>
				<table class="form-table1" style="width: 100%; height: 100%">
					<tr>
						<td style="padding-left:30px;">原始密码:</td>
						<td>
							<input id="oldpass" name="oldpass" type="password" class="easyui-textbox" data-options="required:true">
						</td>
					</tr>
					<tr>
						<td style="padding-left:30px;">新密码:</td>
						<td>
							<input id="password" name="password" type="password" validType="length[6,32]" class="easyui-textbox" data-options="required:true">
						</td>
					</tr>
					<tr>
						<td style="padding-left:30px;">密码强度:</td>
						<td>
							<table cellspacing="0" cellpadding="1" bordercolor="#eeeeee">
								<tr align="center" bgcolor="#f5f5f5">
									<td width="40px;" id="strength_L">弱</td>
									<td width="40px;" id="strength_M">中</td>
									<td width="40px;" id="strength_H">强</td>
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td style="padding-left:30px;">确认新密码:</td>
						<td>
							<input id="repassword" name="repassword" type="password" class="easyui-textbox" data-options="required:true" validType="equalTo['password']" invalidMessage="两次输入密码不匹配">
						</td>
					</tr>
					<tr style="">
						<td></td>
						<td style="text-align: center; padding-top: 20px;padding-left:30px;text-align:right;" align="right">
							<a href="javascript:void(0)" class="blue-btn enter" onclick="javascript:saveEdit()" style="width: 100px;">保存</a>
						</td>
					</tr>
					<!-- tr>
						<td colspan="2" style="text-align: left;color:red;font-size:9pt;" valign="bottom">
						密码至少得包含数字、小写字母、大写字母或特殊字符中的两种。
						</td>
					</tr -->
				</table>
			</form>
		</div>
	</div>

</body>
</html>