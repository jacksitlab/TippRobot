package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;



public class MatchCollection extends ArrayList<Match>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8416770520216992057L;

	public TeamStats getStatistics(Team team) {
		TeamStats stats  = new TeamStats(team.getId());
		for(Match match: this) {
			stats.addMatch(match);
		}
		return stats;
	}

	public Match find(Team home, Team guest) {
		if(home==null || guest==null)
			return null;
		for(Match m:this)
		{
			if(m.homeTeam.equals(home) && m.guestTeam.equals(guest))
				return m;
		}
		
		return null;
	}

}
