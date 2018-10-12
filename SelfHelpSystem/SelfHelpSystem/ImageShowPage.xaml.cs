using Common;
using System;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Reflection;
using System.Runtime.CompilerServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Input;
using System.Windows.Markup;
using System.Windows.Threading;
using Transitionals;
using Transitionals.Controls;
using Transitionals.Transitions;
using log4net;
using System.Windows.Media.Imaging;

namespace SelfHelpSystem
{
    public partial class ImageShowPage : Page, IComponentConnector
	{
		private delegate void SetPrintImageHandler();

		private delegate void SetControlImageHandler(string filepath);

        private ILog _log = LogManager.GetLogger("SelfHelpSystem.Client");

		private List<BitmapImage> imageList = new List<BitmapImage>();

		private int currentImageIndex = 0;

		private DateTime currentImageTime = DateTime.MinValue;

		private int showtime = 10;

		private UserControl1 w1 = new UserControl1();

		private UserControl2 w2 = new UserControl2();

		//private ObservableCollection<Type> transitionTypes = new ObservableCollection<Type>();

		public DispatcherTimer imageThread = null;

		private MainWindow parent;

		public ImageShowPage(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
            //this.LoadTransitions(Assembly.GetAssembly(typeof(Transition)));
            this.InitImage();
			bool flag = this.imageThread == null;
			if (flag)
			{
				this.imageThread = new DispatcherTimer();
				this.imageThread.Interval = TimeSpan.FromSeconds(1.0);
				this.imageThread.Tick += new EventHandler(this.Dtimer_Tick);
			}
			this.imageThread.Start();
		}

		private void Dtimer_Tick(object sender, EventArgs e)
		{
			bool flag2 = DateTime.Now.Subtract(this.currentImageTime).TotalSeconds > (double)this.showtime;
			if (flag2)
			{
				this.currentImageTime = DateTime.Now;
				this.SetImage();
			}
		}

		private void InitImage()
		{
			this.InitImageConfig();
			bool flag = this.imageList == null || this.imageList.Count == 0;
			if (flag)
			{
				this.imageList = new List<BitmapImage>();
				this.currentImageIndex = -1;
			}
			else
			{
				this.currentImageIndex = 0;
			}
		}

		private void InitImageConfig()
		{
            try
            {
                string configValue = ConfigHelper.GetConfigValue("image", "showtime");
                int num;
                bool flag = configValue == "" || !int.TryParse(configValue, out num);
                if (flag)
                {
                    this.showtime = 10;
                }
                else
                {
                    this.showtime = int.Parse(configValue);
                }
                string path = string.Empty;
                string configValue2 = ConfigHelper.GetConfigValue("image", "imagepath");
                bool flag2 = configValue2 == "";
                if (flag2)
                {
                    path = AppDomain.CurrentDomain.BaseDirectory;
                }
                else
                {
                    string text = AppDomain.CurrentDomain.BaseDirectory + configValue2;//+ "\\" 
                    bool flag3 = !Directory.Exists(text);
                    if (flag3)
                    {
                        Directory.CreateDirectory(text);
                    }
                    path = text;
                    if (System.IO.Directory.Exists(path))
                    {
                        foreach (string d in System.IO.Directory.GetFileSystemEntries(path))
                        {
                            if (System.IO.File.Exists(d))
                            {
                                if ((System.IO.Path.GetExtension(d).ToUpper() == ".JPG") || (System.IO.Path.GetExtension(d).ToUpper() == ".PNG"))
                                {
                                    this.imageList.Add(GetImage(d));
                                }
                            }
                        }
                    }
                }
            }
            catch(Exception ex)
            {
                _log.Error("加载动画图片时发生异常!", ex);
            }
        }

		private void SetImage()
		{
			if (this.currentImageIndex >= this.imageList.Count)
			{
				this.currentImageIndex = 0;
			}
			if (this.currentImageIndex % 2 == 0)
			{
				this.SetContent1(this.imageList[this.currentImageIndex]);
			}
			else
			{
				this.SetContent2(this.imageList[this.currentImageIndex]);
			}
			this.currentImageIndex++;
		}

