package test;

import java.util.List;

import data.Constant;
import db.BaseMonGoDB;
import db.MongoWXEntity;
import utils.FileUtil;
import utils.SFileUtil;
import utils.WebUtil;
import helper.CountHelper;
import helper.CwqHelper;

public class HelpterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String name = "腾讯";
		String result = WebUtil.getHttpContent("http://search.top.chinaz.com/Search.aspx?url=" + name);
		System.out.println(result);
		
	}

}
