package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchTipp;
import de.jacksitlab.tipprobot.data.TeamStats;
import de.jacksitlab.tipprobot.data.TippValidationResult;

public class LigaTableBasedTippAlgorithm extends BaseTippAlgorithm {

	private static final int DIFF_FOR_DRAW = 4;
	public LigaTableBasedTippAlgorithm(LigaTable lt) {
		super(lt);
	}
	public MatchTipp getTipp(int gameDay, Match m) {
		return getTipp(gameDay, m, this.table);
	}
	public static MatchTipp getTipp(int gameDay, Match m,LigaTable table) {

		int homePts = 0;
		int guestPts = 0;
		int diff = table.getPosition(m.homeTeam) - table.getPosition(m.guestTeam);
		TeamStats sHome = table.getStats(m.homeTeam);
		TeamStats sGuest = table.getStats(m.guestTeam);
		if (Math.abs(diff) <= DIFF_FOR_DRAW) // set as draw
		{
			int hlp = 0;
			if (sHome != null)
				hlp += sHome.getGoalsMean();
			if (sGuest != null)
				hlp += sGuest.getGoalsMean();
			homePts = guestPts = Math.round(hlp / 2);
		} else if (diff > 0) // home team wins
		{
			if (sHome != null)
				homePts = sHome.getGoalsMean();
			if (sGuest != null)
				guestPts = sGuest.getGoalsMean();
			// fix guest points to loose
			if (guestPts >= homePts) {
				if (homePts > 0)
					guestPts = homePts - 1;
				else
					homePts = guestPts + 1;
			}
		} else // guest team wins
		{
			if (sHome != null)
				homePts = sHome.getGoalsMean();
			if (sGuest != null)
				guestPts = sGuest.getGoalsMean();
			// fix home points to loose
			if (homePts >= guestPts) {
				if (guestPts > 0)
					homePts = guestPts - 1;
				else
					guestPts = homePts + 1;
			}
		}

		return new MatchTipp(m, homePts, guestPts);
	}

	@Override
	public TippValidationResult validate(int gameday) {

		TippValidationResult result = null;
		GameDay gameDay = this.table.getGameDays().getByGameDay(gameday);
		if (gameDay.hasResults()) {
			result = new TippValidationResult(gameDay.gameDay);
			LigaTable table = this.table.getTableAfterGameDay(gameDay.gameDay - 1);
			for(Match match:gameDay.getMatches())
			{
				result.add(getTipp(gameDay.gameDay,match,table));
			}
		}
		return result;

	}
}
