package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;
import java.util.Comparator;

public class GameDayCollection extends ArrayList<GameDay> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768347145761276702L;

	@Override
	public boolean add(GameDay e) {
		boolean b=super.add(e);
		this.sort(GameDayComparator.getInstance());
		return b;
	}
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
	private static class GameDayComparator implements Comparator<GameDay>{

		private static GameDayComparator mObj;
		public static GameDayComparator getInstance() {
			if(mObj==null)
				mObj=new GameDayComparator();
			return mObj;
		}
		@Override
		public int compare(GameDay o1, GameDay o2) {
			return o1.gameDay-o2.gameDay;
		}
		
	}
}
