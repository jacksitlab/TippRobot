package de.jacksitlab.tipprobot.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

	private final DatabaseConfig config;
	private Connection connection;
	
	public Database(DatabaseConfig dbConfig) {
		this.config = dbConfig;
	}
	private boolean connect()
	{
		if(this.connection==null)
			try {
				connection=DriverManager.getConnection("jdbc:mysql://"+this.config.getHost()+":"+this.config.getPort()+"/"+this.config.getDbName(), this.config.getDbUser(), this.config.getDbPassword());
			} catch (SQLException e) {
				
			}
		
		return this.connection!=null;
	}
	private void disconnect()
	{
		if(this.connection!=null)
		{
			try {
				this.connection.close();
			} catch (SQLException e) {
			
			}
			this.connection=null;
		}
	}
	public DataTable getTable(String query) {
		ResultSet rs;
		DataTable dt=null;
		int cols;
		if(this.connect())
		{
			try {
				Statement stmt=this.connection.createStatement();  
				rs = stmt.executeQuery(query);
				dt=new DataTable();
				while(rs.next()) 
				{
					cols=rs.getMetaData().getColumnCount();
					DataRow row = new DataRow();
					for(int i=0;i<cols;i++)
						row.add(rs.getString(i+1));
					dt.addRow(row);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
			
			this.disconnect();
		}
		return dt;
	}

}
