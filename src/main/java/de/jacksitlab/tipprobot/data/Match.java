package de.jacksitlab.tipprobot.data;

import java.util.Date;

public class Match {

	@Override
	public String toString() {
		return "Match [gameId=" + gameId + ", homeTeam=" + homeTeam + ", guestTeam=" + guestTeam + ", date=" + date
				+ ", result=" + result + "]";
	}

	public final int gameId;
	public final Team homeTeam;
	public final Team guestTeam;
	private final Date date;
	private MatchResult result;
	
	public void setResult(int homePts,int guestPts)
	{
		if(this.result==null)
			this.result = new MatchResult();
		result.set(homePts,guestPts);
	}
	
	public Match(int id,Team home,Team guest, Date date)
	{
		this.gameId = id;
		this.homeTeam = home;
		this.guestTeam = guest;
		this.date = date;
	}

	public MatchResult getResult() {
		return this.result;
	}

	public boolean hasResult() {
		return this.result!=null;
	}

	public Date getDate() {
		return this.date; 
	}
	
}
