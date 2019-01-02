package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TeamStats;
import de.jacksitlab.tipprobot.data.TippValidationResult;

public class TrendBasedTippAlgorithm extends BaseTippAlgorithm{

	private static final int LIMIT_LASTGAMES = 5;
	public TrendBasedTippAlgorithm(LigaTable lt) {
		super(lt);
	}

	
	@Override
	public MatchTipp getTipp(int gameDay, Match match) {
		int homepts=0,guestpts=0;
		MatchCollection homeTrend = this.table.getGameDays().getLastMatchesBefore(gameDay, match.homeTeam, LIMIT_LASTGAMES);
		MatchCollection guestTrend = this.table.getGameDays().getLastMatchesBefore(gameDay, match.guestTeam, LIMIT_LASTGAMES);
		TeamStats homeStats = homeTrend.getStatistics(match.homeTeam);
		TeamStats guestStats = guestTrend.getStatistics(match.guestTeam);
		
		return new MatchTipp(match,homepts,guestpts);
	}

	@Override
	public TippValidationResult validate(int gameday) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
