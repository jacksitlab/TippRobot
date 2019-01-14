package tipprobot;

import static org.junit.Assert.*;

import org.junit.Test;

import de.jacksitlab.tipprobot.data.MatchScore;

public class MatchScoreTests {

	@Test
	public void test() {
		
		MatchScore score1 = new MatchScore(1);
		MatchScore score2 = new MatchScore(0);
		score1.addScore(score2.getValue(), 0.5f);
		assertEquals(0.5f,score1.getValue(),0);
		MatchScore score3 = new MatchScore(2);
		assertEquals(1,score3.getValue(),0);
		
	}

}
