package main;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ui.WXJpanel;
import utils.WebUtil;

import helper.WXhelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			System.out.println(WXhelper.getSearchList("adwlfhd"));
		} catch (IOException e) {
			System.out.println("�������ӳ�ʱ!");
		} catch (Exception e) {
			System.out.println("�����ڲ�����...");
		}
	}

}
