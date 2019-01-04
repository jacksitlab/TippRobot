package de.jacksitlab.tipprobot.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class AlgorithmParameters {

	public static final int[] tippCalcParams= {MatchScore.VAR1,MatchScore.VAR2,MatchScore.VAR3,MatchScore.VAR4};
	public static final float[] tippCalcEpsToDraw = {0.1f,0.15f,0.2f,0.25f,0.3f,0.35f,0.4f,0.45f,0.5f};
	private static final float[] algCombinedWeightFactor= {0.4f,0.5f,0.6f,0.7f,0.8f};
	private static final float[] algTrendDiffToWin= {0.6f,0.7f,0.8f,0.9f,1.0f,1.1f,1.2f};
	private static final int[] algTableDiffForDraw= {2,3,4,5,6,7,8};
	private final int resultCalculationParam;
	private final float combinedWeightFactor;
	private final float trendDiffToWin;
	private final int tableDiffForDraw;
	private final float resultCalcEpsToDraw;
	private TippResult result;
	
	public TippResult getResult() {
		return result;
	}
	public void setResult(TippResult result) {
		this.result = result;
	}
	public int getResultCalculationParam() {
		return resultCalculationParam;
	}
	public float getCombinedWeightFactor() {
		return combinedWeightFactor;
	}
	public float getTrendDiffToWin() {
		return trendDiffToWin;
	}
	public int getTableDiffForDraw() {
		return tableDiffForDraw;
	}
	@Override
	public String toString() {
		return String.format("%d %2.2f %2.2f %2.2f %d: %s",
				this.resultCalculationParam,
				this.resultCalcEpsToDraw,
				this.combinedWeightFactor,
				this.trendDiffToWin,
				this.tableDiffForDraw,
				this.result.toString());
	}
	public AlgorithmParameters(int p, float wf, float dtw, int dfd,float e) {
		this.resultCalculationParam=p;
		this.combinedWeightFactor = wf;
		this.trendDiffToWin = dtw;
		this.tableDiffForDraw = dfd;
		this.resultCalcEpsToDraw = e;
	}


	public static List<AlgorithmParameters> combine()
	{
		List<AlgorithmParameters> list = new ArrayList<AlgorithmParameters>();
		for(int p:tippCalcParams)
		{
			for(float wf:algCombinedWeightFactor)
			{
				for(float dtw:algTrendDiffToWin)
				{
					for(int dfd:algTableDiffForDraw)
					{
						for(float e:tippCalcEpsToDraw)
						{
							list.add(new AlgorithmParameters(p,wf,dtw,dfd,e));							
						}
					}
				}
			}
		}
		return list;
	}
	public float getResultCalcEpsToDraw() {
		return resultCalcEpsToDraw;
	}
	public static class AlgorithmParametersComparator implements Comparator<AlgorithmParameters>{

		@Override
		public int compare(AlgorithmParameters arg0, AlgorithmParameters arg1) {
			return arg0.result.getPoints()<arg1.result.getPoints()?1:arg0.result.getPoints()>arg1.result.getPoints()?-1:0;
		}
		
	}
	
}
