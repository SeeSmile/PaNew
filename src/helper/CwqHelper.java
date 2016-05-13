package helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;

import data.Constant;
import data.CwqWX;
import db.CwqDB;
import db.CwqNewDB;
import utils.WebUtil;

public class CwqHelper {
	/**
	 * 登录
	 */
	private final String URL_LOGIN = "http://www.cwq.com/Owner/Account/check_login/";
	
	/**
	 * 获取列表
	 */
	private final String URL_WEIXIN = "http://www.cwq.com/Owner/Weixin/get_weixin_list/";
	
	/**
	 * 获取地区信息
	 */
	private final String URL_REGION = "http://www.cwq.com/Owner/Tool/get_Region_Data/";
	
	private final String USER_NAME = "lengzhifu";
	private final String USER_PWD = "wlf2016";
	
	private int startpage;
	private boolean isRun = false;
	private String id;
	private String type_id;
	private String area;
	private String type_push;
	private static CwqNewDB newdb = new CwqNewDB();
	
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getType() {
		return type_push;
	}

	public void setType(String type_push) {
		this.type_push = type_push;
	}

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
				System.out.println("fail to login:" + json.optString("info"));
			}
			
		} catch (Exception e) {
			System.out.println("login exception");
			e.printStackTrace();
		}
		return false;
	}
	
	public void getWxData() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {

				startGetList(getWXParam(id, area, type_push), URL_WEIXIN, new GetListener() {
					
					@Override
					public void OnFinish(JSONObject json) {
						CwqWX wx = new Gson().fromJson(json.toString(), CwqWX.class);
						System.out.println("json:\n" + wx.toString());
//						try {
//							db.add(wx, type_id);
//						} catch (SQLException e) {
//							System.out.println("execute sql exception");
//							System.out.println("data:" + json.toString());
//							isRun = false;
//							e.printStackTrace();
//						}	
						
						try {
							newdb.insertInfo(wx, type_push, area, type_id);
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
			}
		}).start();
		
	}
	
	/**
	 * 获取需要的请求参数
	 * @param id 分类的编号
	 * @param area 地区编号，格式：324,532,6435,777,55
	 * @return 初始化的参数
	 */
	private List<NameValuePair> getWXParam(String id, String area, String type_push) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		//硬广1，软广2
		params.add(new BasicNameValuePair("flex", type_push));
		params.add(new BasicNameValuePair("dfmr_mt", area));
		params.add(new BasicNameValuePair("cjfl", id));

		params.add(new BasicNameValuePair("is_celebrity", "0"));
		params.add(new BasicNameValuePair("zfjg_type", "2"));
		params.add(new BasicNameValuePair("all", ""));
		System.out.println("params:\n" + params.toString());
		return params;
	}
	
	private void startGetList(List<NameValuePair> param, String url, GetListener listener) {
		int page = startpage;
		isRun = true;
		while(isRun) {
			try {
				System.out.println("current page of " + page);
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
					System.out.println("stoped");
				}
				
			} catch (Exception e) {
				System.out.println("run exception");
				e.printStackTrace();
			}
			page++;
			try {
				Thread.sleep(1 * 1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getRegion(String id) throws Exception {
		List<NameValuePair> param = new ArrayList<>();
		param.add(new BasicNameValuePair("parent_id", id));
		String text = WebUtil.sendPOST(URL_REGION, param );
		JSONObject json = new JSONObject(text);
		JSONArray array = json.getJSONArray("data");
		String regions = "";
		for(int i = 0; i < array.length(); i++) {
			regions += array.getJSONObject(i).optString("region_id") + ",";
		}
		return regions.substring(0, regions.length() - 1);
	}
	
	public void getAllAccount() {
		CwqHelper helper = new CwqHelper();
		if(helper.login()) {
			for(int type : Constant.LIST_TYPE_ID) {
				for(int type_area = 1; type_area < 36; type_area++) {
					for(int type_push = 1; type_push < 3; type_push++) {
						if(type_area == 1) {
							helper.setArea("3412");
						} else {
							helper.setArea(type_area + "");
						}
						
						helper.setPage(1);
						helper.setType(type_push + "");
						helper.setTypeId(type + "");
						helper.getWxData();
					}
				}
			}
		}
	}
	
	interface GetListener{
		public void OnFinish(JSONObject json);
	}
}
