package de.jacksitlab.tipprobot.data;

/**
 * lower quote indicates higher chance to this result, because lower quote ->
 * less money to win, caused by higher chance
 * 
 * @author jack
 *
 */
public class MatchBetQuote {

	private final Match match;
	private float quoteHomeWin;
	private float quoteDraw;
	private float quoteGuestWin;

	public MatchBetQuote(Match match, float hw, float d, float gw) {
		this.match = match;
		this.quoteHomeWin = hw;
		this.quoteDraw = d;
		this.quoteGuestWin = gw;
	}

	public MatchScore getScore() {
		float f = 0;
		float aGuest = Math.min(this.quoteGuestWin, this.quoteDraw)
				+ ((Math.max(this.quoteGuestWin, this.quoteDraw) - Math.min(this.quoteGuestWin, this.quoteDraw))
						/ 2.0f);
		float aHome = Math.min(this.quoteHomeWin, this.quoteDraw)
				+ ((Math.max(this.quoteHomeWin, this.quoteDraw) - Math.min(this.quoteHomeWin, this.quoteDraw)) / 2.0f);
		if (aGuest == aHome)
			f = 0;
		else {
			float dif = 1.5f*(aGuest - aHome);
			if (aGuest > aHome) {
				f = dif / (this.quoteDraw + this.quoteGuestWin / 2.0f);
			} else {
				f = dif / (this.quoteDraw + this.quoteHomeWin / 2.0f);
			}
		}
		return new MatchScore(f);
	}

	public MatchTipp getTipp(TeamStats homeStats, TeamStats guestStats) {
		return getScore().getTipp(this.match, homeStats, guestStats);
	}
}
