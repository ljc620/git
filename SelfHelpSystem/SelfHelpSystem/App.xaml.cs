using System;
using System.Collections.Generic;
using System.Configuration;
using System.Data;
using System.Linq;
using System.Windows;
using Microsoft.Win32;
using log4net;
using System.Windows.Threading;


namespace SelfHelpSystem
{
    /// <summary>
    /// App.xaml 的交互逻辑
    /// </summary>
    public partial class App : Application
    {
        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");

        private void Application_Startup(object sender, StartupEventArgs e)
        {
            if (e.Args.Length > 0)
            {
                int delay = 15;
                try
                {
                    delay = int.Parse(e.Args[0]);
                }
                catch { }
                System.Threading.Thread.Sleep(delay * 1000);   // 延时启动程序，默认15秒
            }
            this.SysInit();
        }
        private void Application_DispatcherUnhandledException(object sender, DispatcherUnhandledExceptionEventArgs e)
        {
            if (e.Exception != null)
            {
                this._log.Error(this.GetType().ToString(), e.Exception);
            }
            e.Handled = true;
        }
        private void SysInit()
        {
            string delay = "15";
            bool autoStart = true;
            try
            {
                autoStart = bool.Parse(ConfigurationManager.AppSettings["AutoStart"]);
                delay = ConfigurationManager.AppSettings["AutoStartDelayTime"];
            }
            catch { }

            //设置开机自动启动，并延时10秒
            this.SetAutoRun(AppDomain.CurrentDomain.BaseDirectory + "SelfHelpSystem.exe " + delay, autoStart);

            try
            {
                System.Threading.Thread.CurrentThread.Name = "SelfHelpSystem";
                MainWindow mainWindow = new MainWindow();
                mainWindow.Show();
            }catch(Exception ex)
            {
                this._log.Error("APP未知异常：" + this.GetType().ToString(), ex);
            }
        }


        /// <summary>
        /// 设置开机自动启动
        /// </summary>
        /// <param name="filePath"></param>
        /// <param name="isAutoRun"></param>
        private void SetAutoRun(string filePath, bool isAutoRun)
        {
            RegistryKey registryKey = null;
            try
            {
                string name = "TBIMS.SelfServiceClient";
                registryKey = Registry.LocalMachine.OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", true);
                if (registryKey == null)
                {
                    registryKey = Registry.LocalMachine.CreateSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run");
                }
                if (isAutoRun)
                {
                    registryKey.SetValue(name, filePath, RegistryValueKind.String);
                    this._log.Info("自动开机设置成功");
                }
                else
                {
                    registryKey.DeleteValue(name, false);
                }
            }
            catch (Exception currentEx)
            {
                this._log.Error(this.GetType().ToString() + ": 自动开机设置失败", currentEx);
            }
            finally
            {
                if (registryKey != null)
                {
                    registryKey.Close();
                }
            }
        }
    }
}
