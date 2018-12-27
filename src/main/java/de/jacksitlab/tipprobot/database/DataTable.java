package de.jacksitlab.tipprobot.database;

import java.util.ArrayList;
import java.util.List;

public class DataTable {

	private final List<DataRow> rows;
	
	public List<DataRow> getRows() {
		return this.rows; 
	}

	public DataTable()
	{
		this.rows = new ArrayList<DataRow>();
	}
	public void addRow(DataRow row) {
		this.rows.add(row);
	}

}
