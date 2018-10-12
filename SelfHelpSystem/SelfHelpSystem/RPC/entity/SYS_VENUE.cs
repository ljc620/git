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
  /// 场馆表
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class SYS_VENUE : TBase
  {
    private string _stat;
    private string _opeUserId;
    private long _opeTime;

    /// <summary>
    /// 场馆编号
    /// </summary>
    public long VenueId { get; set; }

    /// <summary>
    /// 场馆名称
    /// </summary>
    public string VenueName { get; set; }

    /// <summary>
    /// 状态(Y启用N停用)
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


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool stat;
      public bool opeUserId;
      public bool opeTime;
    }

    public SYS_VENUE() {
    }

    public SYS_VENUE(long venueId, string venueName) : this() {
      this.VenueId = venueId;
      this.VenueName = venueName;
    }

    public void Read (TProtocol iprot)
    {
      iprot.IncrementRecursionDepth();
      try
      {
        bool isset_venueId = false;
        bool isset_venueName = false;
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
                VenueId = iprot.ReadI64();
                isset_venueId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.String) {
                VenueName = iprot.ReadString();
                isset_venueName = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.String) {
                Stat = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 4:
              if (field.Type == TType.String) {
                OpeUserId = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 5:
              if (field.Type == TType.I64) {
                OpeTime = iprot.ReadI64();
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
        if (!isset_venueId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_venueName)
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
        TStruct struc = new TStruct("SYS_VENUE");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        field.Name = "venueId";
        field.Type = TType.I64;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(VenueId);
        oprot.WriteFieldEnd();
        field.Name = "venueName";
        field.Type = TType.String;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(VenueName);
        oprot.WriteFieldEnd();
        if (Stat != null && __isset.stat) {
          field.Name = "stat";
          field.Type = TType.String;
          field.ID = 3;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(Stat);
          oprot.WriteFieldEnd();
        }
        if (OpeUserId != null && __isset.opeUserId) {
          field.Name = "opeUserId";
          field.Type = TType.String;
          field.ID = 4;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(OpeUserId);
          oprot.WriteFieldEnd();
        }
        if (__isset.opeTime) {
          field.Name = "opeTime";
          field.Type = TType.I64;
          field.ID = 5;
          oprot.WriteFieldBegin(field);
          oprot.WriteI64(OpeTime);
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
      StringBuilder __sb = new StringBuilder("SYS_VENUE(");
      __sb.Append(", VenueId: ");
      __sb.Append(VenueId);
      __sb.Append(", VenueName: ");
      __sb.Append(VenueName);
      if (Stat != null && __isset.stat) {
        __sb.Append(", Stat: ");
        __sb.Append(Stat);
      }
      if (OpeUserId != null && __isset.opeUserId) {
        __sb.Append(", OpeUserId: ");
        __sb.Append(OpeUserId);
      }
      if (__isset.opeTime) {
        __sb.Append(", OpeTime: ");
        __sb.Append(OpeTime);
      }
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
