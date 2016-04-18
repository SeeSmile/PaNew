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


import data.CwqWX;

public class CwqDB extends BaseDB{
	
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
	
	public static void add(CwqWX data, String type_id) throws SQLException {
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
//		System.out.println("sql = " + sql +", list = " + list.toString());
		PreparedStatement state = getPrepared(sql);
		initPst(state, list);
		state.execute();
		String cid = getId(data.getBs_weixinhao());
		if(cid != null) {
//			System.out.println("cid = " + cid);
			addPrice(cid, data);
		}
		state.close();
	}
	
	public static String getId(String account) throws SQLException {
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
	
	private static void addPrice(String id, CwqWX entity) throws SQLException {
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
	
	private static String getFans(String text) {
		String price = text.replaceFirst("��", "0000");
		if(Integer.valueOf(price) > 1 * 10000 * 10000) {
			return text.replace("��", "");
		}
		return price;
	}
	
	public static void saveFile() throws SQLException{
		String sql = "select ca_icon from " + TABLE_PRICE;
		final PreparedStatement state = getPrepared(sql);
		
	
		//http://img.cwq.com/201603/56eb75a8e741d.jpg
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				int i = 0;
				String path = "c:\\pics\\";
				ResultSet result;
				try {
					result = state.executeQuery();
					while(result.next()) {
						System.out.println("current:" + i);
						String url = result.getString(1);
						System.out.println("url:" + url);
						int p = url.lastIndexOf("/");
						String name = url.substring(p + 1, url.length());
						File file = new File(path + name);
						if(!file.exists()) {
							file.createNewFile();
							downloadFile(url, name, path);
							i++;
							Thread.sleep(2 * 100);
						} 
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
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
		// 1K�����ݻ���  
		byte[] bs = new byte[1024];
		// ��ȡ�������ݳ���  
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
	
}
