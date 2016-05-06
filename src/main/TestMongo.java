package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpHost;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;

import helper.AgencyHelper;
import helper.AgencyHelper.HostLoadListener;
import helper.WXhelper;
import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.SFileUtil;
import utils.SFileUtil.ReadListener;
import utils.WebUtil;
import data.WXEntity;
import db.BaseMonGoDB;

public class TestMongo {

	public static final String last_name = "1440";
	public static final String PATH_NOACCOUNT = SFileUtil.getDataFile("noaccount.txt");
	public static final String PATH_WEIXINID = SFileUtil.getDataFile("weixin_all.txt");
	public static final String PATH_AVATAR = SFileUtil.getDataFile("avatar.txt");
	private static boolean isrun = false;
	private static int index = 0;
	private static List<String> list_unknow = new ArrayList<>();
	private static int token_index = 0;
	public static int lastIndex = 3582;
	
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
				try {
					start2();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailed() {
				
			}
		});
	}
	
	public static void start(final List<HttpHost> list) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(PATH_WEIXINID));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			  final BufferedReader br = new BufferedReader(isr);   
				  new Thread(new Runnable() {
					
					@Override
					public void run() {
						
						try {
							String line = br.readLine();
							while (true) {   
								index++;
								if(index == Integer.valueOf(last_name)) {
									isrun = true;
									
								}
								if(isrun && line.length() > 0) {
									long start_time = System.currentTimeMillis();
									try {
										if((index - token_index) % 45 == 0) {
											doRestart();
										}
										  System.out.print("正在" + index + "行数据:" + line + "; ");
										  
										  	WXEntity en = new WXhelper().getUrlbyAccount(line.trim(), list);
										  	FileUtil.writeText2File(PATH_AVATAR, line + "|" + en.toString());
										  	line = br.readLine();
										  	index++;
											JSONObject json = new WXhelper().getSearchList(line.trim(), list);
											Document doc_main = new Document();
											doc_main.putAll(BasicDBObject.parse(json.toString()));
											BaseMonGoDB.getInstance().insertInfo(doc_main);
										} catch (IOException e) {
											System.out.println("\nIOE错误");
											e.printStackTrace();
											break;
										} catch (FibdException e) {
											System.out.println(" 被禁了:" + line.trim());
											doRestart();
									
										} catch (AccountErrorException e) {
											list_unknow.add(line.trim());
											FileUtil.writeText2File(PATH_NOACCOUNT, line.trim());
											e.showError();
											line = br.readLine();
											index++;
										} catch (Exception e){
											System.out.println("超级异常");
										} finally {
											try {
												Thread.sleep(5 * 1000);
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
	
	public static void doRestart() {
		try {
			WebUtil.restart2();
		} catch (Exception e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			Thread.sleep(2 * 1000);
			boolean wait = true;
			while(wait) {
				wait = !WebUtil.pingIP();
				System.out.println("ping... " + wait);
				Thread.sleep(3 * 1000);
			}
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void start2() throws IOException {
		SFileUtil.readFileLine(new File(PATH_WEIXINID), new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				if(index >= lastIndex) {
					if((index - token_index) % 45 == 0) {
						doRestart();
					}
				  System.out.print("正在" + index + "行数据:" + text + "; ");
				  	WXEntity en;
					try {
						en = WXhelper.getUrlbyAccount(text, null);
						FileUtil.writeText2File(PATH_AVATAR, en.toString());
						JSONObject json = new WXhelper().getSearchList2(en.getAccount(), new JSONObject(en.toString()));
						Document doc_main = new Document();
						doc_main.putAll(BasicDBObject.parse(json.toString()));
						BaseMonGoDB.getInstance().insertInfo(doc_main);
					} catch (IOException e) {
						System.out.println("url有问题:" + e.toString());
					} catch (FibdException e) {
						doRestart();
						onRead(index, text);
					} catch (AccountErrorException e) {
						FileUtil.writeText2File(PATH_NOACCOUNT, text);
						e.showError();
						
					} catch (URISyntaxException e) {
						System.out.println("666");
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} finally {
						System.out.println("\n");
						try {
							Thread.sleep(5 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
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
