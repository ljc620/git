/**
 * RefundTicket.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.saleTicket;

public class RefundTicket  implements java.io.Serializable {
    private java.lang.String identityCode;

    private long refundAmt;

    public RefundTicket() {
    }

    public RefundTicket(
           java.lang.String identityCode,
           long refundAmt) {
           this.identityCode = identityCode;
           this.refundAmt = refundAmt;
    }


    /**
     * Gets the identityCode value for this RefundTicket.
     * 
     * @return identityCode
     */
    public java.lang.String getIdentityCode() {
        return identityCode;
    }


    /**
     * Sets the identityCode value for this RefundTicket.
     * 
     * @param identityCode
     */
    public void setIdentityCode(java.lang.String identityCode) {
        this.identityCode = identityCode;
    }


    /**
     * Gets the refundAmt value for this RefundTicket.
     * 
     * @return refundAmt
     */
    public long getRefundAmt() {
        return refundAmt;
    }


    /**
     * Sets the refundAmt value for this RefundTicket.
     * 
     * @param refundAmt
     */
    public void setRefundAmt(long refundAmt) {
        this.refundAmt = refundAmt;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RefundTicket)) return false;
        RefundTicket other = (RefundTicket) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.identityCode==null && other.getIdentityCode()==null) || 
             (this.identityCode!=null &&
              this.identityCode.equals(other.getIdentityCode()))) &&
            this.refundAmt == other.getRefundAmt();
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
        if (getIdentityCode() != null) {
            _hashCode += getIdentityCode().hashCode();
        }
        _hashCode += new Long(getRefundAmt()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RefundTicket.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://saleTicket.webservice.com/", "refundTicket"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("refundAmt");
        elemField.setXmlName(new javax.xml.namespace.QName("", "refundAmt"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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

}
