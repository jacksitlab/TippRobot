package de.jacksitlab.tipprobot.tippalg;

import de.jacksitlab.tipprobot.data.GameDay;
import de.jacksitlab.tipprobot.data.LigaTable;
import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.data.TippValidationResult;
import de.jacksitlab.tipprobot.data.TippValidationResults;

public abstract class BaseTippAlgorithm implements TippAlgorithm {
	protected final LigaTable table;

	public BaseTippAlgorithm(LigaTable lt) {
		super();
		this.table = lt;
	}

	@Override
	public TippValidationResults validate() {
		TippValidationResults r = new TippValidationResults();
		for (GameDay gameDay : this.table.getGameDays()) {
			if (gameDay.hasResults()) {
				TippValidationResult rr = this.validate(gameDay.gameDay);
				if (rr != null)
					r.add(rr);
			}
		}
		return r;
	}

	public MatchTippCollection getTipps(int gameday, MatchCollection matches) {
		MatchTippCollection c = new MatchTippCollection();
		for (Match m : matches)
			c.add(this.getTipp(gameday,m));
		return c;
	}
}
