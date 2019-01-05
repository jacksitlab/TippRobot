package de.jacksitlab.tipprobot.data;

public class TippResult {

	public int corrects;
	public int directions;
	public int wrongs;
	public int games;
	
	public TippResult(int c,int d,int w,int g)
	{
		this.games =g;
		this.corrects = c;
		this.directions = d;
		this.wrongs = w;
	}

	public TippResult() {
		this(0,0,0,0);
	}

	public int getPoints() {
		return 3*this.corrects+this.directions;
	}

	@Override
	public String toString() {
		return String.format("%d(%2.2f%%) | %d(%2.2f%%) | %d(%2.2f%%) => %d",
				this.corrects,(float)this.corrects*100.0f/(float)this.games,
				this.directions,(float)this.directions*100.0f/(float)this.games,
				this.wrongs,(float)this.wrongs*100.0f/(float)this.games,
				this.getPoints());
	}

	public void add(TippResult r) {
		this.corrects+=r.corrects;
		this.directions +=r.directions;
		this.wrongs+=r.wrongs;
		this.games+=r.games;
	}
}
