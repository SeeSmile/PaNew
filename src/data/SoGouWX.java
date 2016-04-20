package data;

import com.google.gson.Gson;

public class SoGouWX {
	
	public static final String TYPE_TOP = "1";
	public static final String TYPE_SECOND = "2";
	public static final String TYPE_THREE = "3";
	
	private String read_num;
	private String like_num;
	private String title;
	private String subtitle;
	private String type;
	private String time;
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
//		this.subtitle = subtitle.substring(1, subtitle.length() - 1);
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
//		this.title = title.substring(1, title.length() - 1);
	}
	public String getRead_num() {
		return read_num;
	}
	public void setRead_num(String read_num) {
		this.read_num = read_num;
	}
	public String getLike_num() {
		return like_num;
	}
	public void setLike_num(String like_num) {
		this.like_num = like_num;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
}
