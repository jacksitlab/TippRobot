package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;

public class GameDayCollection extends ArrayList<GameDay> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768347145761276702L;

	public MatchCollection getGames(int gameday) {
		for(GameDay gd :this)
		{
			if(gd.gameDay == gameday)
				return gd.getMatches();
		}
		return null;
	}

}
