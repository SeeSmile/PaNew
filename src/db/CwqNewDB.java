package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.Constant;
import data.CwqDBObj;
import data.CwqWBEntity;
import data.CwqWX;

public class CwqNewDB extends BaseDB {
	
	private final String KEY_TABLE = "wlf_weibo_media";
	private final String KEY_NAME = "media_text";
	private final String KEY_AVATAR = "avatar";
	private final String KEY_CITY = "city";
	private final String KEY_FANS = "fans";
	private final String KEY_STRAIGHT = "straight_price";
	private final String KEY_TURN = "turn_price";
	private final String KEY_R_STRAIGHT = "straight_real_price";
	private final String KEY_R_TURN = "turn_real_price";
	private final String KEY_URL = "url";
	private final String KEY_AUTH = "authentication_text";
	private final String KEY_PROVINCE = "province";
	
	private final static String ip = "192.168.0.196";
	private final static String dbname = "media";
	private final static String name = "media";
	private final static String password = "mediamedia";
	
	public CwqNewDB() {
		super(ip, dbname, name, password);
	}
	
	public void insertInfo(CwqWX entity, String type_push, String type_area, String type_type) throws SQLException {
		String id = getAccountId(entity.getBs_weixinhao());
		if(id == null) {
//			System.out.println("id == null");
			insertBaseInfo(entity, type_push, type_area, type_type);
		} else {
			System.out.println("存在账号:" + id);
		}
//		else {
//			if(type == Type.area) {
//				updateArea(id);
//			} else if(type == Type.push) {
//				updatePush(id);
//			} else if(type == Type.type) {
//				updateType(id);
//			}
//		}
	}
	
	private void updateArea(String id) {
		
	}
	
	private void updatePush(String id) {
		
	}
	
	private void updateType(String id) {
		
	}
	
	private void insertBaseInfo(CwqWX entity, String type_push, String type_area, String type_type) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<>();
		list.add(new DbParams(CwqDBObj.KEY_ACCOUNT, entity.getBs_weixinhao()));
		list.add(new DbParams(CwqDBObj.KEY_NAME, entity.getBs_account_name()));
		list.add(new DbParams(CwqDBObj.KEY_UID, "992"));
		list.add(new DbParams(CwqDBObj.KEY_FANS, entity.getBs_fans_num()));
		list.add(new DbParams(CwqDBObj.KEY_AVATAR, entity.getBs_head_img()));
		if(type_area.equals("3412")) {
			list.add(new DbParams(CwqDBObj.KEY_PROVINCE, "1"));
		} else {
			list.add(new DbParams(CwqDBObj.KEY_PROVINCE, type_area));
		}
		
		list.add(new DbParams(CwqDBObj.KEY_CATEGORY, type_type));
		list.add(new DbParams(CwqDBObj.KEY_TIME, System.currentTimeMillis() / 1000 + ""));
		list.add(new DbParams(CwqDBObj.KEY_WEEK_READ, entity.getBs_weekly_read_avg()));
		list.add(new DbParams(CwqDBObj.KEY_QR_CODE, entity.getBs_qr_code()));
		
		if(type_push.equals("1")) {
			list.add(new DbParams(CwqDBObj.KEY_D_TOP, entity.getDtwdyt()));
			list.add(new DbParams(CwqDBObj.KEY_D_ONE, entity.getDtwdet()));
			list.add(new DbParams(CwqDBObj.KEY_D_TWO, entity.getDtwqtwz()));
			list.add(new DbParams(CwqDBObj.KEY_D_THREE, entity.getDtwqtwz()));
			
			list.add(new DbParams(CwqDBObj.KEY_DR_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_DR_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_DR_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_DR_THREE, getRealPrice(entity.getDtwqtwz())));
		} else {
			list.add(new DbParams(CwqDBObj.KEY_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_THREE, getRealPrice(entity.getDtwqtwz())));
			
//			list.add(new DbParams(CwqDBObj.KEY_TOP, entity.getDtwdyt()));
//			list.add(new DbParams(CwqDBObj.KEY_ONE, entity.getDtwdet()));
//			list.add(new DbParams(CwqDBObj.KEY_TWO, entity.getDtwqtwz()));
//			list.add(new DbParams(CwqDBObj.KEY_THREE, entity.getDtwqtwz()));
			
			list.add(new DbParams(CwqDBObj.KEY_R_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_R_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_R_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_R_THREE, getRealPrice(entity.getDtwqtwz())));
		}
		String sql = createInsertSql(CwqDBObj.TABLE_WEIXIN, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		pst.execute();
		close();
	}
	
	private String getRealPrice(String price) {
		float real_price = Float.valueOf(price);
		if(real_price <= 300) {
			real_price = (float) (real_price * 1.5);
		} else {
			real_price = (float) (real_price * 1.35);
		}
		return real_price + "";
	}
	
	
	public String getAccountId(String account) throws SQLException {
		String sql = "select id from wlf_weixin_media where weixin_account=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, account);
		ResultSet result = pst.executeQuery();
		if(result.next()) {
			return result.getString("id");
		}
		return null;
	}
	
	public enum Type{
		area, push, type
	}
	
	public void addWeiBoInfo(CwqWBEntity entity, String type) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<DbParams>();
		list.add(new DbParams(KEY_NAME, entity.getBs_account_name()));
		list.add(new DbParams(KEY_AVATAR, entity.getBs_head_img()));
//		list.add(new DbParams(KEY_CITY, entity.getPg_area_name()));
		list.add(new DbParams(KEY_FANS, getFans(entity.getPg_fans_num_explain())));
		list.add(new DbParams(KEY_STRAIGHT, getPrice(entity.getBs_rg_zhifa())));
		list.add(new DbParams(KEY_TURN, getPrice(entity.getBs_rg_zhuanfa())));
		list.add(new DbParams(KEY_R_STRAIGHT, getRealPrice("" + getPrice(entity.getBs_rg_zhifa()))));
		list.add(new DbParams(KEY_R_TURN, getRealPrice("" + getPrice(entity.getBs_rg_zhuanfa()))));
		list.add(new DbParams(KEY_URL, entity.getBs_weibo_url()));
		list.add(new DbParams(KEY_AUTH, entity.getVerified_explain()));
		list.add(new DbParams(KEY_PROVINCE, type));
		list.add(getTime());
		list.add(new DbParams(KEY_UID, Constant.UID_CWQ));
		String sql = createInsertSql(KEY_TABLE, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		pst.execute();
	}
	
	public float getPrice(String price) {
		float p = 0;
		if(price == null || price.length() == 0) {
			return 0;
		} else {
			p = Float.valueOf(price);
			return p;
		}
	}
	
	/**
	 * 获取粉丝数
	 * @param fans
	 * @return
	 */
	public int getFans(String fans) {
	
		float num = 0;
		int p = fans.indexOf("万");
		if(p > -1) {
			fans = fans.substring(0, p);
			fans = fans.replace(",", "");
			fans = fans.replace(".", "");
			num = Integer.valueOf(fans);
			num = num * 10000;
			return (int)num;
		} else {
			return Integer.valueOf(fans);
		}
	}
}
