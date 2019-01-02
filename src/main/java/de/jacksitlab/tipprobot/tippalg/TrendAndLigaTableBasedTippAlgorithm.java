package de.jacksitlab.tipprobot.tippalg;

import java.awt.peer.LightweightPeer;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TippValidationResult;

public class TrendAndLigaTableBasedTippAlgorithm extends BaseTippAlgorithm{

	private final LigaTableBasedTippAlgorithm alg1;
	private final TrendBasedTippAlgorithm alg2;
	private static final float scaleAlg1=0.7f;
	private static final float scaleAlg2 = 1.0f-scaleAlg1;
	
	public TrendAndLigaTableBasedTippAlgorithm(LigaTable lt) {
		super(lt);
		alg1 = new LigaTableBasedTippAlgorithm(lt);
		alg2 = new TrendBasedTippAlgorithm(lt);
	}

	@Override
	public MatchTipp getTipp(int gameDay, Match m) {
		MatchTipp m1=alg1.getTipp(gameDay, m);
		MatchTipp m2=alg2.getTipp(gameDay, m);
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
