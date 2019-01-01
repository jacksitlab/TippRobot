package de.jacksitlab.tipprobot;

import java.util.Date;

import org.apache.log4j.Logger;

import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.data.TeamCollection;
import de.jacksitlab.tipprobot.database.DatabaseConfig;
import de.jacksitlab.tipprobot.database.MeineLigaDatabase;
import de.jacksitlab.tipprobot.tippalg.TippAlgorithm;
import de.jacksitlab.tipprobot.tippalg.TippAlgorithmFactory;

public class TippRobot {

	private static final Logger LOG = Logger.getLogger(TippRobot.class.getName());
	
	private final int ligaId;
	private LigaTable ligaTable;
	private TeamCollection teams;
	private TippAlgorithm tippAlg;
	private int tippAlgId;
	
	
	public TippRobot(int ligaId,int algId) {
		this.ligaId = ligaId;
		this.tippAlgId = algId;
	}

	public void load(DatabaseConfig dbConfig)
	{
		this.loadTeams(dbConfig);
		this.loadLigaGames(dbConfig);
		this.tippAlg = TippAlgorithmFactory.getInstance(this.tippAlgId,this.ligaTable);
	}
	private void loadTeams(DatabaseConfig dbConfig)
	{
		LOG.debug("loading teams...");
		MeineLigaDatabase db = new MeineLigaDatabase(dbConfig);
		this.teams = db.loadTeams(this.ligaId);
		LOG.debug(this.teams==null?"failed":("succeeded with "+this.teams.size()+" entries"));
	}
	private void loadLigaGames(DatabaseConfig dbConfig) {
		
		LOG.debug("loading matches...");
		MeineLigaDatabase db = new MeineLigaDatabase(dbConfig);
		this.ligaTable = db.loadLigaMatches(this.ligaId, this.teams);
		LOG.debug(this.ligaTable==null?"failed":("succeeded with "+this.ligaTable.getMatches().size()+" entries"));
	}

	public MatchTippCollection getTipps() {
		return this.getTipps(this.getnextGamesDay());
	}
	private int getnextGamesDay() {
		
		return this.ligaTable.getMatchDayAfter(new Date());
	}

	public MatchTippCollection getTipps(int gameday) {
		MatchCollection gameDayMatches = this.ligaTable.getMatches().getGames(gameday);
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
		return this.tippAlg.getTipp( match);
	}

	public void pushTipps(DatabaseConfig dbConfig, MatchTippCollection tipps) {
		// TODO Auto-generated method stub
		
	}

}
