package data;

import com.google.gson.Gson;

public class SoGouWX {
	
	private String read_num;
	private String like_num;
	private String title;
	private String subtitle;

	public String getSubtitle() {
		return subtitle;
	}
	
	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
