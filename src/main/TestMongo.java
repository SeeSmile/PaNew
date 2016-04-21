package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.bson.Document;
import org.json.JSONObject;
import helper.WXhelper;
import com.mongodb.BasicDBObject;
import db.BaseMonGoDB;

public class TestMongo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("d:/abc.txt"));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			  final BufferedReader br = new BufferedReader(isr);   
			  
			  int i = 0;
				  new Thread(new Runnable() {
					
					@Override
					public void run() {
						String line;   
						try {
							while ((line = br.readLine()) != null) {   
								  try {
//								  System.out.println(line.trim() + "  | " + i);
//								  i++;
									  System.out.println("���ڻ�ȡ:" + line.trim());
										JSONObject json = WXhelper.getSearchList(line.trim());
										Document doc_main = new Document();
										doc_main.put("account", BasicDBObject.parse(json.toString()));
										BaseMonGoDB.getInstance().insertInfo(doc_main);
									} catch (Exception e) {
										e.printStackTrace();
									} finally {
										try {
											Thread.sleep(4 * 1000);
										} catch (InterruptedException e) {
											e.printStackTrace();
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
