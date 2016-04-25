package utils;

public class FibdException extends Exception{
	private String url;
	private String text;
	public FibdException(String url, String text) {
		this.url = url;
		this.text = text;
	}
	
	public String getUrl() {
		return url;
	}
	
	public String getText() {
		return text;
	}
}
