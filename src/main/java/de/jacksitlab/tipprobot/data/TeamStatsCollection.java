package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;

public class TeamStatsCollection extends ArrayList<TeamStats>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3308977772242022475L;
	
	public int indexOf(int teamId)
	{
		int idx=0;
		for(TeamStats ts:this)
		{
			if(ts.getId()==teamId)
				return idx;
			idx++;
		}
		return -1;
	}

	public TeamStats getById(int id) {
		for(TeamStats s:this)
		{
			if(s.getId() ==id)
				return s;
		}
		return null;
	}

	public void clearData() {
		for(TeamStats s:this)
		{
			s.clear();
		}
	}

	public void addMatch(Match match) {
		this.getById(match.homeTeam.getId()).addMatch(match);
		this.getById(match.guestTeam.getId()).addMatch(match);	
	}

}
