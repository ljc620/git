服务端采用 CXF框架 搭建webservice
客户端可采用eclipse自动生成客户端或将本实例复制项目中
--------------------------------------------------------------------------------------------
当联调测试或上生产时 SaleTicketImplServiceLocator.java 在地址为正式地址 
http://127.0.0.1:8086/tbims.server.net/services/InfoQuery?wsdl

当联调测试或上生产时 IInfoQueryServiceLocator.java在地址为正式地址 
http://127.0.0.1:8086/tbims.server.net/services/SaleTicket?wsdl

--------------------------------------------------------------------------------------------

SaleTicketTest.java 这个类调用服务方法的例子可以参考


