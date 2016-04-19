package helper;

import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import data.SoGouWX;
import utils.WebUtil;
public class WXhelper {
	
	private static final String URL_REPLACE_TWO = "%3D&&uin=&key=&pass_ticket=&wxtoken=&devicetype=&clientversion=0&x5=0";
	private static final String URL_REPLACE_ONE = "mp/getcomment";
	private static final String URL_HEAD = "http://mp.weixin.qq.com/";
	private static final String URL_SEARCH = "http://weixin.sogou.com/weixin?";
	private static final String PARAM_SEARCH = "type=1&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=11458&sst0=1461036240572&lkt=0%2C0%2C0";
	
	public static JSONArray getSearchList(String wxaccount) throws Exception {
		//调用搜狗搜索相应的微信号
		String url = URL_SEARCH + PARAM_SEARCH + "&query=" + wxaccount;
		Document doc = Jsoup.connect(url)
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(5000)
					.get();
		Elements eles = doc.getElementsByClass("weixin-public");
		String list_url = eles.get(0).select("div[href]").get(0).attr("href");
		//获取第一条微信公众号的主页
		Document doc2 = Jsoup.connect(list_url)
				.userAgent("Mozilla")
				.cookie("auth", "token")
				.timeout(5000)
				.get();
		String result = doc2.toString();
		//截取字符串，提取文章列表的链接
		int p = result.indexOf("msgList");
		//数据清洗，去除多余的字符串
		String text_json = result.substring(p).replaceAll("&quot;", "").replaceAll("amp;", "");
		String[] ss = text_json.split("comm_msg_info");
		JSONArray array = new JSONArray();
		for(String s : ss) {
			if(!s.startsWith("msgList") && !s.endsWith("</html>")) {
				SoGouWX info = getSouWX(s);
				if(info != null) {
					array.put(new JSONObject(info.toString()));
				}
			}
		}
		return array;
	}
	
	/**
	 * 根据文章链接得到获取文章点赞，阅读数等信息的拼接
	 * @param url 单独文章的链接
	 * @return 	JSON格式的字符串
	 */
	public static String replaceUrl(String url) {
		String[] tx = url.split("content_url:");
		String text = URL_HEAD + tx[1].substring(3, tx[1].indexOf(","));
//		System.out.println("url:" + text);
		text = text.replaceFirst("s", URL_REPLACE_ONE);
		text = text.substring(0, text.length() - 1) + URL_REPLACE_TWO;
		return text;
	}
	
	private static SoGouWX getSouWX(String text) throws JsonSyntaxException, Exception {
//		System.out.println("text:\n" + text);
		String s = new String(text);
		int p_title = s.indexOf("title:");
		if(p_title > -1) {
			int p_subtitle = s.indexOf(",digest:");
			String title = s.substring(p_title + "title:".length(), p_subtitle);
			s = s.substring(p_subtitle);
			p_subtitle = s.indexOf(",digest:");
			int p_end = s.indexOf(",content:");
			String subtitle = s.substring(p_subtitle + ",digest:".length(), p_end);
			//拼接完整的文章链接
			String url_s = replaceUrl(text);
			SoGouWX wx = new Gson().fromJson(WebUtil.sendGET(url_s), SoGouWX.class);
			wx.setTitle(title);
			wx.setSubtitle(subtitle);
			System.out.println(wx.toString());
			return wx;
		}
		return null;
	}
}
