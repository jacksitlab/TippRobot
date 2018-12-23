package de.jacksitlab.tipprobot.data;

public class MatchTipp{

	public final Match gameInfos;
	public Tipp tippInfos;
	
	public MatchTipp(Match g, int homePts, int guestPts)
	{
		this.gameInfos = g;
		this.setTipp(homePts, guestPts);
	}
	public void setTipp(int homePts,int guestPts)
	{
		if(this.tippInfos==null)
			this.tippInfos = new Tipp(this.gameInfos.gameId);
		this.tippInfos.goalsHome = homePts;
		this.tippInfos.goalsGuest = guestPts;
	}
}
