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
  /// 废票表
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class SL_USELESS_TICKET_INFO : TBase
  {
    private string _uselessReason;
    private string _confirmUserId;
    private long _confirmTime;

    /// <summary>
    /// 废票信息表id
    /// </summary>
    public string UselessTicketInfoId { get; set; }

    /// <summary>
    /// 票号
    /// </summary>
    public long TicketId { get; set; }

    /// <summary>
    /// 票种编号
    /// </summary>
    public string TicketTypeId { get; set; }

    /// <summary>
    /// 作废时间
    /// </summary>
    public long UselessTime { get; set; }

    /// <summary>
    /// 作废人
    /// </summary>
    public string UselessUserId { get; set; }

    /// <summary>
    /// 作废原因
    /// </summary>
    public string UselessReason
    {
      get
      {
        return _uselessReason;
      }
      set
      {
        __isset.uselessReason = true;
        this._uselessReason = value;
      }
    }

    /// <summary>
    /// 确认人
    /// </summary>
    public string ConfirmUserId
    {
      get
      {
        return _confirmUserId;
      }
      set
      {
        __isset.confirmUserId = true;
        this._confirmUserId = value;
      }
    }

    /// <summary>
    /// 确认时间
    /// </summary>
    public long ConfirmTime
    {
      get
      {
        return _confirmTime;
      }
      set
      {
        __isset.confirmTime = true;
        this._confirmTime = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool uselessReason;
      public bool confirmUserId;
      public bool confirmTime;
    }

    public SL_USELESS_TICKET_INFO() {
    }

    public SL_USELESS_TICKET_INFO(string uselessTicketInfoId, long ticketId, string ticketTypeId, long uselessTime, string uselessUserId) : this() {
      this.UselessTicketInfoId = uselessTicketInfoId;
      this.TicketId = ticketId;
      this.TicketTypeId = ticketTypeId;
      this.UselessTime = uselessTime;
      this.UselessUserId = uselessUserId;
    }

    public void Read (TProtocol iprot)
    {
      iprot.IncrementRecursionDepth();
      try
      {
        bool isset_uselessTicketInfoId = false;
        bool isset_ticketId = false;
        bool isset_ticketTypeId = false;
        bool isset_uselessTime = false;
        bool isset_uselessUserId = false;
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
                UselessTicketInfoId = iprot.ReadString();
                isset_uselessTicketInfoId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.I64) {
                TicketId = iprot.ReadI64();
                isset_ticketId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.String) {
                TicketTypeId = iprot.ReadString();
                isset_ticketTypeId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 4:
              if (field.Type == TType.I64) {
                UselessTime = iprot.ReadI64();
                isset_uselessTime = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 5:
              if (field.Type == TType.String) {
                UselessUserId = iprot.ReadString();
                isset_uselessUserId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 6:
              if (field.Type == TType.String) {
                UselessReason = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 7:
              if (field.Type == TType.String) {
                ConfirmUserId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 8:
              if (field.Type == TType.I64) {
                ConfirmTime = iprot.ReadI64();
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
        if (!isset_uselessTicketInfoId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_ticketId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_ticketTypeId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_uselessTime)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_uselessUserId)
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
        TStruct struc = new TStruct("SL_USELESS_TICKET_INFO");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        field.Name = "uselessTicketInfoId";
        field.Type = TType.String;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(UselessTicketInfoId);
        oprot.WriteFieldEnd();
        field.Name = "ticketId";
        field.Type = TType.I64;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(TicketId);
        oprot.WriteFieldEnd();
        field.Name = "ticketTypeId";
        field.Type = TType.String;
        field.ID = 3;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(TicketTypeId);
        oprot.WriteFieldEnd();
        field.Name = "uselessTime";
        field.Type = TType.I64;
        field.ID = 4;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(UselessTime);
        oprot.WriteFieldEnd();
        field.Name = "uselessUserId";
        field.Type = TType.String;
        field.ID = 5;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(UselessUserId);
        oprot.WriteFieldEnd();
        if (UselessReason != null && __isset.uselessReason) {
          field.Name = "uselessReason";
          field.Type = TType.String;
          field.ID = 6;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(UselessReason);
          oprot.WriteFieldEnd();
        }
        if (ConfirmUserId != null && __isset.confirmUserId) {
          field.Name = "confirmUserId";
          field.Type = TType.String;
          field.ID = 7;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(ConfirmUserId);
          oprot.WriteFieldEnd();
        }
        if (__isset.confirmTime) {
          field.Name = "confirmTime";
          field.Type = TType.I64;
          field.ID = 8;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(ConfirmTime);
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
      StringBuilder __sb = new StringBuilder("SL_USELESS_TICKET_INFO(");
      __sb.Append(", UselessTicketInfoId: ");
      __sb.Append(UselessTicketInfoId);
      __sb.Append(", TicketId: ");
      __sb.Append(TicketId);
      __sb.Append(", TicketTypeId: ");
      __sb.Append(TicketTypeId);
      __sb.Append(", UselessTime: ");
      __sb.Append(UselessTime);
      __sb.Append(", UselessUserId: ");
      __sb.Append(UselessUserId);
      if (UselessReason != null && __isset.uselessReason) {
        __sb.Append(", UselessReason: ");
        __sb.Append(UselessReason);
      }
      if (ConfirmUserId != null && __isset.confirmUserId) {
        __sb.Append(", ConfirmUserId: ");
        __sb.Append(ConfirmUserId);
      }
      if (__isset.confirmTime) {
        __sb.Append(", ConfirmTime: ");
        __sb.Append(ConfirmTime);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
