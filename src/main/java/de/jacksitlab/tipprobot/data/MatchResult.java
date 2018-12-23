package de.jacksitlab.tipprobot.data;

public class MatchResult {

	public static final int WIN = 1;
	public static final int DRAW = 0;
	public static final int LOOSE = -1;
	
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
