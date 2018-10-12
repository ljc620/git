using System;
using System.Collections.Generic;

namespace SelfHelpSystem
{
	public class TicketInfo
	{
		private static List<TicketInfo> dataList = new List<TicketInfo>();

		public string tickettype
		{
			get;
			set;
		}

		public string ticketcount
		{
			get;
			set;
		}

		public string ticketdate
		{
			get;
			set;
		}

		public static List<TicketInfo> GetInfo()
		{
			return TicketInfo.dataList;
		}

		public static void Add(TicketInfo data)
		{
			TicketInfo.dataList.Add(data);
		}

		public static void Add(List<TicketInfo> list)
		{
			TicketInfo.dataList.AddRange(list);
		}

		public static void Clear()
		{
			TicketInfo.dataList = new List<TicketInfo>();
		}
	}
}
