package tipprobot;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jacksitlab.tipprobot.data.Match;
import de.jacksitlab.tipprobot.data.MatchBetQuote;

public class BetQuoteScoreTest {

	@Test
	public void test() {
		
		Match match = null;
		float s1 = new MatchBetQuote(match, 5, 2, 5).getScore().getValue();
		assertEquals(0, s1, 0);
		s1 = new MatchBetQuote(match, 1, 2, 10).getScore().getValue();
		assertEquals(1, s1, 0.1);
		s1 = new MatchBetQuote(match, 2.7f, 3.6f, 2.5f).getScore().getValue();
		assertEquals(0, s1, 0.1);
		
		
	}

}
