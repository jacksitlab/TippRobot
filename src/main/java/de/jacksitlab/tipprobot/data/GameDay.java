package de.jacksitlab.tipprobot.data;

import java.util.Date;

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

	public Date getStartDate()
	{
		Date d=null;
		for(Match m:this.matches)
		{
			if(d==null)
				d=m.getDate();
			else if(m.getDate().before(d))
				d=m.getDate();
		}
		return d;
	}
	public Date getEndDate()
	{
		Date d=null;
		for(Match m:this.matches)
		{
			if(d==null)
				d=m.getDate();
			else if(m.getDate().after(d))
				d=m.getDate();
		}
		return d;
	}
	
}
