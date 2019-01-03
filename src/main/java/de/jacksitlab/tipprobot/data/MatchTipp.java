package de.jacksitlab.tipprobot.data;

public class MatchTipp{

	@Override
	public String toString() {
		return "MatchTipp [gameInfos=" + gameInfos + ", tippInfos=" + tippInfos + "]";
	}
	public final Match gameInfos;
	public Tipp tippInfos;
	private MatchScore score;
	
	public MatchTipp(Match g, int homePts, int guestPts)
	{
		this(g, homePts, guestPts, null);
	}
	public MatchTipp(Match match, int homePts, int guestPts, MatchScore matchScore) {
		this.gameInfos = match;
		this.setTipp(homePts, guestPts);
		this.score = matchScore;
	}
	public void setScore(MatchScore s) {
		this.score=s;
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
		if(this.isCorrect())
			pts=3;
		else {
			if(this.isDirection())
				pts=1;
		}
			
		return pts;
	}
	public MatchScore getScore() {
		return this.score;
	}
	public boolean isDirection() {
		int diffg = this.gameInfos.getResult().getHomePoints()-this.gameInfos.getResult().getGuestPoints();
		int difft = this.tippInfos.goalsHome-this.tippInfos.goalsGuest;
		return ((diffg== 0 && difft==0) || (diffg*difft>0));
		
	}
	
	public boolean isCorrect() {
		return (this.tippInfos.goalsHome==this.gameInfos.getResult().getHomePoints() &&
				this.tippInfos.goalsGuest==this.gameInfos.getResult().getGuestPoints());
		
	}
}
