package de.jacksitlab.tipprobot.data;

public class GameDay {

	public final int gameDay;
	private final MatchCollection matches;
	public MatchCollection getMatches()
	{return this.matches;}
	
	public GameDay(int d)
	{
		this.gameDay = d;
		this.matches = new MatchCollection();
	}
	public void addMatch(Match m)
	{
		this.matches.add(m);
	}

}
