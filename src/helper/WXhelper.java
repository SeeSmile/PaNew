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
public class WXhelper {
	
	private static final String KEY_INTRODUCE = "���ܽ���";
	private static final String KEY_ATT = "��֤";
	private static final String KEY_NEWS = "�������";
	
	private static final String URL_REPLACE_TWO = "%3D&&uin=&key=&pass_ticket=&wxtoken=&devicetype=&clientversion=0&x5=0";
	private static final String URL_REPLACE_ONE = "mp/getcomment";
	private static final String URL_HEAD = "http://mp.weixin.qq.com/";
	private static final String URL_SEARCH = "http://weixin.sogou.com/weixin?";
	private static final String PARAM_SEARCH = "type=1&ie=utf8&_sug_=y&_sug_type_=&w=01019900&sut=11458&sst0=1461036240572&lkt=0%2C0%2C0";

	private static String avatar;
	private static String file_id;
	
	public static JSONObject getSearchList(String account) throws Exception {
		WXEntity entity = getUrlbyAccount(account);
		JSONObject json = new JSONObject(entity.toString());
		JSONArray array =  getNewsByUrl(entity.getUrl());
		json.put("avatar", avatar);
		json.put("news", array);
		json.put("fileid", file_id);
		return json;
	}
	
	/**
	 * �����������ӵõ���ȡ���µ��ޣ��Ķ�������Ϣ��ƴ��
	 * @param url �������µ�����
	 * @return JSON ��ʽ���ַ���
	 */
	public static String replaceUrl(String url) {
		String[] tx = url.split("\"content_url\":");
		String text = URL_HEAD + tx[1].substring(3, tx[1].indexOf(","));
		text = text.replaceFirst("s", URL_REPLACE_ONE);
		text = text.substring(0, text.length() - 1) + URL_REPLACE_TWO;
		return text;
	}
	
	private static WXEntity getUrlbyAccount(String account) throws IOException {
		//�����ѹ�������Ӧ��΢�ź�
		String mainurl = URL_SEARCH + PARAM_SEARCH + "&query=" + account;
		Document doc = Jsoup.connect(mainurl)
					.userAgent("Mozilla")
					.cookie("auth", "token")
					.timeout(5000)
					.get();
		Elements eles = doc.getElementsByClass("weixin-public");
		Element ele = eles.get(0);
		WXEntity entity = new WXEntity();
		boolean first = true;
		//��ȡ��ά���ͷ��
		for(Element e : ele.getElementsByClass("pos-ico").select("img[src]")) {
			if(first) {
				entity.setAvatar(e.attr("src"));
				first = false;
			} else {
				entity.setQrcode(e.attr("src"));
			}
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
				//������Ѷ
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
	
	private static JSONArray getNewsByUrl(String url) throws Exception {
		//��ȡ��һ��΢�Ź��ںŵ���ҳ
		Document doc2 = Jsoup.connect(url)
				.userAgent("Mozilla")
				.cookie("auth", "token")
				.timeout(7000)
				.get();
		String result = doc2.toString();
		avatar = doc2.getElementsByClass("radius_avatar").get(0).getElementsByTag("img").get(0).attr("src");
		//��ȡ�ַ�������ȡ�����б������
		int p = result.indexOf("msgList");
		//������ϴ��ȥ��������ַ���
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
				String fileid = wx.getApp_msg_ext_info().getFileid();
				file_id = wx.getComm_msg_info().getId();
//				System.out.println(file_id);
				String jsontext = WebUtil.sendGET(getReadUrl(contenturl));
				SoGouWX swx = new Gson().fromJson(jsontext, SoGouWX.class);
				swx.setTime(time);
				swx.setUrl(getHtmlUrl(contenturl));
				swx.setFileid(fileid);
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
	
	private static String getHtmlUrl(String url) {
		return URL_HEAD + url.substring(url.indexOf("s"));
	}
}
