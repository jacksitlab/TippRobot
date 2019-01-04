package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchScore;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TeamStats;
import de.jacksitlab.tipprobot.data.TippValidationResult;

public class TrendAndLigaTableBasedTippAlgorithm extends BaseTippAlgorithm{

	private final LigaTableBasedTippAlgorithm alg1;
	private final TrendBasedTippAlgorithm alg2;
	private static float scaleAlg1=0.4f;
	private static float scaleAlg2 = 1.0f-scaleAlg1;
	public static void setFactor(float weightfac) {
		scaleAlg1=weightfac;
		scaleAlg2=1-scaleAlg1;
	}
	public TrendAndLigaTableBasedTippAlgorithm(LigaTable lt) {
		super(lt);
		alg1 = new LigaTableBasedTippAlgorithm(lt);
		alg2 = new TrendBasedTippAlgorithm(lt);
	}

	@Override
	public MatchTipp getTipp(int gameDay, Match match) {
		MatchTipp m1=alg1.getTipp(gameDay, match);
		MatchTipp m2=alg2.getTipp(gameDay, match);
		//MatchCollection homeTrend = this.table.getGameDays().getLastMatchesBefore(gameDay, match.homeTeam, TrendBasedTippAlgorithm.LIMIT_LASTGAMES);
		//MatchCollection guestTrend = this.table.getGameDays().getLastMatchesBefore(gameDay, match.guestTeam, TrendBasedTippAlgorithm.LIMIT_LASTGAMES);
//		TeamStats homeStats = homeTrend.getStatistics(match.homeTeam);
//		TeamStats guestStats = guestTrend.getStatistics(match.guestTeam);
		TeamStats homeStats = this.table.getStats(match.homeTeam);
		TeamStats guestStats = this.table.getStats(match.guestTeam);
		MatchScore score = m1.getScore();
		score.addScore(m2.getScore().getValue(), scaleAlg2);
		return score.getTipp(match, homeStats, guestStats);
		
		/*
		int homepts = (int) (m1.tippInfos.goalsHome*scaleAlg1+m2.tippInfos.goalsHome*scaleAlg2);
		int guestpts= (int) (m1.tippInfos.goalsGuest*scaleAlg1+m2.tippInfos.goalsGuest*scaleAlg2);
		if(m1.tippInfos.homeTeamWins())
		{
			if(homepts<=guestpts)
			{
				homepts=guestpts+1;
			}
		}
		else if(m1.tippInfos.guestTeamWins())
		{
			if(guestpts<=homepts)
				guestpts=homepts+1;
		}
		else	//draw by table position
		{
			if(m2.tippInfos.homeTeamWins())
			{
				if(homepts<=guestpts)
					homepts=guestpts+1;
			}
			else if(m2.tippInfos.guestTeamWins())
			{
				if(guestpts<=homepts)
					guestpts=homepts+1;		
			}
			else	//draw
			{
				homepts=guestpts;
			}
		}
		return new MatchTipp(m,homepts,guestpts);
		*/
	}

	@Override
	public TippValidationResult validate(int gameDay) {
		GameDay gd = this.table.getGameDays().getByGameDay(gameDay);
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
