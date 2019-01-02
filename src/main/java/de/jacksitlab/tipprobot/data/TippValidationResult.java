package de.jacksitlab.tipprobot.data;

public class TippValidationResult {

	private final int gameDay;
	private final MatchTippCollection matchTipps;
	
	public TippValidationResult(int gameDay) {
		this.gameDay = gameDay;
		this.matchTipps = new MatchTippCollection();
	}

	public void add(MatchTipp tipp) {
		this.matchTipps.add(tipp);
	}

	@Override
	public String toString() {
		return "TippValidationResult [gameDay=" + gameDay + ", matchTipps=" + matchTipps + "]";
	}
	public String printResults() {
		final String LR = "\n";
		int pts=0,pt;
		StringBuilder sb = new StringBuilder();
		sb.append("GameDay "+gameDay+":"+LR);
		sb.append("================================"+LR);
		for(MatchTipp mt: this.matchTipps)
		{
			sb.append(String.format("%s %d - %d %s | %d : %d", mt.gameInfos.homeTeam.getTitle(),
					mt.gameInfos.getResult().getHomePoints(),
					mt.gameInfos.getResult().getGuestPoints(),
					mt.gameInfos.guestTeam.getTitle(),
					mt.tippInfos.goalsHome,
					mt.tippInfos.goalsGuest	));
			pt = mt.getPoints();
			sb.append(String.format("\t%d points",pt)+LR);
			pts+=pt;
		}
		sb.append("  points: "+pts+LR+LR);
		return sb.toString();
	}

	public int getPoints() {
		int pts=0;
		for(MatchTipp mt: this.matchTipps)
		{
			pts+=mt.getPoints();
		}
		return pts;
	}

}
