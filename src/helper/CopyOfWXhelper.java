package helper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import utils.WebUtil;
public class CopyOfWXhelper {
	
	private static final String KEY_INTRODUCE = "功能介绍";
	private static final String KEY_ATT = "认证";
	private static final String KEY_NEWS = "最近文章";
	
	private static final String URL_REPLACE_TWO = "%3D&&uin=&key=&pass_ticket=&wxtoken=&devicetype=&clientversion=0&x5=0";
	private static final String URL_REPLACE_ONE = "mp/getcomment";
	private static final String URL_HEAD = "http://mp.weixin.qq.com/";
	private static final String URL_SEARCH = "http://weixin.sogou.com/weixin?";
	private static final String PARAM_SEARCH = "type=1&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=11458&sst0=1461036240572&lkt=0%2C0%2C0";
	
	public static JSONObject getSearchList(String account) throws Exception {
		WXEntity entity = getUrlbyAccount(account);
		JSONObject json = new JSONObject(entity.toString());
//		JSONArray array =  getNewsByUrl(entity.getUrl());
		JSONArray array =  getNewsByUrl2(entity.getUrl());
		json.put("news", array);
		return json;
	}
	
	/**
	 * 根据文章链接得到获取文章点赞，阅读数等信息的拼接
	 * @param url 单独文章的链接
	 * @return JSON格式的字符串
	 */
	public static String replaceUrl(String url) {
		String[] tx = url.split("\"content_url\":");
		String text = URL_HEAD + tx[1].substring(3, tx[1].indexOf(","));
		text = text.replaceFirst("s", URL_REPLACE_ONE);
		text = text.substring(0, text.length() - 1) + URL_REPLACE_TWO;
		return text;
	}
	
	private static List<SoGouWX> getSouWX(String text) throws JsonSyntaxException, Exception {
		String s = new String(text);
		SoGouWX wx = getSingleSouWx(text);
		List<SoGouWX> list = new ArrayList<SoGouWX>();
		if(wx != null) {
			
			String time = wx.getTime();
			wx.setType(SoGouWX.TYPE_TOP);
			list.add(wx);
			if(text.indexOf("multi_app_msg_item_list") > -1) {
				String text_multi = s.split("multi_app_msg_item_list")[1];
				text_multi = text_multi.substring(text_multi.indexOf("["), text_multi.lastIndexOf("]"));
				String[] strs = text_multi.split("}");
				boolean isfirst = true;
				for(String str : strs) {
					SoGouWX mWx = getSingleSouWx(str);
					if(mWx != null) {
						mWx.setTime(time);
						if(isfirst) {
							mWx.setType(SoGouWX.TYPE_SECOND);
						} else {
							mWx.setType(SoGouWX.TYPE_THREE);
						}
						list.add(mWx);
						isfirst = false;
					}
				}
			}
			return list;
		}
		return null;
	}
	
	private static SoGouWX getSingleSouWx(String text) throws Exception {
		String s = new String(text);
		//如果是头条资讯
		if(s.indexOf("app_msg_ext_info") > -1) {
			int p_time = s.indexOf("\"datetime\":");
			int p_title = s.indexOf("\"title\":");
			int p_subtitle = s.indexOf(",\"digest\":");
			String time = s.substring(p_time + "\"datetime\":".length(), s.indexOf(",", p_time));
			String title = s.substring(p_title + "\"title\":".length(), p_subtitle);
			s = s.substring(p_subtitle);
			p_subtitle = s.indexOf(",\"digest\":");
			int p_end = s.indexOf(",\"content\":");
			String subtitle = s.substring(p_subtitle + ",\"digest\":".length(), p_end);
			//拼接完整的文章链接
			String url_s = replaceUrl(text);
			SoGouWX wx = new Gson().fromJson(WebUtil.sendGET(url_s), SoGouWX.class);
			wx.setTitle(title);
			wx.setTime(time);
			wx.setSubtitle(subtitle);
			return wx;
		} else {
			int p_title = s.indexOf("\"title\":");
			if(p_title > -1)  {
				int p_subtitle = s.indexOf(",\"digest\":");
				String title = s.substring(p_title + "\"title\":".length(), p_subtitle);
				s = s.substring(p_subtitle);
				p_subtitle = s.indexOf(",\"digest\":");
				int p_end = s.indexOf(",\"content\":");
				String subtitle = s.substring(p_subtitle + ",\"digest\":".length(), p_end);
				//拼接完整的文章链接
				String url_s = replaceUrl(text);
				SoGouWX wx = new Gson().fromJson(WebUtil.sendGET(url_s), SoGouWX.class);
				wx.setTitle(title);
				wx.setSubtitle(subtitle);
				return wx;
			}
		}
		return null;
	}
	
