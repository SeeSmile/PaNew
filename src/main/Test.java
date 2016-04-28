package main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import db.BaseMonGoDB;
import db.Web360DB;

import ui.WXJpanel;
import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.SFileUtil;
import utils.WebUtil;

import helper.AgencyHelper;
import helper.BaiduHelper;
import helper.AgencyHelper.HostLoadListener;
import helper.WXhelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new Test().run();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() throws IOException {
		final File file = new File("d:/newfile2.txt");
		if(!file.exists()) {
			file.createNewFile();
		}
		SFileUtil.readFileLine(new File("c:/avatar2.txt"), new SFileUtil.ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				System.out.println("index = " + index + ", text = " + text);
				String url = "http://weixin.sogou.com/weixin?type=1&query=" + text;
				FileUtil.writeText2File(file.getAbsolutePath(), url);
			}
			
			@Override
			public void onFinish() {
				System.out.println("onFinish");
			}
			
			@Override
			public void onFail() {
				System.out.println("onFail");
			}
		});
	}
}
