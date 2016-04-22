package utils;

public class FibdException extends Exception{
	private String url;
	public FibdException(String url) {
		this.url = url;
	}
	
	public String getUrl() {
		return url;
	}
}
