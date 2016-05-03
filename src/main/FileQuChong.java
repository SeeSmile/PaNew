package main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import data.WXEntity;
import db.YunSpider;

import utils.SFileUtil;
import utils.SFileUtil.ReadListener;

public class FileQuChong {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		quChong();
	}
	
	public static void quChong() {
		File oldFile = SFileUtil.createDataFile("weixin_id.txt");
		File newFile = new File(SFileUtil.getDataFile("noa.txt"));
		try {
			SFileUtil.lookChong(oldFile, newFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void insertWX() throws IOException {
		final YunSpider db = new YunSpider();
		SFileUtil.readFileLine(SFileUtil.createDataFile("noa.txt"), new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				System.out.println("index:" + index);
				int p = text.indexOf("|");
				text = text.substring(p + 1, text.length());
				if(text.length() > 0) {
					try {
						System.out.println("account:" + text);
						db.insertNoAccountInfo(text);
					} catch (JsonSyntaxException e) {
						e.printStackTrace();
					} catch (SQLException e) {
						e.printStackTrace();
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
