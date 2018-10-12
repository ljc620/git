/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.tbims.rpc.entity;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * 员工通行场馆表
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-10-15")
public class SYS_EMP_VENUE implements org.apache.thrift.TBase<SYS_EMP_VENUE, SYS_EMP_VENUE._Fields>, java.io.Serializable, Cloneable, Comparable<SYS_EMP_VENUE> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SYS_EMP_VENUE");

  private static final org.apache.thrift.protocol.TField EMP_VENUE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("empVenueId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField EMP_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("empId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField VENUE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("venueId", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField VERSION_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("versionNo", org.apache.thrift.protocol.TType.I64, (short)4);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new SYS_EMP_VENUEStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new SYS_EMP_VENUETupleSchemeFactory();

  /**
   * 员工通行场馆表ID
   */
  public java.lang.String empVenueId; // required
  /**
   * 员工编号
   */
  public long empId; // required
  /**
   * 场馆编号
   */
  public long venueId; // optional
  /**
   * 版本号
   */
  public long versionNo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 员工通行场馆表ID
     */
    EMP_VENUE_ID((short)1, "empVenueId"),
    /**
     * 员工编号
     */
    EMP_ID((short)2, "empId"),
    /**
     * 场馆编号
     */
    VENUE_ID((short)3, "venueId"),
    /**
     * 版本号
     */
    VERSION_NO((short)4, "versionNo");

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
        case 1: // EMP_VENUE_ID
          return EMP_VENUE_ID;
        case 2: // EMP_ID
          return EMP_ID;
        case 3: // VENUE_ID
          return VENUE_ID;
        case 4: // VERSION_NO
          return VERSION_NO;
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
  private static final int __EMPID_ISSET_ID = 0;
  private static final int __VENUEID_ISSET_ID = 1;
  private static final int __VERSIONNO_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.VENUE_ID,_Fields.VERSION_NO};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.EMP_VENUE_ID, new org.apache.thrift.meta_data.FieldMetaData("empVenueId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.EMP_ID, new org.apache.thrift.meta_data.FieldMetaData("empId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VENUE_ID, new org.apache.thrift.meta_data.FieldMetaData("venueId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VERSION_NO, new org.apache.thrift.meta_data.FieldMetaData("versionNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SYS_EMP_VENUE.class, metaDataMap);
  }

  public SYS_EMP_VENUE() {
  }

  public SYS_EMP_VENUE(
    java.lang.String empVenueId,
    long empId)
  {
    this();
    this.empVenueId = empVenueId;
    this.empId = empId;
    setEmpIdIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SYS_EMP_VENUE(SYS_EMP_VENUE other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetEmpVenueId()) {
      this.empVenueId = other.empVenueId;
    }
    this.empId = other.empId;
    this.venueId = other.venueId;
    this.versionNo = other.versionNo;
  }

  public SYS_EMP_VENUE deepCopy() {
    return new SYS_EMP_VENUE(this);
  }

  @Override
  public void clear() {
    this.empVenueId = null;
    setEmpIdIsSet(false);
    this.empId = 0;
    setVenueIdIsSet(false);
    this.venueId = 0;
    setVersionNoIsSet(false);
    this.versionNo = 0;
  }

  /**
   * 员工通行场馆表ID
   */
  public java.lang.String getEmpVenueId() {
    return this.empVenueId;
  }

  /**
   * 员工通行场馆表ID
   */
  public SYS_EMP_VENUE setEmpVenueId(java.lang.String empVenueId) {
    this.empVenueId = empVenueId;
    return this;
  }

  public void unsetEmpVenueId() {
    this.empVenueId = null;
  }

  /** Returns true if field empVenueId is set (has been assigned a value) and false otherwise */
  public boolean isSetEmpVenueId() {
    return this.empVenueId != null;
  }

  public void setEmpVenueIdIsSet(boolean value) {
    if (!value) {
      this.empVenueId = null;
    }
  }

  /**
   * 员工编号
   */
  public long getEmpId() {
    return this.empId;
  }

  /**
   * 员工编号
   */
  public SYS_EMP_VENUE setEmpId(long empId) {
    this.empId = empId;
    setEmpIdIsSet(true);
    return this;
  }

  public void unsetEmpId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __EMPID_ISSET_ID);
  }

  /** Returns true if field empId is set (has been assigned a value) and false otherwise */
  public boolean isSetEmpId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __EMPID_ISSET_ID);
  }

  public void setEmpIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __EMPID_ISSET_ID, value);
  }

  /**
   * 场馆编号
   */
  public long getVenueId() {
    return this.venueId;
  }

  /**
   * 场馆编号
   */
  public SYS_EMP_VENUE setVenueId(long venueId) {
    this.venueId = venueId;
    setVenueIdIsSet(true);
    return this;
  }

  public void unsetVenueId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __VENUEID_ISSET_ID);
  }

  /** Returns true if field venueId is set (has been assigned a value) and false otherwise */
  public boolean isSetVenueId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __VENUEID_ISSET_ID);
  }

  public void setVenueIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __VENUEID_ISSET_ID, value);
  }

  /**
   * 版本号
   */
  public long getVersionNo() {
    return this.versionNo;
  }

  /**
   * 版本号
   */
  public SYS_EMP_VENUE setVersionNo(long versionNo) {
    this.versionNo = versionNo;
    setVersionNoIsSet(true);
    return this;
  }

  public void unsetVersionNo() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __VERSIONNO_ISSET_ID);
  }

  /** Returns true if field versionNo is set (has been assigned a value) and false otherwise */
  public boolean isSetVersionNo() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __VERSIONNO_ISSET_ID);
  }

  public void setVersionNoIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __VERSIONNO_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case EMP_VENUE_ID:
      if (value == null) {
        unsetEmpVenueId();
      } else {
        setEmpVenueId((java.lang.String)value);
      }
      break;

    case EMP_ID:
      if (value == null) {
        unsetEmpId();
      } else {
        setEmpId((java.lang.Long)value);
      }
      break;

    case VENUE_ID:
      if (value == null) {
        unsetVenueId();
      } else {
        setVenueId((java.lang.Long)value);
      }
      break;

    case VERSION_NO:
      if (value == null) {
        unsetVersionNo();
      } else {
        setVersionNo((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case EMP_VENUE_ID:
      return getEmpVenueId();

    case EMP_ID:
      return getEmpId();

    case VENUE_ID:
      return getVenueId();

    case VERSION_NO:
      return getVersionNo();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case EMP_VENUE_ID:
      return isSetEmpVenueId();
    case EMP_ID:
      return isSetEmpId();
    case VENUE_ID:
      return isSetVenueId();
    case VERSION_NO:
      return isSetVersionNo();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof SYS_EMP_VENUE)
      return this.equals((SYS_EMP_VENUE)that);
    return false;
  }

  public boolean equals(SYS_EMP_VENUE that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_empVenueId = true && this.isSetEmpVenueId();
    boolean that_present_empVenueId = true && that.isSetEmpVenueId();
    if (this_present_empVenueId || that_present_empVenueId) {
      if (!(this_present_empVenueId && that_present_empVenueId))
        return false;
      if (!this.empVenueId.equals(that.empVenueId))
        return false;
    }

    boolean this_present_empId = true;
    boolean that_present_empId = true;
    if (this_present_empId || that_present_empId) {
      if (!(this_present_empId && that_present_empId))
        return false;
      if (this.empId != that.empId)
        return false;
    }

    boolean this_present_venueId = true && this.isSetVenueId();
    boolean that_present_venueId = true && that.isSetVenueId();
    if (this_present_venueId || that_present_venueId) {
      if (!(this_present_venueId && that_present_venueId))
        return false;
      if (this.venueId != that.venueId)
        return false;
    }

    boolean this_present_versionNo = true && this.isSetVersionNo();
    boolean that_present_versionNo = true && that.isSetVersionNo();
    if (this_present_versionNo || that_present_versionNo) {
      if (!(this_present_versionNo && that_present_versionNo))
        return false;
      if (this.versionNo != that.versionNo)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetEmpVenueId()) ? 131071 : 524287);
    if (isSetEmpVenueId())
      hashCode = hashCode * 8191 + empVenueId.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(empId);

    hashCode = hashCode * 8191 + ((isSetVenueId()) ? 131071 : 524287);
    if (isSetVenueId())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(venueId);

    hashCode = hashCode * 8191 + ((isSetVersionNo()) ? 131071 : 524287);
    if (isSetVersionNo())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(versionNo);

    return hashCode;
  }

  @Override
  public int compareTo(SYS_EMP_VENUE other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetEmpVenueId()).compareTo(other.isSetEmpVenueId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmpVenueId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.empVenueId, other.empVenueId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetEmpId()).compareTo(other.isSetEmpId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEmpId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.empId, other.empId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetVenueId()).compareTo(other.isSetVenueId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVenueId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.venueId, other.venueId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetVersionNo()).compareTo(other.isSetVersionNo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVersionNo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.versionNo, other.versionNo);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("SYS_EMP_VENUE(");
    boolean first = true;

    sb.append("empVenueId:");
    if (this.empVenueId == null) {
      sb.append("null");
    } else {
      sb.append(this.empVenueId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("empId:");
    sb.append(this.empId);
    first = false;
    if (isSetVenueId()) {
      if (!first) sb.append(", ");
      sb.append("venueId:");
      sb.append(this.venueId);
      first = false;
    }
    if (isSetVersionNo()) {
      if (!first) sb.append(", ");
      sb.append("versionNo:");
      sb.append(this.versionNo);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (empVenueId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'empVenueId' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'empId' because it's a primitive and you chose the non-beans generator.
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

  private static class SYS_EMP_VENUEStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SYS_EMP_VENUEStandardScheme getScheme() {
      return new SYS_EMP_VENUEStandardScheme();
    }
  }

  private static class SYS_EMP_VENUEStandardScheme extends org.apache.thrift.scheme.StandardScheme<SYS_EMP_VENUE> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SYS_EMP_VENUE struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // EMP_VENUE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.empVenueId = iprot.readString();
              struct.setEmpVenueIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // EMP_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.empId = iprot.readI64();
              struct.setEmpIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // VENUE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.venueId = iprot.readI64();
              struct.setVenueIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // VERSION_NO
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.versionNo = iprot.readI64();
              struct.setVersionNoIsSet(true);
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
      if (!struct.isSetEmpId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'empId' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SYS_EMP_VENUE struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.empVenueId != null) {
        oprot.writeFieldBegin(EMP_VENUE_ID_FIELD_DESC);
        oprot.writeString(struct.empVenueId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(EMP_ID_FIELD_DESC);
      oprot.writeI64(struct.empId);
      oprot.writeFieldEnd();
      if (struct.isSetVenueId()) {
        oprot.writeFieldBegin(VENUE_ID_FIELD_DESC);
        oprot.writeI64(struct.venueId);
        oprot.writeFieldEnd();
      }
      if (struct.isSetVersionNo()) {
        oprot.writeFieldBegin(VERSION_NO_FIELD_DESC);
        oprot.writeI64(struct.versionNo);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SYS_EMP_VENUETupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SYS_EMP_VENUETupleScheme getScheme() {
      return new SYS_EMP_VENUETupleScheme();
    }
  }

  private static class SYS_EMP_VENUETupleScheme extends org.apache.thrift.scheme.TupleScheme<SYS_EMP_VENUE> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SYS_EMP_VENUE struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.empVenueId);
      oprot.writeI64(struct.empId);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetVenueId()) {
        optionals.set(0);
      }
      if (struct.isSetVersionNo()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetVenueId()) {
        oprot.writeI64(struct.venueId);
      }
      if (struct.isSetVersionNo()) {
        oprot.writeI64(struct.versionNo);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SYS_EMP_VENUE struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.empVenueId = iprot.readString();
      struct.setEmpVenueIdIsSet(true);
      struct.empId = iprot.readI64();
      struct.setEmpIdIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.venueId = iprot.readI64();
        struct.setVenueIdIsSet(true);
      }
      if (incoming.get(1)) {
        struct.versionNo = iprot.readI64();
        struct.setVersionNoIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}
