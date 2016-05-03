package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import data.WXEntity;

public class YunSpider extends BaseDB {
	
	public static final int STATE_NORMAL = 1;
	public static final int STATE_NOAVATAR = 2;
	public static final int STATE_NOACCOUNT = 3;
	public static final int STATE_NOEXIST = 4;
	
	private final String TABLE_WEIXIN = "spider_weixin";

	public YunSpider() {
		super("115.28.39.64", "spider", "fupei", "password");
	}

	public void insertInfo(WXEntity entity) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<>();
		list.add(new DbParams("account", entity.getAccount()));
		list.add(new DbParams("info", entity.toString()));
		list.add(new DbParams("state", STATE_NORMAL));
		String sql = createInsertSql(TABLE_WEIXIN, list);
		insertInfo(sql, list);
	}
	
	private void insertInfo(String sql, ArrayList<DbParams> list) throws SQLException {
		PreparedStatement pst = getPrepared(sql);
		initPst(pst, list);
		pst.execute();
	}
	
	public void insertNoAccountInfo(String account) throws SQLException {
		insertInfoState(account, STATE_NOACCOUNT);
	}
	
	public void insertNoAvatarInfo(String account) throws SQLException {
		insertInfoState(account, STATE_NOAVATAR);
	}
	
	private void insertInfoState(String account, int state) throws SQLException {
		ArrayList<DbParams> list = new ArrayList<>();
		list.add(new DbParams("account", account));
		list.add(new DbParams("state", state));
		String sql = createInsertSql(TABLE_WEIXIN, list);
		insertInfo(sql, list);
	}
	
	public int getStateByAccount(String account) throws SQLException {
		String sql = "select * from " + TABLE_WEIXIN + " where account=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, account);
		ResultSet result = pst.executeQuery();
		if(result.next()) {
			return result.getInt("state");
		}
		return STATE_NOEXIST;
	}
	
	public void setStateByAccount(String account, int state) throws SQLException {
		String sql = "update " + TABLE_WEIXIN + " set state=? where account=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setInt(1, state);
		pst.setString(2, account);
		pst.execute();
	}
	
	public String getInfoByAccount(String account) throws SQLException {
		String sql = "select * from " + TABLE_WEIXIN + " where account=?";
		PreparedStatement pst = getPrepared(sql);
		pst.setString(1, account);
		ResultSet result = pst.executeQuery();
		if(result.next()) {
			return result.getString("info");
		}
		return null;
	}
}
