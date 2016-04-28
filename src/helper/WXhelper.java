package helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JLabel;

import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import data.SoGouWX;
import data.WXEntity;
import data.WxNews;
import data.WxNews.AppMsgExtInfoEntity.MultiAppMsgItemListEntity;
import utils.AccountErrorException;
import utils.FibdException;
import utils.SwebUtil;
import utils.WebUtil;
public class WXhelper {
	
	private static final String KEY_INTRODUCE = "功能介绍";
	private static final String KEY_ATT = "认证";
	private static final String KEY_NEWS = "最近文章";
	
	private static final String URL_GO = "http://weixin.sogou.com/antispider/thank.php";
	private static final String URL_IMG = "http://weixin.sogou.com/antispider/";
	private static final String URL_REPLACE_TWO = "%3D&&uin=&key=&pass_ticket=&wxtoken=&devicetype=&clientversion=0&x5=0";
	private static final String URL_REPLACE_ONE = "mp/getcomment";
	private static final String URL_HEAD = "http://mp.weixin.qq.com/";
	private static final String URL_SEARCH = "http://weixin.sogou.com/weixin?";
//	private static final String PARAM_SEARCH = "type=1&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=11458&sst0=1461036240572&lkt=0%2C0%2C0";
	private static final String PARAM_SEARCH = "type=1";

	private String avatar;
	private String file_id;
	private JLabel j_img;
	private JLabel j_result;
	
	private static SwebUtil mutil;
	
//	
//	public WXhelper(JLabel jlb_img, JLabel jlb_result) {
//		this.j_img = jlb_img;
//		this.j_result = jlb_result;
//	}
	
	public WXhelper() {
		// TODO Auto-generated constructor stub
	}
	
	public void soso(String code) throws Exception {
		Document doc = Jsoup.connect(URL_GO)
				.data("c", code)
				.data("v", "5")
				.data("r", "%2Fweixin%3Ftype%3D1%26query%3Dchihewanlquan%26ie%3Dutf8%26_sug_%3Dn%26_sug_type_%3D")
				.post();
		System.out.println("doc:" + doc.toString());
	}

