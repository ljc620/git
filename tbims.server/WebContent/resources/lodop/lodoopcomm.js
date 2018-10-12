//打印控件处理
var LODOP; // 声明为全局变量
function myPreview(top, left, content) {
	CreatePrintPage(top, left, content);
	LODOP.PREVIEW(); // 打印预览
};
function CreatePrintPage(top, left, content) {
	LODOP = getLodop(document.getElementById('LODOP_OB'), document.getElementById('LODOP_EM'));
	var strBodyStyle = "<style>" + document.getElementById("yangshi").innerHTML + "</style>";
	var strFormHtml =strBodyStyle + "<body>" + content + "</body>";
	 LODOP.PRINT_INIT("票务综合管理系统");
	LODOP.SET_PRINT_PAGESIZE(0, 0, 0, "A4"); // A4纸张纵向打印
	LODOP.ADD_PRINT_HTM(top, left, "70%", "100%", strFormHtml);
};