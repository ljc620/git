/**
 * OrderDetail.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.saleTicket;

public class OrderDetail  implements java.io.Serializable {
    private java.util.Calendar endDate;

    private java.lang.String identityCode;

    private long salePrice;

    private java.util.Calendar startDate;

    private java.lang.String ticketTypeId;

    public OrderDetail() {
    }

    public OrderDetail(
           java.util.Calendar endDate,
           java.lang.String identityCode,
           long salePrice,
           java.util.Calendar startDate,
           java.lang.String ticketTypeId) {
           this.endDate = endDate;
           this.identityCode = identityCode;
           this.salePrice = salePrice;
           this.startDate = startDate;
           this.ticketTypeId = ticketTypeId;
    }


    /**
     * Gets the endDate value for this OrderDetail.
     * 
     * @return endDate
     */
    public java.util.Calendar getEndDate() {
        return endDate;
    }


    /**
     * Sets the endDate value for this OrderDetail.
     * 
     * @param endDate
     */
    public void setEndDate(java.util.Calendar endDate) {
        this.endDate = endDate;
    }


    /**
     * Gets the identityCode value for this OrderDetail.
     * 
     * @return identityCode
     */
    public java.lang.String getIdentityCode() {
        return identityCode;
    }


    /**
     * Sets the identityCode value for this OrderDetail.
     * 
     * @param identityCode
     */
    public void setIdentityCode(java.lang.String identityCode) {
        this.identityCode = identityCode;
    }


    /**
     * Gets the salePrice value for this OrderDetail.
     * 
     * @return salePrice
     */
    public long getSalePrice() {
        return salePrice;
    }


    /**
     * Sets the salePrice value for this OrderDetail.
     * 
     * @param salePrice
     */
    public void setSalePrice(long salePrice) {
        this.salePrice = salePrice;
    }


    /**
     * Gets the startDate value for this OrderDetail.
     * 
     * @return startDate
     */
    public java.util.Calendar getStartDate() {
        return startDate;
    }


    /**
     * Sets the startDate value for this OrderDetail.
     * 
     * @param startDate
     */
    public void setStartDate(java.util.Calendar startDate) {
        this.startDate = startDate;
    }


    /**
     * Gets the ticketTypeId value for this OrderDetail.
     * 
     * @return ticketTypeId
     */
    public java.lang.String getTicketTypeId() {
        return ticketTypeId;
    }


    /**
     * Sets the ticketTypeId value for this OrderDetail.
     * 
     * @param ticketTypeId
     */
    public void setTicketTypeId(java.lang.String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof OrderDetail)) return false;
        OrderDetail other = (OrderDetail) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.endDate==null && other.getEndDate()==null) || 
             (this.endDate!=null &&
              this.endDate.equals(other.getEndDate()))) &&
            ((this.identityCode==null && other.getIdentityCode()==null) || 
             (this.identityCode!=null &&
              this.identityCode.equals(other.getIdentityCode()))) &&
            this.salePrice == other.getSalePrice() &&
            ((this.startDate==null && other.getStartDate()==null) || 
             (this.startDate!=null &&
              this.startDate.equals(other.getStartDate()))) &&
            ((this.ticketTypeId==null && other.getTicketTypeId()==null) || 
             (this.ticketTypeId!=null &&
              this.ticketTypeId.equals(other.getTicketTypeId())));
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
        if (getEndDate() != null) {
            _hashCode += getEndDate().hashCode();
        }
        if (getIdentityCode() != null) {
            _hashCode += getIdentityCode().hashCode();
        }
        _hashCode += new Long(getSalePrice()).hashCode();
        if (getStartDate() != null) {
            _hashCode += getStartDate().hashCode();
        }
        if (getTicketTypeId() != null) {
            _hashCode += getTicketTypeId().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(OrderDetail.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://saleTicket.webservice.com/", "orderDetail"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("endDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "endDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("identityCode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "identityCode"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("salePrice");
        elemField.setXmlName(new javax.xml.namespace.QName("", "salePrice"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startDate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startDate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ticketTypeId"));
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

}
