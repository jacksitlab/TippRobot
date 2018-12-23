package de.jacksitlab.tipprobot.data;

public class Match {

	public final int gameId;
	public final Team homeTeam;
	public final Team guestTeam;
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
		this.homeTeam = home;
		this.guestTeam = guest;
	}

	public MatchResult getResult() {
		return this.result;
	}

	public boolean hasResult() {
		return this.result!=null;
	}
	
}
