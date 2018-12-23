package de.jacksitlab.tipprobot;

import de.jacksitlab.tipprobot.data.MatchTippCollection;
import de.jacksitlab.tipprobot.database.DatabaseConfig;

public class Program {

	public static void main(String[] args) {
		
		int ligaId = 11;
		DatabaseConfig dbConfig = DatabaseConfig.getInstance();
		TippRobot robot= new TippRobot(ligaId);
		robot.loadLigaGames(dbConfig);
		MatchTippCollection tipps=robot.getTipps();
		robot.pushTipps(dbConfig,tipps);
		System.out.println(tipps.toString());
	}
	
}
