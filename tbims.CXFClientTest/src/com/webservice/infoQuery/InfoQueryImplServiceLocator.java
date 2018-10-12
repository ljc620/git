/**
 * InfoQueryImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.infoQuery;

public class InfoQueryImplServiceLocator extends org.apache.axis.client.Service implements com.webservice.infoQuery.InfoQueryImplService {

    public InfoQueryImplServiceLocator() {
    }


    public InfoQueryImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public InfoQueryImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for InfoQueryImplPort
    private java.lang.String InfoQueryImplPort_address = "http://127.0.0.1:8086/tbims.server.net/services/InfoQuery";

    public java.lang.String getInfoQueryImplPortAddress() {
        return InfoQueryImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String InfoQueryImplPortWSDDServiceName = "InfoQueryImplPort";

    public java.lang.String getInfoQueryImplPortWSDDServiceName() {
        return InfoQueryImplPortWSDDServiceName;
    }

    public void setInfoQueryImplPortWSDDServiceName(java.lang.String name) {
        InfoQueryImplPortWSDDServiceName = name;
    }

    public com.webservice.infoQuery.IInfoQuery getInfoQueryImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(InfoQueryImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getInfoQueryImplPort(endpoint);
    }

    public com.webservice.infoQuery.IInfoQuery getInfoQueryImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.webservice.infoQuery.InfoQueryImplServiceSoapBindingStub _stub = new com.webservice.infoQuery.InfoQueryImplServiceSoapBindingStub(portAddress, this);
            _stub.setPortName(getInfoQueryImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setInfoQueryImplPortEndpointAddress(java.lang.String address) {
        InfoQueryImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.webservice.infoQuery.IInfoQuery.class.isAssignableFrom(serviceEndpointInterface)) {
                com.webservice.infoQuery.InfoQueryImplServiceSoapBindingStub _stub = new com.webservice.infoQuery.InfoQueryImplServiceSoapBindingStub(new java.net.URL(InfoQueryImplPort_address), this);
                _stub.setPortName(getInfoQueryImplPortWSDDServiceName());
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
        if ("InfoQueryImplPort".equals(inputPortName)) {
            return getInfoQueryImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://infoQuery.webservice.com/", "InfoQueryImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://infoQuery.webservice.com/", "InfoQueryImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("InfoQueryImplPort".equals(portName)) {
            setInfoQueryImplPortEndpointAddress(address);
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
