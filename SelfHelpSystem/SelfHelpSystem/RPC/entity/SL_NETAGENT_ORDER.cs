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
  /// 网络代理订单表
  /// </summary>
  #if !SILVERLIGHT
  [Serializable]
  #endif
  public partial class SL_NETAGENT_ORDER : TBase
  {
    private string _ticket_service_code;

    /// <summary>
    /// 按销售类型等规则
    /// </summary>
    public string OrderId { get; set; }

    /// <summary>
    /// 第三方单号
    /// </summary>
    public string ThirdOrderNum { get; set; }

    /// <summary>
    /// 组织机构代码
    /// </summary>
    public string OrgId { get; set; }

    /// <summary>
    /// 取票码
    /// </summary>
    public string Ticket_service_code
    {
      get
      {
        return _ticket_service_code;
      }
      set
      {
        __isset.ticket_service_code = true;
        this._ticket_service_code = value;
      }
    }

    /// <summary>
    /// 操作人
    /// </summary>
    public string OpeUserId { get; set; }

    /// <summary>
    /// 操作时间
    /// </summary>
    public long OpeTime { get; set; }

    /// <summary>
    /// 销售明细
    /// </summary>
    public SL_ORDER SlOrder { get; set; }


    public Isset __isset;
    #if !SILVERLIGHT
    [Serializable]
    #endif
    public struct Isset {
      public bool ticket_service_code;
    }

    public SL_NETAGENT_ORDER() {
    }

    public SL_NETAGENT_ORDER(string orderId, string thirdOrderNum, string orgId, string opeUserId, long opeTime, SL_ORDER slOrder) : this() {
      this.OrderId = orderId;
      this.ThirdOrderNum = thirdOrderNum;
      this.OrgId = orgId;
      this.OpeUserId = opeUserId;
      this.OpeTime = opeTime;
      this.SlOrder = slOrder;
    }

    public void Read (TProtocol iprot)
    {
      iprot.IncrementRecursionDepth();
      try
      {
        bool isset_orderId = false;
        bool isset_thirdOrderNum = false;
        bool isset_orgId = false;
        bool isset_opeUserId = false;
        bool isset_opeTime = false;
        bool isset_slOrder = false;
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
                OrderId = iprot.ReadString();
                isset_orderId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 4:
              if (field.Type == TType.String) {
                ThirdOrderNum = iprot.ReadString();
                isset_thirdOrderNum = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 5:
              if (field.Type == TType.String) {
                OrgId = iprot.ReadString();
                isset_orgId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 6:
              if (field.Type == TType.String) {
                Ticket_service_code = iprot.ReadString();
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 2:
              if (field.Type == TType.String) {
                OpeUserId = iprot.ReadString();
                isset_opeUserId = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 3:
              if (field.Type == TType.I64) {
                OpeTime = iprot.ReadI64();
                isset_opeTime = true;
              } else { 
                TProtocolUtil.Skip(iprot, field.Type);
              }
              break;
            case 7:
              if (field.Type == TType.Struct) {
                SlOrder = new SL_ORDER();
                SlOrder.Read(iprot);
                isset_slOrder = true;
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
        if (!isset_orderId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_thirdOrderNum)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_orgId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_opeUserId)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_opeTime)
          throw new TProtocolException(TProtocolException.INVALID_DATA);
        if (!isset_slOrder)
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
        TStruct struc = new TStruct("SL_NETAGENT_ORDER");
        oprot.WriteStructBegin(struc);
        TField field = new TField();
        field.Name = "orderId";
        field.Type = TType.String;
        field.ID = 1;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(OrderId);
        oprot.WriteFieldEnd();
        field.Name = "opeUserId";
        field.Type = TType.String;
        field.ID = 2;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(OpeUserId);
        oprot.WriteFieldEnd();
        field.Name = "opeTime";
        field.Type = TType.I64;
        field.ID = 3;
        oprot.WriteFieldBegin(field);
        oprot.WriteI64(OpeTime);
        oprot.WriteFieldEnd();
        field.Name = "thirdOrderNum";
        field.Type = TType.String;
        field.ID = 4;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(ThirdOrderNum);
        oprot.WriteFieldEnd();
        field.Name = "orgId";
        field.Type = TType.String;
        field.ID = 5;
        oprot.WriteFieldBegin(field);
        oprot.WriteString(OrgId);
        oprot.WriteFieldEnd();
        if (Ticket_service_code != null && __isset.ticket_service_code) {
          field.Name = "ticket_service_code";
          field.Type = TType.String;
          field.ID = 6;
          oprot.WriteFieldBegin(field);
          oprot.WriteString(Ticket_service_code);
          oprot.WriteFieldEnd();
        }
        field.Name = "slOrder";
        field.Type = TType.Struct;
        field.ID = 7;
        oprot.WriteFieldBegin(field);
        SlOrder.Write(oprot);
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
      StringBuilder __sb = new StringBuilder("SL_NETAGENT_ORDER(");
      __sb.Append(", OrderId: ");
      __sb.Append(OrderId);
      __sb.Append(", ThirdOrderNum: ");
      __sb.Append(ThirdOrderNum);
      __sb.Append(", OrgId: ");
      __sb.Append(OrgId);
      if (Ticket_service_code != null && __isset.ticket_service_code) {
        __sb.Append(", Ticket_service_code: ");
        __sb.Append(Ticket_service_code);
      }
      __sb.Append(", OpeUserId: ");
      __sb.Append(OpeUserId);
      __sb.Append(", OpeTime: ");
      __sb.Append(OpeTime);
      __sb.Append(", SlOrder: ");
      __sb.Append(SlOrder== null ? "<null>" : SlOrder.ToString());
      __sb.Append(")");
      return __sb.ToString();
    }

  }

}
