package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;

public class GameDayCollection extends ArrayList<GameDay> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768347145761276702L;

	public boolean contains(int gameday) {
		for (GameDay gd : this) {
			if (gd.gameDay == gameday)
				return true;
		}
		return false;
	}

	public MatchCollection getGames(int gameday) {
		for (GameDay gd : this) {
			if (gd.gameDay == gameday)
				return gd.getMatches();
		}
		return null;
	}

	public MatchCollection getLastGames(int gameday, Team homeTeam) {
		// TODO Auto-generated method stub
		return null;
	}

	public GameDay getByGameDay(int gd) {
		for (GameDay d : this) {
			if (d.gameDay == gd)
				return d;
		}
		return null;
	}

	public void addMatch(int gameDay, Match m) {
		GameDay x=this.getByGameDay(gameDay);
		if(x!=null)
			x.addMatch(m);
	}

}
