package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;

public class TeamCollection extends ArrayList<Team> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3874852597344448570L;

	public Team getById(int id) {
		for (Team t : this) {
			if (t.getId() == id)
				return t;
		}
		return null;
	}

	public Team find(String home) {
		float f=0,h;
		Team x=null;
		for(Team t:this)
		{
			h=Helper.correlate(home, t.getTitle());
			if(h>f)
			{
				f=h;
				x=t;
				if(f==1.0f)
					break;
			}
		}
		return f>0.6?x:null;
	}

}
