package test;

import java.io.IOException;

import utils.SFileUtil;
import utils.SFileUtil.ReadListener;
import helper.CwqHelper;
import data.Constant;

public class TestCaijing {
	
	private static String area_id;
	private static String area_type;
	
	public static void main(String[] args) {
		final CwqHelper helper = new CwqHelper();
		if(helper.login()) {
			for(int index = 1; index < 36; index++) {
				if(index == 1) {
					area_id = "3412";
				} else {
					area_id = index + "";
				}
				try {
					SFileUtil.readFileLine(SFileUtil.createDataFile("config_area"),
							new ReadListener() {

								@Override
								public void onRead(int index, String text) {
									int positon = text.indexOf("|");
									String id = text.substring(0, positon);
									if (id.equals(area_id)) {
										area_type = text.substring(text.indexOf("|") + 1);
									}
								}

								@Override
								public void onFinish() {
//									helper.setId("8");
									helper.setId("10");
									helper.setPage(1);
									helper.setType("2");
									helper.setTypeId(area_id);
									helper.setArea(area_type);
									helper.getWeiBoData();
								}

								@Override
								public void onFail() {
									System.out.println("fail to loading config file");
								}
							});
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
