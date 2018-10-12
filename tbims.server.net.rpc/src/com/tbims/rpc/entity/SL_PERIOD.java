/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.tbims.rpc.entity;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked", "unused"})
/**
 * 预售期表
 */
@javax.annotation.Generated(value = "Autogenerated by Thrift Compiler (0.10.0)", date = "2017-08-21")
public class SL_PERIOD implements org.apache.thrift.TBase<SL_PERIOD, SL_PERIOD._Fields>, java.io.Serializable, Cloneable, Comparable<SL_PERIOD> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("SL_PERIOD");

  private static final org.apache.thrift.protocol.TField SALE_PERIOD_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("salePeriodId", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TICKET_TYPE_ID_FIELD_DESC = new org.apache.thrift.protocol.TField("ticketTypeId", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SALE_PERIOD_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("salePeriodName", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField BEGIN_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("beginDt", org.apache.thrift.protocol.TType.I64, (short)4);
  private static final org.apache.thrift.protocol.TField END_DT_FIELD_DESC = new org.apache.thrift.protocol.TField("endDt", org.apache.thrift.protocol.TType.I64, (short)5);
  private static final org.apache.thrift.protocol.TField DISCOUNT_PRICE_FIELD_DESC = new org.apache.thrift.protocol.TField("discountPrice", org.apache.thrift.protocol.TType.I64, (short)6);
  private static final org.apache.thrift.protocol.TField VERSION_NO_FIELD_DESC = new org.apache.thrift.protocol.TField("versionNo", org.apache.thrift.protocol.TType.I64, (short)7);

  private static final org.apache.thrift.scheme.SchemeFactory STANDARD_SCHEME_FACTORY = new SL_PERIODStandardSchemeFactory();
  private static final org.apache.thrift.scheme.SchemeFactory TUPLE_SCHEME_FACTORY = new SL_PERIODTupleSchemeFactory();

  /**
   * 预售期编号
   */
  public java.lang.String salePeriodId; // required
  /**
   * 票种编号
   */
  public java.lang.String ticketTypeId; // required
  /**
   * 预售期名称
   */
  public java.lang.String salePeriodName; // required
  /**
   * 预售期开始日期
   */
  public long beginDt; // required
  /**
   * 预售期结束日期
   */
  public long endDt; // required
  /**
   * 折后票价（元）
   */
  public long discountPrice; // required
  /**
   * 版本号
   */
  public long versionNo; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    /**
     * 预售期编号
     */
    SALE_PERIOD_ID((short)1, "salePeriodId"),
    /**
     * 票种编号
     */
    TICKET_TYPE_ID((short)2, "ticketTypeId"),
    /**
     * 预售期名称
     */
    SALE_PERIOD_NAME((short)3, "salePeriodName"),
    /**
     * 预售期开始日期
     */
    BEGIN_DT((short)4, "beginDt"),
    /**
     * 预售期结束日期
     */
    END_DT((short)5, "endDt"),
    /**
     * 折后票价（元）
     */
    DISCOUNT_PRICE((short)6, "discountPrice"),
    /**
     * 版本号
     */
    VERSION_NO((short)7, "versionNo");

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
        case 1: // SALE_PERIOD_ID
          return SALE_PERIOD_ID;
        case 2: // TICKET_TYPE_ID
          return TICKET_TYPE_ID;
        case 3: // SALE_PERIOD_NAME
          return SALE_PERIOD_NAME;
        case 4: // BEGIN_DT
          return BEGIN_DT;
        case 5: // END_DT
          return END_DT;
        case 6: // DISCOUNT_PRICE
          return DISCOUNT_PRICE;
        case 7: // VERSION_NO
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
  private static final int __BEGINDT_ISSET_ID = 0;
  private static final int __ENDDT_ISSET_ID = 1;
  private static final int __DISCOUNTPRICE_ISSET_ID = 2;
  private static final int __VERSIONNO_ISSET_ID = 3;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.VERSION_NO};
  public static final java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    java.util.Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new java.util.EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SALE_PERIOD_ID, new org.apache.thrift.meta_data.FieldMetaData("salePeriodId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TICKET_TYPE_ID, new org.apache.thrift.meta_data.FieldMetaData("ticketTypeId", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SALE_PERIOD_NAME, new org.apache.thrift.meta_data.FieldMetaData("salePeriodName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.BEGIN_DT, new org.apache.thrift.meta_data.FieldMetaData("beginDt", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.END_DT, new org.apache.thrift.meta_data.FieldMetaData("endDt", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DISCOUNT_PRICE, new org.apache.thrift.meta_data.FieldMetaData("discountPrice", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.VERSION_NO, new org.apache.thrift.meta_data.FieldMetaData("versionNo", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    metaDataMap = java.util.Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(SL_PERIOD.class, metaDataMap);
  }

  public SL_PERIOD() {
  }

  public SL_PERIOD(
    java.lang.String salePeriodId,
    java.lang.String ticketTypeId,
    java.lang.String salePeriodName,
    long beginDt,
    long endDt,
    long discountPrice)
  {
    this();
    this.salePeriodId = salePeriodId;
    this.ticketTypeId = ticketTypeId;
    this.salePeriodName = salePeriodName;
    this.beginDt = beginDt;
    setBeginDtIsSet(true);
    this.endDt = endDt;
    setEndDtIsSet(true);
    this.discountPrice = discountPrice;
    setDiscountPriceIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public SL_PERIOD(SL_PERIOD other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetSalePeriodId()) {
      this.salePeriodId = other.salePeriodId;
    }
    if (other.isSetTicketTypeId()) {
      this.ticketTypeId = other.ticketTypeId;
    }
    if (other.isSetSalePeriodName()) {
      this.salePeriodName = other.salePeriodName;
    }
    this.beginDt = other.beginDt;
    this.endDt = other.endDt;
    this.discountPrice = other.discountPrice;
    this.versionNo = other.versionNo;
  }

  public SL_PERIOD deepCopy() {
    return new SL_PERIOD(this);
  }

  @Override
  public void clear() {
    this.salePeriodId = null;
    this.ticketTypeId = null;
    this.salePeriodName = null;
    setBeginDtIsSet(false);
    this.beginDt = 0;
    setEndDtIsSet(false);
    this.endDt = 0;
    setDiscountPriceIsSet(false);
    this.discountPrice = 0;
    setVersionNoIsSet(false);
    this.versionNo = 0;
  }

  /**
   * 预售期编号
   */
  public java.lang.String getSalePeriodId() {
    return this.salePeriodId;
  }

  /**
   * 预售期编号
   */
  public SL_PERIOD setSalePeriodId(java.lang.String salePeriodId) {
    this.salePeriodId = salePeriodId;
    return this;
  }

  public void unsetSalePeriodId() {
    this.salePeriodId = null;
  }

  /** Returns true if field salePeriodId is set (has been assigned a value) and false otherwise */
  public boolean isSetSalePeriodId() {
    return this.salePeriodId != null;
  }

  public void setSalePeriodIdIsSet(boolean value) {
    if (!value) {
      this.salePeriodId = null;
    }
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
  public SL_PERIOD setTicketTypeId(java.lang.String ticketTypeId) {
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
   * 预售期名称
   */
  public java.lang.String getSalePeriodName() {
    return this.salePeriodName;
  }

  /**
   * 预售期名称
   */
  public SL_PERIOD setSalePeriodName(java.lang.String salePeriodName) {
    this.salePeriodName = salePeriodName;
    return this;
  }

  public void unsetSalePeriodName() {
    this.salePeriodName = null;
  }

  /** Returns true if field salePeriodName is set (has been assigned a value) and false otherwise */
  public boolean isSetSalePeriodName() {
    return this.salePeriodName != null;
  }

  public void setSalePeriodNameIsSet(boolean value) {
    if (!value) {
      this.salePeriodName = null;
    }
  }

  /**
   * 预售期开始日期
   */
  public long getBeginDt() {
    return this.beginDt;
  }

  /**
   * 预售期开始日期
   */
  public SL_PERIOD setBeginDt(long beginDt) {
    this.beginDt = beginDt;
    setBeginDtIsSet(true);
    return this;
  }

  public void unsetBeginDt() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __BEGINDT_ISSET_ID);
  }

  /** Returns true if field beginDt is set (has been assigned a value) and false otherwise */
  public boolean isSetBeginDt() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __BEGINDT_ISSET_ID);
  }

  public void setBeginDtIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __BEGINDT_ISSET_ID, value);
  }

  /**
   * 预售期结束日期
   */
  public long getEndDt() {
    return this.endDt;
  }

  /**
   * 预售期结束日期
   */
  public SL_PERIOD setEndDt(long endDt) {
    this.endDt = endDt;
    setEndDtIsSet(true);
    return this;
  }

  public void unsetEndDt() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __ENDDT_ISSET_ID);
  }

  /** Returns true if field endDt is set (has been assigned a value) and false otherwise */
  public boolean isSetEndDt() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __ENDDT_ISSET_ID);
  }

  public void setEndDtIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __ENDDT_ISSET_ID, value);
  }

  /**
   * 折后票价（元）
   */
  public long getDiscountPrice() {
    return this.discountPrice;
  }

  /**
   * 折后票价（元）
   */
  public SL_PERIOD setDiscountPrice(long discountPrice) {
    this.discountPrice = discountPrice;
    setDiscountPriceIsSet(true);
    return this;
  }

  public void unsetDiscountPrice() {
    __isset_bitfield = org.apache.thrift.EncodingUtils.clearBit(__isset_bitfield, __DISCOUNTPRICE_ISSET_ID);
  }

  /** Returns true if field discountPrice is set (has been assigned a value) and false otherwise */
  public boolean isSetDiscountPrice() {
    return org.apache.thrift.EncodingUtils.testBit(__isset_bitfield, __DISCOUNTPRICE_ISSET_ID);
  }

  public void setDiscountPriceIsSet(boolean value) {
    __isset_bitfield = org.apache.thrift.EncodingUtils.setBit(__isset_bitfield, __DISCOUNTPRICE_ISSET_ID, value);
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
  public SL_PERIOD setVersionNo(long versionNo) {
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
    case SALE_PERIOD_ID:
      if (value == null) {
        unsetSalePeriodId();
      } else {
        setSalePeriodId((java.lang.String)value);
      }
      break;

    case TICKET_TYPE_ID:
      if (value == null) {
        unsetTicketTypeId();
      } else {
        setTicketTypeId((java.lang.String)value);
      }
      break;

    case SALE_PERIOD_NAME:
      if (value == null) {
        unsetSalePeriodName();
      } else {
        setSalePeriodName((java.lang.String)value);
      }
      break;

    case BEGIN_DT:
      if (value == null) {
        unsetBeginDt();
      } else {
        setBeginDt((java.lang.Long)value);
      }
      break;

    case END_DT:
      if (value == null) {
        unsetEndDt();
      } else {
        setEndDt((java.lang.Long)value);
      }
      break;

    case DISCOUNT_PRICE:
      if (value == null) {
        unsetDiscountPrice();
      } else {
        setDiscountPrice((java.lang.Long)value);
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
    case SALE_PERIOD_ID:
      return getSalePeriodId();

    case TICKET_TYPE_ID:
      return getTicketTypeId();

    case SALE_PERIOD_NAME:
      return getSalePeriodName();

    case BEGIN_DT:
      return getBeginDt();

    case END_DT:
      return getEndDt();

    case DISCOUNT_PRICE:
      return getDiscountPrice();

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
    case SALE_PERIOD_ID:
      return isSetSalePeriodId();
    case TICKET_TYPE_ID:
      return isSetTicketTypeId();
    case SALE_PERIOD_NAME:
      return isSetSalePeriodName();
    case BEGIN_DT:
      return isSetBeginDt();
    case END_DT:
      return isSetEndDt();
    case DISCOUNT_PRICE:
      return isSetDiscountPrice();
    case VERSION_NO:
      return isSetVersionNo();
    }
    throw new java.lang.IllegalStateException();
  }

  @Override
  public boolean equals(java.lang.Object that) {
    if (that == null)
      return false;
    if (that instanceof SL_PERIOD)
      return this.equals((SL_PERIOD)that);
    return false;
  }

  public boolean equals(SL_PERIOD that) {
    if (that == null)
      return false;
    if (this == that)
      return true;

    boolean this_present_salePeriodId = true && this.isSetSalePeriodId();
    boolean that_present_salePeriodId = true && that.isSetSalePeriodId();
    if (this_present_salePeriodId || that_present_salePeriodId) {
      if (!(this_present_salePeriodId && that_present_salePeriodId))
        return false;
      if (!this.salePeriodId.equals(that.salePeriodId))
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

    boolean this_present_salePeriodName = true && this.isSetSalePeriodName();
    boolean that_present_salePeriodName = true && that.isSetSalePeriodName();
    if (this_present_salePeriodName || that_present_salePeriodName) {
      if (!(this_present_salePeriodName && that_present_salePeriodName))
        return false;
      if (!this.salePeriodName.equals(that.salePeriodName))
        return false;
    }

    boolean this_present_beginDt = true;
    boolean that_present_beginDt = true;
    if (this_present_beginDt || that_present_beginDt) {
      if (!(this_present_beginDt && that_present_beginDt))
        return false;
      if (this.beginDt != that.beginDt)
        return false;
    }

    boolean this_present_endDt = true;
    boolean that_present_endDt = true;
    if (this_present_endDt || that_present_endDt) {
      if (!(this_present_endDt && that_present_endDt))
        return false;
      if (this.endDt != that.endDt)
        return false;
    }

    boolean this_present_discountPrice = true;
    boolean that_present_discountPrice = true;
    if (this_present_discountPrice || that_present_discountPrice) {
      if (!(this_present_discountPrice && that_present_discountPrice))
        return false;
      if (this.discountPrice != that.discountPrice)
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

    hashCode = hashCode * 8191 + ((isSetSalePeriodId()) ? 131071 : 524287);
    if (isSetSalePeriodId())
      hashCode = hashCode * 8191 + salePeriodId.hashCode();

    hashCode = hashCode * 8191 + ((isSetTicketTypeId()) ? 131071 : 524287);
    if (isSetTicketTypeId())
      hashCode = hashCode * 8191 + ticketTypeId.hashCode();

    hashCode = hashCode * 8191 + ((isSetSalePeriodName()) ? 131071 : 524287);
    if (isSetSalePeriodName())
      hashCode = hashCode * 8191 + salePeriodName.hashCode();

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(beginDt);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(endDt);

    hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(discountPrice);

    hashCode = hashCode * 8191 + ((isSetVersionNo()) ? 131071 : 524287);
    if (isSetVersionNo())
      hashCode = hashCode * 8191 + org.apache.thrift.TBaseHelper.hashCode(versionNo);

    return hashCode;
  }

  @Override
  public int compareTo(SL_PERIOD other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = java.lang.Boolean.valueOf(isSetSalePeriodId()).compareTo(other.isSetSalePeriodId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSalePeriodId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.salePeriodId, other.salePeriodId);
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
    lastComparison = java.lang.Boolean.valueOf(isSetSalePeriodName()).compareTo(other.isSetSalePeriodName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSalePeriodName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.salePeriodName, other.salePeriodName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetBeginDt()).compareTo(other.isSetBeginDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBeginDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.beginDt, other.beginDt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetEndDt()).compareTo(other.isSetEndDt());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetEndDt()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.endDt, other.endDt);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = java.lang.Boolean.valueOf(isSetDiscountPrice()).compareTo(other.isSetDiscountPrice());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDiscountPrice()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.discountPrice, other.discountPrice);
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
    java.lang.StringBuilder sb = new java.lang.StringBuilder("SL_PERIOD(");
    boolean first = true;

    sb.append("salePeriodId:");
    if (this.salePeriodId == null) {
      sb.append("null");
    } else {
      sb.append(this.salePeriodId);
    }
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
    sb.append("salePeriodName:");
    if (this.salePeriodName == null) {
      sb.append("null");
    } else {
      sb.append(this.salePeriodName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("beginDt:");
    sb.append(this.beginDt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("endDt:");
    sb.append(this.endDt);
    first = false;
    if (!first) sb.append(", ");
    sb.append("discountPrice:");
    sb.append(this.discountPrice);
    first = false;
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
    if (salePeriodId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'salePeriodId' was not present! Struct: " + toString());
    }
    if (ticketTypeId == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'ticketTypeId' was not present! Struct: " + toString());
    }
    if (salePeriodName == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'salePeriodName' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'beginDt' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'endDt' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'discountPrice' because it's a primitive and you chose the non-beans generator.
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

  private static class SL_PERIODStandardSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SL_PERIODStandardScheme getScheme() {
      return new SL_PERIODStandardScheme();
    }
  }

  private static class SL_PERIODStandardScheme extends org.apache.thrift.scheme.StandardScheme<SL_PERIOD> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, SL_PERIOD struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SALE_PERIOD_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.salePeriodId = iprot.readString();
              struct.setSalePeriodIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TICKET_TYPE_ID
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.ticketTypeId = iprot.readString();
              struct.setTicketTypeIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SALE_PERIOD_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.salePeriodName = iprot.readString();
              struct.setSalePeriodNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // BEGIN_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.beginDt = iprot.readI64();
              struct.setBeginDtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // END_DT
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.endDt = iprot.readI64();
              struct.setEndDtIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // DISCOUNT_PRICE
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.discountPrice = iprot.readI64();
              struct.setDiscountPriceIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 7: // VERSION_NO
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
      if (!struct.isSetBeginDt()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'beginDt' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetEndDt()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'endDt' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetDiscountPrice()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'discountPrice' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, SL_PERIOD struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.salePeriodId != null) {
        oprot.writeFieldBegin(SALE_PERIOD_ID_FIELD_DESC);
        oprot.writeString(struct.salePeriodId);
        oprot.writeFieldEnd();
      }
      if (struct.ticketTypeId != null) {
        oprot.writeFieldBegin(TICKET_TYPE_ID_FIELD_DESC);
        oprot.writeString(struct.ticketTypeId);
        oprot.writeFieldEnd();
      }
      if (struct.salePeriodName != null) {
        oprot.writeFieldBegin(SALE_PERIOD_NAME_FIELD_DESC);
        oprot.writeString(struct.salePeriodName);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(BEGIN_DT_FIELD_DESC);
      oprot.writeI64(struct.beginDt);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(END_DT_FIELD_DESC);
      oprot.writeI64(struct.endDt);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DISCOUNT_PRICE_FIELD_DESC);
      oprot.writeI64(struct.discountPrice);
      oprot.writeFieldEnd();
      if (struct.isSetVersionNo()) {
        oprot.writeFieldBegin(VERSION_NO_FIELD_DESC);
        oprot.writeI64(struct.versionNo);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class SL_PERIODTupleSchemeFactory implements org.apache.thrift.scheme.SchemeFactory {
    public SL_PERIODTupleScheme getScheme() {
      return new SL_PERIODTupleScheme();
    }
  }

  private static class SL_PERIODTupleScheme extends org.apache.thrift.scheme.TupleScheme<SL_PERIOD> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, SL_PERIOD struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol oprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      oprot.writeString(struct.salePeriodId);
      oprot.writeString(struct.ticketTypeId);
      oprot.writeString(struct.salePeriodName);
      oprot.writeI64(struct.beginDt);
      oprot.writeI64(struct.endDt);
      oprot.writeI64(struct.discountPrice);
      java.util.BitSet optionals = new java.util.BitSet();
      if (struct.isSetVersionNo()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetVersionNo()) {
        oprot.writeI64(struct.versionNo);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, SL_PERIOD struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TTupleProtocol iprot = (org.apache.thrift.protocol.TTupleProtocol) prot;
      struct.salePeriodId = iprot.readString();
      struct.setSalePeriodIdIsSet(true);
      struct.ticketTypeId = iprot.readString();
      struct.setTicketTypeIdIsSet(true);
      struct.salePeriodName = iprot.readString();
      struct.setSalePeriodNameIsSet(true);
      struct.beginDt = iprot.readI64();
      struct.setBeginDtIsSet(true);
      struct.endDt = iprot.readI64();
      struct.setEndDtIsSet(true);
      struct.discountPrice = iprot.readI64();
      struct.setDiscountPriceIsSet(true);
      java.util.BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        struct.versionNo = iprot.readI64();
        struct.setVersionNoIsSet(true);
      }
    }
  }

  private static <S extends org.apache.thrift.scheme.IScheme> S scheme(org.apache.thrift.protocol.TProtocol proto) {
    return (org.apache.thrift.scheme.StandardScheme.class.equals(proto.getScheme()) ? STANDARD_SCHEME_FACTORY : TUPLE_SCHEME_FACTORY).getScheme();
  }
}

