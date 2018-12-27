package de.jacksitlab.tipprobot.database;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONException;
import org.json.JSONObject;

public class DatabaseConfig {

	private static final String DEFAULT_FILENAME="dbconfig.json";
	private static DatabaseConfig mObj;
	private final String host;
	private final int port;
	private final String dbName;
	private final String dbUser;
	private final String dbPassword;

	public String getHost() {
		return host;
	}
	public int getPort() {
		return port;
	}
	public String getDbName() {
		return dbName;
	}
	public String getDbUser() {
		return dbUser;
	}
	public String getDbPassword() {
		return dbPassword;
	}

	
	public static DatabaseConfig getInstance() throws JSONException, IOException {

		return getInstance(null);
	}
	public static DatabaseConfig getInstance(String fn) throws JSONException, IOException {
		if(mObj==null)
			mObj=new DatabaseConfig(fn==null?DEFAULT_FILENAME:fn);
		return mObj;
	}
	
	
	private DatabaseConfig(String filename) throws JSONException, IOException {
		JSONObject o = new JSONObject(String.join("\n", Files.readAllLines(new File(filename).toPath())));
		this.host = o.getString("host");
		this.port = o.getInt("port");
		this.dbName = o.getString("database");
		this.dbUser = o.getString("dbuser");
		this.dbPassword = o.getString("dbpasswd");
	}

}
