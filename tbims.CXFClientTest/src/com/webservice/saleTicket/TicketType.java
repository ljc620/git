/**
 * TicketType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.webservice.saleTicket;

public class TicketType  implements java.io.Serializable {
    private long price;

    private java.lang.String ticketTypeId;

    private java.lang.String ticketTypeName;

    public TicketType() {
    }

    public TicketType(
           long price,
           java.lang.String ticketTypeId,
           java.lang.String ticketTypeName) {
           this.price = price;
           this.ticketTypeId = ticketTypeId;
           this.ticketTypeName = ticketTypeName;
    }


    /**
     * Gets the price value for this TicketType.
     * 
     * @return price
     */
    public long getPrice() {
        return price;
    }


    /**
     * Sets the price value for this TicketType.
     * 
     * @param price
     */
    public void setPrice(long price) {
        this.price = price;
    }


    /**
     * Gets the ticketTypeId value for this TicketType.
     * 
     * @return ticketTypeId
     */
    public java.lang.String getTicketTypeId() {
        return ticketTypeId;
    }


    /**
     * Sets the ticketTypeId value for this TicketType.
     * 
     * @param ticketTypeId
     */
    public void setTicketTypeId(java.lang.String ticketTypeId) {
        this.ticketTypeId = ticketTypeId;
    }


    /**
     * Gets the ticketTypeName value for this TicketType.
     * 
     * @return ticketTypeName
     */
    public java.lang.String getTicketTypeName() {
        return ticketTypeName;
    }


    /**
     * Sets the ticketTypeName value for this TicketType.
     * 
     * @param ticketTypeName
     */
    public void setTicketTypeName(java.lang.String ticketTypeName) {
        this.ticketTypeName = ticketTypeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof TicketType)) return false;
        TicketType other = (TicketType) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.price == other.getPrice() &&
            ((this.ticketTypeId==null && other.getTicketTypeId()==null) || 
             (this.ticketTypeId!=null &&
              this.ticketTypeId.equals(other.getTicketTypeId()))) &&
            ((this.ticketTypeName==null && other.getTicketTypeName()==null) || 
             (this.ticketTypeName!=null &&
              this.ticketTypeName.equals(other.getTicketTypeName())));
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
        _hashCode += new Long(getPrice()).hashCode();
        if (getTicketTypeId() != null) {
            _hashCode += getTicketTypeId().hashCode();
        }
        if (getTicketTypeName() != null) {
            _hashCode += getTicketTypeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(TicketType.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://saleTicket.webservice.com/", "ticketType"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("price");
        elemField.setXmlName(new javax.xml.namespace.QName("", "price"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ticketTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ticketTypeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ticketTypeName"));
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
