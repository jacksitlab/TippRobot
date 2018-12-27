package de.jacksitlab.tipprobot;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.GameDayCollection;
import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.data.Team;
import de.jacksitlab.tipprobot.data.TeamCollection;
import de.jacksitlab.tipprobot.data.TeamStats;
import de.jacksitlab.tipprobot.database.DataRow;
import de.jacksitlab.tipprobot.database.DataTable;
import de.jacksitlab.tipprobot.database.Database;
import de.jacksitlab.tipprobot.database.DatabaseConfig;
import de.jacksitlab.tipprobot.database.MeineLigaDatabase;

public class TippRobot {

	private final int ligaId;
	private GameDayCollection ligaMatches;
	private LigaTable ligaTable;
	private TeamCollection teams;

	public TippRobot(int ligaId) {
		this.ligaId = ligaId;
	}

	public void load(DatabaseConfig dbConfig)
	{
		this.loadTeams(dbConfig);
		this.ligaTable = new LigaTable(this.ligaId, this.teams);
		this.loadLigaGames(dbConfig);
	}
	private void loadTeams(DatabaseConfig dbConfig)
	{
		MeineLigaDatabase db = new MeineLigaDatabase(dbConfig);
		this.teams = db.loadTeams(this.ligaId);
	}
	private void loadLigaGames(DatabaseConfig dbConfig) {
		
		final int ROWIDX_GAMEDAY = 0;
		String query  = "";
		Database db = new Database(dbConfig);
		DataTable dt = db.getTable(query);
		int gd = 0;
		Team homeTeam,guestTeam;
		Match match;
		GameDay gameDay;
		this.ligaMatches = new GameDayCollection();
		for(DataRow row:dt.getRows())
		{ 
			gd = Integer.parseInt(row.get(ROWIDX_GAMEDAY));
			if(this.ligaMatches.contains(gd))
			{
				gameDay = new GameDay(gd);
				this.ligaMatches.add(gameDay);
			}
			else
				gameDay = this.ligaMatches.getByGameDay(gd);
		}
	}

	public MatchTippCollection getTipps() {
		return this.getTipps(this.getnextGamesDay());
	}
	private int getnextGamesDay() {
		
		return 0;
	}

	public MatchTippCollection getTipps(int gameday) {
		MatchCollection gameDayMatches = this.ligaMatches.getGames(gameday);
		if(gameDayMatches==null)
			return null;
		MatchTippCollection gameDayTipps=new MatchTippCollection();
		for(Match match:gameDayMatches)
		{
			gameDayTipps.add(this.getTipp(gameday,match));
		}
		return gameDayTipps;
	}
	private MatchTipp getTipp(int gameday,Match match) {
		MatchCollection homeTeamMatches = this.ligaMatches.getLastGames(gameday,match.homeTeam);
		MatchCollection guestTeamMatches = this.ligaMatches.getLastGames(gameday,match.guestTeam);
		int tablePosHomeTeam = this.ligaTable.getPosition(match.homeTeam);
		int tablePosGuestTeam = this.ligaTable.getPosition(match.guestTeam);
		
		TeamStats homeTeamStats = homeTeamMatches.getStatistics(match.homeTeam);
		TeamStats guestTeamStats = guestTeamMatches.getStatistics(match.guestTeam);
		
		
		return null;
	}

	public void pushTipps(DatabaseConfig dbConfig, MatchTippCollection tipps) {
		// TODO Auto-generated method stub
		
	}

}
