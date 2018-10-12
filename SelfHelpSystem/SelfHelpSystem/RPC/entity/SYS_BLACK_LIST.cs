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
  /// 黑名单表
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class SYS_BLACK_LIST : TBase
  {
    private string _chipId;
    private string _lossReason;
    private string _opeUserId;
    private long _opeTime;
    private long _versionNo;
    private string _stat;

    /// <summary>
    /// 黑名单ID
    /// </summary>
    public string BlackListId { get; set; }

    /// <summary>
    /// 挂失日期
    /// </summary>
    public long LossDt { get; set; }

    /// <summary>
    /// 票号
    /// </summary>
    public string TicketId { get; set; }

    /// <summary>
    /// 芯片ID
    /// </summary>
    public string ChipId
    {
      get
      {
        return _chipId;
      }
      set
      {
        __isset.chipId = true;
        this._chipId = value;
      }
    }

    /// <summary>
    /// 类型(1-员工卡或2-票)
    /// </summary>
    public string CardType { get; set; }

    /// <summary>
    /// 挂失原因
    /// </summary>
    public string LossReason
    {
      get
      {
        return _lossReason;
      }
      set
      {
        __isset.lossReason = true;
        this._lossReason = value;
      }
    }

    /// <summary>
    /// 操作人
    /// </summary>
    public string OpeUserId
    {
      get
      {
        return _opeUserId;
      }
      set
      {
        __isset.opeUserId = true;
        this._opeUserId = value;
      }
    }

    /// <summary>
    /// 操作时间
    /// </summary>
    public long OpeTime
    {
      get
      {
        return _opeTime;
      }
      set
      {
        __isset.opeTime = true;
        this._opeTime = value;
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
    /// 状态(Y启用N取消)
    /// </summary>
    public string Stat
    {
      get
      {
        return _stat;
      }
      set
      {
        __isset.stat = true;
        this._stat = value;
      }
    }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool chipId;
      public bool lossReason;
      public bool opeUserId;
      public bool opeTime;
      public bool versionNo;
      public bool stat;
    }

    public SYS_BLACK_LIST() {
    }

    public SYS_BLACK_LIST(string blackListId, long lossDt, string ticketId, string cardType) : this() {
      this.BlackListId = blackListId;
      this.LossDt = lossDt;
      this.TicketId = ticketId;
      this.CardType = cardType;
    }

    public void Read (TProtocol iprot)
    {
      iprot.IncrementRecursionDepth();
      try
      {
        bool isset_blackListId = false;
        bool isset_lossDt = false;
        bool isset_ticketId = false;
        bool isset_cardType = false;
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
                BlackListId = iprot.ReadString();
                isset_blackListId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.I64) {
                LossDt = iprot.ReadI64();
                isset_lossDt = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.String) {
                TicketId = iprot.ReadString();
                isset_ticketId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 4:
              if (field.Type == TType.String) {
                ChipId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 5:
              if (field.Type == TType.String) {
                CardType = iprot.ReadString();
                isset_cardType = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 6:
              if (field.Type == TType.String) {
                LossReason = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 7:
              if (field.Type == TType.String) {
                OpeUserId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 8:
              if (field.Type == TType.I64) {
                OpeTime = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 9:
              if (field.Type == TType.I64) {
                VersionNo = iprot.ReadI64();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 10:
              if (field.Type == TType.String) {
                Stat = iprot.ReadString();
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
        if (!isset_blackListId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_lossDt)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_ticketId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_cardType)
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
        TStruct struc = new TStruct("SYS_BLACK_LIST");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        field.Name = "blackListId";
        field.Type = TType.String;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(BlackListId);
        oprot.WriteFieldEnd();
        field.Name = "lossDt";
        field.Type = TType.I64;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(LossDt);
        oprot.WriteFieldEnd();
        field.Name = "ticketId";
        field.Type = TType.String;
        field.ID = 3;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(TicketId);
        oprot.WriteFieldEnd();
        if (ChipId != null && __isset.chipId) {
          field.Name = "chipId";
          field.Type = TType.String;
          field.ID = 4;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(ChipId);
          oprot.WriteFieldEnd();
        }
        field.Name = "cardType";
        field.Type = TType.String;
        field.ID = 5;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(CardType);
        oprot.WriteFieldEnd();
        if (LossReason != null && __isset.lossReason) {
          field.Name = "lossReason";
          field.Type = TType.String;
          field.ID = 6;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(LossReason);
          oprot.WriteFieldEnd();
        }
        if (OpeUserId != null && __isset.opeUserId) {
          field.Name = "opeUserId";
          field.Type = TType.String;
          field.ID = 7;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(OpeUserId);
          oprot.WriteFieldEnd();
        }
        if (__isset.opeTime) {
          field.Name = "opeTime";
          field.Type = TType.I64;
          field.ID = 8;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(OpeTime);
          oprot.WriteFieldEnd();
        }
        if (__isset.versionNo) {
          field.Name = "versionNo";
          field.Type = TType.I64;
          field.ID = 9;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(VersionNo);
          oprot.WriteFieldEnd();
        }
        if (Stat != null && __isset.stat) {
          field.Name = "stat";
          field.Type = TType.String;
          field.ID = 10;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(Stat);
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
      StringBuilder __sb = new StringBuilder("SYS_BLACK_LIST(");
      __sb.Append(", BlackListId: ");
      __sb.Append(BlackListId);
      __sb.Append(", LossDt: ");
      __sb.Append(LossDt);
      __sb.Append(", TicketId: ");
      __sb.Append(TicketId);
      if (ChipId != null && __isset.chipId) {
        __sb.Append(", ChipId: ");
        __sb.Append(ChipId);
      }
      __sb.Append(", CardType: ");
      __sb.Append(CardType);
      if (LossReason != null && __isset.lossReason) {
        __sb.Append(", LossReason: ");
        __sb.Append(LossReason);
      }
      if (OpeUserId != null && __isset.opeUserId) {
        __sb.Append(", OpeUserId: ");
        __sb.Append(OpeUserId);
      }
      if (__isset.opeTime) {
        __sb.Append(", OpeTime: ");
        __sb.Append(OpeTime);
      }
      if (__isset.versionNo) {
        __sb.Append(", VersionNo: ");
        __sb.Append(VersionNo);
      }
      if (Stat != null && __isset.stat) {
        __sb.Append(", Stat: ");
        __sb.Append(Stat);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
