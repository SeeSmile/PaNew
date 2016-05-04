package main;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import utils.MD5Util;
import utils.WebUtil;

public class TestXiaChufang {
	
	public static final String API_KEY = "37757c2d622de327";
	
	public static final String API_SECRET = "8d8473a028fb0ea7";
	
	public static final String URL_MAIN = "http://openapi.xiachufang.com";
	
	public static final String URL_GET = URL_MAIN + "/goods/get";
	
	public static final String URL_SEND = URL_MAIN + "/logistics/send";
	
	public static final String URL_GETLOGT = URL_MAIN + "/logistics/getsupport";

	public static void main(String[] args) {
//		'company_name': "申通快递",
//        'out_sid': '220693905058',
//        'tid': '20160427-176202577',

		try {
			List<NameValuePair> param = new ArrayList<>();
//			String time = getTime();
			String time = "2016-05-05 00:45:33";
			System.out.println(time);
			String text = "api_key37757c2d622de327param{\"company_name\": \"\\u7533\\u901a\\u5feb\\u9012\", \"out_sid\": \"220693905058\", \"tid\": \"20160427-176202577\"}time2016-05-05 00:45:33version18d8473a028fb0ea7";
			System.out.println(MD5Util.MD5(text));
			JSONObject json_param = new JSONObject();
			System.out.println(text);
			json_param.put("tid", "20160427-176202577");
//			json_param.put("company_name", string2Unicode("申通快递"));
			json_param.put("out_sid", "220693905058");
			//api_key37757c2d622de327param{"333": "123"}time2016-05-05 00:45:33version18d8473a028fb0ea7'
			//api_key37757c2d622de327param{"333":"123"}time2016-05-05 00:45:33version18d8473a028fb0ea7

//			json_param.put("333", "123");
//			Gson gson = new Gson();
			JsonObject jj = new JsonObject();
			jj.addProperty("tid", "20160427-176202577");
			json_param.put("company_name", string2Unicode("申通快递"));
			jj.addProperty("out_sid", "220693905058");
			
			System.out.println(jj.toString());
			String sign = makeSign(time, json_param.toString());
			System.out.println(sign);
			param.add(new BasicNameValuePair("api_key", API_KEY));
			param.add(new BasicNameValuePair("api_sign", sign));
			param.add(new BasicNameValuePair("version", getVersion()));
			param.add(new BasicNameValuePair("time", time));
			param.add(new BasicNameValuePair("param", json_param.toString()));
//			String result = WebUtil.sendPOST(URL_SEND, param);
//			JSONObject json = new JSONObject(result);
//			System.out.println(json.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
	
	public static String getVersion() {
		return "1";
	}
	
	public static String makeSign(String time, String param) {
		
		String sign = "api_key" + API_KEY 
				+ "param" + param.replaceAll(":", ": ").replaceAll(",", ", ")
				+ "time" + time
				+ "version" + getVersion()
				+ API_SECRET;
		System.out.println("sign:\n" + sign);
		return MD5Util.MD5(sign).toLowerCase();
	}
 
	public static String string2Unicode(String string) {
		 
	    StringBuffer unicode = new StringBuffer();
	 
	    for (int i = 0; i < string.length(); i++) {
	 
	        // 取出每一个字符
	        char c = string.charAt(i);
	 
	        // 转换为unicode
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	 
	    return unicode.toString();
	}
}
