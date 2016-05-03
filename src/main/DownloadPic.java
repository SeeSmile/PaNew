package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import data.WXEntity;
import db.CwqDB;
import utils.FileUtil;
import utils.MD5Util;
import utils.SFileUtil;

public class DownloadPic {
	
	public static List<String> list_account;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File(SFileUtil.getDataFile("weixin_avatar.txt")));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			  final BufferedReader br = new BufferedReader(isr);   
				  new Thread(new Runnable() {
					
					@Override
					public void run() {
						String line;
						String path = "f:/images";
						int index = 0;
						List<String> nolist = new ArrayList<>();
							
							try {
								while ((line = br.readLine()) != null) {  
									index++;
									
									if(line.length() > 0 && index >= 0) {
										System.out.println("index:" + index);
										int p = line.indexOf("|");
										String account = line.substring(0, p);
										line = line.substring(p + 1, line.length());
										try {
											WXEntity en = new Gson().fromJson(line, WXEntity.class);
											FileUtil.downloadFile(en.getAvatar(), MD5Util.MD5(en.getAvatar()) + ".jpg", path);
											try {
												new CwqDB().insertWX(account, en.getAvatar(), MD5Util.MD5(en.getAvatar()));
											} catch (SQLException e) {
												e.printStackTrace();
											}
										} catch (IOException e1) {
											nolist.add(line);
											
										}	 
										
									}
									
  }
								for(String s : nolist) {
									System.out.println(s);
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
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
