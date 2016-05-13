package main;

import java.io.IOException;
import java.net.URISyntaxException;
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

public class Copy_2_of_Test {
	
	public static List<String> list;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		insertInfoToDB();
	}
	
	public static void insertInfoToDB() throws IOException {
		SFileUtil.readFileLine(SFileUtil.createDataFile("weixin(14).txt"), new SFileUtil.ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				if(text.length() == 0) {
					return;
				}
				if(index > 500 || index <= 393) {
					return;
				}
				System.out.println("index:" + index);
				JSONObject json;
				try {
					json = new JSONObject(text);
					JSONObject json2;
					try {
						String account = json.getString("account");
						System.out.println("account:" + account);
						json2 = new WXhelper().getSearchList2(account, json);
						org.bson.Document doc_main = new org.bson.Document();
						doc_main.putAll(BasicDBObject.parse(json2.toString()));
						BaseMonGoDB.getInstance().insertInfo(doc_main);
					} catch (IOException e) {
						System.out.println("io");
					} catch (FibdException e) {
						System.out.println("fidb");
					} catch (AccountErrorException e) {
						System.out.println("accerror");
					} catch (URISyntaxException e) {
						System.out.println("uri");
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
