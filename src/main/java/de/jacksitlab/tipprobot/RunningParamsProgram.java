package de.jacksitlab.tipprobot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.NullAppender;
import org.json.JSONException;

import de.jacksitlab.tipprobot.data.TippValidationResults;
import de.jacksitlab.tipprobot.database.DatabaseConfig;
import de.jacksitlab.tipprobot.database.MeineLigaDatabase;
import de.jacksitlab.tipprobot.tippalg.LigaTableBasedTippAlgorithm;
import de.jacksitlab.tipprobot.tippalg.TippAlgorithmFactory;
import de.jacksitlab.tipprobot.tippalg.TrendAndLigaTableBasedTippAlgorithm;
import de.jacksitlab.tipprobot.tippalg.TrendBasedTippAlgorithm;

public class RunningParamsProgram {

	private static final int PROGRAM_DEFAULT = 0;
	private static final int PROGRAM_VALIDATE = 1;
	private static final int PROGRAM_PRINTTABLE = 2;
	private static final float[] alg_factors= {0.4f,0.5f,0.6f,0.7f,0.8f};
	private static final float[] alg_diffstowin= {0.6f,0.7f,0.8f,0.9f,1.0f,1.1f,1.2f};
	private static final int[] alg_difffordraw= {2,3,4,5,6,7,8};
	private static Logger LOG;
	private static String outputFilename = null;

	private static void init_log(Level lvl, boolean silentMode) {
		Logger l = Logger.getRootLogger();
		l.setLevel(lvl);
		l.addAppender(silentMode?new NullAppender():new ConsoleAppender(new PatternLayout(PatternLayout.TTCC_CONVERSION_PATTERN)));
		LOG = Logger.getLogger(RunningParamsProgram.class.getName());
	}

	public static void main(String[] args) {

		int ligaId = 16;
		int program = PROGRAM_DEFAULT;
		DatabaseConfig dbConfig = null;
		String dbFilename = null;
		int tippAlgId = TippAlgorithmFactory.ID_LIGABASED;
		boolean run = true;
		boolean silentMode=false;
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
				} else if (args[i].equals("--validate")) {
					program = PROGRAM_VALIDATE;
				} else if (args[i].equals("--print-table")) {
					program = PROGRAM_PRINTTABLE;
				}
				else if (args[i].equals("--outfile")) {
					outputFilename = args[i + 1];
					i++;
				}else if (args[i].equals("--silent"))
					silentMode=true;
			}
		}
		init_log(Level.toLevel(logLevel),silentMode);
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
				switch (program) {
				case PROGRAM_DEFAULT:
					for(float weightfac:alg_factors)
					{
						TrendAndLigaTableBasedTippAlgorithm.setFactor(weightfac);
						for(float diftowin:alg_diffstowin)
						{
							TrendBasedTippAlgorithm.setMeanDiffToWin(diftowin);
							for(int diffordraw:alg_difffordraw)
							{
								LigaTableBasedTippAlgorithm.setDiffForDraw(diffordraw);
								TippValidationResults r = robot.validate();
								out(String.format("%2.2f %2.2f %d\t%d/%d",weightfac,diftowin,diffordraw,r.getPoints(),r.getMaxPoints()));
							}				
						}
					}
					break;
				case PROGRAM_VALIDATE:
					TippValidationResults r = robot.validate();
					out(r.printResults());
					break;
				case PROGRAM_PRINTTABLE:
					out(robot.printTable());
					break;
				default:
					break;
				}
			} else {
				LOG.warn("ligaid " + ligaId + " not found in db");
			}
		}
		System.exit(0);

	}

	private static void out(String msg) {
		if (outputFilename == null)
			System.out.println(msg);
		else {
			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(new File(outputFilename)));
				bw.write(msg);
				bw.flush();
				bw.close();
			} catch (IOException e) {
				LOG.warn(e.getMessage());
			}
		}
	}

	private static void error(Exception e) {
		LOG.error(e.getMessage());
		System.exit(1);
	}

}
