package main;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import db.BaseMonGoDB;

import ui.WXJpanel;
import utils.WebUtil;

import helper.WXhelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		BaseMonGoDB.getInstance().getAllInfo();
		
	}

}
