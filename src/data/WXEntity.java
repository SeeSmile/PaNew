package data;

import sun.security.krb5.internal.Ticket;

import com.google.gson.Gson;

public class WXEntity {
	
	public static final String ATT_YES = "1";
	public static final String ATT_NO = "2";
	
	private String avatar;
	
	private String qrcode;
	/**
	 * ��ҳ��ҳ��ַ
	 */
	private String url;
	/**
	 * �Ƿ���֤�� 1��; 2��
	 */
	private String is_att;
	/**
	 * ΢�Ž���
	 */
	private String introduce;
	/**
	 * ��֤��Ϣ����
	 */
	private String info_att;
	/**
	 * ΢������
	 */
	private String name;
	/**
	 * ΢���˺�
	 */
	private String account;
	
	public WXEntity() {
		this.is_att = ATT_NO;
	}
	
	
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getIs_att() {
		return is_att;
	}
	public void setIs_att(String is_att) {
		this.is_att = is_att;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getInfo_att() {
		return info_att;
	}
	public void setInfo_att(String info_att) {
		this.info_att = info_att;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
