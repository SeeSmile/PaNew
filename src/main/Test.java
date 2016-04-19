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
			WXhelper.getSearchList("headline_today");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
