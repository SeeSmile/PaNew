package utils;

public class AccountErrorException extends Exception {
	private String name;
	public AccountErrorException(String name) {
		this.name = name;
		
	}
	
	public void showError() {
		System.out.println("������ " + name);
	}
}
