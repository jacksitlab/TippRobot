package de.jacksitlab.tipprobot.data;

import de.jacksitlab.tipprobot.database.DataRow;

public class Team {

	private final int id;
	
	public int getId() {
		return this.id;
	}
	
	public Team(int id)
	{
		this.id = id;
	}

	public static Team fromDBRow(DataRow row) {
		// TODO Auto-generated method stub
		return null;
	}

}
