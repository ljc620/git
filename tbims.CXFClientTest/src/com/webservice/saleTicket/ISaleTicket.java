/**
 * ISaleTicket.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.saleTicket;

public interface ISaleTicket extends java.rmi.Remote {
    public int saleDateSync(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, int arg3, long arg4, java.util.Calendar arg5, com.webservice.saleTicket.OrderDetail[] arg6, java.lang.String arg7, java.lang.String arg8, java.lang.String arg9) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException;
    public int tradeRefund(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3, int arg4, long arg5, java.util.Calendar arg6, com.webservice.saleTicket.RefundTicket[] arg7, java.lang.String arg8) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException;
    public com.webservice.saleTicket.TicketStatus[] getTicketStatusByOrderID(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException;
    public com.webservice.saleTicket.TicketType[] getTicketType(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException;
    public int getTicketCountByID(java.lang.String arg0, java.lang.String arg1, java.lang.String arg2, java.lang.String arg3) throws java.rmi.RemoteException, com.webservice.saleTicket.CXFException;
}
