package main;

import com.google.gson.Gson;

public class TestJson {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String text = "[{title:��ѵ���at:101},{title:��ѵ���at:101},{title:��ѵ���at:101}";
		String[] ts = text.split("}");
		for(String s : ts) {
			System.out.println(s);
		}
		
	}

	public class Enn{
		private String id;
		private String type;
		
		@Override
		public String toString() {
			return "id = " + id + ", type = " + type;
		}
	}
}
