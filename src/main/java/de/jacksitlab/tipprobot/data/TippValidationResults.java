package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;

public class TippValidationResults extends ArrayList<TippValidationResult>{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 253027522466248892L;

	public String printResults() {
		int pts=0;
		StringBuilder sb = new StringBuilder();
		for(TippValidationResult r: this) {
			sb.append(r.printResults());
			pts+=r.getPoints();
		}
		sb.append("resulting points: "+pts);
		return sb.toString();
	}

	public int getPoints() {
		int pts=0;
		for(TippValidationResult r:this)
			pts+=r.getPoints();
		return pts;
	}

	public int getMaxPoints() {
		int pts=0;
		for(TippValidationResult r:this)
			pts+=r.getMaxPoints();
		return pts;
	}
}
