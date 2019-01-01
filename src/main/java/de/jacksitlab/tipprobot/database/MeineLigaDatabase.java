package de.jacksitlab.tipprobot.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.log4j.Logger;

import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.Team;
import de.jacksitlab.tipprobot.data.TeamCollection;

public class MeineLigaDatabase extends Database {

	private static Logger LOG = Logger.getLogger(MeineLigaDatabase.class.getName());

	private static final String DBPREFIX = "yydur_";
	private static final String TABLENAME_TEAMS = DBPREFIX + "liga_mannschaften";
	private static final String TABLENAME_MATCHES = DBPREFIX + "liga_spiele";
	private static final String TABLENAME_LIGAS = DBPREFIX +  "liga_liga";
	private static final String TEAM_SELECT_QUERY = "SELECT `id`, `title` FROM " + TABLENAME_TEAMS
			+ " WHERE liga_id=%d";
	private static final String MATCHES_SELECT_QUERY = "SELECT `id`, `spieltag`, `heim_id`, `gast_id`, `angesetzt` FROM "
			+ TABLENAME_MATCHES + " WHERE `liga_id`=%d";

	private static final String LIGA_SELECT_QUERY = "SELECT `id`,`title` FROM "+TABLENAME_LIGAS ;

	private static final String TEAMBYID_SELECT_QUERY = "SELECT `id`, `title` FROM "+TABLENAME_TEAMS+ " WHERE `id` in (%s)";

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
	public TeamCollection loadTeamsById(String[] teamIds ) {
		DataTable dt = this.getTable(String.format(TEAMBYID_SELECT_QUERY, String.join(",",teamIds)));
		TeamCollection list = null;
		if (dt != null) {
			list = new TeamCollection();
			for (DataRow row : dt.getRows()) {
				try {
					LOG.debug(row);
					list.add(new Team(Integer.parseInt(row.get(0)), row.get(1)));
				} catch (NumberFormatException e) {
					LOG.warn(e.getMessage());
				}

			}
		}
		return list;
	}
	
	public LigaTable loadLigaMatches(int ligaId, TeamCollection teams) {

		final int COLIDX_GAMEDAY = 1;
		final int COLIDX_MATCHID = 0;
		final int COLIDX_HOMETEAMID = 2;
		final int COLIDX_GUESTTEAMID = 3;
		final int COLIDX_DATE = 4;
		String query = String.format(MATCHES_SELECT_QUERY, ligaId);
		DataTable dt = this.getTable(query);
		int gd = 0, mid = 0;
		Team homeTeam, guestTeam;
		LigaTable table = null;
		Date date;
		SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (dt != null) {
			table = new LigaTable(ligaId, teams);
			for (DataRow row : dt.getRows()) {
				try {
					gd = Integer.parseInt(row.get(COLIDX_GAMEDAY));
					homeTeam = teams.getById(Integer.parseInt(row.get(COLIDX_HOMETEAMID)));
					guestTeam = teams.getById(Integer.parseInt(row.get(COLIDX_GUESTTEAMID)));
					mid = Integer.parseInt(row.get(COLIDX_MATCHID));
					date = parser.parse(row.get(COLIDX_DATE));
					if (mid > 0 && homeTeam != null && guestTeam != null) {
						table.addMatch(new Match(mid, homeTeam, guestTeam, date), gd);
					} else
						LOG.warn("found unknown match entry");
				} catch (NumberFormatException e) {

				} catch (ParseException e) {
					LOG.warn("cannot parse dt string: " + row.get(COLIDX_DATE));
				}
			}
		}
		return table;
	}
	public HashMap<Integer,String> loadLigas() {
		final int COLIDX_ID = 0;
		final int COLIDX_LIGANAME = 1;
		DataTable dt = this.getTable(LIGA_SELECT_QUERY);
		HashMap<Integer,String> list = null;
		if (dt != null) {
			list = new HashMap<Integer,String>();
			for (DataRow row : dt.getRows()) {
				try {
					list.put(Integer.parseInt(row.get(COLIDX_ID)),row.get(COLIDX_LIGANAME));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}

			}
		}
		return list;
	}

}
