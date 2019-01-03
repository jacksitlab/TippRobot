package de.jacksitlab.tipprobot.tippalg;

import org.apache.log4j.Logger;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TeamStats;
import de.jacksitlab.tipprobot.data.TippValidationResult;

public class TrendBasedTippAlgorithm extends BaseTippAlgorithm{

	private static final Logger LOG = Logger.getLogger(TrendBasedTippAlgorithm.class.getName());

	private static final int LIMIT_LASTGAMES = 5;
	private static float MEANDIFF_TO_WIN = 1.2f;
	public static void setMeanDiffToWin(float f)
	{
		MEANDIFF_TO_WIN = f;
	}
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
		float homeMeanPts = homeStats.getPointsMean();
		float guestMeanPts = guestStats.getPointsMean();
		LOG.debug(String.format("homepts=%f guestpts=%f",homeMeanPts,guestMeanPts));
		if(Math.abs(homeMeanPts-guestMeanPts)>MEANDIFF_TO_WIN)
		{
			int difpts = (int)(Math.abs(homeMeanPts-guestMeanPts)*2.0f/MEANDIFF_TO_WIN);
			LOG.debug(String.format("someone wins with %d goals",difpts));
			if(homeMeanPts>guestMeanPts)
			{
				homepts = homeStats.getGoalsMean();
				guestpts = homepts-difpts;
				while(guestpts<0) {
					homepts++;guestpts++;
				}
			}
			else
			{
				guestpts = guestStats.getGoalsMean();
				homepts = guestpts-difpts;
				while(homepts<0) {
					homepts++;guestpts++;
				}
			}
		}
		else
		{
			homepts = guestpts = (int)(homeStats.getGoalsMean()+guestStats.getGoalsMean())/2;
		}
		
		return new MatchTipp(match,homepts,guestpts);
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
