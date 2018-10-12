using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using tbims.rpc.entity;
using log4net;

namespace SelfHelpSystem.Tool
{
    /// <summary>
    /// 获取当前票种的票据信息
    /// </summary>
     public  class T_GetCurTicketInfo
    {
         //日志
         private static ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");

         /// <summary>
         /// 获取指定票种的票据信息
         /// </summary>
         /// <param name="tickettypeid">票种</param>
         /// <param name="listtype">票种List</param>
         /// <returns></returns>
         public static SYS_TICKET_TYPE GetCurTicketTypeIDInfo(string tickettypeid,List<SYS_TICKET_TYPE> listtype)
         {             
             SYS_TICKET_TYPE systickettype = new SYS_TICKET_TYPE();
             try
             {
                 for (int i = 0; i < listtype.Count - 1; i++)
                 {
                     if (listtype[i].TeamFlag == "N")//只售非团体票
                     {
                         if (listtype[i].TicketTypeId == tickettypeid)
                         {
                             systickettype = listtype[i];
                         }
                     }
                 } 
             }
             catch (Exception ex)
             {
                 _log.Error(typeof(T_GetCurTicketInfo), ex);
             }
             return systickettype;
         }
    }
}
