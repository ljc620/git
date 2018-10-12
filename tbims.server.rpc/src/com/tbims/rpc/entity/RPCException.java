/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.tbims.rpc.entity;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * 异常描述定义
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-10-15")
public class RPCException extends org.apache.thrift.TException implements org.apache.thrift.TBase<RPCException, RPCException._Fields>, java.io.Serializable, Cloneable, Comparable<RPCException> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RPCException");

  private static final org.apache.thrift.protocol.TField ERROR_CODE_FIELD_DESC = new org.apache.thrift.protocol.TField("errorCode", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField ERROR_MESS_FIELD_DESC = new org.apache.thrift.protocol.TField("errorMess", org.apache.thrift.protocol.TType.STRING, (short)2);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new RPCExceptionStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new RPCExceptionTupleSchemeFactory();

  /**
   * 错误码
   */
  public int errorCode; // required
  /**
   * 错误信息
   */
  public java.lang.String errorMess; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 错误码
     */
    ERROR_CODE((short)1, "errorCode"),
    /**
     * 错误信息
     */
    ERROR_MESS((short)2, "errorMess");

    private static final java.util.Map<java.lang.String, _Fields> byName = new java.util.HashMap<java.lang.String, _Fields>();

    static {
      for (_Fields field : java.util.EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // ERROR_CODE
          return ERROR_CODE;
        case 2: // ERROR_MESS
          return ERROR_MESS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new java.lang.IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(java.lang.String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final java.lang.String _fieldName;

    _Fields(short thriftId, java.lang.String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public java.lang.String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __ERRORCODE_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ERROR_CODE, new org.apache.thrift.meta_data.FieldMetaData("errorCode", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ERROR_MESS, new org.apache.thrift.meta_data.FieldMetaData("errorMess", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RPCException.class, metaDataMap);
  }

  public RPCException() {
  }

  public RPCException(
    int errorCode,
    java.lang.String errorMess)
  {
    this();
    this.errorCode = errorCode;
    setErrorCodeIsSet(true);
    this.errorMess = errorMess;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public RPCException(RPCException other) {
    __isset_bitfield = other.__isset_bitfield;
    this.errorCode = other.errorCode;
    if (other.isSetErrorMess()) {
      this.errorMess = other.errorMess;
    }
  }

  public RPCException deepCopy() {
    return new RPCException(this);
  }

  @Override
  public void clear() {
    setErrorCodeIsSet(false);
    this.errorCode = 0;
    this.errorMess = null;
  }

  /**
   * 错误码
   */
  public int getErrorCode() {
    return this.errorCode;
  }

  /**
   * 错误码
   */
  public RPCException setErrorCode(int errorCode) {
    this.errorCode = errorCode;
    setErrorCodeIsSet(true);
    return this;
  }

  public void unsetErrorCode() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ERRORCODE_ISSET_ID);
  }

  /** Returns true if field errorCode is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorCode() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ERRORCODE_ISSET_ID);
  }

  public void setErrorCodeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ERRORCODE_ISSET_ID, value);
  }

  /**
   * 错误信息
   */
  public java.lang.String getErrorMess() {
    return this.errorMess;
  }

  /**
   * 错误信息
   */
  public RPCException setErrorMess(java.lang.String errorMess) {
    this.errorMess = errorMess;
    return this;
  }

  public void unsetErrorMess() {
    this.errorMess = null;
  }

  /** Returns true if field errorMess is set (has been assigned a value) and false otherwise */
  public boolean isSetErrorMess() {
    return this.errorMess != null;
  }

  public void setErrorMessIsSet(boolean value) {
    if (!value) {
      this.errorMess = null;
    }
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case ERROR_CODE:
      if (value == null) {
        unsetErrorCode();
      } else {
        setErrorCode((java.lang.Integer)value);
      }
      break;

    case ERROR_MESS:
      if (value == null) {
        unsetErrorMess();
      } else {
        setErrorMess((java.lang.String)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case ERROR_CODE:
      return getErrorCode();

    case ERROR_MESS:
      return getErrorMess();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case ERROR_CODE:
      return isSetErrorCode();
    case ERROR_MESS:
      return isSetErrorMess();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof RPCException)
      return this.equals((RPCException)that);
    return false;
  }

  public boolean equals(RPCException that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_errorCode = true;
    boolean that_present_errorCode = true;
    if (this_present_errorCode || that_present_errorCode) {
      if (!(this_present_errorCode && that_present_errorCode))
        return false;
      if (this.errorCode != that.errorCode)
        return false;
    }

    boolean this_present_errorMess = true && this.isSetErrorMess();
    boolean that_present_errorMess = true && that.isSetErrorMess();
    if (this_present_errorMess || that_present_errorMess) {
      if (!(this_present_errorMess && that_present_errorMess))
        return false;
      if (!this.errorMess.equals(that.errorMess))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + errorCode;

    hashCode = hashCode * 8191 + ((isSetErrorMess()) ? 131071 : 524287);
    if (isSetErrorMess())
      hashCode = hashCode * 8191 + errorMess.hashCode();

    return hashCode;
  }

  @Override
  public int compareTo(RPCException other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetErrorCode()).compareTo(other.isSetErrorCode());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorCode()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorCode, other.errorCode);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetErrorMess()).compareTo(other.isSetErrorMess());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetErrorMess()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.errorMess, other.errorMess);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    scheme(iprot).read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    scheme(oprot).write(oprot, this);
  }

  @Override
  public java.lang.String toString() {
    java.lang.StringBuilder sb = new java.lang.StringBuilder("RPCException(");
    boolean first = true;

    sb.append("errorCode:");
    sb.append(this.errorCode);
    first = false;
    if (!first) sb.append(", ");
    sb.append("errorMess:");
    if (this.errorMess == null) {
      sb.append("null");
    } else {
      sb.append(this.errorMess);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'errorCode' because it's a primitive and you chose the non-beans generator.
    if (errorMess == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'errorMess' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, java.lang.ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class RPCExceptionStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RPCExceptionStandardScheme getScheme() {
      return new RPCExceptionStandardScheme();
    }
  }

  private static class RPCExceptionStandardScheme extends org.apache.thrift.scheme.StandardScheme<RPCException> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, RPCException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ERROR_CODE
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.errorCode = iprot.readI32();
              struct.setErrorCodeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // ERROR_MESS
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.errorMess = iprot.readString();
              struct.setErrorMessIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetErrorCode()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'errorCode' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, RPCException struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ERROR_CODE_FIELD_DESC);
      oprot.writeI32(struct.errorCode);
      oprot.writeFieldEnd();
      if (struct.errorMess != null) {
        oprot.writeFieldBegin(ERROR_MESS_FIELD_DESC);
        oprot.writeString(struct.errorMess);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class RPCExceptionTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public RPCExceptionTupleScheme getScheme() {
      return new RPCExceptionTupleScheme();
    }
  }

  private static class RPCExceptionTupleScheme extends org.apache.thrift.scheme.TupleScheme<RPCException> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, RPCException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeI32(struct.errorCode);
      oprot.writeString(struct.errorMess);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, RPCException struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.errorCode = iprot.readI32();
      struct.setErrorCodeIsSet(true);
      struct.errorMess = iprot.readString();
      struct.setErrorMessIsSet(true);
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

