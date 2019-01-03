package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;

public class MatchTippCollection extends ArrayList<MatchTipp>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4154090931415723670L;

	public int getPoints() {
		int pts=0;
		for(MatchTipp t :this)
		{
			pts+=t.getPoints();
		}
		return pts;
	}


}
