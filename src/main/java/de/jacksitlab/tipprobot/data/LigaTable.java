package de.jacksitlab.tipprobot.data;

import java.util.Comparator;
import java.util.Date;

import org.apache.log4j.Logger;

public class LigaTable {

	private static final Logger LOG = Logger.getLogger(LigaTable.class.getName());
	
	public final int id;
	private TeamStatsCollection teamsList;
	private final GameDayCollection gameDays;
	private boolean recalcFlag;

	public GameDayCollection getMatches() {
		return this.gameDays;
	}
	
	public LigaTable(int id, TeamCollection tc) {
		this.id = id;
		this.teamsList = new TeamStatsCollection();
		for (Team team : tc) {
			this.teamsList.add(new TeamStats(team.getId()));
		}
		this.gameDays = new GameDayCollection();
		this.recalcFlag = true;
	}

	public void addMatch(Match m,int gameDay) {
		if(!this.gameDays.contains(gameDay))
		{
			this.gameDays.add(new GameDay(gameDay));
		}
		this.gameDays.addMatch(gameDay,m);
		TeamStats shome = this.teamsList.getById(m.homeTeam.getId());
		TeamStats sguest = this.teamsList.getById(m.guestTeam.getId());
		if (m.hasResult()) {
			shome.incGames();
			sguest.incGames();
			shome.addGoals(m.getResult().getHomePoints(),m.getResult().getGuestPoints());
			sguest.addGoals(m.getResult().getGuestPoints(),m.getResult().getHomePoints());
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

	public int getMatchDayAfter(Date date) {
		int d=0;
		LOG.debug("searching for matchday after"+date);
		for(GameDay gd:this.gameDays) {
//			LOG.debug("gd "+gd.gameDay+ " from "+gd.getStartDate()+" to "+gd.getEndDate());
			if(gd.getStartDate().after(date))
			{
				d=gd.gameDay;
				LOG.debug("found. gd="+d);
				break;
			}
		}
		return d;
	}
}