		private void SetContent1(BitmapImage image)
		{
            try
            {
                //this.selTrans();
                this.w1.SetBackgroungImage(image);
                this.mainp.Content = this.w1;
            }
            catch (Exception ex)
            {
                _log.Error("图片加载超时!", ex);
            }
		}

		private void SetContent2(BitmapImage image)
		{
            try
            {
                //this.selTrans();
                this.w2.SetBackgroungImage(image);
                this.mainp.Content = this.w2;
            }
            catch (Exception ex)
            {
                _log.Error("图片加载超时!", ex);
            }
        }

  //      private void LoadTransitions(Assembly assembly)
		//{
		//	Type[] types = assembly.GetTypes();
		//	for (int i = 0; i < types.Length; i++)
		//	{
		//		Type type = types[i];
		//		bool flag = this.transitionTypes.Contains(type);
		//		if (!flag)
		//		{
		//			bool flag2 = typeof(Transition).IsAssignableFrom(type) && !type.IsAbstract;
		//			if (flag2)
		//			{
		//				this.transitionTypes.Add(type);
		//			}
		//		}
		//	}
		//}

		private void tmp()
		{
			StarTransition starTransition = new StarTransition();
			RotateTransition rotateTransition = new RotateTransition();
			VerticalWipeTransition verticalWipeTransition = new VerticalWipeTransition();
			PageTransition pageTransition = new PageTransition();
			RollTransition rollTransition = new RollTransition();
			DiamondsTransition diamondsTransition = new DiamondsTransition();
			VerticalBlindsTransition verticalBlindsTransition = new VerticalBlindsTransition();
			HorizontalWipeTransition horizontalWipeTransition = new HorizontalWipeTransition();
			FadeAndBlurTransition fadeAndBlurTransition = new FadeAndBlurTransition();
			ExplosionTransition explosionTransition = new ExplosionTransition();
			CheckerboardTransition checkerboardTransition = new CheckerboardTransition();
			TranslateTransition translateTransition = new TranslateTransition();
			RotateWipeTransition rotateWipeTransition = new RotateWipeTransition();
			MeltTransition meltTransition = new MeltTransition();
			DiagonalWipeTransition diagonalWipeTransition = new DiagonalWipeTransition();
			FlipTransition flipTransition = new FlipTransition();
			DotsTransition dotsTransition = new DotsTransition();
			FadeAndGrowTransition fadeAndGrowTransition = new FadeAndGrowTransition();
			DoubleRotateWipeTransition doubleRotateWipeTransition = new DoubleRotateWipeTransition();
			DoorTransition doorTransition = new DoorTransition();
			HorizontalBlindsTransition horizontalBlindsTransition = new HorizontalBlindsTransition();
			FadeTransition fadeTransition = new FadeTransition();
		}

		//private void selTrans()
		//{
		//	int index = new Random().Next(0, this.transitionTypes.Count - 1);
		//	Type type = this.transitionTypes[index];
		//	Transition transition = (Transition)Activator.CreateInstance(type);
		//	this.mainp.Transition = transition;
		//}

		private void WrapPanel_MouseDown(object sender, MouseButtonEventArgs e)
		{
            if (this.currentImageIndex > -1)
            {
                this.SetImage();
            }
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
            for(int i=0;i<imageList.Count;i++)
            {
                imageList[i].UriSource = null;
            }
            imageList = null;
		}

        public static BitmapImage GetImage(string imagePath)
        {
            BitmapImage bitmap = new BitmapImage();

            if (System.IO.File.Exists(imagePath))
            {
                using (System.IO.Stream ms = new System.IO.MemoryStream(System.IO.File.ReadAllBytes(imagePath)))
                {
                    bitmap.BeginInit();
                    bitmap.CacheOption = BitmapCacheOption.OnLoad;
                    bitmap.StreamSource = ms;
                    bitmap.EndInit();
                    bitmap.Freeze();
                }
            }

            return bitmap;
        }

    }
}
