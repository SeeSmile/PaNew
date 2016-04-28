package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.bson.Document;
import org.json.JSONObject;

import helper.AgencyHelper;
import helper.AgencyHelper.HostLoadListener;
import helper.WXhelper;
import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.WebUtil;

import com.mongodb.BasicDBObject;

import data.WXEntity;
import db.BaseMonGoDB;

public class TestMongo {

	public static final String last_name = "524";
	public static final String PATH_NOACCOUNT = "c:/noaccount2.txt";
	private static boolean isrun = false;
	private static int index = 0;
	private static List<String> list_unknow = new ArrayList<>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//110.73.11.68   8123
		AgencyHelper.getHostList(1, new HostLoadListener() {
			
			@Override
			public void onProgress(int page) {
				
			}
			
			@Override
			public void onLoadFinish(List<HttpHost> list) {
//				for(HttpHost h : list) {
//					System.out.println(h.getHostName() + ":" + h.getPort());
//				}
				start(list);
			}
			
			@Override
			public void onFailed() {
				
			}
		});
	}
	
	public static void start(final List<HttpHost> list) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("c:/avatar2.txt"));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			  final BufferedReader br = new BufferedReader(isr);   
				  new Thread(new Runnable() {
					
					@Override
					public void run() {
						String line;
						try {
							while ((line = br.readLine()) != null) {   
								index++;

//								if(line.trim().equals(last_name)) {
//									 isrun = true;
//								}
								if(index == Integer.valueOf(last_name)) {
									isrun = true;
								}
								if(isrun && line.length() > 0) {
									String text = "";
									long start_time = System.currentTimeMillis();
									Pattern p = Pattern.compile("\\s*");
									Matcher m = p.matcher(line);
									text = m.replaceAll("");
									text = line.replaceAll(" ", "");
									try {
										  System.out.print("正在" + index + "行数据:" + text + "; ");
										  	WXEntity en = new WXhelper().getUrlbyAccount(text.trim(), list);
										  	FileUtil.writeText2File("c:/weixin2.txt", line + "|" + en.getAvatar());
//											JSONObject json = new WXhelper().getSearchList(line.trim(), list);
//											Document doc_main = new Document();
//											doc_main.putAll(BasicDBObject.parse(json.toString()));
//											BaseMonGoDB.getInstance().insertInfo(doc_main);
										} catch (IOException e) {
											System.out.println("\nIOE错误");
											e.printStackTrace();
											break;
										} catch (FibdException e) {
											System.out.println(" 被禁了:" + line.trim());
											break;
										} catch (AccountErrorException e) {
											list_unknow.add(line.trim());
											FileUtil.writeText2File(PATH_NOACCOUNT, line.trim());
											e.showError();
										} finally {
											try {
												
												
												Thread.sleep(new Random().nextInt(8) * 1000);
												System.out.println(" 耗时: " + (System.currentTimeMillis() - start_time) / 1000 + "秒");
											} catch (InterruptedException e) {
												e.printStackTrace();
											}
										}
								}
								 
						  }
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}).start();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
	}

}
