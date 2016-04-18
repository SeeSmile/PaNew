package data;

public class CwqWX {
	//bs_account_name,bs_weixinhao,bs_head_img,2016-4-18,141,type,bs_introduction,1,dtwdyt,dtwdet,dtwqtwz
	
	/**
	 * 微信名字
	 */
	private String bs_account_name;
	/**
	 * 微信号
	 */
	private String bs_weixinhao;
	/**
	 * 头像
	 */
	private String bs_head_img;
	/**
	 * 介绍
	 */
	private String bs_introduction;
	/**
	 * 头条
	 */
	private String dtwdyt;
	/**
	 * 次条
	 */
	private String dtwdet;
	/**
	 * 多图
	 */
	private String dtwqtwz;
	
	private String bs_fans_num;

	public String getBs_fans_num() {
		return bs_fans_num;
	}
	public void setBs_fans_num(String bs_fans_num) {
		this.bs_fans_num = bs_fans_num;
	}
	public String getBs_account_name() {
		return bs_account_name;
	}
	public void setBs_account_name(String bs_account_name) {
		this.bs_account_name = bs_account_name;
	}
	public String getBs_weixinhao() {
		return bs_weixinhao;
	}
	public void setBs_weixinhao(String bs_weixinhao) {
		this.bs_weixinhao = bs_weixinhao;
	}
	public String getBs_head_img() {
		return bs_head_img;
	}
	public void setBs_head_img(String bs_head_img) {
		this.bs_head_img = bs_head_img;
	}
	public String getBs_introduction() {
		return bs_introduction;
	}
	public void setBs_introduction(String bs_introduction) {
		this.bs_introduction = bs_introduction;
	}
	public String getDtwdyt() {
		return dtwdyt;
	}
	public void setDtwdyt(String dtwdyt) {
		this.dtwdyt = dtwdyt;
	}
	public String getDtwdet() {
		return dtwdet;
	}
	public void setDtwdet(String dtwdet) {
		this.dtwdet = dtwdet;
	}
	public String getDtwqtwz() {
		return dtwqtwz;
	}
	public void setDtwqtwz(String dtwqtwz) {
		this.dtwqtwz = dtwqtwz;
	}
	
}
