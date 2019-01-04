package de.jacksitlab.tipprobot.tippalg;

import org.apache.log4j.Logger;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.MatchScore;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TeamStats;
import de.jacksitlab.tipprobot.data.TippValidationResult;

public class TrendBasedTippAlgorithm extends BaseTippAlgorithm{

	private static final Logger LOG = Logger.getLogger(TrendBasedTippAlgorithm.class.getName());

	public static final int LIMIT_LASTGAMES = 5;
	private static float MEANDIFF_TO_WIN = 1.0f;
	public static void setMeanDiffToWin(float f)
	{
		MEANDIFF_TO_WIN = f;
	}
	public TrendBasedTippAlgorithm(LigaTable lt) {
		super(lt);
	}

	
	@Override
	public MatchTipp getTipp(int gameDay, Match match) {

		MatchCollection homeTrend = this.table.getGameDays().getLastMatchesBefore(gameDay, match.homeTeam, LIMIT_LASTGAMES);
		MatchCollection guestTrend = this.table.getGameDays().getLastMatchesBefore(gameDay, match.guestTeam, LIMIT_LASTGAMES);
		TeamStats homeStats = homeTrend.getStatistics(match.homeTeam);
		TeamStats guestStats = guestTrend.getStatistics(match.guestTeam);
		float homeMeanPts = homeStats.getPointsMean();
		float guestMeanPts = guestStats.getPointsMean();
		
		float scoreValue = (homeMeanPts-guestMeanPts)>MEANDIFF_TO_WIN?(homeMeanPts-guestMeanPts):0;
		LOG.debug(String.format("gameday=%d homepts=%f guestpts=%f score=%f",gameDay,homeMeanPts,guestMeanPts,scoreValue));
		MatchScore score  = new MatchScore(scoreValue );
		return score.getTipp(match, homeStats, guestStats);
	}

	@Override
	public TippValidationResult validate(int gameday) {
		GameDay gd = this.table.getGameDays().getByGameDay(gameday);
		TippValidationResult result=null;
		if(gd.hasResults())
		{
			result = new TippValidationResult(gd.gameDay);
			for(Match match:gd.getMatches())
			{
				result.add(this.getTipp(gd.gameDay, match));
			}
		}
		return result;
	}

	
}
