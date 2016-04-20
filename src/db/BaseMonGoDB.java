package db;

import com.mongodb.Mongo;
import com.mongodb.MongoURI;

public class BaseMonGoDB {
	public static void main() {
		MongoURI uri = new MongoURI("");
		Mongo db = new Mongo(uri);
	}
}
