using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SelfHelpSystem.BLL
{
    public class B_CallBackEventArg
    {
        public byte[] status;
        public string code;
        public string message;
        public string TicketNumber;//票号        
        public string ReTicketNumber;//退票票号
    }
    public delegate void ProgressReport(int ProgressPercentage, object UserState, Exception ex);
}
