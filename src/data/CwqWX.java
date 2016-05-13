package data;

import com.google.gson.Gson;

public class CwqWX {
	//bs_account_name,bs_weixinhao,bs_head_img,2016-4-18,141,type,bs_introduction,1,dtwdyt,dtwdet,dtwqtwz
	
	/**
	 * ΢������
	 */
	private String bs_account_name;
	/**
	 * ΢�ź�
	 */
	private String bs_weixinhao;
	/**
	 * ͷ��
	 */
	private String bs_head_img;
	/**
	 * ����
	 */
	private String bs_introduction;
	/**
	 * top
	 */
	private String dtwdyt;
	/**
	 * one
	 */
	private String dtwdet;
	/**
	 * two
	 */
	private String dtwqtwz;
	
	private String bs_fans_num;
	
	private String bs_order_note;
	
	private String bs_weekly_read_avg;
	
	private String bs_qr_code;
	
	public String getBs_qr_code() {
		return bs_qr_code;
	}
	public void setBs_qr_code(String bs_qr_code) {
		this.bs_qr_code = bs_qr_code;
	}
	public String getBs_order_note() {
		return bs_order_note;
	}
	public void setBs_order_note(String bs_order_note) {
		this.bs_order_note = bs_order_note;
	}
	public String getBs_weekly_read_avg() {
		return bs_weekly_read_avg;
	}
	public void setBs_weekly_read_avg(String bs_weekly_read_avg) {
		this.bs_weekly_read_avg = bs_weekly_read_avg;
	}
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
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
