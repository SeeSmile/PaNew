package db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import data.CwqWX;

public class CwqDB extends BaseDB{
	
	private final static String ip = "192.168.0.196";
	private final static String dbname = "media";
	private final static String name = "media";
	private final static String password = "mediamedia";
	
	public CwqDB() {
		super(ip, dbname, name, password);
	}

	private static final String TABLE_MAIN = "ysh_business_inf";
	private static final String TABLE_PRICE = "ysh_oper_catogory";
	
	public static final String KEY_TJXS = "tjxs";
	public static final String KEY_GONGZH = "gongzh";
	public static final String KEY_FANSNO = "fansno";
	public static final String KEY_B_DATE = "b_date";
	public static final String KEY_AREA_NO = "area_no";
	public static final String KEY_MID = "mid";
	public static final String KEY_UID = "uid";
	public static final String KEY_PRICE = "price";
	public static final String KEY_MPRICE = "mprice";
	public static final String CA_NAME = "ca_name";
	public static final String CA_ACCOUNT = "ca_account";
	public static final String CA_ICON = "ca_icon";
	public static final String CA_DATE = "ca_date";
	public static final String PLAT_ID = "plat_id";
	public static final String TYPE_ID = "type_id";
	public static final String CA_DESC = "ca_desc";
	public static final String STATUS = "status";
	
	public static final String time = "2016-04-18 00:00:00";
	public static final String PLATID = "141";
	public static final String mSTATUS = "1";
	public static final String UID = "98";
	public static final String TJXS = "3";
	
	public void add(CwqWX data, String type_id) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<DbParams>();
		list.add(new DbParams(CA_NAME, data.getBs_account_name()));
		list.add(new DbParams(CA_ACCOUNT, data.getBs_weixinhao()));
		list.add(new DbParams(CA_ICON, data.getBs_head_img()));
		list.add(new DbParams(CA_DATE, time));
		list.add(new DbParams(PLAT_ID, PLATID));
		list.add(new DbParams(TYPE_ID, type_id));
		list.add(new DbParams(CA_DESC, data.getBs_introduction()));
		list.add(new DbParams(STATUS, mSTATUS));
		String sql = createInsertSql(TABLE_PRICE, list);
		PreparedStatement state = getPrepared(sql);
		initPst(state, list);
		state.execute();
		String cid = getId(data.getBs_weixinhao());
		if(cid != null) {
			addPrice(cid, data);
		}
		state.close();
	}
	
	public String getId(String account) throws SQLException {
		String sql = "select ca_id from " + TABLE_PRICE + " where " + CA_ACCOUNT + "=?";
		PreparedStatement pStatement = getPrepared(sql);
		pStatement.setString(1, account);
		ResultSet result = pStatement.executeQuery();
		if(result != null) {
			result.next();
			return result.getString(1);
		}
		return null;
	}
	
	private void addPrice(String id, CwqWX entity) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<DbParams>();
		list.add(new DbParams(KEY_MID, id));
		list.add(new DbParams(KEY_UID, UID));
		list.add(new DbParams(KEY_PRICE, entity.getDtwdyt()));
		list.add(new DbParams(KEY_MPRICE, entity.getDtwdyt()));
		list.add(new DbParams(KEY_TJXS, TJXS));
		list.add(new DbParams(KEY_GONGZH, "182"));
		list.add(new DbParams(KEY_FANSNO, getFans(entity.getBs_fans_num())));
		list.add(new DbParams(KEY_B_DATE, time));
		list.add(new DbParams(KEY_AREA_NO, "208"));
		String sql = createInsertSql(TABLE_MAIN, list);
		PreparedStatement state = getPrepared(sql);
		initPst(state, list);
		state.execute();
	}
	
	private void update(String id, String url) throws SQLException {
		String sql = "update " + TABLE_PRICE + " set " + CA_ICON + "=?where ca_id=?";
		PreparedStatement state = getPrepared(sql);
		state.setString(1, "/Uploads/20160418/" + url);
		state.setString(2, id);
		state.execute();
	}
	
	private static String getFans(String text) {
		String price = text.replaceFirst("��", "0000");
		if(Integer.valueOf(price) > 1 * 10000 * 10000) {
			return text.replace("��", "");
		}
		return price;
	}
	
	public void saveFile() throws SQLException{
		String sql = "select ca_icon, ca_id from " + TABLE_PRICE;
		final PreparedStatement state = getPrepared(sql);
		
	
		//http://img.cwq.com/201603/56eb75a8e741d.jpg
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 0;
				String path = "d:\\pics\\";
				ResultSet result;
				
					
					List<String> urls = new ArrayList<String>();
					try {
						result = state.executeQuery();
						while(result.next()) {
							
							System.out.println("current:" + i);
							String url = result.getString(1);
							int p = url.lastIndexOf("/");
							String name = url.substring(p + 1, url.length());
							if(name.indexOf("?") > -1) {
								System.out.println("url:" + url);
								int p2 = name.indexOf("?");
								name = name.substring(p2 + 1, name.length());
							}
							File file = new File(path + name);
							if(!file.exists()) {
								try {
									file.createNewFile();
									downloadFile(url, name, path);
									Thread.sleep(2 * 100);
									
								} catch (Exception e) {
									urls.add(url);
									System.out.println("url:" + url);
								}
							} else {
								update(result.getString(2), name);
							}
							i++;
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for(String t : urls) {
						System.out.println(t);
					}
					System.out.println(urls.size());
				
				
			}
		}).start();
	
	}
	
	public static void downloadFile(String urlString, String filename,
			String savePath) throws IOException {
		// ����URL  
		URL url = new URL(urlString);
		// ������  
		URLConnection con = url.openConnection();
		//��������ʱΪ5s  
		con.setConnectTimeout(5 * 1000);
		// ������  
		InputStream is = con.getInputStream();
		// 1K����ݻ���  
		byte[] bs = new byte[1024];
		// ��ȡ������ݳ���  
		int len;
		// ������ļ���  
		File sf = new File(savePath);
		if (!sf.exists()) {
			sf.mkdirs();
		}
		OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
		// ��ʼ��ȡ  
		while ((len = is.read(bs)) != -1) {
			os.write(bs, 0, len);
		}
		// ��ϣ��ر���������  
		os.close();
		is.close();
	}
	
	public void insertWX(String account, String avatar, String url) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<DbParams>();
		list.add(new DbParams("weixin_account", account));
		list.add(new DbParams("avatar", avatar));
		list.add(new DbParams("new_avatar", "Public/wxpic/" + url + ".jpg"));
		String sql = createInsertSql("wlf_weixin_media_temp_2", list);
		PreparedStatement state = getPrepared(sql);
		initPst(state, list);
		state.execute();
	}
	
	public String getcity() throws SQLException {
		String sql = "select region_name from wlf_region where parent_id=1";
		PreparedStatement pst = getPrepared(sql);
		ResultSet result = pst.executeQuery();
		String text = "";
		while(result.next()) {
			text += ",\"" + result.getString("region_name") + "\"";
		}
		return text;
	}
}
