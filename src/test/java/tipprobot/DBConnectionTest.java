package tipprobot;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;

import de.jacksitlab.tipprobot.data.TeamCollection;
import de.jacksitlab.tipprobot.database.DatabaseConfig;
import de.jacksitlab.tipprobot.database.MeineLigaDatabase;

public class DBConnectionTest {

	@Test
	public void test() {
		DatabaseConfig dbConfig=null;
		try {
			dbConfig = DatabaseConfig.getInstance();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertNotNull("failed loading config",dbConfig);
		MeineLigaDatabase db = new MeineLigaDatabase(dbConfig);
		TeamCollection teams = db.loadTeams(11);
		assertNotNull("no data found", teams);
		System.out.println(teams.toString());
	}

}
