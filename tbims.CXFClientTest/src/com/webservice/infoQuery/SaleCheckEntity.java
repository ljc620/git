/**
 * SaleCheckEntity.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.infoQuery;

public class SaleCheckEntity  implements java.io.Serializable {
    private int checkinCount;

    private int saleCount;

    public SaleCheckEntity() {
    }

    public SaleCheckEntity(
           int checkinCount,
           int saleCount) {
           this.checkinCount = checkinCount;
           this.saleCount = saleCount;
    }


    /**
     * Gets the checkinCount value for this SaleCheckEntity.
     * 
     * @return checkinCount
     */
    public int getCheckinCount() {
        return checkinCount;
    }


    /**
     * Sets the checkinCount value for this SaleCheckEntity.
     * 
     * @param checkinCount
     */
    public void setCheckinCount(int checkinCount) {
        this.checkinCount = checkinCount;
    }


    /**
     * Gets the saleCount value for this SaleCheckEntity.
     * 
     * @return saleCount
     */
    public int getSaleCount() {
        return saleCount;
    }


    /**
     * Sets the saleCount value for this SaleCheckEntity.
     * 
     * @param saleCount
     */
    public void setSaleCount(int saleCount) {
        this.saleCount = saleCount;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SaleCheckEntity)) return false;
        SaleCheckEntity other = (SaleCheckEntity) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.checkinCount == other.getCheckinCount() &&
            this.saleCount == other.getSaleCount();
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
        _hashCode += getCheckinCount();
        _hashCode += getSaleCount();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SaleCheckEntity.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://infoQuery.webservice.com/", "saleCheckEntity"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("checkinCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "checkinCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("saleCount");
        elemField.setXmlName(new javax.xml.namespace.QName("", "saleCount"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
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
