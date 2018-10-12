using Common;
using System;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.ComponentModel;
using System.Diagnostics;
using System.IO;
using System.Linq;
using System.Runtime.CompilerServices;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Markup;
using System.Windows.Media;
using System.Windows.Threading;

namespace SelfHelpSystem
{
	partial class VideoPage : Page, IComponentConnector
    {
        private delegate void SetPlayHandler();


        private List<string> videoList = new List<string>();

        private int currentVideoIndex = 0;

        private DispatcherTimer dtimer = null;

        private MainWindow parent;

        private TimeSpan currentPlayTime;

        private MediaClock clock;

        private bool pause = false;

        //internal MediaElement mediaElement;

        private string str = "";
        private Uri re = null;

        public VideoPage(MainWindow parentWindow)
        {
            this.parent = parentWindow;
            this.InitializeComponent();
            str = AppDomain.CurrentDomain.BaseDirectory + "video" + "\\新乡南太行旅游宣传片.mp4";
            re = new Uri(str, UriKind.Relative);
            Uri resourceLocator = re;
            mediaElement.Source = re;
            button_Click(null, null);

        }
        private void Page_Loaded(object sender, RoutedEventArgs e)
        {
        }
        private void Page_Unloaded(object sender, RoutedEventArgs e)
        {
            this.mediaElement.Stop();
            this.mediaElement.Source = null;
        }

        //播放按钮
        private void button_Click(object sender, RoutedEventArgs e)
        {
            if (button.Content.ToString() == "播放")
            {
                button.Content = "暂停";
                mediaElement.Play();
            }
            else
            {
                button.Content = "播放";
                mediaElement.Pause();
            }
        }

        DispatcherTimer timer = null;
        private void mediaElement_MediaOpened(object sender, RoutedEventArgs e)
        {
            //sliderPosition.Maximum = mediaElement.NaturalDuration.TimeSpan.TotalSeconds;
            ////媒体文件打开成功
            //timer = new DispatcherTimer();
            //timer.Interval = TimeSpan.FromSeconds(1);
            //timer.Tick += new EventHandler(timer_tick);
            //timer.Start();
        }
        private void timer_tick(object sender, EventArgs e)
        {
            //  sliderPosition.Value = mediaElement.Position.TotalSeconds;
        }
        //控制视频的位置
        private void sliderPosition_ValueChanged(object sender, RoutedPropertyChangedEventArgs<double> e)
        {
            ////mediaElement.Stop();
            //mediaElement.Position = TimeSpan.FromSeconds(sliderPosition.Value);
            ////mediaElement.Play();
        }

