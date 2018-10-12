/**
 * IInfoQuery.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.infoQuery;

public interface IInfoQuery extends java.rmi.Remote {
    public com.webservice.infoQuery.SaleCheckEntity getCurrentSaleCheckin(java.lang.String arg0, java.lang.String arg1) throws java.rmi.RemoteException, com.webservice.infoQuery.CXFException;
    public com.webservice.infoQuery.SaleCheckEntity getSaleCheckinByDate(java.lang.String arg0, java.util.Calendar arg1, java.lang.String arg2) throws java.rmi.RemoteException, com.webservice.infoQuery.CXFException;
}
