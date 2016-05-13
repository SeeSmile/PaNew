package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.CwqDBObj;
import data.CwqWX;

public class CwqNewDB extends BaseDB {
	
	private final static String ip = "192.168.0.196";
	private final static String dbname = "media";
	private final static String name = "media";
	private final static String password = "mediamedia";
	
	public CwqNewDB() {
		super(ip, dbname, name, password);
	}
	
	public void insertInfo(CwqWX entity, String type_push, String type_area, String type_type) throws SQLException {
		String id = getAccountId(entity.getBs_account_name().trim());
		if(id == null) {
			insertBaseInfo(entity, type_push, type_area, type_type);
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
		if(type_area.equals("3413")) {
			list.add(new DbParams(CwqDBObj.KEY_PROVINCE, "1"));
		} else {
			list.add(new DbParams(CwqDBObj.KEY_PROVINCE, type_area));
		}
		
		list.add(new DbParams(CwqDBObj.KEY_CATEGORY, type_type));
		list.add(new DbParams(CwqDBObj.KEY_TIME, System.currentTimeMillis() / 1000 + ""));
		list.add(new DbParams(CwqDBObj.KEY_WEEK_READ, entity.getBs_weekly_read_avg()));
		list.add(new DbParams(CwqDBObj.KEY_QR_CODE, entity.getBs_qr_code()));
		
		if(type_push.equals("1")) {
			list.add(new DbParams(CwqDBObj.KEY_D_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_D_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_D_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_D_THREE, getRealPrice(entity.getDtwqtwz())));
			
			list.add(new DbParams(CwqDBObj.KEY_DR_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_DR_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_DR_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_DR_THREE, getRealPrice(entity.getDtwqtwz())));
		} else {
			list.add(new DbParams(CwqDBObj.KEY_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_THREE, getRealPrice(entity.getDtwqtwz())));
			
			list.add(new DbParams(CwqDBObj.KEY_R_TOP, getRealPrice(entity.getDtwdyt())));
			list.add(new DbParams(CwqDBObj.KEY_R_ONE, getRealPrice(entity.getDtwdet())));
			list.add(new DbParams(CwqDBObj.KEY_R_TWO, getRealPrice(entity.getDtwqtwz())));
			list.add(new DbParams(CwqDBObj.KEY_R_THREE, getRealPrice(entity.getDtwqtwz())));
		}
		String sql = createInsertSql(CwqDBObj.TABLE_WEIXIN, list);
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		
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
}
