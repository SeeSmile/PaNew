package helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.gson.Gson;

import data.SoGouWX;

import utils.WebUtil;

public class WXhelper {
	
	private static final String URL_REPLACE_TWO = "%3D&&uin=&key=&pass_ticket=&wxtoken=&devicetype=&clientversion=0&x5=0";
	
	private static final String URL_REPLACE_ONE = "mp/getcomment";
	
	private static final String URL_HEAD = "http://mp.weixin.qq.com";
	
	private static final String URL_SEARCH = "http://weixin.sogou.com/weixin?";
	private static final String PARAM_SEARCH = "type=1&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=11458&sst0=1461036240572&lkt=0%2C0%2C0";
	
	public static void getSearchList(String wxaccount) throws Exception {
		String url = URL_SEARCH + PARAM_SEARCH + "&query=" + wxaccount;
		Document doc = Jsoup.connect(url)
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(5000)
					.get();
		
		Elements eles = doc.getElementsByClass("weixin-public");
		String list_url = eles.get(0).select("div[href]").get(0).attr("href");
		System.out.println("url:\n" + list_url);
		Document doc2 = Jsoup.connect(list_url)
				.userAgent("Mozilla")
				.cookie("auth", "token")
				.timeout(5000)
				.get();
		
		String result = doc2.toString();
		int p = result.indexOf("msgList");
		String text_json = result.substring(p).replaceAll("&quot;", "").replaceAll("amp;", "");
		String[] ss = text_json.split("content_url");
		for(String s : ss) {
			if(s.startsWith(":")) {
				String url_s = URL_HEAD + s.substring(3, s.indexOf(","));
				url_s = replaceUrl(url_s);
				SoGouWX wx = new Gson().fromJson(WebUtil.sendGET(url_s), SoGouWX.class);
				System.out.println("ÔÄ¶ÁÊý:" + wx.getRead_num() + ", likeÊý:" + wx.getLike_num());
			}
		}
	}
	
	public static String replaceUrl(String url) {
		String text = "";
		text = url.replaceFirst("s", URL_REPLACE_ONE);
		text = text.substring(0, text.length() - 1) + URL_REPLACE_TWO;
		return text;
	}
}
