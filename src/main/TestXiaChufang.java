package main;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import utils.MD5Util;
import utils.WebUtil;

public class TestXiaChufang {
	
	public static final String API_KEY = "37757c2d622de327";
	
	public static final String API_SECRET = "8d8473a028fb0ea7";
	
	public static final String DEBUG_KEY = "94e7df22feeb9c5d";
	
	public static final String DEBUG_SECRET = "54bbed854301494d";
	
	public static final String URL_DEBUG = "http://galaxy.openapi.dev.xiachufang.com";
	
	public static final String URL_MAIN = "http://openapi.xiachufang.com";
	
	public static final String URL_GET = URL_MAIN + "/goods/get";
	
	public static final String URL_SYNC = URL_DEBUG + "/order/sync";
	
	public static final String URL_SEND = URL_DEBUG + "/logistics/send";
	
//	public static final String URL_SEND = URL_MAIN + "/logistics/send";
	
	public static final String URL_GETLOGT = URL_MAIN + "/logistics/getsupport";

	public static void main(String[] args) {
//		'company_name': "申通快递",
//        'out_sid': '220693905058',
//        'tid': '20160427-176202577',

		try {
			List<NameValuePair> param = new ArrayList<>();
			String time = getTime();
			JSONObject json_param = new JSONObject();
			
			json_param.put("tid", "20160407-165154607");
			json_param.put("out_sid", "220693905058");
			json_param.put("company_name", "申通快递");
			
//			json_param.put("start_modified", "2016-03-09 00:00:00");
//			json_param.put("end_modified", "2016-03-09 23:59:59");
//			json_param.put("page_no", "1");
//			json_param.put("page_size", "100");
//			json_param.put("status", "");
			
			String sign = makeDebugSign(time, json_param.toString());
			System.out.println(sign);
//			param.add(new BasicNameValuePair("api_key", API_KEY));
			param.add(new BasicNameValuePair("api_key", DEBUG_KEY));
			param.add(new BasicNameValuePair("api_sign", sign));
			param.add(new BasicNameValuePair("version", getVersion()));
			param.add(new BasicNameValuePair("time", time));
			param.add(new BasicNameValuePair("param", json_param.toString()));
//			System.out.println(MD5Util.MD5("中通快递"));
			String result = WebUtil.sendPOST(URL_SEND, param);
//			String result = WebUtil.sendPOST(URL_SYNC, param);
			JSONObject json = new JSONObject(result);
			System.out.println(json.toString());
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
				+ "param" + param.replace("\\\\", "\\")
				+ "time" + time
				+ "version" + getVersion()
				+ API_SECRET;
		return MD5Util.MD5(sign).toLowerCase();
	}
	
	public static String jsonFormatter(String uglyJSONString){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonParser jp = new JsonParser();
        JsonElement je = jp.parse(uglyJSONString);
        String prettyJsonString = gson.toJson(je);
        return prettyJsonString;
    }
	
	public static String makeDebugSign(String time, String param) {
		String sign = "api_key" + DEBUG_KEY 
				+ "param" + param.replace("\\\\", "\\")
				+ "time" + time
				+ "version" + getVersion()
				+ DEBUG_SECRET;
		System.out.println(sign);
		return MD5Util.MD5(sign).toLowerCase();
	}
	
	public static String string2Unicode(String string) {
	    StringBuffer unicode = new StringBuffer();
	    for (int i = 0; i < string.length(); i++) {
	        char c = string.charAt(i);
	        unicode.append("\\u" + Integer.toHexString(c));
	    }
	    return unicode.toString();
	}
	
}
