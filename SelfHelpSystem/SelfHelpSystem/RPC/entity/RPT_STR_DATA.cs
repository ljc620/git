/**
 * Autogenerated by Thrift Compiler (0.10.0)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
using System;
using System.Collections;
using System.Collections.Generic;
using System.Text;
using System.IO;
using Thrift;
using Thrift.Collections;
using System.Runtime.Serialization;
using Thrift.Protocol;
using Thrift.Transport;

namespace tbims.rpc.entity
{

  /// <summary>
  /// 网点库存统计表
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class RPT_STR_DATA : TBase
  {
    private long _rtpDate;
    private long _outletId;
    private string _outletName;
    private string _ticketTypeId;
    private string _ticketTypeName;
    private long _strLastTicketNum;
    private long _deliveryNum;
    private long _strTicketNum;
    private long _uselessNum;
    private long _saleTicketNum;

    /// <summary>
    /// 交易日期
    /// </summary>
    public long RtpDate
    {
      get
      {
        return _rtpDate;
      }
      set
      {
        __isset.rtpDate = true;
        this._rtpDate = value;
      }
    }

    /// <summary>
    /// 网点编号
    /// </summary>
    public long OutletId
    {
      get
      {
        return _outletId;
      }
      set
      {
        __isset.outletId = true;
        this._outletId = value;
      }
    }

    /// <summary>
    /// 网点名称
    /// </summary>
    public string OutletName
    {
      get
      {
        return _outletName;
      }
      set
      {
        __isset.outletName = true;
        this._outletName = value;
      }
    }

    /// <summary>
    /// 票种名称
    /// </summary>
    public string TicketTypeId
    {
      get
      {
        return _ticketTypeId;
      }
      set
      {
        __isset.ticketTypeId = true;
        this._ticketTypeId = value;
      }
    }

    /// <summary>
    /// 票种编码
    /// </summary>
    public string TicketTypeName
    {
      get
      {
        return _ticketTypeName;
      }
      set
      {
        __isset.ticketTypeName = true;
        this._ticketTypeName = value;
      }
    }

    /// <summary>
    /// 上日结余数量
    /// </summary>
    public long StrLastTicketNum
    {
      get
      {
        return _strLastTicketNum;
      }
      set
      {
        __isset.strLastTicketNum = true;
        this._strLastTicketNum = value;
      }
    }

    /// <summary>
    /// 配送数量
    /// </summary>
    public long DeliveryNum
    {
      get
      {
        return _deliveryNum;
      }
      set
      {
        __isset.deliveryNum = true;
        this._deliveryNum = value;
      }
    }

    /// <summary>
    /// 当日结余数量
    /// </summary>
    public long StrTicketNum
    {
      get
      {
        return _strTicketNum;
      }
      set
      {
        __isset.strTicketNum = true;
        this._strTicketNum = value;
      }
    }

    /// <summary>
    /// 废票数量
    /// </summary>
    public long UselessNum
    {
      get
      {
        return _uselessNum;
      }
      set
      {
        __isset.uselessNum = true;
        this._uselessNum = value;
      }
    }

    /// <summary>
    /// 销售数量
    /// </summary>
    public long SaleTicketNum
    {
      get
      {
        return _saleTicketNum;
      }
      set
      {
        __isset.saleTicketNum = true;
        this._saleTicketNum = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool rtpDate;
      public bool outletId;
      public bool outletName;
      public bool ticketTypeId;
      public bool ticketTypeName;
      public bool strLastTicketNum;
      public bool deliveryNum;
      public bool strTicketNum;
      public bool uselessNum;
      public bool saleTicketNum;
    }

    public RPT_STR_DATA() {
    }

    public void Read (TProtocol iprot)
    {
      iprot.IncrementRecursionDepth();
      try
      {
        TField field;
        iprot.ReadStructBegin();
        while (true)
        {
          field = iprot.ReadFieldBegin();
          if (field.Type == TType.Stop) { 
            break;
          }
          switch (field.ID)
          {
            case 1:
              if (field.Type == TType.I64) {
                RtpDate = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.I64) {
                OutletId = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.String) {
                OutletName = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 4:
              if (field.Type == TType.String) {
                TicketTypeId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 5:
              if (field.Type == TType.String) {
                TicketTypeName = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 6:
              if (field.Type == TType.I64) {
                StrLastTicketNum = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 7:
              if (field.Type == TType.I64) {
                DeliveryNum = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 8:
              if (field.Type == TType.I64) {
                StrTicketNum = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 9:
              if (field.Type == TType.I64) {
                UselessNum = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 10:
              if (field.Type == TType.I64) {
                SaleTicketNum = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            default: 
              TProtocolUtil.Skip(iprot, field.Type);
              break;
          }
          iprot.ReadFieldEnd();
        }
        iprot.ReadStructEnd();
      }
      finally
      {
        iprot.DecrementRecursionDepth();
      }
    }

    public void Write(TProtocol oprot) {
      oprot.IncrementRecursionDepth();
      try
      {
        TStruct struc = new TStruct("RPT_STR_DATA");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        if (__isset.rtpDate) {
          field.Name = "rtpDate";
          field.Type = TType.I64;
          field.ID = 1;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(RtpDate);
          oprot.WriteFieldEnd();
        }
        if (__isset.outletId) {
          field.Name = "outletId";
          field.Type = TType.I64;
          field.ID = 2;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(OutletId);
          oprot.WriteFieldEnd();
        }
        if (OutletName != null && __isset.outletName) {
          field.Name = "outletName";
          field.Type = TType.String;
          field.ID = 3;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(OutletName);
          oprot.WriteFieldEnd();
        }
        if (TicketTypeId != null && __isset.ticketTypeId) {
          field.Name = "ticketTypeId";
          field.Type = TType.String;
          field.ID = 4;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(TicketTypeId);
          oprot.WriteFieldEnd();
        }
        if (TicketTypeName != null && __isset.ticketTypeName) {
          field.Name = "ticketTypeName";
          field.Type = TType.String;
          field.ID = 5;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(TicketTypeName);
          oprot.WriteFieldEnd();
        }
        if (__isset.strLastTicketNum) {
          field.Name = "strLastTicketNum";
          field.Type = TType.I64;
          field.ID = 6;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(StrLastTicketNum);
          oprot.WriteFieldEnd();
        }
        if (__isset.deliveryNum) {
          field.Name = "deliveryNum";
          field.Type = TType.I64;
          field.ID = 7;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(DeliveryNum);
          oprot.WriteFieldEnd();
        }
        if (__isset.strTicketNum) {
          field.Name = "strTicketNum";
          field.Type = TType.I64;
          field.ID = 8;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(StrTicketNum);
          oprot.WriteFieldEnd();
        }
        if (__isset.uselessNum) {
          field.Name = "uselessNum";
          field.Type = TType.I64;
          field.ID = 9;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(UselessNum);
          oprot.WriteFieldEnd();
        }
        if (__isset.saleTicketNum) {
          field.Name = "saleTicketNum";
          field.Type = TType.I64;
          field.ID = 10;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(SaleTicketNum);
          oprot.WriteFieldEnd();
        }
        oprot.WriteFieldStop();
        oprot.WriteStructEnd();
      }
      finally
      {
        oprot.DecrementRecursionDepth();
      }
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("RPT_STR_DATA(");
      bool __first = true;
      if (__isset.rtpDate) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("RtpDate: ");
        __sb.Append(RtpDate);
      }
      if (__isset.outletId) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("OutletId: ");
        __sb.Append(OutletId);
      }
      if (OutletName != null && __isset.outletName) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("OutletName: ");
        __sb.Append(OutletName);
      }
      if (TicketTypeId != null && __isset.ticketTypeId) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("TicketTypeId: ");
        __sb.Append(TicketTypeId);
      }
      if (TicketTypeName != null && __isset.ticketTypeName) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("TicketTypeName: ");
        __sb.Append(TicketTypeName);
      }
      if (__isset.strLastTicketNum) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("StrLastTicketNum: ");
        __sb.Append(StrLastTicketNum);
      }
      if (__isset.deliveryNum) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("DeliveryNum: ");
        __sb.Append(DeliveryNum);
      }
      if (__isset.strTicketNum) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("StrTicketNum: ");
        __sb.Append(StrTicketNum);
      }
      if (__isset.uselessNum) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("UselessNum: ");
        __sb.Append(UselessNum);
      }
      if (__isset.saleTicketNum) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("SaleTicketNum: ");
        __sb.Append(SaleTicketNum);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}