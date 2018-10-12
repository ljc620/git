var bConnect = 0;
var digitArray = new Array('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f');

/**
 * 页面自动加载Ukey
 * 
 * @returns {Boolean}
 */
function load() {
	// 如果是IE10及以下浏览器，则跳过不处理
	if (navigator.userAgent.indexOf("MSIE") > 0 && !navigator.userAgent.indexOf("opera") > -1)
		return;
	try {
		var s_pnp = new SoftKey6W();
		s_pnp.Socket_UK.onopen = function() {
			bConnect = 1;// 代表已经连接，用于判断是否安装了客户端服务
		}

		// 在使用事件插拨时，注意，一定不要关掉Sockey，否则无法监测事件插拨
		s_pnp.Socket_UK.onmessage = function got_packet(Msg) {
			var PnpData = JSON.parse(Msg.data);
			if (PnpData.type == "PnpEvent")// 如果是插拨事件处理消息
			{
				if (PnpData.IsIn) {
					alert("UKEY已被插入");
					// alert("UKEY已被插入，被插入的锁的路径是：" + PnpData.DevicePath);
				} else {
					alert("UKEY已被拨出");
					// alert("UKEY已被拨出，被拨出的锁的路径是：" + PnpData.DevicePath);
				}
			}
		}

		s_pnp.Socket_UK.onclose = function() {

		}
	} catch (e) {
		alert(e.name + ": " + e.message);
		return false;
	}
}

function toHex(n) {

	var result = ''
	var start = true;

	for (var i = 32; i > 0;) {
		i -= 4;
		var digit = (n >> i) & 0xf;

		if (!start || digit != 0) {
			start = false;
			result += digitArray[digit];
		}
	}

	return (result == '' ? '0' : result);
}

/**
 * 登录操作
 * 
 * @returns {Boolean}
 */
