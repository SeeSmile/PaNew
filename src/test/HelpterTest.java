package test;

import java.util.List;

import data.Constant;
import db.BaseMonGoDB;
import db.MongoWXEntity;
import utils.FileUtil;
import utils.SFileUtil;
import helper.CountHelper;
import helper.CwqHelper;

public class HelpterTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		List<MongoWXEntity> list = BaseMonGoDB.getInstance().getDocumentByAccount("hlwggzt");
		CountHelper.getCountWx(list);
//		System.out.println(list.toString());
//		System.out.println("获取点赞数:" + CountHelper.getAllPraiseCount(list));
//		System.out.println("获取头条阅读数:" + CountHelper.getHeadReadCount(list));
//		System.out.println("获取平均阅读数:" + CountHelper.getAvgReadCount(list));
//		System.out.println("获取最高阅读数:" + CountHelper.getHeightReadCount(list));
//		System.out.println("获取总阅读数:" + CountHelper.getAllReadCount(list));
//		System.out.println("获取周更新频率:" + CountHelper.getRangeWeek(list));
//		System.out.println("获取参考阅读数:" + CountHelper.getModelofRead(list));
		
	}

}
