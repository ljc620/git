/**
 * CXFException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.saleTicket;

public class CXFException  extends org.apache.axis.AxisFault  implements java.io.Serializable {
    private java.lang.String errinfo;

    private java.lang.Integer errcode;

    private java.lang.String message1;

    public CXFException() {
    }

    public CXFException(
           java.lang.String errinfo,
           java.lang.Integer errcode,
           java.lang.String message1) {
        this.errinfo = errinfo;
        this.errcode = errcode;
        this.message1 = message1;
    }


    /**
     * Gets the errinfo value for this CXFException.
     * 
     * @return errinfo
     */
    public java.lang.String getErrinfo() {
        return errinfo;
    }


    /**
     * Sets the errinfo value for this CXFException.
     * 
     * @param errinfo
     */
    public void setErrinfo(java.lang.String errinfo) {
        this.errinfo = errinfo;
    }


    /**
     * Gets the errcode value for this CXFException.
     * 
     * @return errcode
     */
    public java.lang.Integer getErrcode() {
        return errcode;
    }


    /**
     * Sets the errcode value for this CXFException.
     * 
     * @param errcode
     */
    public void setErrcode(java.lang.Integer errcode) {
        this.errcode = errcode;
    }


    /**
     * Gets the message1 value for this CXFException.
     * 
     * @return message1
     */
    public java.lang.String getMessage1() {
        return message1;
    }


    /**
     * Sets the message1 value for this CXFException.
     * 
     * @param message1
     */
    public void setMessage1(java.lang.String message1) {
        this.message1 = message1;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof CXFException)) return false;
        CXFException other = (CXFException) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.errinfo==null && other.getErrinfo()==null) || 
             (this.errinfo!=null &&
              this.errinfo.equals(other.getErrinfo()))) &&
            ((this.errcode==null && other.getErrcode()==null) || 
             (this.errcode!=null &&
              this.errcode.equals(other.getErrcode()))) &&
            ((this.message1==null && other.getMessage1()==null) || 
             (this.message1!=null &&
              this.message1.equals(other.getMessage1())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getErrinfo() != null) {
            _hashCode += getErrinfo().hashCode();
        }
        if (getErrcode() != null) {
            _hashCode += getErrcode().hashCode();
        }
        if (getMessage1() != null) {
            _hashCode += getMessage1().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(CXFException.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://saleTicket.webservice.com/", "CXFException"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errinfo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errinfo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("errcode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "errcode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("message1");
        elemField.setXmlName(new javax.xml.namespace.QName("", "message"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }


    /**
     * Writes the exception data to the faultDetails
     */
    public void writeDetails(javax.xml.namespace.QName qname, org.apache.axis.encoding.SerializationContext context) throws java.io.IOException {
        context.serialize(qname, null, this);
    }
}
