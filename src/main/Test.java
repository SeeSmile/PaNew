package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import com.mongodb.BasicDBObject;

import db.BaseMonGoDB;
import db.YunSpider;

import utils.AccountErrorException;
import utils.FibdException;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;
import helper.WXhelper;

public class Test {
	
	public static List<String> list;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		insertInfoToDB();
	}
	
	public static void findDb() throws IOException {
		final YunSpider db = new YunSpider();
		list = new ArrayList<>();
		SFileUtil.readFileLine(SFileUtil.createDataFile("weixin_all.txt"), new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				System.out.println("index:" + index);
				try {
					text = SFileUtil.trim(text);
					if(text.length() > 0) {
						
						int state = db.getStateByAccount(text);
						if(state == YunSpider.STATE_NOEXIST || state == YunSpider.STATE_NOAVATAR) {
							list.add(text);
						}
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFinish() {
				for(String t : list) {
					System.out.println(t);
				}
				System.out.println(list.size());
			}
			
			@Override
			public void onFail() {
				
			}
		});
	}
	
	public static void insertInfoToDB() throws IOException {
		SFileUtil.readFileLine(SFileUtil.createDataFile("avatar.txt"), new SFileUtil.ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				if(text.length() == 0) {
					return;
				}
				JSONObject json;
				try {
					int p = text.indexOf("|");
					String text2 = text.substring(p + 1, text.length());
					json = new JSONObject(text2);
					JSONObject json2;
					try {
						json2 = new WXhelper().getSearchList2(json.getString("account"), json);
						org.bson.Document doc_main = new org.bson.Document();
						doc_main.putAll(BasicDBObject.parse(json2.toString()));
						BaseMonGoDB.getInstance().insertInfo(doc_main);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (FibdException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (AccountErrorException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
}
