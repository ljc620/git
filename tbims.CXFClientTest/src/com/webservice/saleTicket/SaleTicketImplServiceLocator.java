/**
 * SaleTicketImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.saleTicket;

public class SaleTicketImplServiceLocator extends org.apache.axis.client.Service implements com.webservice.saleTicket.SaleTicketImplService {

    public SaleTicketImplServiceLocator() {
    }


    public SaleTicketImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SaleTicketImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SaleTicketImplPort
    private java.lang.String SaleTicketImplPort_address = "http://127.0.0.1:8086/tbims.server.net/services/SaleTicket";

    public java.lang.String getSaleTicketImplPortAddress() {
        return SaleTicketImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SaleTicketImplPortWSDDServiceName = "SaleTicketImplPort";

    public java.lang.String getSaleTicketImplPortWSDDServiceName() {
        return SaleTicketImplPortWSDDServiceName;
    }

    public void setSaleTicketImplPortWSDDServiceName(java.lang.String name) {
        SaleTicketImplPortWSDDServiceName = name;
    }

    public com.webservice.saleTicket.ISaleTicket getSaleTicketImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SaleTicketImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSaleTicketImplPort(endpoint);
    }

    public com.webservice.saleTicket.ISaleTicket getSaleTicketImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.webservice.saleTicket.SaleTicketImplServiceSoapBindingStub _stub = new com.webservice.saleTicket.SaleTicketImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getSaleTicketImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSaleTicketImplPortEndpointAddress(java.lang.String address) {
        SaleTicketImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.webservice.saleTicket.ISaleTicket.class.isAssignableFrom(serviceEndpointInterface)) {
                com.webservice.saleTicket.SaleTicketImplServiceSoapBindingStub _stub = new com.webservice.saleTicket.SaleTicketImplServiceSoapBindingStub(new java.net.URL(SaleTicketImplPort_address), this);
                _stub.setPortName(getSaleTicketImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SaleTicketImplPort".equals(inputPortName)) {
            return getSaleTicketImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://saleTicket.webservice.com/", "SaleTicketImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://saleTicket.webservice.com/", "SaleTicketImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SaleTicketImplPort".equals(portName)) {
            setSaleTicketImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
