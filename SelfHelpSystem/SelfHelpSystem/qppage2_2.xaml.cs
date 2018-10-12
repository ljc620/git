using Common;
using System;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.Linq;
using System.Speech.Synthesis;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;

namespace SelfHelpSystem
{
    public partial class QPPage2_2 : Page, IComponentConnector
	{
		private MainWindow parent;

		private SpeechSynthesizer ss = new SpeechSynthesizer();

		public QPPage2_2(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
		}

		private void btn_SRXX_Click(object sender, RoutedEventArgs e)
		{
			Button button = sender as Button;
			bool flag = button.Content.ToString() == "";
			if (!flag)
			{
				bool flag2 = button.Content.ToString() == "清空";
				if (flag2)
				{
					this.inputValue.Text = "";
				}
				else
				{
					bool flag3 = button.Content.ToString() == "取消";
					if (flag3)
					{
						this.parent.Operation("qp1");
					}
					else
					{
						bool flag4 = button.Content.ToString() == "更正";
						if (flag4)
						{
							bool flag5 = this.inputValue.Text.Length > 0;
							if (flag5)
							{
								this.inputValue.Text = this.inputValue.Text.Remove(this.inputValue.Text.Length - 1);
							}
						}
						else
						{
							bool flag6 = button.Content.ToString() == "确定";
							if (flag6)
							{
                                string configValue = ConfigHelper.GetConfigValue("getticket", "g" + this.parent.SelfHelpInfo.GetTicketType, "length");
                                List<string> list = configValue.Split(new string[]
                                {
                                    ","
                                }, StringSplitOptions.RemoveEmptyEntries).ToList<string>();
                                bool flag7 = !list.Exists((string x) => x == this.inputValue.Text.Length.ToString());
                                if (flag7)
                                {
                                    this.ss.SpeakAsyncCancelAll();
                                  //  this.ss.SelectVoiceByHints(VoiceGender.Female);
                                 //   this.ss.SpeakAsync("输入的信息有误，请重新输入！");
                                }
                                else
                                {
                                    bool flag8 = this.CheckInfo(this.inputValue.Text);
                                    if (flag8)
                                    {
                                        this.parent.Operation("qp3");
                                    }
                                    else
                                    {
                                        this.parent.WarmOperation("未找到相应的订单，请核对后重新输入！", "qp2");
                                    }
                                }
							}
							else
							{
								bool flag9 = this.inputValue.Text.Length < 17 && button.Content.ToString() == "X";
								if (!flag9)
								{
									bool flag10 = this.inputValue.Text.Length == 18;
									if (!flag10)
									{
										bool flag11 = this.inputValue.Text.Length + button.Content.ToString().Length > 18;
										if (!flag11)
										{
											this.inputValue.Text = this.inputValue.Text + button.Content.ToString();
										}
									}
								}
							}
						}
					}
				}
			}
		}

		private bool CheckInfo(string inputInfo)
		{
			return true;
		}

		private void btn_SY_Click(object sender, RoutedEventArgs e)
		{
			this.parent.EndOperation();
		}

		private void btn_FH_Click(object sender, RoutedEventArgs e)
		{
			this.parent.Operation("qp1");
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
			//this.ss.SelectVoiceByHints(VoiceGender.Female);
		//	this.ss.SpeakAsync("请输入手机号、身份证号或其他有效编码,按确认键确认");
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
			this.ss.SpeakAsyncCancelAll();
		}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/qpoperationpage/qppage2_2.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    switch (connectionId)
        //    {
        //    case 1:
        //        ((QPPage2_2)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((QPPage2_2)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //        break;
        //    case 2:
        //        this.dockfull = (StackPanel)target;
        //        break;
        //    case 3:
        //        this.inputValue = (TextBlock)target;
        //        break;
        //    case 4:
        //        this.btn_1 = (Button)target;
        //        this.btn_1.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 5:
        //        this.btn_2 = (Button)target;
        //        this.btn_2.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 6:
        //        this.btn_3 = (Button)target;
        //        this.btn_3.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 7:
        //        this.btn_C = (Button)target;
        //        this.btn_C.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 8:
        //        this.btn_4 = (Button)target;
        //        this.btn_4.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 9:
        //        this.btn_5 = (Button)target;
        //        this.btn_5.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 10:
        //        this.btn_6 = (Button)target;
        //        this.btn_6.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 11:
        //        this.btn_ = (Button)target;
        //        this.btn_.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 12:
        //        this.btn_7 = (Button)target;
        //        this.btn_7.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 13:
        //        this.btn_8 = (Button)target;
        //        this.btn_8.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 14:
        //        this.btn_9 = (Button)target;
        //        this.btn_9.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 15:
        //        this.btn_D = (Button)target;
        //        this.btn_D.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 16:
        //        this.btn_X = (Button)target;
        //        this.btn_X.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 17:
        //        this.btn_0 = (Button)target;
        //        this.btn_0.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 18:
        //        this.btn__ = (Button)target;
        //        this.btn__.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 19:
        //        this.btn_O = (Button)target;
        //        this.btn_O.Click += new RoutedEventHandler(this.btn_SRXX_Click);
        //        break;
        //    case 20:
        //        this.btn_SY = (Button)target;
        //        this.btn_SY.Click += new RoutedEventHandler(this.btn_SY_Click);
        //        break;
        //    case 21:
        //        this.btn_FH = (Button)target;
        //        this.btn_FH.Click += new RoutedEventHandler(this.btn_FH_Click);
        //        break;
        //    default:
        //        this._contentLoaded = true;
        //        break;
        //    }
        //}
	}
}
