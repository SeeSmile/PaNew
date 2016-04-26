package db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Web360DB extends BaseDB {
	
	private final static String ip = "192.168.0.196";
	private final static String dbname = "360";
	private final static String name = "root";
	private final static String password = "leikaixinG1234";

	public Web360DB() {
		super(ip, dbname, name, password);
	}

	public void getKeyWordInfo() throws SQLException {
		String sql = "SELECT om.oid,om.keyword,om.title from wlf_order_status as os ,wlf_order_manuscript as om where om.id = os.order_demand_id and os.order_type in(9,10,11,12)";
		PreparedStatement state = getPrepared(sql);
		ResultSet result = state.executeQuery();
		while(result.next()) {
			System.out.println("id:" + result.getString(1) + ", keyword: " + result.getString(2) + ", title: " + result.getString(3));
		}
		close();
	}
	
}