function login_onclick() {

	if ($('#loginform').form('validate')) {
		setCookie("tbims_userId", $("#userId").textbox("getValue"));
		// *屏蔽Ukey*上生产时记得删除****************************
		$("#loginform").submit();
		return true;
	} else {
		return false;
	}

	// 如果是IE10及以下浏览器，则使用AVCTIVEX控件的方式
	if (navigator.userAgent.indexOf("MSIE") > 0 && !navigator.userAgent.indexOf("opera") > -1) {
		return Handle_IE10();
	}

	// 判断是否安装了服务程序，如果没有安装提示用户安装
	if (bConnect == 0) {
		window.alert("未能连接服务程序，请确定服务程序是否安装。");
		return false;
	}

	var DevicePath, ret, n, mylen, ID_1, ID_2, addr;
	try {

		// 由于是使用事件消息的方式与服务程序进行通讯，
		// 好处是不用安装插件，不分系统及版本，控件也不会被拦截，同时安装服务程序后，可以立即使用，不用重启浏览器
		// 不好的地方，就是但写代码会复杂一些
		var s_simnew1 = new SoftKey6W(); // 创建UK类
		s_simnew1.Socket_UK.onopen = function() {
			s_simnew1.ResetOrder();// 这里调用ResetOrder将计数清零，这样，消息处理处就会收到0序号的消息，通过计数及序号的方式，从而生产流程
		}

		// 写代码时一定要注意，每调用我们的一个UKEY函数，就会生产一个计数，即增加一个序号，较好的逻辑是一个序号的消息处理中，只调用我们一个UKEY的函数
		s_simnew1.Socket_UK.onmessage = function got_packet(Msg) {
			var UK_Data = JSON.parse(Msg.data);
			// alert(Msg.data);
			if (UK_Data.type != "Process") {
				return;// 如果不是流程处理消息，则跳过
			}

			switch (UK_Data.order) {
			case 0: {
				s_simnew1.FindPort(0);// 发送命令取UK的路径
			}
				break;// !!!!!重要提示，如果在调试中，发现代码不对，一定要注意，是不是少了break,这个少了是很常见的错误
			case 1: {
				if (UK_Data.LastError != 0) {
					window.alert("未发现加密锁，请插入加密锁");
					s_simnew1.Socket_UK.close();
					return false;
				}
				DevicePath = UK_Data.return_value;// 获得返回的UK的路径
				s_simnew1.GetID_1(DevicePath); // 发送命令取ID_1
			}
				break;
			case 2: {
				if (UK_Data.LastError != 0) {
					window.alert("返回ID号错误，错误码为：" + UK_Data.LastError.toString());
					s_simnew1.Socket_UK.close();
					return false;
				}
				ID_1 = UK_Data.return_value;// 获得返回的UK的ID_1
				s_simnew1.GetID_2(DevicePath); // 发送命令取ID_2
			}
				break;
			case 3: {
				if (UK_Data.LastError != 0) {
					window.alert("取得ID错误，错误码为：" + UK_Data.LastError.toString());
					s_simnew1.Socket_UK.close();
					return false;
				}
				ID_2 = UK_Data.return_value;// 获得返回的UK的ID_2

				$("#KeyID").val(toHex(ID_1) + toHex(ID_2));

				s_simnew1.ContinueOrder();// 为了方便阅读，这里调用了一句继续下一行的计算的命令，因为在这个消息中没有调用我们的函数，所以要调用这个
			}
				break;
			case 4: {
				// 这里返回对随机数的HASH结果
				s_simnew1.EncString($("#random").val(), DevicePath);// 发送命令让UK进行加密操作
				s_simnew1.ContinueOrder();// 为了方便阅读，这里调用了一句继续下一行的计算的命令，因为在这个消息中没有调用我们的函数，所以要调用这个
			}
				break;
			case 5: {
				// 获得返回的加密后的字符串
				$("#encDataClient").val(UK_Data.return_value);// 获得返回的加密后的字符串
				s_simnew1.ContinueOrder();// 为了方便阅读，这里调用了一句继续下一行的计算的命令，因为在这个消息中没有调用我们的函数，所以要调用这个
			}
				break;
			case 6: {
				// 获取设置在锁中的自定义业务数据
				// 先从地址0读取字符串的长度
				s_simnew1.YReadString(0, 2031, "951A93AE", "BF7D8973", DevicePath);// 发送命令取UK地址0的数据
			}
				break;
			case 6: {
				// 获取设置在锁中的自定义业务数据
				// 先从地址0读取字符串的长度
				$("#encOpeDataClient").val(UK_Data.return_value);// 获得返回的加密后的字符串
				s_simnew1.ContinueOrder();// 为了方便阅读，这里调用了一句继续下一行的计算的命令，因为在这个消息中没有调用我们的函数，所以要调用这个
			}
				break;
			case 8: {
				if (UK_Data.LastError != 0) {
					window.alert("进行加密运行算时错误，错误码为：" + UK_Data.LastError.toString());
					s_simnew1.Socket_UK.close();
					return false;
				}

				// !!!!!注意，这里一定要主动提交，
				$("#loginform").submit();

				// 所有工作处理完成后，关掉Socket
				s_simnew1.Socket_UK.close();
			}
				break;
			}
		}

		s_simnew1.Socket_UK.onclose = function() {

		}

		return true;
	} catch (e) {
		alert(e.name + ": " + e.message);
	}

}

/**
 * IE登录操作处理
 * 
 * @returns {Boolean}
 */
function Handle_IE10() {
	var DevicePath, ret, n, mylen;
	try {

		// 建立操作我们的锁的控件对象，用于操作我们的锁
		var s_simnew1;
		// 创建控件

		s_simnew1 = new ActiveXObject("Syunew6A.s_simnew6");

		// 查找是否存在锁,这里使用了FindPort函数
		DevicePath = s_simnew1.FindPort(0);
		if (s_simnew1.LastError != 0) {
			window.alert("未发现加密锁，请插入加密锁。");

			return false;
		}

		// '读取锁的ID
		$("#KeyID").val(toHex(s_simnew1.GetID_1(DevicePath)) + toHex(s_simnew1.GetID_2(DevicePath)));

		if (s_simnew1.LastError != 0) {
			window.alert("返回ID号错误，错误码为：" + s_simnew1.LastError.toString());
			return false;
		}

		// 这里返回对随机数的HASH结果
		// 对客户端中的随机数加密
		$("#encDataClient").val(s_simnew1.EncString($("#random").val(), DevicePath));
		if (s_simnew1.LastError != 0) {
			window.alert("进行加密运行算时错误，错误码为：" + s_simnew1.LastError.toString());
			return false;
		}

		var encOpeDataClient = s_simnew1.YReadString(0, 100, "951A93AE", "BF7D8973", DevicePath);
		$("#encOpeDataClient").val($.trim(encOpeDataClient));
		if (s_simnew1.LastError != 0) {
			window.alert("进行加密运行算时错误，错误码为：" + s_simnew1.LastError.toString());
			return false;
		}

		$('#loginform').submit();

		return;
	} catch (e) {
		alert(e.name + ": " + e.message + "。可能是没有安装相应的控件或插件");
	}
}