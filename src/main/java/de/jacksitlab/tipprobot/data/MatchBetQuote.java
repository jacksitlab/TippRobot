package de.jacksitlab.tipprobot.data;

/**
 * lower quote indicates higher chance to this result, because lower quote ->
 * less money to win, caused by higher chance
 * 
 * @author jack
 *
 */
public class MatchBetQuote {

	public static final int VAR1=0;
	public static final int VAR2=1;
	private static int VAR=VAR1;
	private static float qDiff=2.0f;
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
		if(VAR==VAR1)
		{
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
		}
		else if (VAR==VAR2){
			float b=0.2f;
			float q=Math.max(this.quoteHomeWin, this.quoteGuestWin)/Math.min(this.quoteHomeWin, this.quoteGuestWin);
			if(q<=qDiff)
			{
				f=q/qDiff*b;
			}
			else
			{
				f=q/qDiff*b;
			}
			
		}
		return new MatchScore(f);
	}

	@Override
	public String toString() {
		return "MatchBetQuote [match=" + match + ", quoteHomeWin=" + quoteHomeWin + ", quoteDraw=" + quoteDraw
				+ ", quoteGuestWin=" + quoteGuestWin + "]";
	}

	public MatchTipp getTipp(TeamStats homeStats, TeamStats guestStats) {
		return getScore().getTipp(this.match, homeStats, guestStats);
	}
}
