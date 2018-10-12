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
  /// 网点员工销售统计表
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class RPT_SALE_DATA_USER : TBase
  {
    private long _rtpDate;
    private long _outletId;
    private string _outletName;
    private string _userId;
    private string _userName;
    private string _payName;
    private string _payType;
    private string _ticketTypeId;
    private string _ticketTypeName;
    private long _saleNum;
    private double _saleSumAmt;

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
    /// 销售员号
    /// </summary>
    public string UserId
    {
      get
      {
        return _userId;
      }
      set
      {
        __isset.userId = true;
        this._userId = value;
      }
    }

    /// <summary>
    /// 销售员名称
    /// </summary>
    public string UserName
    {
      get
      {
        return _userName;
      }
      set
      {
        __isset.userName = true;
        this._userName = value;
      }
    }

    /// <summary>
    /// 支付方式编码
    /// </summary>
    public string PayName
    {
      get
      {
        return _payName;
      }
      set
      {
        __isset.payName = true;
        this._payName = value;
      }
    }

    /// <summary>
    /// 支付方式名称
    /// </summary>
    public string PayType
    {
      get
      {
        return _payType;
      }
      set
      {
        __isset.payType = true;
        this._payType = value;
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
    /// 销售数量
    /// </summary>
    public long SaleNum
    {
      get
      {
        return _saleNum;
      }
      set
      {
        __isset.saleNum = true;
        this._saleNum = value;
      }
    }

    /// <summary>
    /// 销售总金额
    /// </summary>
    public double SaleSumAmt
    {
      get
      {
        return _saleSumAmt;
      }
      set
      {
        __isset.saleSumAmt = true;
        this._saleSumAmt = value;
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
      public bool userId;
      public bool userName;
      public bool payName;
      public bool payType;
      public bool ticketTypeId;
      public bool ticketTypeName;
      public bool saleNum;
      public bool saleSumAmt;
    }

    public RPT_SALE_DATA_USER() {
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
                UserId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 5:
              if (field.Type == TType.String) {
                UserName = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 6:
              if (field.Type == TType.String) {
                PayName = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 7:
              if (field.Type == TType.String) {
                PayType = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 8:
              if (field.Type == TType.String) {
                TicketTypeId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 9:
              if (field.Type == TType.String) {
                TicketTypeName = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 10:
              if (field.Type == TType.I64) {
                SaleNum = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 11:
              if (field.Type == TType.Double) {
                SaleSumAmt = iprot.ReadDouble();
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
        TStruct struc = new TStruct("RPT_SALE_DATA_USER");
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
        if (UserId != null && __isset.userId) {
          field.Name = "userId";
          field.Type = TType.String;
          field.ID = 4;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(UserId);
          oprot.WriteFieldEnd();
        }
        if (UserName != null && __isset.userName) {
          field.Name = "userName";
          field.Type = TType.String;
          field.ID = 5;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(UserName);
          oprot.WriteFieldEnd();
        }
        if (PayName != null && __isset.payName) {
          field.Name = "payName";
          field.Type = TType.String;
          field.ID = 6;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(PayName);
          oprot.WriteFieldEnd();
        }
        if (PayType != null && __isset.payType) {
          field.Name = "payType";
          field.Type = TType.String;
          field.ID = 7;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(PayType);
          oprot.WriteFieldEnd();
        }
        if (TicketTypeId != null && __isset.ticketTypeId) {
          field.Name = "ticketTypeId";
          field.Type = TType.String;
          field.ID = 8;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(TicketTypeId);
          oprot.WriteFieldEnd();
        }
        if (TicketTypeName != null && __isset.ticketTypeName) {
          field.Name = "ticketTypeName";
          field.Type = TType.String;
          field.ID = 9;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(TicketTypeName);
          oprot.WriteFieldEnd();
        }
        if (__isset.saleNum) {
          field.Name = "saleNum";
          field.Type = TType.I64;
          field.ID = 10;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(SaleNum);
          oprot.WriteFieldEnd();
        }
        if (__isset.saleSumAmt) {
          field.Name = "saleSumAmt";
          field.Type = TType.Double;
          field.ID = 11;
          oprot.WriteFieldBegin(field);
          oprot.WriteDouble(SaleSumAmt);
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
      StringBuilder __sb = new StringBuilder("RPT_SALE_DATA_USER(");
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
      if (UserId != null && __isset.userId) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("UserId: ");
        __sb.Append(UserId);
      }
      if (UserName != null && __isset.userName) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("UserName: ");
        __sb.Append(UserName);
      }
      if (PayName != null && __isset.payName) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("PayName: ");
        __sb.Append(PayName);
      }
      if (PayType != null && __isset.payType) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("PayType: ");
        __sb.Append(PayType);
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
      if (__isset.saleNum) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("SaleNum: ");
        __sb.Append(SaleNum);
      }
      if (__isset.saleSumAmt) {
        if(!__first) { __sb.Append(", "); }
        __first = false;
        __sb.Append("SaleSumAmt: ");
        __sb.Append(SaleSumAmt);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}