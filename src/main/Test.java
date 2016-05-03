package main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.HttpHost;
import org.eclipse.jetty.util.thread.SpinLock;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import data.WXEntity;
import db.BaseMonGoDB;
import db.Web360DB;
import db.YunSpider;

import ui.WXJpanel;
import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;
import utils.SwebUtil;
import utils.WebUtil;

import helper.AgencyHelper;
import helper.BaiduHelper;
import helper.AgencyHelper.HostLoadListener;
import helper.WXhelper;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
	}
	
	public static void findDb() throws IOException {
		final YunSpider db = new YunSpider();
		SFileUtil.readFileLine(SFileUtil.createDataFile("weixin_id.txt"), new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				try {
					text = SFileUtil.trim(text);
					if(text.length() > 0) {
						int state = db.getStateByAccount(text);
						if(state == 4) {
							System.out.println(text);
						}
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFinish() {
				
			}
			
			@Override
			public void onFail() {
				
			}
		});
	}
}
