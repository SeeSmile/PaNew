package db;

import helper.CountHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.BSON;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gargoylesoftware.htmlunit.javascript.host.Set;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

import data.WXEntity;
import data.WxNews;
import db.MongoWXEntity.NewsBean;
import db.MongoWXEntity.NewsBean;

public class BaseMonGoDB {
	
	private static String DB_ADDRESS = "203.195.238.137";
//	private static String DB_ADDRESS = "115.28.39.64";
	private static int DB_PORT = 27017;
//	private static String DB_NAME = "360netnews";
	private static String DB_NAME = "360netnews_test";
//	private static String DB_NAME = "dbtest";
	private static String DB_COLLECTION = "wlf_data_wx";
//	private static String DB_COLLECTION = "wlf_fupei";
	
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
	 * ������ݿ�
	 */
	private void connectDB() {
		initClient();
		connectDB(DB_NAME);
	}
	
	/**
	 * ������Ϣ
	 */
	public void insertInfo(Document entity) {
		connectDB();
		MongoCollection<Document> collection = mDatabase.getCollection(DB_COLLECTION);
		String account = entity.getString("account");
		List<MongoWXEntity> list = getDocumentByAccount(account);
		if(list.size() == 0) {
			MongoWXEntity wx = new Gson().fromJson(entity.toJson(), MongoWXEntity.class);
			list.add(wx);
			wx.setCount(CountHelper.getCountWx(list));
			Document document = new Document();
	        document.putAll(BasicDBObject.parse(wx.toString()));  
	        collection.insertOne(document); 
	        System.out.println("添加了账号信息：" + account);
		} else {
			MongoWXEntity entityOflist = list.get(0);
			MongoWXEntity wx = new Gson().fromJson(entity.toJson(), MongoWXEntity.class);
			if(wx.getNews() != null && wx.getNews().size() > 0) {
				wx.getNews().addAll(entityOflist.getNews());
				list.clear();
				list.add(wx);
				wx.setCount(CountHelper.getCountWx(list));
			}
			quChong(wx);
			updateAccount(wx);
			System.out.println("更新了信息:" + account);
		}
	}
	
	private void quChong(MongoWXEntity entity) {
		List<NewsBean> list = new ArrayList<>();
		for(NewsBean en : entity.getNews()) {
			if(list.contains(en)) {
				int read_num = Integer.valueOf(list.get(list.indexOf(en)).getRead_num());
				int read_num2 = Integer.valueOf(en.getRead_num());
				if(read_num < read_num2) {
					list.remove(list.indexOf(en));
					list.add(en);
				}
			} else {
				list.add(en);
			}
		}
		entity.getNews().clear();
		entity.getNews().addAll(list);
	}
	
	private void initClient() {
		if(mClient == null) {
			
			//需要用户名和密码的连接
//			MongoCredential credential = MongoCredential.createCredential("fproot", DB_NAME, "123456".toCharArray()); 
//			ServerAddress serverAddress = new ServerAddress(DB_ADDRESS, DB_PORT);
//			mClient = new MongoClient(serverAddress, Arrays.asList(credential));
			
			//不需要密码的连接
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
		MongoIterable<String> list = mDatabase.listCollectionNames();
		for(String s : list) {
			System.out.println("s:" + s);
		}
		return mDatabase.getName();
	}
	
	public void getAllInfo() {
		BasicDBObject fifter = new BasicDBObject();
		fifter.put("state", 1);
		MongoCursor<Document> cursor =  mDatabase.getCollection(DB_COLLECTION).find(fifter).iterator();
		while(cursor.hasNext()) {
			System.out.println(cursor.next().toJson());
		}
	}
	
	/**
	 * 得到名称为account账号的信息列表
	 * @param account
	 * @return
	 */
	public List<MongoWXEntity> getDocumentByAccount(String account) {
		BasicDBObject fifter = new BasicDBObject();
		fifter.put("account", account);
		MongoCursor<Document> cursor =  mDatabase.getCollection(DB_COLLECTION).find(fifter).iterator();
		List<MongoWXEntity> list = new ArrayList<>();
		while(cursor.hasNext()) {
			String text = cursor.next().toJson();
			System.out.println("text:\n" + text);
			MongoWXEntity entity = new Gson().fromJson(text, MongoWXEntity.class);
			list.add(entity);
		}
		return list;
	}
	
	public void updateAccount(MongoWXEntity entity) {
		deleteAccount(entity);
		Document doc = new Document();
		MongoCollection<Document> collection = mDatabase.getCollection(DB_COLLECTION);
		try {
			JSONObject json = new JSONObject(entity.toString());
			json.put("index_status", "0");
			 doc.putAll(BasicDBObject.parse(json.toString()));
		     collection.insertOne(doc); 
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	private void deleteAccount(MongoWXEntity entity) {
		BasicDBObject data = new BasicDBObject();  
        //删除名称为lucy的记录  
        data.put("account", entity.getAccount());  
        //传入[空实例]删除所有  
        mDatabase.getCollection(DB_COLLECTION).deleteMany(data);
	}
	
	public MongoWXEntity getSingleEntity(String account) {
		MongoWXEntity entity = new MongoWXEntity();
		BasicDBObject fifter = new BasicDBObject();
		fifter.put("account", account);
		MongoCursor<Document> cursor =  mDatabase.getCollection(DB_COLLECTION).find(fifter).iterator();
		List<MongoWXEntity> list = new ArrayList<>();
		  
		while(cursor.hasNext()) {
			String text = cursor.next().toJson();
			MongoWXEntity en = new Gson().fromJson(text, MongoWXEntity.class);
			list.add(en);
		}
		return entity;
	}
}
