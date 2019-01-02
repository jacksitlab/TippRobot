package de.jacksitlab.tipprobot.data;

public class Tipp {

	@Override
	public String toString() {
		return "Tipp [gameId=" + gameId + ", goalsHome=" + goalsHome + ", goalsGuest=" + goalsGuest + "]";
	}

	public final int gameId;
	public int goalsHome;
	public int goalsGuest;
	
	public Tipp(int id)
	{
		this.gameId = id;
	}

	public boolean homeTeamWins() {
		return this.goalsHome>this.goalsGuest;
	}

	public boolean guestTeamWins() {
		return this.goalsGuest>this.goalsHome;
	}
	
}
