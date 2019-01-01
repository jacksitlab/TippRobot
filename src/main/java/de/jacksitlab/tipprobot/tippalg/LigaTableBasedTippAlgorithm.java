package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TeamStats;

public class LigaTableBasedTippAlgorithm extends BaseTippAlgorithm{

	public LigaTableBasedTippAlgorithm(LigaTable lt)
	{
		super(lt);
	}

	public MatchTipp getTipp(Match m) {
		
		int DIFF_FOR_DRAW=3;
		int homePts=0;
		int guestPts=0;
		int diff = this.table.getPosition(m.homeTeam)-this.table.getPosition(m.guestTeam);
		TeamStats sHome=this.table.getStats(m.homeTeam);
		TeamStats sGuest = this.table.getStats(m.guestTeam);
		if(Math.abs(diff)<=DIFF_FOR_DRAW) //set as draw
		{
			int hlp=0;
			if(sHome!=null)
				hlp += sHome.getGoalsMean();
			if(sGuest!=null)
				hlp += sGuest.getGoalsMean();
			homePts=guestPts=Math.round(hlp/2);
		}
		else if(diff > 0)	//home team wins
		{
			if(sHome!=null)
				homePts = sHome.getGoalsMean();
			if(sGuest!=null)
				guestPts = sGuest.getGoalsMean();
			//fix guest points to loose
			if(guestPts>=homePts)
			{
				if(homePts>0)
					guestPts=homePts-1;
				else
					homePts=guestPts+1;
			}
		}
		else		//guest team wins
		{
			if(sHome!=null)
				homePts = sHome.getGoalsMean();
			if(sGuest!=null)
				guestPts = sGuest.getGoalsMean();
			//fix home points to loose
			if(homePts>=guestPts)
			{
				if(guestPts>0)
					homePts = guestPts-1;
				else
					guestPts=homePts+1;
			}
		}
			
		return new MatchTipp(m, homePts, guestPts);
	}
}
