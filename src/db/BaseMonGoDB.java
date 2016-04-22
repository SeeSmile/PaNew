package db;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.sun.jndi.cosnaming.CNNameParser;

import data.WXMonGoEntity;

public class BaseMonGoDB {
	
//	private static String DB_ADDRESS = "192.168.0.197";
	private static String DB_ADDRESS = "203.195.238.137";
	private static int DB_PORT = 27017;
//	private static String DB_NAME = "dbwx";
	private static String DB_NAME = "360netnews";
	private static String DB_COLLECTION = "data_wx";
	
	private MongoClient mClient;
	private MongoDatabase mDatabase;
	
	private static BaseMonGoDB mDB;
	
	private BaseMonGoDB() {
		connectDB();
	}
	
	public static BaseMonGoDB getInstance() {
		if(mDB == null) {
			mDB = new BaseMonGoDB();
		}
		return mDB;
	}
	
	/**
	 * 连接数据库
	 */
	private void connectDB() {
		initClient();
		connectDB(DB_NAME);
	}
	
	/**
	 * 插入信息
	 */
	public void insertInfo(Document document) {
		connectDB();
		MongoCollection<Document> collection = mDatabase.getCollection(DB_COLLECTION);
        List<Document> documents = new ArrayList<Document>();  
        documents.add(document);  
        collection.insertMany(documents);  
	}
	
	private void initClient() {
		if(mClient == null) {
			mClient = new MongoClient(DB_ADDRESS, DB_PORT);
		}
	}
	
	private void connectDB(String name) {
		if(mDatabase == null) {
			mDatabase = mClient.getDatabase(name);
		} else {
			if(!mDatabase.getName().equals(name)) {
				mDatabase = mClient.getDatabase(name);
			}
		}
	}
	
	public String getName() {
		return mDatabase.getName();
	}
	
	public void getAllInfo() {
		WXMonGoEntity en = (WXMonGoEntity) mDatabase.getCollection(DB_COLLECTION, WXMonGoEntity.class);
		System.out.println(en.toString());
	}
}