	private static WXEntity getUrlbyAccount(String account) throws IOException {
		//调用搜狗搜索相应的微信号
		String mainurl = URL_SEARCH + PARAM_SEARCH + "&query=" + account;
		Document doc = Jsoup.connect(mainurl)
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(5000)
					.get();
		Elements eles = doc.getElementsByClass("weixin-public");
		Element ele = eles.get(0);
		WXEntity entity = new WXEntity();
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
	
	private static JSONArray getNewsByUrl(String url) throws JsonSyntaxException, Exception {
		//获取第一条微信公众号的主页
		Document doc2 = Jsoup.connect(url)
				.userAgent("Mozilla")
				.cookie("auth", "token")
				.timeout(5000)
				.get();
		String result = doc2.toString();
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
//		JSONObject json = new JSONObject(text_json);
		String[] ss = text_json.split("comm_msg_info");
		JSONArray array = new JSONArray();
		for(String s : ss) {
			List<SoGouWX> infos = getSouWX(s);
			if(infos != null) {
				for(SoGouWX info : infos) {
					array.put(new JSONObject(info.toString()));
				}
			}
		}
		return array;
	}
	
	private static JSONArray getNewsByUrl2(String url) throws Exception {
		//获取第一条微信公众号的主页
		Document doc2 = Jsoup.connect(url)
				.userAgent("Mozilla")
				.cookie("auth", "token")
				.timeout(5000)
				.get();
		String result = doc2.toString();
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
		JSONObject json = new JSONObject(text_json);
		JSONArray array = json.optJSONArray("list");
		if(array != null) {
			JSONArray dataArray = new JSONArray();
			for(int i = 0; i < array.length(); i++) {
				WxNews wx = new Gson().fromJson(array.getJSONObject(i).toString(), WxNews.class);
				String time = wx.getComm_msg_info().getDatetime() + "";
				String title = wx.getApp_msg_ext_info().getTitle();
				String subtitle = wx.getApp_msg_ext_info().getDigest();
				String contenturl = wx.getApp_msg_ext_info().getContent_url();
				String jsontext = WebUtil.sendGET(getReadUrl(contenturl));
				SoGouWX swx = new Gson().fromJson(jsontext, SoGouWX.class);
				swx.setTime(time);
				swx.setTitle(title);
				swx.setSubtitle(subtitle);
				swx.setType(SoGouWX.TYPE_TOP);
				dataArray.put(new JSONObject(swx.toString()));
				if(wx.getApp_msg_ext_info().getIs_multi() == 1) {
					List<MultiAppMsgItemListEntity> list = wx.getApp_msg_ext_info().getMulti_app_msg_item_list();
					for(int j = 0; j < list.size(); j++) {
						MultiAppMsgItemListEntity entity = list.get(j);
						String itemurl = WebUtil.sendGET(getReadUrl(entity.getContent_url()));
						SoGouWX mwx = new Gson().fromJson(itemurl, SoGouWX.class);
						mwx.setTime(time);
						mwx.setTitle(entity.getTitle());
						mwx.setSubtitle(entity.getDigest());
						if(j == 0) {
							mwx.setType(SoGouWX.TYPE_SECOND);
						} else {
							mwx.setType(SoGouWX.TYPE_THREE);
						}
						dataArray.put(new JSONObject(mwx.toString()));
					}
				}
			}
			return dataArray;
		}
		return null;
	}
	
	private static String getReadUrl(String url) {
		return URL_HEAD + URL_REPLACE_ONE + url.substring(url.indexOf("?"), url.length() - 1) + URL_REPLACE_TWO;
	}
}
