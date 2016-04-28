package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.List;

import db.CwqDB;
import utils.FileUtil;
import utils.MD5Util;

public class DownloadPic {
	
	public static List<String> list_account;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(new File("d:/weixin.txt"));
			InputStreamReader isr = new InputStreamReader(fis, "UTF-8");   
			  final BufferedReader br = new BufferedReader(isr);   
				  new Thread(new Runnable() {
					
					@Override
					public void run() {
						String line;
						String path = "d:/images";
						try {
							while ((line = br.readLine()) != null) {  
								int p = line.indexOf("|");
								String account = line.substring(0, p);
								line = line.substring(p + 1, line.length());
								FileUtil.downloadFile(line, MD5Util.MD5(line), path);	 
								try {
									new CwqDB().insertWX(account, MD5Util.MD5(line));
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
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
