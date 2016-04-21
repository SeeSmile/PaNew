package main;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ui.WXJpanel;
import utils.WebUtil;

import helper.WXhelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		String mainurl = "http://weibo.com/zhangzilin?from=feed&loc=nickname&is_hot=1";
		try {
			Document doc = Jsoup.connect(mainurl)
						.userAgent("Mozilla")
						.cookie("auth", "token")
						.timeout(5000)
						.get();
			System.out.println(doc.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
