package de.jacksitlab.tipprobot;

import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.json.JSONException;

import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.database.DatabaseConfig;
import de.jacksitlab.tipprobot.database.MeineLigaDatabase;
import de.jacksitlab.tipprobot.tippalg.TippAlgorithmFactory;

public class Program {

	private static Logger LOG;

	private static void init_log(Level lvl) {
		Logger l = Logger.getRootLogger();
		l.setLevel(lvl);
		l.addAppender(new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
		LOG = Logger.getLogger(Program.class.getName());
	}

	public static void main(String[] args) {

		int ligaId = 16;

		DatabaseConfig dbConfig = null;
		String dbFilename = null;
		int tippAlgId = TippAlgorithmFactory.ID_LIGABASED;
		boolean run = true;
		String logLevel = "DEBUG";
		if (args != null && args.length > 0) {
			for (int i = 0; i < args.length; i++) {
				if (args[i].equals("--db")) {
					dbFilename = args[i + 1];
					i++;
				} else if (args[i].equals("--alg")) {
					int x = Integer.parseInt(args[i + 1]);
					if (TippAlgorithmFactory.validate(x))
						tippAlgId = x;
					i++;
				} else if (args[i].equals("--alglist")) {
					System.out.println(TippAlgorithmFactory.getAlgorithmList());
					run = false;
				} else if (args[i].equals("--log")) {
					logLevel = args[i + 1];
					i++;
				}
			}
		}
		init_log(Level.toLevel(logLevel));
		LOG.debug("starting...");

		if (run) {
			try {
				dbConfig = dbFilename == null ? DatabaseConfig.getInstance() : DatabaseConfig.getInstance(dbFilename);
			} catch (JSONException e) {
				error(e);
			} catch (IOException e) {
				error(e);
			}
			MeineLigaDatabase db = new MeineLigaDatabase(dbConfig);
			db.loadTeamsById(new String[] { "279" });
			HashMap<Integer, String> ligas = db.loadLigas();
			LOG.debug(ligas);
			if (ligas.containsKey(ligaId)) {
				LOG.debug("found requested ligaid " + ligaId + " :" + ligas.get(ligaId));
				TippRobot robot = new TippRobot(ligaId, tippAlgId);
				robot.load(dbConfig);
				MatchTippCollection tipps = robot.getTipps();
				robot.pushTipps(dbConfig, tipps);
				System.out.println(String.valueOf(tipps));
			} else {
				LOG.warn("ligaid " + ligaId + " not found in db");
			}
		}
		System.exit(0);

	}

	private static void error(Exception e) {
		LOG.error(e.getMessage());
		System.exit(1);
	}

}