	public JSONObject getSearchList(String account, List<HttpHost> list) throws IOException, FibdException, AccountErrorException {
		mutil = new SwebUtil(list);
		WXEntity entity = getUrlbyAccount(account, null);
		JSONObject json = null;
		try {
			json = new JSONObject(entity.toString());
			JSONArray array =  getNewsByUrl(entity.getUrl());
			json.put("avatar", avatar);
			json.put("news", array);
			json.put("sid", file_id);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return json;
	}
	
	/**
	 * 根据文章链接得到获取文章点赞，阅读数等信息的拼接
	 * @param url 单独文章的链接
	 * @return JSON 格式的字符串
	 */
	public static String replaceUrl(String url) {
		String[] tx = url.split("\"content_url\":");
		String text = URL_HEAD + tx[1].substring(3, tx[1].indexOf(","));
		text = text.replaceFirst("s", URL_REPLACE_ONE);
		text = text.substring(0, text.length() - 1) + URL_REPLACE_TWO;
		return text;
	}
	
	public static String getUrl(String account) {
		return URL_SEARCH + PARAM_SEARCH + "&query=" + account;
	}
	
	public static WXEntity getUrlbyAccount(String account, List<HttpHost> list) throws IOException, FibdException, AccountErrorException {
		
		mutil = new SwebUtil(list);
		//调用搜狗搜索相应的微信号
		String mainurl = URL_SEARCH + PARAM_SEARCH + "&query=" + account;
		Random rand = new Random();
		int x = rand.nextInt(10);
		System.out.print(" 获取主账号信息;");
		Document doc;
//		if(x % 3 == 0) {
//			String html = "";
//			try {
//				html = WebUtil.sendGET(mainurl);
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			doc = Jsoup.parse(html);
//		} else if(x % 3 == 1) {
//			String html = "";
//			try {
//				html = WebUtil.getHttpContent(mainurl);
//			} catch (Exception e1) {
//				
//				e1.printStackTrace();
//			}
//			doc = Jsoup.parse(html);
//		} else {
//			doc = Jsoup.connect(mainurl)
//					.userAgent("Mozilla")
//					.cookie("auth", "token")
//					.timeout(5000)
//					.get();
//		}
		doc = Jsoup.parse(mutil.doPortGet(mainurl));
			
		Elements eles = doc.getElementsByClass("weixin-public");
		if(eles.size() == 0) {
//			String img = URL_IMG + doc.getElementsByClass("s1").get(1).select("img[src]").attr("src").toString();
			throw new FibdException("图片地址", doc.text());
		}
		Element ele = eles.get(0);
		WXEntity entity = new WXEntity();
		boolean first = true;
		//获取二维码和头像
		for(Element e : ele.getElementsByClass("pos-ico").select("img[src]")) {
			if(first) {
				entity.setAvatar(e.attr("src"));
				first = false;
			} else {
				entity.setQrcode(e.attr("src"));
			}
		}
//		System.out.println("ele:" + ele.getElementsByTag("div"));
		if(ele.select("div[href]").size() == 0) {
			throw new AccountErrorException(account);
		}
		String url = ele.select("div[href]").get(0).attr("href");
		ele = ele.getElementsByClass("txt-box").get(0);
		String name = ele.getElementsByTag("h3").text();
		String wxaccount = ele.getElementsByTag("h4").select("label").text();
		eles = ele.getElementsByClass("s-p3");
		for(Element e : eles) {
			if(e.text().startsWith(KEY_ATT)) {
				entity.setIs_att(WXEntity.ATT_YES);
				entity.setInfo_att(e.text().substring(KEY_ATT.length() + 1));
			} else if(e.text().startsWith(KEY_INTRODUCE)) {
				entity.setIntroduce(e.text().substring(KEY_INTRODUCE.length() + 1));
			} else if(e.text().startsWith(KEY_NEWS)) {
				//最新资讯
				//System.out.println("news:" + e.text().substring(KEY_NEWS.length() + 1));
			} else {
				System.out.println("else:" + e.text());
			}
		}
		entity.setUrl(url);
		entity.setName(name);
		entity.setAccount(wxaccount);
		return entity;
	}
	
	private JSONArray getNewsByUrl(String url) throws IOException {
		Random rand = new Random();
		int x = rand.nextInt(10);
		Document doc2;
		String html = "";
		String result = "";
		System.out.print(" 开始获取新闻;");
//		if(x % 3 == 0) {
//			//获取第一条微信公众号的主页
//			doc2 = Jsoup.connect(url)
//					.userAgent("Mozilla")
//					.cookie("auth", "token")
//					.timeout(7000)
//					.get();
//			result = doc2.toString();
//		} else if(x % 3 == 1) {
//			try {
//				html = WebUtil.sendGET(url);
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			doc2 = Jsoup.parse(html);
//			result = html;
//		} else {
//			try {
//				html = WebUtil.getHttpContent(url);
//			} catch (Exception e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//			doc2 = Jsoup.parse(html);
//			result = html;
//		}
		
		doc2 = Jsoup.parse(mutil.doPortGet(url));
		
		result = doc2.toString();
		avatar = doc2.getElementsByClass("radius_avatar").get(0).getElementsByTag("img").get(0).attr("src");
		//截取字符串，提取文章列表的链接
		int p = result.indexOf("msgList");
		//数据清洗，去除多余的字符串
		String text_json = result.substring(p)
				.replaceAll("&quot;", "\"")
				.replaceAll("amp;", "")
				.replaceAll("&nbsp;", " ");
		int p_s = text_json.indexOf("{");
		int p_end = text_json.lastIndexOf("}");
		text_json = text_json.substring(p_s, p_end + 1);
		JSONObject json = null;
		try {
			json = new JSONObject(text_json);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		JSONArray array = json.optJSONArray("list");
		if(array != null) {
			JSONArray dataArray = new JSONArray();
			for(int i = 0; i < array.length(); i++) {
				WxNews wx;
				try {
					wx = new Gson().fromJson(array.getJSONObject(i).toString(), WxNews.class);
					String time = wx.getComm_msg_info().getDatetime() + "";
					String title = wx.getApp_msg_ext_info().getTitle();
					String subtitle = wx.getApp_msg_ext_info().getDigest();
					String contenturl = wx.getApp_msg_ext_info().getContent_url();
					String fileid = wx.getApp_msg_ext_info().getFileid();
					file_id = wx.getComm_msg_info().getId();
							
					String jsontext = "";
					try {
						jsontext = mutil.doPortGet(getReadUrl(contenturl));
						SoGouWX swx = new Gson().fromJson(jsontext, SoGouWX.class);
						swx.setTime(time);
						swx.setUrl(getHtmlUrl(contenturl));
						swx.setFileid(fileid);
						swx.setTitle(title);
						swx.setSubtitle(subtitle);
						swx.setType(SoGouWX.TYPE_TOP);
						dataArray.put(new JSONObject(swx.toString()));
					} catch (StringIndexOutOfBoundsException e1) {
						System.out.print("  单图文头条不存在;");
					}
					if(wx.getApp_msg_ext_info().getIs_multi() == 1) {
						List<MultiAppMsgItemListEntity> list = wx.getApp_msg_ext_info().getMulti_app_msg_item_list();
						for(int j = 0; j < list.size(); j++) {
							MultiAppMsgItemListEntity entity = list.get(j);
							String itemurl = "";
							try {
								itemurl = WebUtil.sendGET(getReadUrl(entity.getContent_url()));
							} catch (Exception e) {
								System.out.println("3-n条报错：\n" + entity.toString());
							}
							SoGouWX mwx = new Gson().fromJson(itemurl, SoGouWX.class);
							mwx.setTime(time);
							mwx.setUrl(getHtmlUrl(entity.getContent_url()));
							mwx.setTitle(entity.getTitle());
							mwx.setSubtitle(entity.getDigest());
							mwx.setFileid(entity.getFileid());
							if(j == 0) {
								mwx.setType(SoGouWX.TYPE_SECOND);
							} else if(j == 1){
								mwx.setType(SoGouWX.TYPE_THREE);
							} else {
								mwx.setType(SoGouWX.TYPE_OTHER);
							}
							dataArray.put(new JSONObject(mwx.toString()));
//							try {
//								Thread.sleep((long) (800 * (rand.nextDouble() + 0.1)));
//							} catch (InterruptedException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
						}
					}
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				try {
//					Thread.sleep((long) (700 * (rand.nextDouble() + 0.1)));
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
			}
			return dataArray;
		}
		return null;
	}
	
	private String getReadUrl (String url) throws StringIndexOutOfBoundsException{
		int postion = url.indexOf("?");
		return URL_HEAD + URL_REPLACE_ONE + url.substring(postion, url.length() - 1) + URL_REPLACE_TWO;
	}
	
	private String getHtmlUrl(String url) {
		return URL_HEAD + url.substring(url.indexOf("s"));
	}
}
