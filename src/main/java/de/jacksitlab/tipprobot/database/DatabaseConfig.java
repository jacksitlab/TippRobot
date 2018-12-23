package de.jacksitlab.tipprobot.database;

public class DatabaseConfig {

	private static DatabaseConfig mObj;
	
	public static DatabaseConfig getInstance() {
		if(mObj==null)
			mObj=new DatabaseConfig();
		return mObj;
	}

}
