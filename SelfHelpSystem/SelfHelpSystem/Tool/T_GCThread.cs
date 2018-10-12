using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using System.Threading;

namespace SelfHelpSystem.Tool
{
    public class T_GCThread
    {
                //日志实例化
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");
        public bool IsStop = false;

        public void DoWork()
        {
            while (!IsStop)
            {
                try
                {
                    GC.Collect();
                    GC.WaitForPendingFinalizers();

                }
                catch (Exception ex)
                {
                    _log.Error(typeof(T_GCThread), ex);
                }
                Thread.Sleep(300000);//5分钟一次
            }
        }
    }
}
