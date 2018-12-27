package de.jacksitlab.tipprobot.data;

public class Team {

	@Override
	public String toString() {
		return "Team [id=" + id + ", title=" + title + "]";
	}

	private final int id;
	private final String title;
	
	public int getId() {
		return this.id;
	}
	public String getTitle() {
		return this.title;
	}
	
	public Team(int id,String t)
	{
		this.id = id;
		this.title = t;
	}

}
