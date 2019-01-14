package de.jacksitlab.tipprobot.data;

import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.Logger;

public class LigaTable {

	private static final Logger LOG = Logger.getLogger(LigaTable.class.getName());

	public final int id;
	private TeamStatsCollection teamStats;
	private final GameDayCollection gameDays;
	private final TeamCollection teams;
	private boolean recalcFlag;
	
	public GameDayCollection getGameDays() {
		return this.gameDays;
	}
	public TeamStatsCollection getTableStatistics() {
		if (this.recalcFlag) {
			teamStats.sort(PointsComparator.getInstance());
			this.recalcFlag = false;
		}
		return this.teamStats;
	}
	
	public LigaTable(int id, TeamCollection tc) {
		this.id = id;
		this.teams = tc;
		this.teamStats = new TeamStatsCollection();
		for (Team team : tc) {
			this.teamStats.add(new TeamStats(team.getId()));
		}
		this.gameDays = new GameDayCollection();
		this.recalcFlag = true;
	}

	public void addMatch(Match m, int gameDay) {
		if (!this.gameDays.contains(gameDay)) {
			this.gameDays.add(new GameDay(gameDay));
		}
		this.gameDays.addMatch(gameDay, m);
		addMatchToStats(this.teamStats, m);
		this.recalcFlag = true;
	
	}

	public int getPosition(Team homeTeam) {
		if (this.recalcFlag) {
			teamStats.sort(PointsComparator.getInstance());
			this.recalcFlag = false;
		}
		return teamStats.indexOf(homeTeam.getId()) + 1;
	}

	private static class PointsComparator implements Comparator<TeamStats> {

		private static PointsComparator mObj;

		public static Comparator<? super TeamStats> getInstance() {
			if (mObj == null)
				mObj = new PointsComparator();
			return mObj;
		}

		public int compare(TeamStats arg0, TeamStats arg1) {
			//compare points
			if (arg0.getPoints() < arg1.getPoints())
				return 1;
			else if (arg0.getPoints() > arg1.getPoints())
				return -1;
			else
			{
				//compare goal difference
				if(arg0.getGoalDiff()<arg1.getGoalDiff())
					return 1;
				else if(arg0.getGoalDiff()>arg1.getGoalDiff())
					return -1;
				else
				{
					//compare goals
					if(arg0.getGoals()<arg1.getGoals())
						return 1;
					else if(arg0.getGoals()>arg1.getGoals())
						return -1;
					else
					{
						//compare direct match
						LOG.warn("not handled comparison");
					}
				}
			}
			return 0;
		}
	}

	public TeamStats getStats(Team team) {
		for (TeamStats ts : this.teamStats) {
			if (ts.getId() == team.getId())
				return ts;
		}
		return null;
	}

	public int getMatchDayAfter(Date date) {
		int d = 0;
		LOG.debug("searching for matchday after" + date);
		for (GameDay gd : this.gameDays) {
//			LOG.debug("gd "+gd.gameDay+ " from "+gd.getStartDate()+" to "+gd.getEndDate());
			if (gd.getStartDate().after(date)) {
				d = gd.gameDay;
				LOG.debug("found. gd=" + d);
				break;
			}
		}
		return d;
	}

	public LigaTable getTableAfterGameDay(int gd) {
		LigaTable t = new LigaTable(this.id,this.teams);
		TeamStatsCollection c = (TeamStatsCollection) this.teamStats.clone();
		c.clearData();
		for (GameDay gameDay : this.gameDays) {
			if (gameDay.gameDay > gd)
				break;

			for (Match match : gameDay.getMatches()) {
				t.addMatch(match, gameDay.gameDay);
			}
		}
		return t;
	}

	private static void addMatchToStats(TeamStatsCollection c, Match match) {
		c.addMatch(match);	
	}
	public MatchCollection getAllMatches() {
		MatchCollection c=new MatchCollection();
		for(GameDay gd:this.gameDays)
		{
			c.addAll(gd.getMatches());
		}
		return c;
	}
}
