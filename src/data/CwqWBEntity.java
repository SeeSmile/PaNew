package data;

import com.google.gson.Gson;

public class CwqWBEntity {
	/**
	 * 类型
	 */
	private String pg_cjfl_explain;
	private String bs_account_name;
	private String pg_fans_num_explain;
	private String bs_weibo_url;
	private String pg_area_name;
	private String bs_head_img;
	/**
	 * 认证类型
	 */
	private String verified_explain;
//	private String sy_fans_num;
	private String bs_rg_zhuanfa;
	private String bs_rg_zhifa;
	private String bs_create_time;
	private String bs_update_time;
	
	public String getPg_cjfl_explain() {
		return pg_cjfl_explain;
	}
	public void setPg_cjfl_explain(String pg_cjfl_explain) {
		this.pg_cjfl_explain = pg_cjfl_explain;
	}
	public String getBs_account_name() {
		return bs_account_name;
	}
	public void setBs_account_name(String bs_account_name) {
		this.bs_account_name = bs_account_name;
	}
	public String getBs_weibo_url() {
		return bs_weibo_url;
	}
	public void setBs_weibo_url(String bs_weibo_url) {
		this.bs_weibo_url = bs_weibo_url;
	}
	public String getPg_area_name() {
		return pg_area_name;
	}
	public void setPg_area_name(String pg_area_name) {
		this.pg_area_name = pg_area_name;
	}
	public String getBs_head_img() {
		return bs_head_img;
	}
	public void setBs_head_img(String bs_head_img) {
		this.bs_head_img = bs_head_img;
	}
	public String getVerified_explain() {
		return verified_explain;
	}
	public void setVerified_explain(String verified_explain) {
		this.verified_explain = verified_explain;
	}

	public String getPg_fans_num_explain() {
		return pg_fans_num_explain;
	}
	public void setPg_fans_num_explain(String pg_fans_num_explain) {
		this.pg_fans_num_explain = pg_fans_num_explain;
	}
	public String getBs_rg_zhuanfa() {
		return bs_rg_zhuanfa;
	}
	public void setBs_rg_zhuanfa(String bs_rg_zhuanfa) {
		this.bs_rg_zhuanfa = bs_rg_zhuanfa;
	}
	public String getBs_rg_zhifa() {
		return bs_rg_zhifa;
	}
	public void setBs_rg_zhifa(String bs_rg_zhifa) {
		this.bs_rg_zhifa = bs_rg_zhifa;
	}
	public String getBs_create_time() {
		return bs_create_time;
	}
	public void setBs_create_time(String bs_create_time) {
		this.bs_create_time = bs_create_time;
	}
	public String getBs_update_time() {
		return bs_update_time;
	}
	public void setBs_update_time(String bs_update_time) {
		this.bs_update_time = bs_update_time;
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