        public void Resume()
        {
            bool flag = this.mediaElement != null;
            if (flag)
            {
                //this.clock.Controller.Resume();
                this.pause = false;
              //  this.dtimer.Start();
                str = AppDomain.CurrentDomain.BaseDirectory + "video" + "\\新乡南太行旅游宣传片.mp4";
                re = new Uri(str, UriKind.Relative);
                Uri resourceLocator = re;
                mediaElement.Source = re;
                this.mediaElement.Play();
            }
        }
        public void Pause()
        {
            bool flag = this.mediaElement != null && this.mediaElement.HasVideo;
            if (flag)
            {
                //this.dtimer.Stop();
                this.pause = true;
                //this.clock.Controller.Pause();
                this.mediaElement.Pause();
            }
        }
        public void Close()
        {
          //  this.dtimer.Stop();
            this.mediaElement.Stop();
            this.mediaElement.Source = null;
            this.mediaElement = null;
        }
        //原来的
        #region
        /*
        private delegate void SetPlayHandler();


		private List<string> videoList = new List<string>();

		private int currentVideoIndex = 0;

		private DispatcherTimer dtimer = null;

		private MainWindow parent;

		private TimeSpan currentPlayTime;

		private MediaClock clock;

		private bool pause = false;

		//internal MediaElement mediaElement;

		private bool _contentLoaded;

		public VideoPage(MainWindow parentWindow)
		{
			this.parent = parentWindow;
			this.InitializeComponent();
            this.mediaElement.LoadedBehavior = MediaState.Manual;
            this.mediaElement.UnloadedBehavior = MediaState.Manual;
			this.InitVideoPlayer();
			bool flag = this.dtimer == null;
			if (flag)
			{
				this.dtimer = new DispatcherTimer();
				this.dtimer.Interval = TimeSpan.FromSeconds(1.0);
				this.dtimer.Tick += new EventHandler(this.Dtimer_Tick);
			}
			this.dtimer.Start();
		}

		private void Page_Loaded(object sender, RoutedEventArgs e)
		{
		}

		private void Dtimer_Tick(object sender, EventArgs e)
		{
            bool flag = this.currentVideoIndex == -1;
            if (flag)
            {
                this.InitVideoPlayer();
            }
            else
            {
                bool flag2 = !this.mediaElement.HasVideo && !this.pause;
                if (flag2)
                {
                    this.Player();
                }
                bool flag3 = this.mediaElement.NaturalDuration.HasTimeSpan && this.mediaElement.NaturalDuration.TimeSpan.TotalSeconds == this.mediaElement.Position.TotalSeconds;
                if (flag3)
                {
                    this.Player();
                }
            }
		}

		private void InitVideoPlayer()
		{
			this.InitVideoList();
			bool flag = this.videoList == null || this.videoList.Count == 0;
			if (flag)
			{
				this.videoList = new List<string>();
				this.currentVideoIndex = -1;
			}
			else
			{
				this.currentVideoIndex = 0;
			}
		}

		private void Player()
		{
			base.Dispatcher.BeginInvoke(new VideoPage.SetPlayHandler(this.PlayVideo), new object[0]);
		}

		private void PlayVideo()
		{
			bool flag = this.currentVideoIndex >= this.videoList.Count;
			if (flag)
			{
				this.currentVideoIndex = 0;
			}
			this.pause = false;
			bool flag2 = this.clock != null;
			if (flag2)
			{
				this.clock.Controller.Stop();
			}
            this.mediaElement.Stop();
            this.mediaElement.Source = null;
			List<string> arg_7B_0 = this.videoList;
			int num = this.currentVideoIndex;
			this.currentVideoIndex = num + 1;
			MediaTimeline mediaTimeline = new MediaTimeline(new Uri(arg_7B_0[num], UriKind.RelativeOrAbsolute));
			this.clock = mediaTimeline.CreateClock();
            this.mediaElement.Clock = this.clock;
			this.clock.Controller.Begin();
		}

		public void Pause()
		{
            bool flag = this.mediaElement != null && this.mediaElement.HasVideo;
            if (flag)
            {
                this.dtimer.Stop();
                this.pause = true;
                this.clock.Controller.Pause();
            }
		}

		public void Resume()
		{
            bool flag = this.mediaElement != null;
            if (flag)
            {
                //this.clock.Controller.Resume();
                this.pause = false;
                this.dtimer.Start();
            }
		}

		private void InitVideoList()
		{
			string configValue = ConfigHelper.GetConfigValue("player", "videotype");
			bool flag = configValue == "local";
			if (flag)
			{
				string path = string.Empty;
				string configValue2 = ConfigHelper.GetConfigValue("player", "videopath");
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
                    path = text + "\\新乡南太行旅游宣传片.mp4";
				}
                //IEnumerable<string> arg_BC_0 = Directory.GetFiles(path, "*.*", SearchOption.AllDirectories);
                //Func<string, bool> arg_BC_1;
                ////if ((arg_BC_1 = VideoPage.<>c.<>9__16_0) == null)
                ////{
                ////    arg_BC_1 = (VideoPage.<>c.<>9__16_0 = new Func<string, bool>(VideoPage.<>c.<>9.<InitVideoList>b__16_0));
                ////}
                //this.videoList = arg_BC_0.Where(arg_BC_1).ToList<string>();
			}
		}

		private void Page_Unloaded(object sender, RoutedEventArgs e)
		{
            this.mediaElement.Stop();
            this.mediaElement.Source = null;
		}

		public void Close()
		{
            this.dtimer.Stop();
            this.mediaElement.Stop();
            this.mediaElement.Source = null;
            this.mediaElement = null;
		}
        */
#endregion
        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), DebuggerNonUserCode]
        //public void InitializeComponent()
        //{
        //    bool contentLoaded = this._contentLoaded;
        //    if (!contentLoaded)
        //    {
        //        this._contentLoaded = true;
        //        Uri resourceLocator = new Uri("/SelfHelpSystem;component/videopage/videopage.xaml", UriKind.Relative);
        //        Application.LoadComponent(this, resourceLocator);
        //    }
        //}

        //[GeneratedCode("PresentationBuildTasks", "4.0.0.0"), EditorBrowsable(EditorBrowsableState.Never), DebuggerNonUserCode]
        //void IComponentConnector.Connect(int connectionId, object target)
        //{
        //    if (connectionId != 1)
        //    {
        //        if (connectionId != 2)
        //        {
        //            this._contentLoaded = true;
        //        }
        //        else
        //        {
        //            this.mediaElement = (MediaElement)target;
        //        }
        //    }
        //    else
        //    {
        //        ((VideoPage)target).Loaded += new RoutedEventHandler(this.Page_Loaded);
        //        ((VideoPage)target).Unloaded += new RoutedEventHandler(this.Page_Unloaded);
        //    }
        //}
	}
}
