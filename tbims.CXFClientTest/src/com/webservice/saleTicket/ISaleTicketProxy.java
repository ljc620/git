package com.webservice.saleTicket;

public class ISaleTicketProxy implements com.webservice.saleTicket.ISaleTicket {
  private String _endpoint = null;
  private com.webservice.saleTicket.ISaleTicket iSaleTicket = null;
  
  public ISaleTicketProxy() {
    _initISaleTicketProxy();
  }
  
  public ISaleTicketProxy(String endpoint) {
    _endpoint = endpoint;
    _initISaleTicketProxy();
  }
  
  private void _initISaleTicketProxy() {
    try {
      iSaleTicket = (new com.webservice.saleTicket.SaleTicketImplServiceLocator()).getSaleTicketImplPort();
      if (iSaleTicket != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iSaleTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iSaleTicket)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iSaleTicket != null)
      ((javax.xml.rpc.Stub)iSaleTicket)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.webservice.saleTicket.ISaleTicket getISaleTicket() {
    if (iSaleTicket == null)
      _initISaleTicketProxy();
    return iSaleTicket;
  }
  
  public int saleDateSync(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3, long arg4, java.util.Calendar arg5, com.webservice.saleTicket.OrderDetail[] arg6, java.lang.String arg7, java.lang.String arg8, java.lang.String arg9) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException{
    if (iSaleTicket == null)
      _initISaleTicketProxy();
    return iSaleTicket.saleDateSync(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9);
  }
  
  public int tradeRefund(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, int arg4, long arg5, java.util.Calendar arg6, com.webservice.saleTicket.RefundTicket[] arg7, java.lang.String arg8) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException{
    if (iSaleTicket == null)
      _initISaleTicketProxy();
    return iSaleTicket.tradeRefund(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8);
  }
  
  public com.webservice.saleTicket.TicketStatus[] getTicketStatusByOrderID(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException{
    if (iSaleTicket == null)
      _initISaleTicketProxy();
    return iSaleTicket.getTicketStatusByOrderID(arg0, arg1, arg2, arg3);
  }
  
  public com.webservice.saleTicket.TicketType[] getTicketType(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException{
    if (iSaleTicket == null)
      _initISaleTicketProxy();
    return iSaleTicket.getTicketType(arg0, arg1, arg2);
  }
  
  public int getTicketCountByID(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException{
    if (iSaleTicket == null)
      _initISaleTicketProxy();
    return iSaleTicket.getTicketCountByID(arg0, arg1, arg2, arg3);
  }
  
  
}