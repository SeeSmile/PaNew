package data;

import java.util.List;

public class WXMonGoEntity {
	
	private WXEntity account;
	private List<WxNews> news;
	
	public WXEntity getAccount() {
		return account;
	}
	public void setAccount(WXEntity account) {
		this.account = account;
	}
	public List<WxNews> getNews() {
		return news;
	}
	public void setNews(List<WxNews> news) {
		this.news = news;
	}
	
	
}
