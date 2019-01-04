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
		sb.append("resulting points: "+pts+"/"+this.getMaxPoints()+"\n");
		int c=this.getCorrects();
		int d=this.getDirections();
		int s=this.getNumMatches();
		int f=s-d-c;
		sb.append(String.format("statistic: \n"));
		sb.append(String.format("\tgames: %d \n",s));
		sb.append(String.format("\tcor  : %d (%2.2f%%)\n",c,(float)c*100.0f/(float)s));
		sb.append(String.format("\tdir  : %d (%2.2f%%)\n",d,(float)d*100.0f/(float)s));
		sb.append(String.format("\twrg  : %d (%2.2f%%)\n",f,(float)f*100.0f/(float)s));
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
	public int getCorrects() {
		int pts=0;
		for(TippValidationResult r:this)
			pts+=r.getCorrects();
		return pts;
	}
	public int getDirections() {
		int pts=0;
		for(TippValidationResult r:this)
			pts+=r.getDirections();
		return pts;
	}
	public int getNumMatches() {
		int pts=0;
		for(TippValidationResult r:this)
			pts+=r.getNumMatches();
		return pts;
	}

	public TippResult getTippResult() {
		TippResult pts=new TippResult();
		for(TippValidationResult r:this)
			pts.add(r.getTippResult());
		return pts;
	}
	
	
}
