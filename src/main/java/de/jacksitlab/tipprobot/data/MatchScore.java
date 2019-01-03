package de.jacksitlab.tipprobot.data;

/**
 * value=[-1;1] -1=guest team wins 1=home team wins 0=draw
 * 
 * @author jack
 *
 */
public class MatchScore {

	private static final int VAR1 = 0;
	private static final int VAR2 = 1;
	private static final int VAR3 = 2;
	public static int VAR = VAR3;
	private static float EPS_FOR_DRAW = 0.2f;
	public static void setEpsForDraw(float e) {
		EPS_FOR_DRAW = e;
	}
	private float value;

	public float getValue() {
		return this.value;
	}

	public void addScore(float v,float weight) {
		if(weight>1)
			weight=1;
		if(weight<0)
			weight=0;
		this.value = (weight*v) + ((1-weight)*this.value) ;
	}

	public MatchScore(float v) {
		if(v>1)
			v=1;
		else if(v<-1)
			v=-1;
		this.value = v;
	}

	public MatchTipp getTipp(Match match, TeamStats homeTeam, TeamStats guestTeam) {
		int homePts = 0;
		int guestPts = 0;
		if (Math.abs(this.value) <= EPS_FOR_DRAW) {
			homePts = guestPts = (homeTeam.getGoalsMean() + homeTeam.getGoalsAgainstMean() + guestTeam.getGoalsMean()
					+ guestTeam.getGoalsAgainstMean()) / 4;
		} else {
			if(VAR==VAR1)
			{
			homePts = (int) (this.value * (float) (homeTeam.getGoalsMean() + guestTeam.getGoalsAgainstMean()) / 2.0f);
			guestPts = (int) (this.value * (float) (guestTeam.getGoalsMean() + homeTeam.getGoalsAgainstMean()) / 2.0f);
			}
			else if(VAR ==VAR2)
			{
				homePts=(int) (this.value*homeTeam.getGoalsMean());
				guestPts = (int) (this.value*homeTeam.getGoalsAgainstMean());
			}
			else if(VAR ==VAR3)
			{
				homePts=(int) (homeTeam.getGoalsMean());
				guestPts = (int) (homeTeam.getGoalsAgainstMean());
			}
			
			if (this.value > 0) {
				if(guestPts>=homePts)
					guestPts=homePts-1;
				while(guestPts<0)
				{homePts++;guestPts++;}
			} else {
				if(homePts>=guestPts)
					homePts=guestPts-1;
				while(homePts<0)
				{homePts++;guestPts++;}
			}
		}
		return new MatchTipp(match, homePts, guestPts,this);
	}

}
