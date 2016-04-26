package helper;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import utils.WebUtil;

public class BaiduHelper {
	
	private static final String URL_MAIN = "http://www.baidu.com/s?ie=utf-8&wd=";
	
	public static int getCountByWord(String... keyword) {
		return 0;
	}
	
	public static int getCountByWord(String key) throws IOException {
		String url = URL_MAIN + key;
		Document doc = Jsoup.parse(WebUtil.sendGET(url));
		
		Element ele = doc.getElementsByClass("nums").get(0);
		String text = ele.ownText();
		int p = text.indexOf("Լ");
		text = text.substring(p + 1, text.length() - 1);
		text = text.replaceAll(",", "");
		return Integer.valueOf(text);
	}
}
