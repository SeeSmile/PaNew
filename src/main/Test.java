package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.apache.http.HttpHost;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import db.BaseMonGoDB;
import db.Web360DB;

import ui.WXJpanel;
import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.WebUtil;

import helper.AgencyHelper;
import helper.BaiduHelper;
import helper.AgencyHelper.HostLoadListener;
import helper.WXhelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			int key = BaiduHelper.getCountByWord("∏∂≈‡");
			System.out.println("key:" + key);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
