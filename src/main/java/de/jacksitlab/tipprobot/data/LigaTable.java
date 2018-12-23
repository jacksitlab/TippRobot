package de.jacksitlab.tipprobot.data;

import java.util.Comparator;
import java.util.HashMap;

public class LigaTable {

	public final int id;
	private TeamStatsCollection teamsList;
	private boolean recalcFlag;

	public LigaTable(int id, TeamCollection tc) {
		this.id = id;
		for (Team team : tc) {
			this.teamsList.add(new TeamStats(team.getId()));
		}
		this.recalcFlag = true;
	}

	public void addMatch(Match m) {
		TeamStats shome = this.teamsList.getById(m.homeTeam.getId());
		TeamStats sguest = this.teamsList.getById(m.guestTeam.getId());
		if (m.hasResult()) {
			shome.incGames();
			sguest.incGames();
			if (m.getResult().homeTeamHasWon()) {
				shome.incWins();
				sguest.incLooses();
			}
			if (m.getResult().isADraw()) {
				shome.incDraws();
				sguest.incDraws();
			}
			if (m.getResult().guestTeamHasWon()) {
				shome.incLooses();
				sguest.incWins();
			}
			this.recalcFlag = true;
		}
	}

	public int getPosition(Team homeTeam) {
		if (this.recalcFlag)
			teamsList.sort(PointsComparator.getInstance());
		return teamsList.indexOf(homeTeam.getId())+1;
	}

	private static class PointsComparator implements Comparator<TeamStats> {

		private static PointsComparator mObj;
		public static Comparator<? super TeamStats> getInstance() {
			if(mObj==null)
				mObj=new PointsComparator();
			return mObj;
		}

		public int compare(TeamStats arg0, TeamStats arg1) {
			if (arg0.getPoints() > arg1.getPoints())
				return 1;
			if (arg0.getPoints() < arg1.getPoints())
				return -1;
			return 0;
		}
	}

	public TeamStats getStats(Team team) {
		for(TeamStats ts: this.teamsList)
		{
			if(ts.getId()==team.getId())
				return ts;
		}
		return null;
	}
}
