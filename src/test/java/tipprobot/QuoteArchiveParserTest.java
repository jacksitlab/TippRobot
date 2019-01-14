package tipprobot;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;

import de.jacksitlab.tipprobot.betproviders.QuoteArchiveBetProvider;
import de.jacksitlab.tipprobot.data.MatchBetQuoteCollection;
import de.jacksitlab.tipprobot.data.MatchCollection;
import de.jacksitlab.tipprobot.data.TeamCollection;
import de.jacksitlab.tipprobot.database.DatabaseConfig;
import de.jacksitlab.tipprobot.database.MeineLigaDatabase;

public class QuoteArchiveParserTest {

	private static final int LIGAID = 16;

	@Test
	public void test() {
		
		DatabaseConfig dbConfig=null;
		try {
			dbConfig = DatabaseConfig.getInstance();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		MeineLigaDatabase db = new MeineLigaDatabase(dbConfig);
		
		TeamCollection tc = db.loadTeams(LIGAID);
		MatchCollection mc = db.loadLigaMatches(LIGAID, tc).getAllMatches();
		MatchBetQuoteCollection x = QuoteArchiveBetProvider.parseTestSite( mc, tc);
		System.out.println(x.toString());
	}

}
