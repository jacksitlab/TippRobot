package tipprobot;

import static org.junit.Assert.*;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;

import de.jacksitlab.tipprobot.database.DatabaseConfig;

public class ConfigTest {

	private static final String TESTCONFIG_FILENAME = "dbconfig.example.json";

	@Test
	public void test() {
		DatabaseConfig cfg=null;
		try {
			cfg =new DatabaseConfig(TESTCONFIG_FILENAME);
		} catch (JSONException e) {
			fail(e.getMessage());
		} catch (IOException e) {
			fail(e.getMessage());
		}
		assertNotNull(cfg);
	}

}
