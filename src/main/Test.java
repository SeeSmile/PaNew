package main;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import db.BaseMonGoDB;

import ui.WXJpanel;
import utils.AccountErrorException;
import utils.FibdException;
import utils.FileUtil;
import utils.WebUtil;

import helper.WXhelper;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			WXhelper.getUrlbyAccount("a");
		} catch (IOException | FibdException | AccountErrorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
