package de.jacksitlab.tipprobot;

import de.jacksitlab.tipprobot.data.GameDayCollection;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.data.Team;
import de.jacksitlab.tipprobot.database.DataRow;
import de.jacksitlab.tipprobot.database.DataTable;
import de.jacksitlab.tipprobot.database.Database;
import de.jacksitlab.tipprobot.database.DatabaseConfig;

public class TippRobot {

	private final int ligaId;
	private GameDayCollection ligaMatches;

	public TippRobot(int ligaId) {
		this.ligaId = ligaId;
	}

	public void loadLigaGames(DatabaseConfig dbConfig) {
		
		String query  = "";
		Database db = new Database(dbConfig);
		DataTable dt = db.getTable(query);
		int gd = 0;
		Team homeTeam,guestTeam;
		Match match;
		for(DataRow row:dt.getRows())
		{ 
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
			gameDayTipps.add(this.getTipp(match));
		}
		return gameDayTipps;
	}
	private MatchTipp getTipp(Match match) {
		// TODO Auto-generated method stub
		return null;
	}

	public void pushTipps(DatabaseConfig dbConfig, MatchTippCollection tipps) {
		// TODO Auto-generated method stub
		
	}

}
