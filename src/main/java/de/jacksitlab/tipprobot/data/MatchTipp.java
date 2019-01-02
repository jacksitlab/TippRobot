package de.jacksitlab.tipprobot.data;

public class MatchTipp{

	@Override
	public String toString() {
		return "MatchTipp [gameInfos=" + gameInfos + ", tippInfos=" + tippInfos + "]";
	}
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
	/**
	 * get userpoints for tipp
	 * correct tipp => 3pts
	 * correct direction => 1pt
	 * else 0pts
	 * @return
	 */
	public int getPoints() {
		int pts = 0;
		if(this.tippInfos.goalsHome==this.gameInfos.getResult().getHomePoints() &&
				this.tippInfos.goalsGuest==this.gameInfos.getResult().getGuestPoints())
			pts=3;
		else {
			int diffg = this.gameInfos.getResult().getHomePoints()-this.gameInfos.getResult().getGuestPoints();
			int difft = this.tippInfos.goalsHome-this.tippInfos.goalsGuest;
			if((diffg== 0 && difft==0) || (diffg*difft>0))
				pts=1;
		}
			
		return pts;
	}
}
