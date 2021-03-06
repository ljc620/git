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
  /// 检票表
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class SL_CHECK : TBase
  {
    private string _ticketTypeId;
    private string _ticketUid;
    private string _passFlag;
    private string _errorCode;
    private string _nopassReason;
    private long _remainTimes;
    private long _versionNo;

    /// <summary>
    /// 检票表id
    /// </summary>
    public string CheckId { get; set; }

    /// <summary>
    /// 门票类型(1-FRID、2-身份证、3-二维码 4-员工卡)
    /// </summary>
    public string TicketClass { get; set; }

    /// <summary>
    /// 票种编号
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
    /// 票号
    /// </summary>
    public long TicketId { get; set; }

    /// <summary>
    /// 票据唯一号
    /// </summary>
    public string TicketUid
    {
      get
      {
        return _ticketUid;
      }
      set
      {
        __isset.ticketUid = true;
        this._ticketUid = value;
      }
    }

    /// <summary>
    /// 场馆编号
    /// </summary>
    public long VenueId { get; set; }

    /// <summary>
    /// 终端编号
    /// </summary>
    public long ClientId { get; set; }

    /// <summary>
    /// 是否通过(Y是N否)
    /// </summary>
    public string PassFlag
    {
      get
      {
        return _passFlag;
      }
      set
      {
        __isset.passFlag = true;
        this._passFlag = value;
      }
    }

    /// <summary>
    /// 错误码
    /// </summary>
    public string ErrorCode
    {
      get
      {
        return _errorCode;
      }
      set
      {
        __isset.errorCode = true;
        this._errorCode = value;
      }
    }

    /// <summary>
    /// 未通过原因
    /// </summary>
    public string NopassReason
    {
      get
      {
        return _nopassReason;
      }
      set
      {
        __isset.nopassReason = true;
        this._nopassReason = value;
      }
    }

    /// <summary>
    /// 剩余次数
    /// </summary>
    public long RemainTimes
    {
      get
      {
        return _remainTimes;
      }
      set
      {
        __isset.remainTimes = true;
        this._remainTimes = value;
      }
    }

    /// <summary>
    /// 版本号
    /// </summary>
    public long VersionNo
    {
      get
      {
        return _versionNo;
      }
      set
      {
        __isset.versionNo = true;
        this._versionNo = value;
      }
    }

    /// <summary>
    /// 操作时间
    /// </summary>
    public long OpeTime { get; set; }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool ticketTypeId;
      public bool ticketUid;
      public bool passFlag;
      public bool errorCode;
      public bool nopassReason;
      public bool remainTimes;
      public bool versionNo;
    }

    public SL_CHECK() {
    }

    public SL_CHECK(string checkId, string ticketClass, long ticketId, long venueId, long clientId, long opeTime) : this() {
      this.CheckId = checkId;
      this.TicketClass = ticketClass;
      this.TicketId = ticketId;
      this.VenueId = venueId;
      this.ClientId = clientId;
      this.OpeTime = opeTime;
    }

    public void Read (TProtocol iprot)
    {
      iprot.IncrementRecursionDepth();
      try
      {
        bool isset_checkId = false;
        bool isset_ticketClass = false;
        bool isset_ticketId = false;
        bool isset_venueId = false;
        bool isset_clientId = false;
        bool isset_opeTime = false;
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
              if (field.Type == TType.String) {
                CheckId = iprot.ReadString();
                isset_checkId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.String) {
                TicketClass = iprot.ReadString();
                isset_ticketClass = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.String) {
                TicketTypeId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 4:
              if (field.Type == TType.I64) {
                TicketId = iprot.ReadI64();
                isset_ticketId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 5:
              if (field.Type == TType.String) {
                TicketUid = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 6:
              if (field.Type == TType.I64) {
                VenueId = iprot.ReadI64();
                isset_venueId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 7:
              if (field.Type == TType.I64) {
                ClientId = iprot.ReadI64();
                isset_clientId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 8:
              if (field.Type == TType.String) {
                PassFlag = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 9:
              if (field.Type == TType.String) {
                ErrorCode = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 10:
              if (field.Type == TType.String) {
                NopassReason = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 11:
              if (field.Type == TType.I64) {
                RemainTimes = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 12:
              if (field.Type == TType.I64) {
                VersionNo = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 13:
              if (field.Type == TType.I64) {
                OpeTime = iprot.ReadI64();
                isset_opeTime = true;
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
        if (!isset_checkId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_ticketClass)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_ticketId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_venueId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_clientId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_opeTime)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
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
        TStruct struc = new TStruct("SL_CHECK");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        field.Name = "checkId";
        field.Type = TType.String;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(CheckId);
        oprot.WriteFieldEnd();
        field.Name = "ticketClass";
        field.Type = TType.String;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(TicketClass);
        oprot.WriteFieldEnd();
        if (TicketTypeId != null && __isset.ticketTypeId) {
          field.Name = "ticketTypeId";
          field.Type = TType.String;
          field.ID = 3;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(TicketTypeId);
          oprot.WriteFieldEnd();
        }
        field.Name = "ticketId";
        field.Type = TType.I64;
        field.ID = 4;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(TicketId);
        oprot.WriteFieldEnd();
        if (TicketUid != null && __isset.ticketUid) {
          field.Name = "ticketUid";
          field.Type = TType.String;
          field.ID = 5;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(TicketUid);
          oprot.WriteFieldEnd();
        }
        field.Name = "venueId";
        field.Type = TType.I64;
        field.ID = 6;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(VenueId);
        oprot.WriteFieldEnd();
        field.Name = "clientId";
        field.Type = TType.I64;
        field.ID = 7;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(ClientId);
        oprot.WriteFieldEnd();
        if (PassFlag != null && __isset.passFlag) {
          field.Name = "passFlag";
          field.Type = TType.String;
          field.ID = 8;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(PassFlag);
          oprot.WriteFieldEnd();
        }
        if (ErrorCode != null && __isset.errorCode) {
          field.Name = "errorCode";
          field.Type = TType.String;
          field.ID = 9;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(ErrorCode);
          oprot.WriteFieldEnd();
        }
        if (NopassReason != null && __isset.nopassReason) {
          field.Name = "nopassReason";
          field.Type = TType.String;
          field.ID = 10;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(NopassReason);
          oprot.WriteFieldEnd();
        }
        if (__isset.remainTimes) {
          field.Name = "remainTimes";
          field.Type = TType.I64;
          field.ID = 11;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(RemainTimes);
          oprot.WriteFieldEnd();
        }
        if (__isset.versionNo) {
          field.Name = "versionNo";
          field.Type = TType.I64;
          field.ID = 12;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(VersionNo);
          oprot.WriteFieldEnd();
        }
        field.Name = "opeTime";
        field.Type = TType.I64;
        field.ID = 13;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(OpeTime);
        oprot.WriteFieldEnd();
        oprot.WriteFieldStop();
        oprot.WriteStructEnd();
      }
      finally
      {
        oprot.DecrementRecursionDepth();
      }
    }

    public override string ToString() {
      StringBuilder __sb = new StringBuilder("SL_CHECK(");
      __sb.Append(", CheckId: ");
      __sb.Append(CheckId);
      __sb.Append(", TicketClass: ");
      __sb.Append(TicketClass);
      if (TicketTypeId != null && __isset.ticketTypeId) {
        __sb.Append(", TicketTypeId: ");
        __sb.Append(TicketTypeId);
      }
      __sb.Append(", TicketId: ");
      __sb.Append(TicketId);
      if (TicketUid != null && __isset.ticketUid) {
        __sb.Append(", TicketUid: ");
        __sb.Append(TicketUid);
      }
      __sb.Append(", VenueId: ");
      __sb.Append(VenueId);
      __sb.Append(", ClientId: ");
      __sb.Append(ClientId);
      if (PassFlag != null && __isset.passFlag) {
        __sb.Append(", PassFlag: ");
        __sb.Append(PassFlag);
      }
      if (ErrorCode != null && __isset.errorCode) {
        __sb.Append(", ErrorCode: ");
        __sb.Append(ErrorCode);
      }
      if (NopassReason != null && __isset.nopassReason) {
        __sb.Append(", NopassReason: ");
        __sb.Append(NopassReason);
      }
      if (__isset.remainTimes) {
        __sb.Append(", RemainTimes: ");
        __sb.Append(RemainTimes);
      }
      if (__isset.versionNo) {
        __sb.Append(", VersionNo: ");
        __sb.Append(VersionNo);
      }
      __sb.Append(", OpeTime: ");
      __sb.Append(OpeTime);
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
