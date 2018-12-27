package de.jacksitlab.tipprobot.database;

import java.util.logging.Logger;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.GameDayCollection;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.Team;
import de.jacksitlab.tipprobot.data.TeamCollection;

public class MeineLigaDatabase extends Database {

	private static Logger LOG = Logger.getLogger(MeineLigaDatabase.class.getName());
	
	private static final String DBPREFIX = "yydur_";
	private static final String TABLENAME_TEAMS = DBPREFIX + "liga_mannschaften";
	private static final String TEAM_SELECT_QUERY = "SELECT `id`, `title` FROM " + TABLENAME_TEAMS
			+ " WHERE liga_id=%d";
	private static final String MATCHES_SELECT_QUERY = null;

	public MeineLigaDatabase(DatabaseConfig dbConfig) {
		super(dbConfig);
	}

	public TeamCollection loadTeams(int ligaId) {
		DataTable dt = this.getTable(String.format(TEAM_SELECT_QUERY, ligaId));
		TeamCollection list = null;
		if (dt != null) {
			list = new TeamCollection();
			for (DataRow row : dt.getRows()) {
				try {
					list.add(new Team(Integer.parseInt(row.get(0)), row.get(1)));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			}
		}
		return list;
	}

	public GameDayCollection loadLigaMatches(int ligaId,TeamCollection teams) {

		final int COLIDX_GAMEDAY = 0;
		final int COLIDX_MATCHID = 1;
		final int COLIDX_HOMETEAMID = 2;
		final int COLIDX_GUESTTEAMID = 3;
		String query = String.format(MATCHES_SELECT_QUERY, ligaId);
		DataTable dt = this.getTable(query);
		int gd = 0,mid=0;
		Team homeTeam, guestTeam;
		GameDay gameDay;
		GameDayCollection ligaMatches=null;
		if (dt != null) {
			ligaMatches = new GameDayCollection();
			for (DataRow row : dt.getRows()) {
				gd = Integer.parseInt(row.get(COLIDX_GAMEDAY));
				if (!ligaMatches.contains(gd)) {
					gameDay = new GameDay(gd);
					ligaMatches.add(gameDay);
				} else
					gameDay = ligaMatches.getByGameDay(gd);
				homeTeam = teams.getById(Integer.parseInt(row.get(COLIDX_HOMETEAMID)));
				guestTeam = teams.getById(Integer.parseInt(row.get(COLIDX_GUESTTEAMID)));
				mid=Integer.parseInt(row.get(COLIDX_MATCHID));
				if(mid>0 && homeTeam!=null && guestTeam!=null)
				{
					gameDay.addMatch(new Match(mid, homeTeam, guestTeam));
				}
				else
					LOG.warning("found unknown match entry");
			}
		}
		return ligaMatches;
	}
}
