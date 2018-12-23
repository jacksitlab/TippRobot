package de.jacksitlab.tipprobot;

import java.io.IOException;

import org.json.JSONException;

import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.database.DatabaseConfig;

public class Program {

	public static void main(String[] args) {

		int ligaId = 11;
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
		System.out.println("Error: " + e.getMessage());
		System.exit(1);
	}

}
