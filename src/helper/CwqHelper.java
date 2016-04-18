package helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;

import data.CwqWX;
import db.CwqDB;
import utils.WebUtil;

public class CwqHelper {
	/**
	 * 登录url
	 */
	private final String URL_LOGIN = "http://www.cwq.com/Owner/Account/check_login/";
	
	/**
	 * 获取数据列表url
	 */
	private final String URL_WEIXIN = "http://www.cwq.com/Owner/Weixin/get_weixin_list/";
	
	private final String USER_NAME = "lengzhifu";
	private final String USER_PWD = "wlf2016";
	
	private int startpage;
	private boolean isRun = false;
	private String id;
	private String type_id;
	
	public void setTypeId(String id) {
		this.type_id = id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public void setPage(int page) {
		this.startpage = page;
	}
	
	public boolean login() {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		urlParameters.add(new BasicNameValuePair("account", USER_NAME));
		urlParameters.add(new BasicNameValuePair("password", USER_PWD));
		urlParameters.add(new BasicNameValuePair("verify", ""));
		urlParameters.add(new BasicNameValuePair("inajax", "1"));
		try {
			String result = WebUtil.sendPOST(URL_LOGIN, urlParameters);
			JSONObject json = new JSONObject(result);
			String status = json.optString("status");
			if("1".equals(status)) {
				System.out.println("login ok");
				return true;
			} else {
				System.out.println("登录失败:" + json.optString("info"));
			}
			
		} catch (Exception e) {
			System.out.println("登录失败");
			e.printStackTrace();
		}
		return false;
	}
	
	public void getWxData() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				startGetList(getWXParam(id), URL_WEIXIN, new GetListener() {
					
					@Override
					public void OnFinish(JSONObject json) {
						CwqWX wx = new Gson().fromJson(json.toString(), CwqWX.class);
						try {
							CwqDB.add(wx, type_id);
						} catch (SQLException e) {
							System.out.println("加入数据库失败");
							System.out.println("data:" + json.toString());
							isRun = false;
							e.printStackTrace();
						}				
					}
				});
			}
		}).start();
		
	}
	
	private List<NameValuePair> getWXParam(String id) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("is_celebrity", "0"));
//		params.add(new BasicNameValuePair("ids", ""));
//		params.add(new BasicNameValuePair("order_by", ""));
		params.add(new BasicNameValuePair("all", ""));
		params.add(new BasicNameValuePair("flex", "1"));
		params.add(new BasicNameValuePair("zfjg_type", "2"));
		params.add(new BasicNameValuePair("cjfl", id));
		return params;
	}
	
	private void startGetList(List<NameValuePair> param, String url, GetListener listener) {
		int page = startpage;
		isRun = true;
		while(isRun) {
			try {
				System.out.println("正在获取" + page + "页数据");
				param.add(new BasicNameValuePair("p", page + ""));
				String result = WebUtil.sendPOST(url, param);
				JSONObject json = new JSONObject(result);
				json = json.optJSONObject("data");
				JSONArray array = json.optJSONArray("list");
				for(int i = 0; i < array.length(); i++) {
					json = array.getJSONObject(i);
					listener.OnFinish(json);
				}
				if(array.length() == 0) {
					isRun = false;
					System.out.println("数据采集完毕");
				}
				System.out.println("第" + page + "页数据获取完毕");
//				if(page > 3) {
//					isRun = false;
//				}
			} catch (Exception e) {
				System.out.println("获取信息失败");
				e.printStackTrace();
			}
			page++;
			try {
				Thread.sleep(1 * 400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	interface GetListener{
		public void OnFinish(JSONObject json);
	}
}
