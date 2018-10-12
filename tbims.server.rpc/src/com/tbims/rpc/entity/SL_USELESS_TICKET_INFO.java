/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.tbims.rpc.entity;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * 废票表
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-10-15")
public class SL_USELESS_TICKET_INFO implements org.apache.thrift.TBase<SL_USELESS_TICKET_INFO, SL_USELESS_TICKET_INFO._Fields>, java.io.Serializable, Cloneable, Comparable<SL_USELESS_TICKET_INFO> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SL_USELESS_TICKET_INFO");

  private static final org.apache.thrift.protocol.TField USELESS_TICKET_INFO_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("uselessTicketInfoId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TICKET_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("ticketId", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField TICKET_TYPE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("ticketTypeId", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField USELESS_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("uselessTime", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField USELESS_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("uselessUserId", org.apache.thrift.protocol.TType.STRING, (short)5);
  private static final org.apache.thrift.protocol.TField USELESS_REASON_FIELD_DESC = new org.apache.thrift.protocol.TField("uselessReason", org.apache.thrift.protocol.TType.STRING, (short)6);
  private static final org.apache.thrift.protocol.TField CONFIRM_USER_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("confirmUserId", org.apache.thrift.protocol.TType.STRING, (short)7);
  private static final org.apache.thrift.protocol.TField CONFIRM_TIME_FIELD_DESC = new org.apache.thrift.protocol.TField("confirmTime", org.apache.thrift.protocol.TType.I64, (short)8);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new SL_USELESS_TICKET_INFOStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new SL_USELESS_TICKET_INFOTupleSchemeFactory();

  /**
   * 废票信息表id
   */
  public java.lang.String uselessTicketInfoId; // required
  /**
   * 票号
   */
  public long ticketId; // required
  /**
   * 票种编号
   */
  public java.lang.String ticketTypeId; // required
  /**
   * 作废时间
   */
  public long uselessTime; // required
  /**
   * 作废人
   */
  public java.lang.String uselessUserId; // required
  /**
   * 作废原因
   */
  public java.lang.String uselessReason; // optional
  /**
   * 确认人
   */
  public java.lang.String confirmUserId; // optional
  /**
   * 确认时间
   */
  public long confirmTime; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 废票信息表id
     */
    USELESS_TICKET_INFO_ID((short)1, "uselessTicketInfoId"),
    /**
     * 票号
     */
    TICKET_ID((short)2, "ticketId"),
    /**
     * 票种编号
     */
    TICKET_TYPE_ID((short)3, "ticketTypeId"),
    /**
     * 作废时间
     */
    USELESS_TIME((short)4, "uselessTime"),
    /**
     * 作废人
     */
    USELESS_USER_ID((short)5, "uselessUserId"),
    /**
     * 作废原因
     */
    USELESS_REASON((short)6, "uselessReason"),
    /**
     * 确认人
     */
    CONFIRM_USER_ID((short)7, "confirmUserId"),
    /**
     * 确认时间
     */
    CONFIRM_TIME((short)8, "confirmTime");

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
        case 1: // USELESS_TICKET_INFO_ID
          return USELESS_TICKET_INFO_ID;
        case 2: // TICKET_ID
          return TICKET_ID;
        case 3: // TICKET_TYPE_ID
          return TICKET_TYPE_ID;
        case 4: // USELESS_TIME
          return USELESS_TIME;
        case 5: // USELESS_USER_ID
          return USELESS_USER_ID;
        case 6: // USELESS_REASON
          return USELESS_REASON;
        case 7: // CONFIRM_USER_ID
          return CONFIRM_USER_ID;
        case 8: // CONFIRM_TIME
          return CONFIRM_TIME;
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
  private static final int __TICKETID_ISSET_ID = 0;
  private static final int __USELESSTIME_ISSET_ID = 1;
  private static final int __CONFIRMTIME_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.USELESS_REASON,_Fields.CONFIRM_USER_ID,_Fields.CONFIRM_TIME};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.USELESS_TICKET_INFO_ID, new org.apache.thrift.meta_data.FieldMetaData("uselessTicketInfoId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TICKET_ID, new org.apache.thrift.meta_data.FieldMetaData("ticketId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.TICKET_TYPE_ID, new org.apache.thrift.meta_data.FieldMetaData("ticketTypeId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USELESS_TIME, new org.apache.thrift.meta_data.FieldMetaData("uselessTime", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.USELESS_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("uselessUserId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.USELESS_REASON, new org.apache.thrift.meta_data.FieldMetaData("uselessReason", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONFIRM_USER_ID, new org.apache.thrift.meta_data.FieldMetaData("confirmUserId", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.CONFIRM_TIME, new org.apache.thrift.meta_data.FieldMetaData("confirmTime", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SL_USELESS_TICKET_INFO.class, metaDataMap);
  }

  public SL_USELESS_TICKET_INFO() {
  }

  public SL_USELESS_TICKET_INFO(
    java.lang.String uselessTicketInfoId,
    long ticketId,
    java.lang.String ticketTypeId,
    long uselessTime,
    java.lang.String uselessUserId)
  {
    this();
    this.uselessTicketInfoId = uselessTicketInfoId;
    this.ticketId = ticketId;
    setTicketIdIsSet(true);
    this.ticketTypeId = ticketTypeId;
    this.uselessTime = uselessTime;
    setUselessTimeIsSet(true);
    this.uselessUserId = uselessUserId;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SL_USELESS_TICKET_INFO(SL_USELESS_TICKET_INFO other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetUselessTicketInfoId()) {
      this.uselessTicketInfoId = other.uselessTicketInfoId;
    }
    this.ticketId = other.ticketId;
    if (other.isSetTicketTypeId()) {
      this.ticketTypeId = other.ticketTypeId;
    }
    this.uselessTime = other.uselessTime;
    if (other.isSetUselessUserId()) {
      this.uselessUserId = other.uselessUserId;
    }
    if (other.isSetUselessReason()) {
      this.uselessReason = other.uselessReason;
    }
    if (other.isSetConfirmUserId()) {
      this.confirmUserId = other.confirmUserId;
    }
    this.confirmTime = other.confirmTime;
  }

  public SL_USELESS_TICKET_INFO deepCopy() {
    return new SL_USELESS_TICKET_INFO(this);
  }

  @Override
  public void clear() {
    this.uselessTicketInfoId = null;
    setTicketIdIsSet(false);
    this.ticketId = 0;
    this.ticketTypeId = null;
    setUselessTimeIsSet(false);
    this.uselessTime = 0;
    this.uselessUserId = null;
    this.uselessReason = null;
    this.confirmUserId = null;
    setConfirmTimeIsSet(false);
    this.confirmTime = 0;
  }

  /**
   * 废票信息表id
   */
  public java.lang.String getUselessTicketInfoId() {
    return this.uselessTicketInfoId;
  }

  /**
   * 废票信息表id
   */
  public SL_USELESS_TICKET_INFO setUselessTicketInfoId(java.lang.String uselessTicketInfoId) {
    this.uselessTicketInfoId = uselessTicketInfoId;
    return this;
  }

  public void unsetUselessTicketInfoId() {
    this.uselessTicketInfoId = null;
  }

  /** Returns true if field uselessTicketInfoId is set (has been assigned a value) and false otherwise */
  public boolean isSetUselessTicketInfoId() {
    return this.uselessTicketInfoId != null;
  }

  public void setUselessTicketInfoIdIsSet(boolean value) {
    if (!value) {
      this.uselessTicketInfoId = null;
    }
  }

  /**
   * 票号
   */
  public long getTicketId() {
    return this.ticketId;
  }

  /**
   * 票号
   */
  public SL_USELESS_TICKET_INFO setTicketId(long ticketId) {
    this.ticketId = ticketId;
    setTicketIdIsSet(true);
    return this;
  }

  public void unsetTicketId() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __TICKETID_ISSET_ID);
  }

  /** Returns true if field ticketId is set (has been assigned a value) and false otherwise */
  public boolean isSetTicketId() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __TICKETID_ISSET_ID);
  }

  public void setTicketIdIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __TICKETID_ISSET_ID, value);
  }

  /**
   * 票种编号
   */
  public java.lang.String getTicketTypeId() {
    return this.ticketTypeId;
  }

  /**
   * 票种编号
   */
  public SL_USELESS_TICKET_INFO setTicketTypeId(java.lang.String ticketTypeId) {
    this.ticketTypeId = ticketTypeId;
    return this;
  }

  public void unsetTicketTypeId() {
    this.ticketTypeId = null;
  }

  /** Returns true if field ticketTypeId is set (has been assigned a value) and false otherwise */
  public boolean isSetTicketTypeId() {
    return this.ticketTypeId != null;
  }

  public void setTicketTypeIdIsSet(boolean value) {
    if (!value) {
      this.ticketTypeId = null;
    }
  }

  /**
   * 作废时间
   */
  public long getUselessTime() {
    return this.uselessTime;
  }

  /**
   * 作废时间
   */
  public SL_USELESS_TICKET_INFO setUselessTime(long uselessTime) {
    this.uselessTime = uselessTime;
    setUselessTimeIsSet(true);
    return this;
  }

  public void unsetUselessTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __USELESSTIME_ISSET_ID);
  }

  /** Returns true if field uselessTime is set (has been assigned a value) and false otherwise */
  public boolean isSetUselessTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __USELESSTIME_ISSET_ID);
  }

  public void setUselessTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __USELESSTIME_ISSET_ID, value);
  }

  /**
   * 作废人
   */
  public java.lang.String getUselessUserId() {
    return this.uselessUserId;
  }

  /**
   * 作废人
   */
  public SL_USELESS_TICKET_INFO setUselessUserId(java.lang.String uselessUserId) {
    this.uselessUserId = uselessUserId;
    return this;
  }

  public void unsetUselessUserId() {
    this.uselessUserId = null;
  }

  /** Returns true if field uselessUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetUselessUserId() {
    return this.uselessUserId != null;
  }

  public void setUselessUserIdIsSet(boolean value) {
    if (!value) {
      this.uselessUserId = null;
    }
  }

  /**
   * 作废原因
   */
  public java.lang.String getUselessReason() {
    return this.uselessReason;
  }

  /**
   * 作废原因
   */
  public SL_USELESS_TICKET_INFO setUselessReason(java.lang.String uselessReason) {
    this.uselessReason = uselessReason;
    return this;
  }

  public void unsetUselessReason() {
    this.uselessReason = null;
  }

  /** Returns true if field uselessReason is set (has been assigned a value) and false otherwise */
  public boolean isSetUselessReason() {
    return this.uselessReason != null;
  }

  public void setUselessReasonIsSet(boolean value) {
    if (!value) {
      this.uselessReason = null;
    }
  }

  /**
   * 确认人
   */
  public java.lang.String getConfirmUserId() {
    return this.confirmUserId;
  }

  /**
   * 确认人
   */
  public SL_USELESS_TICKET_INFO setConfirmUserId(java.lang.String confirmUserId) {
    this.confirmUserId = confirmUserId;
    return this;
  }

  public void unsetConfirmUserId() {
    this.confirmUserId = null;
  }

  /** Returns true if field confirmUserId is set (has been assigned a value) and false otherwise */
  public boolean isSetConfirmUserId() {
    return this.confirmUserId != null;
  }

  public void setConfirmUserIdIsSet(boolean value) {
    if (!value) {
      this.confirmUserId = null;
    }
  }

  /**
   * 确认时间
   */
  public long getConfirmTime() {
    return this.confirmTime;
  }

  /**
   * 确认时间
   */
  public SL_USELESS_TICKET_INFO setConfirmTime(long confirmTime) {
    this.confirmTime = confirmTime;
    setConfirmTimeIsSet(true);
    return this;
  }

  public void unsetConfirmTime() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __CONFIRMTIME_ISSET_ID);
  }

  /** Returns true if field confirmTime is set (has been assigned a value) and false otherwise */
  public boolean isSetConfirmTime() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __CONFIRMTIME_ISSET_ID);
  }

  public void setConfirmTimeIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __CONFIRMTIME_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, java.lang.Object value) {
    switch (field) {
    case USELESS_TICKET_INFO_ID:
      if (value == null) {
        unsetUselessTicketInfoId();
      } else {
        setUselessTicketInfoId((java.lang.String)value);
      }
      break;

    case TICKET_ID:
      if (value == null) {
        unsetTicketId();
      } else {
        setTicketId((java.lang.Long)value);
      }
      break;

    case TICKET_TYPE_ID:
      if (value == null) {
        unsetTicketTypeId();
      } else {
        setTicketTypeId((java.lang.String)value);
      }
      break;

    case USELESS_TIME:
      if (value == null) {
        unsetUselessTime();
      } else {
        setUselessTime((java.lang.Long)value);
      }
      break;

    case USELESS_USER_ID:
      if (value == null) {
        unsetUselessUserId();
      } else {
        setUselessUserId((java.lang.String)value);
      }
      break;

    case USELESS_REASON:
      if (value == null) {
        unsetUselessReason();
      } else {
        setUselessReason((java.lang.String)value);
      }
      break;

    case CONFIRM_USER_ID:
      if (value == null) {
        unsetConfirmUserId();
      } else {
        setConfirmUserId((java.lang.String)value);
      }
      break;

    case CONFIRM_TIME:
      if (value == null) {
        unsetConfirmTime();
      } else {
        setConfirmTime((java.lang.Long)value);
      }
      break;

    }
  }

  public java.lang.Object getFieldValue(_Fields field) {
    switch (field) {
    case USELESS_TICKET_INFO_ID:
      return getUselessTicketInfoId();

    case TICKET_ID:
      return getTicketId();

    case TICKET_TYPE_ID:
      return getTicketTypeId();

    case USELESS_TIME:
      return getUselessTime();

    case USELESS_USER_ID:
      return getUselessUserId();

    case USELESS_REASON:
      return getUselessReason();

    case CONFIRM_USER_ID:
      return getConfirmUserId();

    case CONFIRM_TIME:
      return getConfirmTime();

    }
    throw new java.lang.IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new java.lang.IllegalArgumentException();
    }

    switch (field) {
    case USELESS_TICKET_INFO_ID:
      return isSetUselessTicketInfoId();
    case TICKET_ID:
      return isSetTicketId();
    case TICKET_TYPE_ID:
      return isSetTicketTypeId();
    case USELESS_TIME:
      return isSetUselessTime();
    case USELESS_USER_ID:
      return isSetUselessUserId();
    case USELESS_REASON:
      return isSetUselessReason();
    case CONFIRM_USER_ID:
      return isSetConfirmUserId();
    case CONFIRM_TIME:
      return isSetConfirmTime();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof SL_USELESS_TICKET_INFO)
      return this.equals((SL_USELESS_TICKET_INFO)that);
    return false;
  }

  public boolean equals(SL_USELESS_TICKET_INFO that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_uselessTicketInfoId = true && this.isSetUselessTicketInfoId();
    boolean that_present_uselessTicketInfoId = true && that.isSetUselessTicketInfoId();
    if (this_present_uselessTicketInfoId || that_present_uselessTicketInfoId) {
      if (!(this_present_uselessTicketInfoId && that_present_uselessTicketInfoId))
        return false;
      if (!this.uselessTicketInfoId.equals(that.uselessTicketInfoId))
        return false;
    }

    boolean this_present_ticketId = true;
    boolean that_present_ticketId = true;
    if (this_present_ticketId || that_present_ticketId) {
      if (!(this_present_ticketId && that_present_ticketId))
        return false;
      if (this.ticketId != that.ticketId)
        return false;
    }

    boolean this_present_ticketTypeId = true && this.isSetTicketTypeId();
    boolean that_present_ticketTypeId = true && that.isSetTicketTypeId();
    if (this_present_ticketTypeId || that_present_ticketTypeId) {
      if (!(this_present_ticketTypeId && that_present_ticketTypeId))
        return false;
      if (!this.ticketTypeId.equals(that.ticketTypeId))
        return false;
    }

    boolean this_present_uselessTime = true;
    boolean that_present_uselessTime = true;
    if (this_present_uselessTime || that_present_uselessTime) {
      if (!(this_present_uselessTime && that_present_uselessTime))
        return false;
      if (this.uselessTime != that.uselessTime)
        return false;
    }

    boolean this_present_uselessUserId = true && this.isSetUselessUserId();
    boolean that_present_uselessUserId = true && that.isSetUselessUserId();
    if (this_present_uselessUserId || that_present_uselessUserId) {
      if (!(this_present_uselessUserId && that_present_uselessUserId))
        return false;
      if (!this.uselessUserId.equals(that.uselessUserId))
        return false;
    }

    boolean this_present_uselessReason = true && this.isSetUselessReason();
    boolean that_present_uselessReason = true && that.isSetUselessReason();
    if (this_present_uselessReason || that_present_uselessReason) {
      if (!(this_present_uselessReason && that_present_uselessReason))
        return false;
      if (!this.uselessReason.equals(that.uselessReason))
        return false;
    }

    boolean this_present_confirmUserId = true && this.isSetConfirmUserId();
    boolean that_present_confirmUserId = true && that.isSetConfirmUserId();
    if (this_present_confirmUserId || that_present_confirmUserId) {
      if (!(this_present_confirmUserId && that_present_confirmUserId))
        return false;
      if (!this.confirmUserId.equals(that.confirmUserId))
        return false;
    }

    boolean this_present_confirmTime = true && this.isSetConfirmTime();
    boolean that_present_confirmTime = true && that.isSetConfirmTime();
    if (this_present_confirmTime || that_present_confirmTime) {
      if (!(this_present_confirmTime && that_present_confirmTime))
        return false;
      if (this.confirmTime != that.confirmTime)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 1;

    hashCode = hashCode * 8191 + ((isSetUselessTicketInfoId()) ? 131071 : 524287);
    if (isSetUselessTicketInfoId())
      hashCode = hashCode * 8191 + uselessTicketInfoId.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(ticketId);

    hashCode = hashCode * 8191 + ((isSetTicketTypeId()) ? 131071 : 524287);
    if (isSetTicketTypeId())
      hashCode = hashCode * 8191 + ticketTypeId.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(uselessTime);

    hashCode = hashCode * 8191 + ((isSetUselessUserId()) ? 131071 : 524287);
    if (isSetUselessUserId())
      hashCode = hashCode * 8191 + uselessUserId.hashCode();

    hashCode = hashCode * 8191 + ((isSetUselessReason()) ? 131071 : 524287);
    if (isSetUselessReason())
      hashCode = hashCode * 8191 + uselessReason.hashCode();

    hashCode = hashCode * 8191 + ((isSetConfirmUserId()) ? 131071 : 524287);
    if (isSetConfirmUserId())
      hashCode = hashCode * 8191 + confirmUserId.hashCode();

    hashCode = hashCode * 8191 + ((isSetConfirmTime()) ? 131071 : 524287);
    if (isSetConfirmTime())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(confirmTime);

    return hashCode;
  }

  @Override
  public int compareTo(SL_USELESS_TICKET_INFO other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetUselessTicketInfoId()).compareTo(other.isSetUselessTicketInfoId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUselessTicketInfoId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uselessTicketInfoId, other.uselessTicketInfoId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTicketId()).compareTo(other.isSetTicketId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTicketId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ticketId, other.ticketId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetTicketTypeId()).compareTo(other.isSetTicketTypeId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTicketTypeId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.ticketTypeId, other.ticketTypeId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUselessTime()).compareTo(other.isSetUselessTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUselessTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uselessTime, other.uselessTime);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUselessUserId()).compareTo(other.isSetUselessUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUselessUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uselessUserId, other.uselessUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetUselessReason()).compareTo(other.isSetUselessReason());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUselessReason()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uselessReason, other.uselessReason);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetConfirmUserId()).compareTo(other.isSetConfirmUserId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetConfirmUserId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.confirmUserId, other.confirmUserId);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetConfirmTime()).compareTo(other.isSetConfirmTime());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetConfirmTime()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.confirmTime, other.confirmTime);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("SL_USELESS_TICKET_INFO(");
    boolean first = true;

    sb.append("uselessTicketInfoId:");
    if (this.uselessTicketInfoId == null) {
      sb.append("null");
    } else {
      sb.append(this.uselessTicketInfoId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("ticketId:");
    sb.append(this.ticketId);
    first = false;
    if (!first) sb.append(", ");
    sb.append("ticketTypeId:");
    if (this.ticketTypeId == null) {
      sb.append("null");
    } else {
      sb.append(this.ticketTypeId);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("uselessTime:");
    sb.append(this.uselessTime);
    first = false;
    if (!first) sb.append(", ");
    sb.append("uselessUserId:");
    if (this.uselessUserId == null) {
      sb.append("null");
    } else {
      sb.append(this.uselessUserId);
    }
    first = false;
    if (isSetUselessReason()) {
      if (!first) sb.append(", ");
      sb.append("uselessReason:");
      if (this.uselessReason == null) {
        sb.append("null");
      } else {
        sb.append(this.uselessReason);
      }
      first = false;
    }
    if (isSetConfirmUserId()) {
      if (!first) sb.append(", ");
      sb.append("confirmUserId:");
      if (this.confirmUserId == null) {
        sb.append("null");
      } else {
        sb.append(this.confirmUserId);
      }
      first = false;
    }
    if (isSetConfirmTime()) {
      if (!first) sb.append(", ");
      sb.append("confirmTime:");
      sb.append(this.confirmTime);
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (uselessTicketInfoId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'uselessTicketInfoId' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'ticketId' because it's a primitive and you chose the non-beans generator.
    if (ticketTypeId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'ticketTypeId' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'uselessTime' because it's a primitive and you chose the non-beans generator.
    if (uselessUserId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'uselessUserId' was not present! Struct: " + toString());
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

  private static class SL_USELESS_TICKET_INFOStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SL_USELESS_TICKET_INFOStandardScheme getScheme() {
      return new SL_USELESS_TICKET_INFOStandardScheme();
    }
  }

  private static class SL_USELESS_TICKET_INFOStandardScheme extends org.apache.thrift.scheme.StandardScheme<SL_USELESS_TICKET_INFO> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SL_USELESS_TICKET_INFO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // USELESS_TICKET_INFO_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.uselessTicketInfoId = iprot.readString();
              struct.setUselessTicketInfoIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TICKET_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.ticketId = iprot.readI64();
              struct.setTicketIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TICKET_TYPE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ticketTypeId = iprot.readString();
              struct.setTicketTypeIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // USELESS_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.uselessTime = iprot.readI64();
              struct.setUselessTimeIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // USELESS_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.uselessUserId = iprot.readString();
              struct.setUselessUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // USELESS_REASON
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.uselessReason = iprot.readString();
              struct.setUselessReasonIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // CONFIRM_USER_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.confirmUserId = iprot.readString();
              struct.setConfirmUserIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 8: // CONFIRM_TIME
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.confirmTime = iprot.readI64();
              struct.setConfirmTimeIsSet(true);
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
      if (!struct.isSetTicketId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'ticketId' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetUselessTime()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'uselessTime' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SL_USELESS_TICKET_INFO struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.uselessTicketInfoId != null) {
        oprot.writeFieldBegin(USELESS_TICKET_INFO_ID_FIELD_DESC);
        oprot.writeString(struct.uselessTicketInfoId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(TICKET_ID_FIELD_DESC);
      oprot.writeI64(struct.ticketId);
      oprot.writeFieldEnd();
      if (struct.ticketTypeId != null) {
        oprot.writeFieldBegin(TICKET_TYPE_ID_FIELD_DESC);
        oprot.writeString(struct.ticketTypeId);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(USELESS_TIME_FIELD_DESC);
      oprot.writeI64(struct.uselessTime);
      oprot.writeFieldEnd();
      if (struct.uselessUserId != null) {
        oprot.writeFieldBegin(USELESS_USER_ID_FIELD_DESC);
        oprot.writeString(struct.uselessUserId);
        oprot.writeFieldEnd();
      }
      if (struct.uselessReason != null) {
        if (struct.isSetUselessReason()) {
          oprot.writeFieldBegin(USELESS_REASON_FIELD_DESC);
          oprot.writeString(struct.uselessReason);
          oprot.writeFieldEnd();
        }
      }
      if (struct.confirmUserId != null) {
        if (struct.isSetConfirmUserId()) {
          oprot.writeFieldBegin(CONFIRM_USER_ID_FIELD_DESC);
          oprot.writeString(struct.confirmUserId);
          oprot.writeFieldEnd();
        }
      }
      if (struct.isSetConfirmTime()) {
        oprot.writeFieldBegin(CONFIRM_TIME_FIELD_DESC);
        oprot.writeI64(struct.confirmTime);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SL_USELESS_TICKET_INFOTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SL_USELESS_TICKET_INFOTupleScheme getScheme() {
      return new SL_USELESS_TICKET_INFOTupleScheme();
    }
  }

  private static class SL_USELESS_TICKET_INFOTupleScheme extends org.apache.thrift.scheme.TupleScheme<SL_USELESS_TICKET_INFO> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SL_USELESS_TICKET_INFO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.uselessTicketInfoId);
      oprot.writeI64(struct.ticketId);
      oprot.writeString(struct.ticketTypeId);
      oprot.writeI64(struct.uselessTime);
      oprot.writeString(struct.uselessUserId);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetUselessReason()) {
        optionals.set(0);
      }
      if (struct.isSetConfirmUserId()) {
        optionals.set(1);
      }
      if (struct.isSetConfirmTime()) {
        optionals.set(2);
      }
      oprot.writeBitSet(optionals, 3);
      if (struct.isSetUselessReason()) {
        oprot.writeString(struct.uselessReason);
      }
      if (struct.isSetConfirmUserId()) {
        oprot.writeString(struct.confirmUserId);
      }
      if (struct.isSetConfirmTime()) {
        oprot.writeI64(struct.confirmTime);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SL_USELESS_TICKET_INFO struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.uselessTicketInfoId = iprot.readString();
      struct.setUselessTicketInfoIdIsSet(true);
      struct.ticketId = iprot.readI64();
      struct.setTicketIdIsSet(true);
      struct.ticketTypeId = iprot.readString();
      struct.setTicketTypeIdIsSet(true);
      struct.uselessTime = iprot.readI64();
      struct.setUselessTimeIsSet(true);
      struct.uselessUserId = iprot.readString();
      struct.setUselessUserIdIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(3);
      if (incoming.get(0)) {
        struct.uselessReason = iprot.readString();
        struct.setUselessReasonIsSet(true);
      }
      if (incoming.get(1)) {
        struct.confirmUserId = iprot.readString();
        struct.setConfirmUserIdIsSet(true);
      }
      if (incoming.get(2)) {
        struct.confirmTime = iprot.readI64();
        struct.setConfirmTimeIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

