package main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;

import db.CwqDB;

public class DownloadPic {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CwqDB.saveFile();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
