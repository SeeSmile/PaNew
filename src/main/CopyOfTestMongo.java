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

import org.bson.Document;
import org.json.JSONObject;
import helper.WXhelper;
import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.WebUtil;

import com.mongodb.BasicDBObject;
import db.BaseMonGoDB;

public class CopyOfTestMongo {

	public static final String last_name = "hhhlw889";
	public static final String PATH_NOACCOUNT = "d:/noaccount.txt";
	private static boolean isrun = false;
	private static int index = 0;
	private static List<String> list_unknow = new ArrayList<>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("d:/abc.txt"));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			  final BufferedReader br = new BufferedReader(isr);   
				  new Thread(new Runnable() {
					
					@Override
					public void run() {
//						WebUtil.getCookie();
						String line;
						try {
							while ((line = br.readLine()) != null) {   
								index++;
//								if(line.trim().equals(last_name)) {
//									 isrun = true;
//								}
								if(index > 3000) {
									isrun = true;
								}
								if(isrun && line.length() > 0) {
									long start_time = System.currentTimeMillis();
									try {
										  System.out.print("����" + index + "������:" + line.trim() + "; ");
											JSONObject json = new WXhelper().getSearchList(line.trim());
											Document doc_main = new Document();
											doc_main.putAll(BasicDBObject.parse(json.toString()));
											BaseMonGoDB.getInstance().insertInfo(doc_main);
										} catch (IOException e) {
											System.out.println("\nIOE����");
										} catch (FibdException e) {
											System.out.println(" ������:" + line.trim());
											System.out.println(" δ֪�б�:");
											for(String s : list_unknow) {
												System.out.println(s);
											}
											break;
										} catch (AccountErrorException e) {
											list_unknow.add(line.trim());
											FileUtil.writeText2File(PATH_NOACCOUNT, "\n:" + line.trim());
											e.showError();
										} finally {
											try {
												Thread.sleep(1 * 1000);
												System.out.println(" ��ʱ: " + (System.currentTimeMillis() - start_time) / 1000 + "��");
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
