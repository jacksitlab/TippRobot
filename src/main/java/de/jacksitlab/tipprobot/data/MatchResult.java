package de.jacksitlab.tipprobot.data;

public class MatchResult {

	private int homePoints;
	private int guestPoints;

	public int getHomePoints()
	{return this.homePoints;}
	public int getGuestPoints()
	{return this.guestPoints;}
	
	public void set(int homePts, int guestPts) {
		this.homePoints = homePts;
		this.guestPoints = guestPts;
	}
	public boolean homeTeamHasWon()
	{
		return this.homePoints>this.guestPoints;
	}
	public boolean guestTeamHasWon()
	{
		return this.guestPoints>this.homePoints;
	}
	public boolean isADraw()
	{
		return this.homePoints == this.guestPoints;
	}

}
