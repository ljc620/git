using Common;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using SmartPay.common;
using SmartPay.Model;
using SmartPay.Service;
using System;
using System.Collections.Generic;
using System.Drawing;
using System.Runtime.InteropServices;
using System.Windows;
using System.Windows.Interop;
using System.Windows.Media.Imaging;

namespace SelfHelpSystem
{
	public class PayHelper
	{
		public static int CodeSize;

		private static string batchNo;

		public static bool IsLogin
		{
			get;
			set;
		}

		static PayHelper()
		{
			PayHelper.CodeSize = 260;
			PayHelper.batchNo = string.Empty;
			PayHelper.InitCofig();
		}

		public static void InitCofig()
		{
			Config.PathAddress = ConfigHelper.GetConfigValue("pay", "PathAddress");
			Config.XappKey = ConfigHelper.GetConfigValue("pay", "XappKey");
			Config.MerchId = ConfigHelper.GetConfigValue("pay", "MerchId");
			Config.IposSn = ConfigHelper.GetConfigValue("pay", "IposSn");
			Config.notifyUrl = ConfigHelper.GetConfigValue("pay", "notifyUrl");
			Config.payCode = ConfigHelper.GetConfigValue("pay", "payCode");
			Config.Operator = ConfigHelper.GetConfigValue("pay", "Operator");
			Config.Pwd = ConfigHelper.GetConfigValue("pay", "Pwd");
			Config.PrivateKeyPath = ConfigHelper.GetConfigValue("pay", "PrivateKeyPath");
			Config.PublicKeyPath = ConfigHelper.GetConfigValue("pay", "PublicKeyPath");
		}

		public static bool Login(out LoginResponseModel response)
		{
			response = null;
			PayHelper.IsLogin = false;
			bool result;
			try
			{
				LoginService loginService = new LoginService();
				response = null;
				string jsonString = loginService.DefContent();
				response = JsonHelper.JsonDeserialize<List<LoginResponseModel>>(jsonString)[0];
				bool flag = response.responseCode == "0";
				if (flag)
				{
					PayHelper.batchNo = response.batchNo;
					PayHelper.IsLogin = true;
					result = true;
				}
				else
				{
					result = false;
				}
			}
			catch (Exception var_4_5B)
			{
				result = false;
			}
			return result;
		}

		public static bool LogOut(out LogOutResponseModel response)
		{
			bool result;
			try
			{
				LogOutService logOutService = new LogOutService();
				response = null;
				string jsonString = logOutService.DefContent();
				response = JsonHelper.JsonDeserialize<List<LogOutResponseModel>>(jsonString)[0];
				result = true;
			}
			catch
			{
				response = null;
				result = false;
			}
			return result;
		}

		public static bool CsbWeixinPay(string odNo, decimal amount, out string refNo, out string txnId, out string response, out BitmapSource img)
		{
			img = null;
			refNo = string.Empty;
			txnId = string.Empty;
			response = string.Empty;
			bool result;
			try
			{
				CsbWeiXinpayService csbWeiXinpayService = new CsbWeiXinpayService();
				csbWeiXinpayService.transAmount = amount;
				csbWeiXinpayService.odNo = odNo;
				bool flag = false;
				JArray jArray;
				while (true)
				{
					string text = csbWeiXinpayService.DefContent();
					response = text;
					jArray = (JsonConvert.DeserializeObject(text) as JArray);
					bool flag2 = jArray == null;
					if (flag2)
					{
						break;
					}
					bool flag3 = jArray.First["responseCode"].ToString() != "0";
					if (!flag3)
					{
						goto IL_D2;
					}
					bool flag4 = jArray.First["responseCode"].ToString() == "21";
					if (!flag4)
					{
						goto IL_CA;
					}
					bool flag5 = !flag;
					if (!flag5)
					{
						goto IL_C9;
					}
					flag = true;
					LoginResponseModel loginResponseModel;
					bool flag6 = PayHelper.Login(out loginResponseModel);
					if (!flag6)
					{
						goto IL_C8;
					}
				}
				result = false;
				return result;
				IL_C8:
				IL_C9:
				IL_CA:
				result = false;
				return result;
				IL_D2:
				bool flag7 = jArray.First != null && jArray.First["data"] != null;
				if (flag7)
				{
					JArray jArray2 = JsonConvert.DeserializeObject("[" + jArray.First["data"].ToString() + "]") as JArray;
					bool flag8 = jArray2 == null;
					if (flag8)
					{
						result = false;
					}
					else
					{
						bool flag9 = jArray2.First["qrcodeResult"].ToString() != "SUCCESS";
						if (flag9)
						{
							result = false;
						}
						else
						{
							refNo = jArray2.First["refNo"].ToString();
							txnId = jArray.First["txnId"].ToString();
							img = PayHelper.GetQRCode(jArray2.First["qrcode"].ToString(), PayHelper.CodeSize, "weixin");
							result = true;
						}
					}
				}
				else
				{
					result = false;
				}
			}
			catch (Exception var_15_1C9)
			{
				result = false;
			}
			return result;
		}

