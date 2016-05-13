package test;

import java.sql.SQLException;

import db.CwqDB;
import db.CwqNewDB;

public class DBTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(new CwqNewDB().getAccountId("aa"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
