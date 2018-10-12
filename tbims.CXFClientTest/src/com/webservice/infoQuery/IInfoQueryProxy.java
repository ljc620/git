package com.webservice.infoQuery;

public class IInfoQueryProxy implements com.webservice.infoQuery.IInfoQuery {
  private String _endpoint = null;
  private com.webservice.infoQuery.IInfoQuery iInfoQuery = null;
  
  public IInfoQueryProxy() {
    _initIInfoQueryProxy();
  }
  
  public IInfoQueryProxy(String endpoint) {
    _endpoint = endpoint;
    _initIInfoQueryProxy();
  }
  
  private void _initIInfoQueryProxy() {
    try {
      iInfoQuery = (new com.webservice.infoQuery.InfoQueryImplServiceLocator()).getInfoQueryImplPort();
      if (iInfoQuery != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)iInfoQuery)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)iInfoQuery)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (iInfoQuery != null)
      ((javax.xml.rpc.Stub)iInfoQuery)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.webservice.infoQuery.IInfoQuery getIInfoQuery() {
    if (iInfoQuery == null)
      _initIInfoQueryProxy();
    return iInfoQuery;
  }
  
  public com.webservice.infoQuery.SaleCheckEntity getCurrentSaleCheckin(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, com.webservice.infoQuery.CXFException{
    if (iInfoQuery == null)
      _initIInfoQueryProxy();
    return iInfoQuery.getCurrentSaleCheckin(arg0, arg1);
  }
  
  public com.webservice.infoQuery.SaleCheckEntity getSaleCheckinByDate(java.lang.String arg0, java.util.Calendar arg1, java.lang.String arg2) throws java.rmi.RemoteException, com.webservice.infoQuery.CXFException{
    if (iInfoQuery == null)
      _initIInfoQueryProxy();
    return iInfoQuery.getSaleCheckinByDate(arg0, arg1, arg2);
  }
  
  
}