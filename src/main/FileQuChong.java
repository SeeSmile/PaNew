package main;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import data.WXEntity;
import data.WxNews;
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
		File oldFile = SFileUtil.createDataFile("noaccount_all.txt");
		File newFile = new File(SFileUtil.getDataFile("ooooo.txt"));
		try {
			SFileUtil.lookChong(oldFile, newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertWX() throws IOException {
		final YunSpider db = new YunSpider();
		SFileUtil.readFileLine(SFileUtil.createDataFile("avatar.txt"), new ReadListener() {
			
			@Override
			public void onRead(int index, String text) {
				System.out.println("index:" + index);
				int p = text.indexOf("|");
				text = text.substring(p + 1, text.length());
				if(text.length() > 0) {
					try {
						System.out.println("account:" + text);
						db.insertInfo(new Gson().fromJson(text, WXEntity.class));
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
