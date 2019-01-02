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

}