		public static bool CsbZhiFuBaopay(string odNo, decimal amount, out string refNo, out string txnId, out string response, out BitmapSource img)
		{
			img = null;
			refNo = string.Empty;
			txnId = string.Empty;
			response = string.Empty;
			bool result;
			try
			{
				CsbZhiFuBaopayService csbZhiFuBaopayService = new CsbZhiFuBaopayService();
				csbZhiFuBaopayService.odNo = odNo;
				csbZhiFuBaopayService.transAmount = amount;
				bool flag = false;
				JArray jArray;
				while (true)
				{
					string text = csbZhiFuBaopayService.DefContent();
					response = text;
					jArray = (JsonConvert.DeserializeObject(text) as JArray);
					bool flag2 = jArray == null;
					if (flag2)
					{
						break;
					}
					bool flag3 = jArray.First["responseCode"].ToString() != "0";
					if (!flag3)
					{
						goto IL_D2;
					}
					bool flag4 = jArray.First["responseCode"].ToString() == "21";
					if (!flag4)
					{
						goto IL_CA;
					}
					bool flag5 = !flag;
					if (!flag5)
					{
						goto IL_C9;
					}
					flag = true;
					LoginResponseModel loginResponseModel;
					bool flag6 = PayHelper.Login(out loginResponseModel);
					if (!flag6)
					{
						goto IL_C8;
					}
				}
				result = false;
				return result;
				IL_C8:
				IL_C9:
				IL_CA:
				result = false;
				return result;
				IL_D2:
				bool flag7 = jArray.First != null && jArray.First["data"] != null;
				if (flag7)
				{
					JArray jArray2 = JsonConvert.DeserializeObject("[" + jArray.First["data"].ToString() + "]") as JArray;
					bool flag8 = jArray2 == null;
					if (flag8)
					{
						result = false;
					}
					else
					{
						bool flag9 = jArray2.First["qrcodeResult"].ToString() != "SUCCESS";
						if (flag9)
						{
							result = false;
						}
						else
						{
							refNo = jArray2.First["refNo"].ToString();
							txnId = jArray.First["txnId"].ToString();
							img = PayHelper.GetQRCode(jArray2.First["qrcode"].ToString(), PayHelper.CodeSize, "alipay");
							result = true;
						}
					}
				}
				else
				{
					result = false;
				}
			}
			catch (Exception var_15_1C9)
			{
				result = false;
			}
			return result;
		}

		public static bool Cancel(string refNo, string payType, decimal amount, out CancelResponseModel response)
		{
			bool result;
			try
			{
				string jsonString = new CancelService
				{
					refNo = refNo,
					payType = payType,
					transAmount = amount
				}.DefContent();
				response = null;
				response = JsonHelper.JsonDeserialize<List<CancelResponseModel>>(jsonString)[0];
				result = true;
			}
			catch
			{
				response = null;
				result = false;
			}
			return result;
		}

