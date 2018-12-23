package de.jacksitlab.tipprobot.data;

public class Match {

	public final int gameId;
	public final Team home;
	public final Team guest;
	private MatchResult result;
	
	public void setResult(int homePts,int guestPts)
	{
		if(this.result==null)
			this.result = new MatchResult();
		result.set(homePts,guestPts);
	}
	
	public Match(int id,Team home,Team guest)
	{
		this.gameId = id;
		this.home = home;
		this.guest = guest;
	}
	
}
