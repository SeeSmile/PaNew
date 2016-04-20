package utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtil {
	
	public static JSONObject toJson(String text) {
		JSONObject json = null;
		try {
			json = new JSONObject(formatJsonText(text));
		} catch (JSONException e) {
			System.out.println("Éú³Éjson±¨´í:" + e.toString());
		}
		return json;
	}
	
	private static String formatJsonText(String text) {
		String jsontext = "";
		text = text.substring(1, text.length());
		
		return jsontext;
	}
}