		public static bool Correct(string refNo, string payType, string oriTxnId, decimal amount, out CorrectResponseModel response)
		{
			bool result;
			try
			{
				string text = new CorrectService
				{
					payType = payType,
					refNo = refNo,
					oriTxnId = oriTxnId,
					transAmount = int.Parse(amount.ToString())
				}.DefContent();
				response = null;
				bool flag = text != "无法查询该订单" && text != "冲正失败！";
				if (flag)
				{
					result = false;
				}
				else
				{
					response = JsonHelper.JsonDeserialize<List<CorrectResponseModel>>(text)[0];
					result = true;
				}
			}
			catch
			{
				response = null;
				result = false;
			}
			return result;
		}

		public static bool PagingPayQueryaging(int pageNo, int pageSize, string paytype, out PagingPayQueryResponseModel response)
		{
			bool result;
			try
			{
				string jsonString = new PagingPayQueryagingService
				{
					pageNo = pageNo,
					PageSize = pageSize,
					payType = paytype
				}.DefContent();
				response = JsonHelper.JsonDeserialize<List<PagingPayQueryResponseModel>>(jsonString)[0];
				result = true;
			}
			catch
			{
				response = null;
				result = false;
			}
			return result;
		}

		public static bool PayQuery(string payType, string refNo, string txnId, out string signString)
		{
			signString = string.Empty;
			bool result;
			try
			{
				string text = new PayQueryService
				{
					paymentId = BaseService.GetPaymentId(payType),
					refNo = refNo,
					txnId = txnId
				}.DefContent();
				signString = text;
				JArray jArray = JsonConvert.DeserializeObject(text) as JArray;
				bool flag = jArray == null;
				if (flag)
				{
					result = false;
				}
				else
				{
					bool flag2 = jArray.First["responseCode"].ToString() != "0";
					if (flag2)
					{
						result = false;
					}
					else
					{
						bool flag3 = jArray.First != null && jArray.First["txnId"].ToString() == txnId && jArray.First["refNo"].ToString() == refNo;
						if (flag3)
						{
							bool flag4 = jArray.First["statusCode"].ToString() == "5";
							if (flag4)
							{
								result = false;
							}
							else
							{
								bool flag5 = jArray.First["statusCode"].ToString() == "0" && jArray.First["resultMsg"].ToString() == "Pay Success";
								if (flag5)
								{
									result = true;
								}
								else
								{
									result = false;
								}
							}
						}
						else
						{
							result = true;
						}
					}
				}
			}
			catch
			{
				result = false;
			}
			return result;
		}

		[DllImport("gdi32")]
		private static extern int DeleteObject(IntPtr o);

		public static BitmapSource GetQRCode(string content, int size, string paytype)
		{
			Bitmap bitmap = QRCodeHelper.Create(content, size);
			IntPtr hbitmap = bitmap.GetHbitmap();
			bool flag = paytype == "weixin";
			Bitmap bitmap2;
			if (flag)
			{
				bitmap2 = new Bitmap(AppDomain.CurrentDomain.BaseDirectory + "pic\\qrcode1.png");
			}
			else
			{
				bitmap2 = new Bitmap(AppDomain.CurrentDomain.BaseDirectory + "pic\\qrcode2.png");
			}
			IntPtr hbitmap2 = bitmap2.GetHbitmap();
			Bitmap bitmap3 = QRCodeHelper.MergeQrImg(bitmap, bitmap2, 0.23);
			IntPtr hbitmap3 = bitmap3.GetHbitmap();
			BitmapSource result = Imaging.CreateBitmapSourceFromHBitmap(hbitmap3, IntPtr.Zero, Int32Rect.Empty, BitmapSizeOptions.FromEmptyOptions());
			PayHelper.DeleteObject(hbitmap3);
			PayHelper.DeleteObject(hbitmap2);
			PayHelper.DeleteObject(hbitmap);
			bitmap3.Dispose();
			bitmap2.Dispose();
			bitmap.Dispose();
			return result;
		}
	}
}
