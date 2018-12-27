package de.jacksitlab.tipprobot;

import java.io.IOException;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONException;

import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.database.DatabaseConfig;

public class Program {

	private static Logger LOG;
	private static void init_log(Level lvl)
	{
		Logger l = Logger.getRootLogger();
		l.setLevel(lvl);
		l.addAppender(new ConsoleAppender());
		LOG = Logger.getLogger(Program.class.getName());
	}
	public static void main(String[] args) {

		int ligaId = 11;
		init_log(Level.DEBUG);
		DatabaseConfig dbConfig = null;
		try {
			dbConfig = DatabaseConfig.getInstance();
		} catch (JSONException e) {
			error(e);
		} catch (IOException e) {
			error(e);
		}
		TippRobot robot = new TippRobot(ligaId);
		robot.load(dbConfig);
		MatchTippCollection tipps = robot.getTipps();
		robot.pushTipps(dbConfig, tipps);
		System.out.println(tipps.toString());
		System.exit(0);

	}

	private static void error(Exception e) {
		LOG.error(e.getMessage());
		System.exit(1);
	}

}
