com.webservice.tool.Java2WSDL.java
生成wsdl文件

使用cxf自带的wsdl2java工具生成客户端代码的步骤

1. 将CXF_HOME/bin加入到环境变量path中
例：%PATH%;D:\tbims\tbims.doc\票务综合管理系统\相关文档\apache-cxf-3.1.12\bin

2. 使用cmd命令进入到此目录下，生成客户端代码
wsdl2java -p com.webservice.client.infoQuery -d D:\tbims\CXFClientTest\src -client D:\tbims\tbims.server.net\src\com\webservice\client\wsdl\InfoQueryImplService.wsdl
wsdl2java -p com.webservice.client.saleTicket -d D:\tbims\CXFClientTest\src -client D:\tbims\tbims.server.net\src\com\webservice\client\wsdl\SaleTicketImplService.wsdl

wsdl2java -p com.webservice.client.infoQuery -d D:\tbims\CXFClientTest\src -client http://127.0.0.1:8086/tbims.server.net/services/InfoQuery?wsdl
wsdl2java -p com.webservice.client.saleTicket -d D:\tbims\CXFClientTest\src -client http://127.0.0.1:8086/tbims.server.net/services/SaleTicket?wsdl


3. 将生成的客户端复制到项目中



